package com.infomedia.abacox.menus.repository;

import com.infomedia.abacox.menus.entity.MenuShortcut;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MenuShortcutRepository extends JpaRepository<MenuShortcut, Long>, JpaSpecificationExecutor<MenuShortcut> {

    long countByUsername(String username);

    List<MenuShortcut> findByUsernameOrderByOrderAsc(String username);

    boolean existsByUsernameAndOrder(String username, Integer order);

    Optional<MenuShortcut> findByUsernameAndOrder(String username, Integer order);

    List<MenuShortcut> findByMenuPermission_Id(Long id);
}