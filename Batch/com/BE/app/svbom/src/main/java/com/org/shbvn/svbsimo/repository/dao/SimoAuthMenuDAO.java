package com.org.shbvn.svbsimo.repository.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.org.shbvn.svbsimo.repository.entities.SimoAuthMenu;

@Repository
public interface SimoAuthMenuDAO extends JpaRepository<SimoAuthMenu, Long> {
    
    @Query("SELECT m FROM SimoAuthMenu m WHERE m.menuId = :menuId")
    Optional<SimoAuthMenu> findByMenuId(@Param("menuId") String menuId);
    
    @Query("SELECT m FROM SimoAuthMenu m WHERE m.roleId = :roleId AND m.status = 'A'")
    List<SimoAuthMenu> findActiveByRoleId(@Param("roleId") String roleId);
    
    @Query("SELECT m FROM SimoAuthMenu m WHERE m.roleId IN :roleIds AND m.status = 'A'")
    List<SimoAuthMenu> findActiveByRoleIds(@Param("roleIds") List<String> roleIds);
}