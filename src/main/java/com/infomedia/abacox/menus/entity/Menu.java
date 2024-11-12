package com.infomedia.abacox.menus.entity;

import com.infomedia.abacox.menus.entity.superclass.ActivableEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.proxy.HibernateProxy;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@Entity
@Table(name = "menus")
public class Menu extends ActivableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, length = 100, unique = true)
    private String name;


    @Column(name = "path")
    private String path;

    @Column(name = "display_name", nullable = false, length = 100)
    private String displayName;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Menu parent;

    @Column(name = "parent_id", insertable = false, updatable = false)
    private Long parentMenuId;

    @ToString.Exclude
    @OneToMany(mappedBy = "parent", orphanRemoval = true)
    private Set<Menu> submenus = new LinkedHashSet<>();

    @Column(name = "requires_permissions")
    private boolean requiresPermissions;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Menu menu = (Menu) o;
        return getId() != null && Objects.equals(getId(), menu.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
