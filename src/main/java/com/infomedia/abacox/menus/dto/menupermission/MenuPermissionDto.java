package com.infomedia.abacox.menus.dto.menupermission;

import com.infomedia.abacox.menus.dto.menu.Menu0Dto;
import com.infomedia.abacox.menus.dto.superclass.AuditedDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * DTO for {@link com.infomedia.abacox.menus.entity.MenuPermission}
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuPermissionDto extends AuditedDto {
    private Long id;
    private String rolename;
    private Menu0Dto menu;
    private Boolean canCreate;
    private Boolean canRead;
    private Boolean canUpdate;
    private Boolean canDelete;
}