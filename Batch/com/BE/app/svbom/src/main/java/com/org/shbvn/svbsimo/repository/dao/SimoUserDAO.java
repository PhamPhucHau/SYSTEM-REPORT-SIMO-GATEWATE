package com.org.shbvn.svbsimo.repository.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.org.shbvn.svbsimo.repository.entities.SimoUser;

@Repository
public interface SimoUserDAO extends JpaRepository<SimoUser, Long> {
    
    @Query("SELECT u FROM SimoUser u WHERE u.username = :username OR u.email = :email")
    Optional<SimoUser> findByUsernameOrEmail(@Param("username") String username, @Param("email") String email);
    
    @Query("SELECT u FROM SimoUser u WHERE u.username = :username")
    Optional<SimoUser> findByUsername(@Param("username") String username);
    
    @Query("SELECT u FROM SimoUser u WHERE u.email = :email")
    Optional<SimoUser> findByEmail(@Param("email") String email);
    
    @Query("SELECT u FROM SimoUser u WHERE u.status = 'A'")
    List<SimoUser> findActiveUsers();
}
