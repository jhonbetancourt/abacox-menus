package com.infomedia.abacox.menus.service;

import com.infomedia.abacox.menus.dto.menu.CreateMenu;
import com.infomedia.abacox.menus.dto.menu.UpdateMenu;
import com.infomedia.abacox.menus.entity.Menu;
import com.infomedia.abacox.menus.repository.MenuRepository;
import com.infomedia.abacox.menus.service.common.CrudService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuService extends CrudService<Menu, Long, MenuRepository> {


    public MenuService(MenuRepository repository) {
        super(repository);
    }

    public Menu createDtoToEntity(CreateMenu cDto) {
        return Menu.builder()
                .name(cDto.getName())
                .path(cDto.getPath())
                .displayName(cDto.getDisplayName())
                .parent(find(cDto.getParentId()).orElse(null))
                .requiresPermissions(cDto.getRequiresPermissions())
                .build();
    }

    public Menu updateDtoToEntity(Menu entity, UpdateMenu uDto) {
        uDto.getName().ifPresent(entity::setName);
        uDto.getPath().ifPresent(entity::setPath);
        uDto.getDisplayName().ifPresent(entity::setDisplayName);
        uDto.getParentId().ifPresent(id -> entity.setParent(find(id).orElse(null)));
        uDto.getRequiresPermissions().ifPresent(entity::setRequiresPermissions);
        return entity;
    }

    public Menu create(CreateMenu cDto) {
        return save(createDtoToEntity(cDto));
    }

    public Menu update(Long id, UpdateMenu uDto) {
        return save(updateDtoToEntity(get(id), uDto));
    }

    public List<Menu> getRootMenus() {
        return getRepository().findByActiveAndParentId(true, null);
    }
}
