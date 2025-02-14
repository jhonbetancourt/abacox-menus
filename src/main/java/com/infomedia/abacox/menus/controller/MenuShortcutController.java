package com.infomedia.abacox.menus.controller;

import com.infomedia.abacox.menus.component.modeltools.ModelConverter;
import com.infomedia.abacox.menus.dto.menu.CreateMenu;
import com.infomedia.abacox.menus.dto.menu.MenuDto;
import com.infomedia.abacox.menus.dto.menu.UpdateMenu;
import com.infomedia.abacox.menus.dto.menushortcut.CreateMenuShortcut;
import com.infomedia.abacox.menus.dto.menushortcut.MenuShortcutDto;
import com.infomedia.abacox.menus.dto.menushortcut.SwapMenuShortcut;
import com.infomedia.abacox.menus.dto.superclass.ActivationDto;
import com.infomedia.abacox.menus.entity.Menu;
import com.infomedia.abacox.menus.service.MenuService;
import com.infomedia.abacox.menus.service.MenuShortcutService;
import com.turkraft.springfilter.boot.Filter;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
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
@Tag(name = "Menu Shortcut", description = "Menu Shortcut Controller")
@SecurityRequirements({
        @SecurityRequirement(name = "JWT_Token"),
        @SecurityRequirement(name = "Username")
})
@RequestMapping("/api/menuShortcut")
public class MenuShortcutController {

    private final MenuShortcutService menuShortcutService;
    private final ModelConverter modelConverter;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public MenuShortcutDto create(@Valid @RequestBody CreateMenuShortcut cDto) {
        return modelConverter.map(menuShortcutService.create(cDto), MenuShortcutDto.class);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MenuShortcutDto> findByUser(@RequestParam String username) {
        return modelConverter.mapList(menuShortcutService.findByUsername(username), MenuShortcutDto.class);
    }

    @DeleteMapping(value = "{id}")
    public void deleteById(@PathVariable("id") Long id) {
        menuShortcutService.deleteById(id);
    }

    @PatchMapping(value = "swap", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MenuShortcutDto> swap(@Valid @RequestBody SwapMenuShortcut uDto) {
        menuShortcutService.swapShortcutsByOrder(uDto.getUsername(), uDto.getOrder1(), uDto.getOrder2());
        return modelConverter.mapList(menuShortcutService.findByUsername(uDto.getUsername()), MenuShortcutDto.class);
    }
}
