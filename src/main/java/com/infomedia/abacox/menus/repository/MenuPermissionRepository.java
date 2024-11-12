package com.infomedia.abacox.menus.repository;

import com.infomedia.abacox.menus.entity.Menu;
import com.infomedia.abacox.menus.entity.MenuPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface MenuPermissionRepository extends JpaRepository<MenuPermission, Long>, JpaSpecificationExecutor<MenuPermission> {

    boolean existsByRolenameAndMenu_IdAndIdNot(String rolename, Long id, Long id1);

    boolean existsByRolenameAndMenu_Id(String rolename, Long id);

    Optional<MenuPermission> findByRolenameAndMenu(String rolename, Menu menu);

}