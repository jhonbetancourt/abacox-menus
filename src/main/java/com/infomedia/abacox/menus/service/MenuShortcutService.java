package com.infomedia.abacox.menus.service;

import com.infomedia.abacox.menus.component.filtertools.SpecificationFilter;
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
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MenuShortcutService extends CrudService<MenuShortcut, Long, MenuShortcutRepository> {

    private final MenuPermissionService menuPermissionService;
    private final UserService userService;

    public MenuShortcutService(MenuShortcutRepository repository, MenuPermissionService menuPermissionService, UserService userService) {
        super(repository);
        this.menuPermissionService = menuPermissionService;
        this.userService = userService;
    }

    @Transactional
    public MenuShortcut create(CreateMenuShortcut cDto) {
        MenuPermission menuPermission = menuPermissionService.get(cDto.getMenuPermissionId());
        MenuShortcut menuShortcut = MenuShortcut.builder()
                .menuPermission(menuPermission)
                .position(cDto.getPosition())
                .username(cDto.getUsername())
                .style(cDto.getStyle())
                .shortName(cDto.getShortName())
                .initials(cDto.getInitials())
                .build();
        validateUser(cDto.getUsername(), menuPermission);
        validatePosition(cDto.getUsername(), cDto.getPosition());
        validateDuplicateShortcut(cDto.getUsername(), menuPermission);
        reorderExistingShortcuts(cDto.getUsername(), cDto.getPosition());
        return save(menuShortcut);
    }

    private void validateDuplicateShortcut(String username, MenuPermission menuPermission) {
        if (getRepository().existsByUsernameAndMenuPermission_Id(username, menuPermission.getId())) {
            throw new ValidationException("Shortcut already exists for this menu");
        }
    }

    private void validateUser(String username, MenuPermission menuPermission) {
        UserDto user = userService.findUser("username:'" + username + "'");
        if (user == null) {
            throw new ValidationException("User does not exist");
        }
        if (!menuPermission.getRolename().equals(user.getRole().getRolename())) {
            throw new ValidationException("User does not have the required role");
        }
    }

    private void validatePosition(String username, Integer position) {
        long shortcutCount = getRepository().countByUsername(username);
        if(shortcutCount >= 100) {
            throw new ValidationException("User has reached the maximum number of shortcuts");
        }

        if(position > shortcutCount || position < 0) {
            throw new ValidationException("Position is invalid");
        }
    }

    private void reorderExistingShortcuts(String username, Integer newPosition) {
        List<MenuShortcut> existingShortcuts = getRepository().findByUsernameOrderByPositionAsc(username);

        for (MenuShortcut shortcut : existingShortcuts) {
            if (shortcut.getPosition() >= newPosition) {
                shortcut.setPosition(shortcut.getPosition() + 1);
                save(shortcut);
            }
        }
    }

    @Transactional
    public void swapShortcutsByPosition(String username, Integer position1, Integer position2) {
        MenuShortcut shortcut1 = getRepository().findByUsernameAndPosition(username, position1)
                .orElseThrow(() -> new ResourceNotFoundException(MenuShortcut.class, "position " + position1));
        MenuShortcut shortcut2 = getRepository().findByUsernameAndPosition(username, position2)
                .orElseThrow(() -> new ResourceNotFoundException(MenuShortcut.class, "position " + position2));

        // Swap the orders
        shortcut1.setPosition(position2);
        shortcut2.setPosition(position1);

        // Save both shortcuts
        save(shortcut1);
        save(shortcut2);
    }

    @Transactional
    public List<MenuShortcut> findByUsername(String username) {
        removeInvalidShortcuts(username);
        Specification<MenuShortcut> spec = SpecificationFilter.build("username:'" + username + "' and menuPermission.menu.active:'true'");
        return getRepository().findAll(spec);
    }

    private void removeInvalidShortcuts(String username) {
        UserDto user = userService.findUser("username:'" + username + "'");
        List<MenuShortcut> existingShortcuts = getRepository().findByUsernameOrderByPositionAsc(username);
        List<MenuShortcut> toRemove = existingShortcuts.stream()
                .filter(shortcut -> !shortcut.getMenuPermission().getRolename().equals(user.getRole().getRolename()))
                .toList();
        deleteAll(toRemove);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        MenuShortcut shortcut = get(id);
        super.deleteById(shortcut.getId());
        reorderAfterDeletion(shortcut.getUsername());
    }

    private void reorderAfterDeletion(String username) {
        List<MenuShortcut> remainingShortcuts = getRepository().findByUsernameOrderByPositionAsc(username);
        for (int i = 0; i < remainingShortcuts.size(); i++) {
            MenuShortcut shortcut = remainingShortcuts.get(i);
            if (shortcut.getPosition() != i) {
                shortcut.setPosition(i);
                save(shortcut);
            }
        }
    }
}