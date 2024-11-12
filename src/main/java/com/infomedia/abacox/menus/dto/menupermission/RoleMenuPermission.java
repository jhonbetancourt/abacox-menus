package com.infomedia.abacox.menus.dto.menupermission;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.LinkedHashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@Data
public class RoleMenuPermission {
    private Long id;
    private String name;
    private String path;
    private String displayName;
    private PermissionDto permission;
    private Set<RoleMenuPermission> submenus = new LinkedHashSet<>();
}
