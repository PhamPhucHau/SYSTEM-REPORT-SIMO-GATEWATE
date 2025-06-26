package com.org.shbvn.svbsimo.repository.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.org.shbvn.svbsimo.repository.entities.SimoFileHis;


@Repository
public interface SimoFileHisDAO extends JpaRepository<SimoFileHis, Long> {
    
    @Query("SELECT f FROM SimoFileHis f WHERE f.id = :id")
    Optional<SimoFileHis> findById(@Param("id") Long id);

    @Query("SELECT f FROM SimoFileHis f WHERE f.templateCode = :templateCode")
    List<SimoFileHis> findByTemplateCode(@Param("templateCode") String templateCode);

    @Query("SELECT f FROM SimoFileHis f WHERE f.fileName = :fileName")
    List<SimoFileHis> findByFileName(@Param("fileName") String fileName);

    @Query("SELECT f FROM SimoFileHis f WHERE f.fileName = :fileName AND f.fileUploadDt = :fileUploadDt and f.fileStatus = 'UPLD'")
    Optional<SimoFileHis> findByFileNameAndUploadDt(@Param("fileName") String fileName, @Param("fileUploadDt") String fileUploadDt);

}
