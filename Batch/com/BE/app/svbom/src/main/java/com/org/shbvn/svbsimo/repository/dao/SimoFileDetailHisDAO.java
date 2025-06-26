package com.org.shbvn.svbsimo.repository.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.org.shbvn.svbsimo.repository.entities.SimoFileDetailHis;


@Repository
public interface SimoFileDetailHisDAO extends JpaRepository<SimoFileDetailHis, Long> {
    
    @Query("SELECT f FROM SimoFileDetailHis f WHERE f.id = :id")
    Optional<SimoFileDetailHis> findById(@Param("id") Long id);

    @Query("SELECT f FROM SimoFileDetailHis f WHERE f.templateCode = :templateCode")
    List<SimoFileDetailHis> findByTemplateCode(@Param("templateCode") String templateCode);

    @Query("SELECT f FROM SimoFileDetailHis f WHERE f.fileName = :fileName")
    List<SimoFileDetailHis> findByFileName(@Param("fileName") String fileName);
    
}
