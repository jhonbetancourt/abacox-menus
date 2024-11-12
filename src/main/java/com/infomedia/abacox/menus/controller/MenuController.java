package com.infomedia.abacox.menus.controller;

import com.infomedia.abacox.menus.component.modeltools.ModelConverter;
import com.infomedia.abacox.menus.dto.menu.CreateMenu;
import com.infomedia.abacox.menus.dto.menu.MenuDto;
import com.infomedia.abacox.menus.dto.menu.UpdateMenu;
import com.infomedia.abacox.menus.dto.superclass.ActivationDto;
import com.infomedia.abacox.menus.entity.Menu;
import com.infomedia.abacox.menus.service.MenuService;
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

@RequiredArgsConstructor
@RestController
@Tag(name = "Menu", description = "Menu Controller")
@SecurityRequirement(name = "JWT_Token")
@RequestMapping("/api/menu")
public class MenuController {

    private final MenuService menuService;
    private final ModelConverter modelConverter;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public MenuDto create(@Valid @RequestBody CreateMenu cDto) {
        return modelConverter.map(menuService.create(cDto), MenuDto.class);
    }

    @PatchMapping(value = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public MenuDto update(@PathVariable("id") Long id, @Valid @RequestBody UpdateMenu uDto) {
        return modelConverter.map(menuService.update(id, uDto), MenuDto.class);
    }

    @PatchMapping(value = "/status/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public MenuDto changeStatus(@PathVariable("id") Long id, @Valid @RequestBody ActivationDto activationDto) {
        return modelConverter.map(menuService.changeActivation(id, activationDto), MenuDto.class);
    }

    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    private MenuDto get(@PathVariable("id") Long id) {
        return modelConverter.map(menuService.get(id), MenuDto.class);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<MenuDto> find(@Parameter(hidden = true) @Filter Specification<Menu> spec
            , @Parameter(hidden = true) Pageable pageable
            , @RequestParam(required = false) String filter, @RequestParam(required = false) Integer page
            , @RequestParam(required = false) Integer size, @RequestParam(required = false) String sort) {
        return modelConverter.mapPage(menuService.find(spec, pageable), MenuDto.class);
    }
}
