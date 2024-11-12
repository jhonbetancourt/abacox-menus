package com.infomedia.abacox.menus.repository;

import com.infomedia.abacox.menus.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long>, JpaSpecificationExecutor<Menu> {


    List<Menu> findByActiveAndParentId(boolean active, Long parentId);

}