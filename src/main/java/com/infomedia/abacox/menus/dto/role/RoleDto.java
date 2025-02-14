package com.infomedia.abacox.menus.dto.role;

import com.infomedia.abacox.menus.dto.superclass.ActivableDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleDto extends ActivableDto {
    private UUID id;
    private String name;
    private String rolename;
}