package com.infomedia.abacox.menus.dto.menu;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.openapitools.jackson.nullable.JsonNullable;

/**
 * DTO for {@link com.infomedia.abacox.menus.entity.Menu}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateMenu {
    @NotBlank
    @Size(max = 100)
    @Schema(description = "Name of the menu", example = "menu_name")
    private JsonNullable<String> name = JsonNullable.undefined();
    @Size(max = 255)
    @Schema(description = "Path of the menu", example = "/path")
    private JsonNullable<String> path = JsonNullable.undefined();
    @NotBlank
    @Size(max = 100)
    @Schema(description = "Display name of the menu", example = "Menu Name")
    private JsonNullable<String> displayName = JsonNullable.undefined();
    @Schema(description = "Whether the menu requires permissions", example = "true")
    private JsonNullable<Boolean> requiresPermissions = JsonNullable.undefined();
    @NotNull
    @Schema(description = "Whether the menu is a CRUD menu", example = "true")
    private JsonNullable<Boolean> crud = JsonNullable.undefined();
    @Schema(description = "ID of the parent menu", example = "1")
    private JsonNullable<Long> parentId = JsonNullable.undefined();
}