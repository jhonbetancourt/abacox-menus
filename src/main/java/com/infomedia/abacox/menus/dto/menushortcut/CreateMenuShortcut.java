package com.infomedia.abacox.menus.dto.menushortcut;

import com.infomedia.abacox.menus.constants.Regexp;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
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

    @NotBlank
    @Size(max = 20)
    @Schema(description = "Style of the shortcut", example = "blue")
    private String style;

    @NotBlank
    @Size(max = 10)
    @Schema(description = "Short name of the shortcut", example = "User Mng")
    private String shortName;

    @NotBlank
    @Size(max = 3)
    @Schema(description = "Initials of the shortcut", example = "UM")
    @Pattern(regexp = Regexp.MAYUS_ONLY, message = Regexp.MSG_MAYUS_ONLY)
    private String initials;
}