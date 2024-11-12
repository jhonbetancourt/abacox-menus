package com.infomedia.abacox.menus.service;

import com.infomedia.abacox.menus.component.modeltools.ModelConverter;
import com.infomedia.abacox.menus.dto.menupermission.CreateMenuPermission;
import com.infomedia.abacox.menus.dto.menupermission.PermissionDto;
import com.infomedia.abacox.menus.dto.menupermission.RoleMenuPermission;
import com.infomedia.abacox.menus.dto.menupermission.UpdateMenuPermission;
import com.infomedia.abacox.menus.entity.Menu;
import com.infomedia.abacox.menus.entity.MenuPermission;
import com.infomedia.abacox.menus.exception.ResourceAlreadyExistsException;
import com.infomedia.abacox.menus.repository.MenuPermissionRepository;
import com.infomedia.abacox.menus.service.common.CrudService;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class MenuPermissionService extends CrudService<MenuPermission, Long, MenuPermissionRepository> {
    private final MenuService menuService;
    private final ModelConverter modelConverter;
    public MenuPermissionService(MenuPermissionRepository repository, MenuService menuService, ModelConverter modelConverter) {
        super(repository);
        this.menuService = menuService;
        this.modelConverter = modelConverter;
    }


    public MenuPermission createDtoToEntity(CreateMenuPermission cDto) {
        return MenuPermission.builder()
                .rolename(cDto.getRolename())
                .menu(menuService.get(cDto.getMenuId()))
                .canCreate(cDto.getCanCreate())
                .canRead(cDto.getCanRead())
                .canUpdate(cDto.getCanUpdate())
                .canDelete(cDto.getCanDelete())
                .build();
    }

    public MenuPermission updateDtoToEntity(MenuPermission entity, UpdateMenuPermission uDto) {
        uDto.getRolename().ifPresent(entity::setRolename);
        uDto.getMenuId().ifPresent(id -> entity.setMenu(menuService.get(id)));
        uDto.getCanCreate().ifPresent(entity::setCanCreate);
        uDto.getCanRead().ifPresent(entity::setCanRead);
        uDto.getCanUpdate().ifPresent(entity::setCanUpdate);
        uDto.getCanDelete().ifPresent(entity::setCanDelete);
        return entity;
    }

    public void validateExists(MenuPermission entity) {
        if (entity.getId()==null) {
            if(getRepository().existsByRolenameAndMenu_Id(entity.getRolename(), entity.getMenu().getId())) {
                throw new ResourceAlreadyExistsException(MenuPermission.class);
            }
        }else{
            if(getRepository().existsByRolenameAndMenu_IdAndIdNot(entity.getRolename(), entity.getMenu().getId(), entity.getId())) {
                throw new ResourceAlreadyExistsException(MenuPermission.class);
            }
        }
    }

    public void validateRequirePermissions(MenuPermission entity) {
        if(!entity.getMenu().isRequiresPermissions()){
            entity.setCanCreate(true);
            entity.setCanRead(true);
            entity.setCanUpdate(true);
            entity.setCanDelete(true);
        }
    }

    public MenuPermission create(CreateMenuPermission cDto) {
        MenuPermission menuPermission = createDtoToEntity(cDto);
        validateRequirePermissions(menuPermission);
        validateExists(menuPermission);
        return save(menuPermission);
    }

    public MenuPermission update(Long id, UpdateMenuPermission uDto) {
        MenuPermission menuPermission = updateDtoToEntity(get(id), uDto);
        validateRequirePermissions(menuPermission);
        validateExists(menuPermission);
        return save(menuPermission);
    }

    public List<RoleMenuPermission> getRoleMenuPermissions(String rolename, Long menuId){
        if(menuId==null) {
            return buildRoleMenuPermissions(rolename, new LinkedHashSet<>(menuService.getRootMenus()));
        }
        return buildRoleMenuPermissions(rolename, Set.of(menuService.getActive(menuId)));
    }

    private List<RoleMenuPermission> buildRoleMenuPermissions(String rolename, Set<Menu> menus){
        if(menus==null){
            return List.of();
        }
        List<RoleMenuPermission> roleMenuPermissions = new ArrayList<>();
        for (Menu menu : menus) {
            MenuPermission menuPermission = getRepository().findByRolenameAndMenu(rolename, menu).orElse(null);
            PermissionDto permission = menuPermission==null?null:modelConverter.map(menuPermission, PermissionDto.class);
            RoleMenuPermission rmp = RoleMenuPermission.builder()
                    .id(menu.getId())
                    .name(menu.getName())
                    .path(menu.getPath())
                    .displayName(menu.getDisplayName())
                    .permission(permission)
                    .submenus(new LinkedHashSet<>(buildRoleMenuPermissions(rolename, menu.getSubmenus())))
                    .build();

            if(menu.isRequiresPermissions()){
                if(permission!=null){
                    roleMenuPermissions.add(rmp);
                }else if(!rmp.getSubmenus().isEmpty()){
                    rmp.setPermission(new PermissionDto(null, false, false, false, false));
                    roleMenuPermissions.add(rmp);
                }
            }else{
                roleMenuPermissions.add(rmp);
            }
        }
        roleMenuPermissions.sort(Comparator.comparing(RoleMenuPermission::getDisplayName));
        return roleMenuPermissions;
     }
}
