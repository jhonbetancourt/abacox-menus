package com.infomedia.abacox.menus.dto.menushortcut;

import com.infomedia.abacox.menus.dto.menupermission.MenuPermissionDto;
import com.infomedia.abacox.menus.dto.superclass.AuditedDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * DTO for {@link com.infomedia.abacox.menus.entity.MenuShortcut}
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuShortcutDto extends AuditedDto {
    private Long id;
    private MenuPermissionDto menuPermission;
    private Integer position;
    private String username;
    private String style;
    private String shortName;
    private String initials;
}