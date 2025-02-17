package com.infomedia.abacox.menus.repository;

import com.infomedia.abacox.menus.entity.MenuShortcut;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface MenuShortcutRepository extends JpaRepository<MenuShortcut, Long>, JpaSpecificationExecutor<MenuShortcut> {

    long countByUsername(String username);

    List<MenuShortcut> findByUsernameOrderByPositionAsc(String username);

    Optional<MenuShortcut> findByUsernameAndPosition(String username, Integer order);

    List<MenuShortcut> findByMenuPermission_Id(Long id);

    boolean existsByUsernameAndMenuPermission_Id(String username, Long id);
}