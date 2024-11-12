package com.infomedia.abacox.menus.dto.menupermission;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for {@link com.infomedia.abacox.menus.entity.MenuPermission}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PermissionDto {
    private Long id;
    private Boolean canCreate;
    private Boolean canRead;
    private Boolean canUpdate;
    private Boolean canDelete;
}