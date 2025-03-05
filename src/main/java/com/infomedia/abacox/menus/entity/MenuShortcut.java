package com.infomedia.abacox.menus.entity;

import com.infomedia.abacox.menus.entity.superclass.AuditedEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@Entity
@Table(name = "menu_shortcuts")
public class MenuShortcut extends AuditedEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ToString.Exclude
    @ManyToOne(optional = false)
    @JoinColumn(name = "menu_permission_id", nullable = false)
    private MenuPermission menuPermission;

    @Column(name = "position", nullable = false)
    private Integer position;

    @Column(name = "username", nullable = false, length = 30)
    private String username;

    @Column(name = "style", nullable = false, length = 20)
    private String style;

    @Column(name = "short_name", nullable = false, length = 10)
    private String shortName;

    @Column(name = "initials", nullable = false, length = 3)
    private String initials;
}
