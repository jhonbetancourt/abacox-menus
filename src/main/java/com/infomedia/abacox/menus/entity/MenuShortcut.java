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
    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "menu_permission_id", nullable = false)
    private MenuPermission menuPermission;

    @Column(name = "order", nullable = false)
    private Integer order;

    @Column(name = "username", nullable = false, length = 30)
    private String username;
}
