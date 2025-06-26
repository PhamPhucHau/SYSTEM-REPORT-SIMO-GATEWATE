package com.org.shbvn.svbsimo.repository.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.org.shbvn.svbsimo.repository.entities.SimoUserRole;

@Repository
public interface SimoUserRoleDAO extends JpaRepository<SimoUserRole, Long> {
    
    @Query("SELECT ur FROM SimoUserRole ur WHERE ur.userId = :userId AND ur.roleId = :roleId")
    Optional<SimoUserRole> findByUserAndRole(@Param("userId") String userId, @Param("roleId") String roleId);

    @Query("SELECT ur FROM SimoUserRole ur WHERE ur.userId = :userId AND ur.status = :status")
    List<SimoUserRole> findByUserAndStatus(@Param("userId") String userId, @Param("status") String status);
    
    @Query("SELECT ur FROM SimoUserRole ur WHERE ur.roleId = :roleId AND ur.status = :status")
    List<SimoUserRole> findByRoleAndStatus( @Param("roleId") String roleId, @Param("status") String status);
    
    @Query("SELECT ur FROM SimoUserRole ur WHERE ur.userId = :userId AND ur.status = 'A'")
    List<SimoUserRole> findActiveByUserId(@Param("userId") String userId);
    
    @Query("SELECT ur FROM SimoUserRole ur WHERE ur.roleId = :roleId AND ur.status = 'A'")
    List<SimoUserRole> findActiveByRoleId(@Param("roleId") String roleId);
}
