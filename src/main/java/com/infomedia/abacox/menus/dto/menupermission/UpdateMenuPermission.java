package com.infomedia.abacox.menus.dto.menupermission;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.openapitools.jackson.nullable.JsonNullable;

/**
 * DTO for {@link com.infomedia.abacox.menus.entity.MenuPermission}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateMenuPermission {
    @NotBlank
    @Size(max = 30)
    @Schema(description = "Role name", example = "admin")
    private JsonNullable<String> rolename = JsonNullable.undefined();
    @NotNull
    @Schema(description = "Menu id", example = "1")
    private JsonNullable<Long> menuId = JsonNullable.undefined();
    @NotNull
    @Schema(description = "Create permission", example = "true")
    private JsonNullable<Boolean> canCreate = JsonNullable.undefined();
    @NotNull
    @Schema(description = "Read permission", example = "true")
    private JsonNullable<Boolean> canRead = JsonNullable.undefined();
    @NotNull
    @Schema(description = "Update permission", example = "true")
    private JsonNullable<Boolean> canUpdate = JsonNullable.undefined();
    @NotNull
    @Schema(description = "Delete permission", example = "true")
    private JsonNullable<Boolean> canDelete = JsonNullable.undefined();
}