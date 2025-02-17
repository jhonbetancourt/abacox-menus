package com.infomedia.abacox.menus.dto.menushortcut;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for {@link com.infomedia.abacox.menus.entity.MenuShortcut}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateMenuShortcut {
    @NotNull
    @Schema(description = "ID of the menu permission", example = "1")
    private Long menuPermissionId;
    @NotNull
    @Min(0)
    @Max(99)
    @Schema(description = "Position of the shortcut", example = "0")
    private Integer position;
    @NotBlank
    @Schema(description = "Username of the user who has the shortcut", example = "admin")
    private String username;
}