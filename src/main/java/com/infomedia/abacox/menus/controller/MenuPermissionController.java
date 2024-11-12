package com.infomedia.abacox.menus.controller;

import com.infomedia.abacox.menus.component.modeltools.ModelConverter;
import com.infomedia.abacox.menus.dto.menupermission.CreateMenuPermission;
import com.infomedia.abacox.menus.dto.menupermission.MenuPermissionDto;
import com.infomedia.abacox.menus.dto.menupermission.RoleMenuPermission;
import com.infomedia.abacox.menus.dto.menupermission.UpdateMenuPermission;
import com.infomedia.abacox.menus.dto.superclass.ActivationDto;
import com.infomedia.abacox.menus.entity.MenuPermission;
import com.infomedia.abacox.menus.service.MenuPermissionService;
import com.turkraft.springfilter.boot.Filter;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Tag(name = "Menu Permission", description = "Menu Permission controller")
@SecurityRequirement(name = "JWT_Token")
@RequestMapping("/api/menuPermission")
public class MenuPermissionController {

    private final MenuPermissionService menuPermissionService;
    private final ModelConverter modelConverter;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public MenuPermissionDto create(@Valid @RequestBody CreateMenuPermission cDto) {
        return modelConverter.map(menuPermissionService.create(cDto), MenuPermissionDto.class);
    }

    @PatchMapping(value = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public MenuPermissionDto update(@PathVariable("id") Long id, @Valid @RequestBody UpdateMenuPermission uDto) {
        return modelConverter.map(menuPermissionService.update(id, uDto), MenuPermissionDto.class);
    }

    @PatchMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void delete(@PathVariable("id") Long id) {
        menuPermissionService.deleteById(id);
    }

    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    private MenuPermissionDto get(@PathVariable("id") Long id) {
        return modelConverter.map(menuPermissionService.get(id), MenuPermissionDto.class);
    }

    @GetMapping(value = "role", produces = MediaType.APPLICATION_JSON_VALUE)
    private List<RoleMenuPermission> role(@RequestParam String rolename, @RequestParam(required = false) Long menuId) {
        return menuPermissionService.getRoleMenuPermissions(rolename, menuId);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<MenuPermissionDto> find(@Parameter(hidden = true) @Filter Specification<MenuPermission> spec
            , @Parameter(hidden = true) Pageable pageable
            , @RequestParam(required = false) String filter, @RequestParam(required = false) Integer page
            , @RequestParam(required = false) Integer size, @RequestParam(required = false) String sort) {
        return modelConverter.mapPage(menuPermissionService.find(spec, pageable), MenuPermissionDto.class);
    }
}
