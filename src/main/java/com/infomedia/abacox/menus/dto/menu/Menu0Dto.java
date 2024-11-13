package com.infomedia.abacox.menus.dto.menu;

import com.infomedia.abacox.menus.dto.superclass.ActivableDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * DTO for {@link com.infomedia.abacox.menus.entity.Menu}
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Menu0Dto extends ActivableDto {
    private Long id;
    private String name;
    private String path;
    private String displayName;
    private Long parentMenuId;
    private boolean requiresPermissions;
    private boolean crud;
}