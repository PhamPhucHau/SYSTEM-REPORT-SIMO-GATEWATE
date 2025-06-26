package com.org.shbvn.svbsimo.repository.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.org.shbvn.svbsimo.repository.entities.SimoRole;

@Repository
public interface SimoRoleDAO extends JpaRepository<SimoRole, Long> {
    
    @Query("SELECT r FROM SimoRole r WHERE r.roleId = :roleId")
    Optional<SimoRole> findByRoleId(@Param("roleId") String roleId);
    
    @Query("SELECT r FROM SimoRole r WHERE r.roleId = :roleId AND r.status = 'A'")
    Optional<SimoRole> findActiveByRoleId(@Param("roleId") String roleId);
    
    @Query("SELECT r FROM SimoRole r WHERE r.status = 'A'")
    List<SimoRole> findActiveRoles();

}
