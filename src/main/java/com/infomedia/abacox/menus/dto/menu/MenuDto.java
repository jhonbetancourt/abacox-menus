package com.infomedia.abacox.menus.dto.menu;

import com.infomedia.abacox.menus.dto.superclass.ActivableDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * DTO for {@link com.infomedia.abacox.menus.entity.Menu}
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuDto extends ActivableDto {
    private Long id;
    private String name;
    private String path;
    private String displayName;
    private boolean requiresPermissions;
    private Set<MenuDto> submenus = new LinkedHashSet<>();
}