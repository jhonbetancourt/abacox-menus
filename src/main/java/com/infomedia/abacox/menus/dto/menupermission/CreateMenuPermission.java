package com.infomedia.abacox.menus.dto.menupermission;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for {@link com.infomedia.abacox.menus.entity.MenuPermission}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateMenuPermission {
    @NotBlank
    @Size(max = 30)
    @Schema(description = "Role name", example = "admin")
    private String rolename;
    @NotNull
    @Schema(description = "Menu id", example = "1")
    private Long menuId;
    @NotNull
    @Schema(description = "Create permission", example = "true")
    private Boolean canCreate;
    @NotNull
    @Schema(description = "Read permission", example = "true")
    private Boolean canRead;
    @NotNull
    @Schema(description = "Update permission", example = "true")
    private Boolean canUpdate;
    @NotNull
    @Schema(description = "Delete permission", example = "true")
    private Boolean canDelete;
}