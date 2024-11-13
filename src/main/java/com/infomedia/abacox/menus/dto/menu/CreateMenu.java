package com.infomedia.abacox.menus.dto.menu;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for {@link com.infomedia.abacox.menus.entity.Menu}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateMenu {
    @NotBlank
    @Size(max = 100)
    @Schema(description = "Name of the menu", example = "menu_name")
    private String name;
    @Size(max = 255)
    @Schema(description = "Path of the menu", example = "/path")
    private String path;
    @NotBlank
    @Size(max = 100)
    @Schema(description = "Display name of the menu", example = "Menu Name")
    private String displayName;
    @NotNull
    @Schema(description = "Whether the menu requires permissions", example = "true")
    private Boolean requiresPermissions;
    @NotNull
    @Schema(description = "Whether the menu is a CRUD menu", example = "true")
    private Boolean crud;
    @Schema(description = "ID of the parent menu", example = "1")
    private Long parentId;
}