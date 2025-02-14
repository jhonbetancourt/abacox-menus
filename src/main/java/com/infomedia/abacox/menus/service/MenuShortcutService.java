package com.infomedia.abacox.menus.service;

import com.infomedia.abacox.menus.component.filtertools.SpecificationFilter;
import com.infomedia.abacox.menus.dto.applicationevent.MenuPermissionDeletedEvent;
import com.infomedia.abacox.menus.dto.menushortcut.CreateMenuShortcut;
import com.infomedia.abacox.menus.dto.user.UserDto;
import com.infomedia.abacox.menus.entity.MenuPermission;
import com.infomedia.abacox.menus.entity.MenuShortcut;
import com.infomedia.abacox.menus.exception.ResourceNotFoundException;
import com.infomedia.abacox.menus.repository.MenuShortcutRepository;
import com.infomedia.abacox.menus.service.common.CrudService;
import com.infomedia.abacox.menus.service.remote.UserService;
import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;
import org.springframework.context.event.EventListener;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuShortcutService extends CrudService<MenuShortcut, Long, MenuShortcutRepository> {

    private final MenuPermissionService menuPermissionService;
    private final UserService userService;
    private final SpecificationFilter specificationFilter;

    public MenuShortcutService(MenuShortcutRepository repository, MenuPermissionService menuPermissionService, UserService userService, SpecificationFilter specificationFilter) {
        super(repository);
        this.menuPermissionService = menuPermissionService;
        this.userService = userService;
        this.specificationFilter = specificationFilter;
    }

    @Transactional
    public MenuShortcut create(CreateMenuShortcut cDto) {
        MenuPermission menuPermission = menuPermissionService.get(cDto.getMenuPermissionId());
        MenuShortcut menuShortcut = MenuShortcut.builder()
                .menuPermission(menuPermission)
                .order(cDto.getOrder())
                .username(cDto.getUsername())
                .build();
        validateUser(cDto.getUsername(), menuPermission);
        validateOrder(cDto.getUsername(), cDto.getOrder());
        reorderExistingShortcuts(cDto.getUsername(), cDto.getOrder());
        return save(menuShortcut);
    }

    private void validateUser(String username, MenuPermission menuPermission) {
        UserDto user = userService.findUser(username);
        if (user == null) {
            throw new ValidationException("User does not exist");
        }
        if (!menuPermission.getRolename().equals(user.getRole().getName())) {
            throw new ValidationException("User does not have the required role");
        }
    }

    private void validateOrder(String username, Integer order) {
        long shortcutCount = getRepository().countByUsername(username);
        if(shortcutCount >= 100) {
            throw new ValidationException("User has reached the maximum number of shortcuts");
        }

        if(order > shortcutCount || order < 0) {
            throw new ValidationException("Order is invalid");
        }
    }

    private void reorderExistingShortcuts(String username, Integer newOrder) {
        List<MenuShortcut> existingShortcuts = getRepository().findByUsernameOrderByOrderAsc(username);

        for (MenuShortcut shortcut : existingShortcuts) {
            if (shortcut.getOrder() >= newOrder) {
                shortcut.setOrder(shortcut.getOrder() + 1);
                save(shortcut);
            }
        }
    }

    @Transactional
    public void swapShortcutsByOrder(String username, Integer order1, Integer order2) {
        MenuShortcut shortcut1 = getRepository().findByUsernameAndOrder(username, order1)
                .orElseThrow(() -> new ResourceNotFoundException(MenuShortcut.class, "order " + order1));
        MenuShortcut shortcut2 = getRepository().findByUsernameAndOrder(username, order2)
                .orElseThrow(() -> new ResourceNotFoundException(MenuShortcut.class, "order " + order2));

        // Swap the orders
        shortcut1.setOrder(order2);
        shortcut2.setOrder(order1);

        // Save both shortcuts
        save(shortcut1);
        save(shortcut2);
    }

    @Transactional
    public List<MenuShortcut> findByUsername(String username, Specification<MenuShortcut> specification) {
        removeInvalidShortcuts(username);
        Specification<MenuShortcut> userSpec = specificationFilter.build("'username':'" + username + "'");
        return getRepository().findAll(userSpec.and(specification));
    }

    private void removeInvalidShortcuts(String username) {
        UserDto user = userService.findUser(username);
        List<MenuShortcut> existingShortcuts = getRepository().findByUsernameOrderByOrderAsc(username);
        List<MenuShortcut> toRemove = existingShortcuts.stream()
                .filter(shortcut -> !shortcut.getMenuPermission().getRolename().equals(user.getRole().getName()))
                .toList();
        deleteAll(toRemove);
    }

    @EventListener
    @Transactional
    public void handleMenuPermissionDeleted(MenuPermissionDeletedEvent event) {
        List<MenuShortcut> shortcutsToRemove = getRepository().findByMenuPermission_Id(event.getMenuPermissionId());
        if (!shortcutsToRemove.isEmpty()) {
            List<String> affectedUsers = shortcutsToRemove.stream()
                    .map(MenuShortcut::getUsername)
                    .distinct()
                    .toList();

            deleteAll(shortcutsToRemove);

            for (String username : affectedUsers) {
                reorderAfterDeletion(username);
            }
        }
    }

    private void reorderAfterDeletion(String username) {
        List<MenuShortcut> remainingShortcuts = getRepository().findByUsernameOrderByOrderAsc(username);
        for (int i = 0; i < remainingShortcuts.size(); i++) {
            MenuShortcut shortcut = remainingShortcuts.get(i);
            if (shortcut.getOrder() != i) {
                shortcut.setOrder(i);
                save(shortcut);
            }
        }
    }
}
