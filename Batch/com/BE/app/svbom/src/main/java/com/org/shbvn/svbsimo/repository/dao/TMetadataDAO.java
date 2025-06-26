package com.org.shbvn.svbsimo.repository.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.org.shbvn.svbsimo.repository.entities.TMetadata;

/**
 * Data Access Object for TMetadata entity
 */
@Repository
public interface TMetadataDAO extends JpaRepository<TMetadata, Long> {

    /**
     * Find metadata by lookup code
     * 
     * @param lookupCode the lookup code
     * @return list of metadata entries
     */
    @Query("SELECT m FROM TMetadata m WHERE m.lookupCode = :lookupCode ORDER BY m.orderBy, m.value")
    List<TMetadata> findByLookupCode(@Param("lookupCode") String lookupCode);
    
    /**
     * Find metadata by lookup code and lookup code ID
     * 
     * @param lookupCode the lookup code
     * @param lookupCodeId the lookup code ID
     * @return the metadata entry
     */
    @Query("SELECT m FROM TMetadata m WHERE m.lookupCode = :lookupCode AND m.lookupCodeId = :lookupCodeId")
    Optional<TMetadata> findByLookupCodeAndLookupCodeId(
            @Param("lookupCode") String lookupCode, 
            @Param("lookupCodeId") String lookupCodeId);
    
    /**
     * Find metadata by service name
     * 
     * @param serviceName the service name
     * @return list of metadata entries
     */
    @Query("SELECT m FROM TMetadata m WHERE m.serviceName = :serviceName ORDER BY m.lookupCode, m.orderBy, m.value")
    List<TMetadata> findByServiceName(@Param("serviceName") String serviceName);
    
    /**
     * Find metadata by lookup code and language
     * 
     * @param lookupCode the lookup code
     * @param language the language code
     * @return list of metadata entries
     */
    @Query("SELECT m FROM TMetadata m WHERE m.lookupCode = :lookupCode AND m.language = :language ORDER BY m.orderBy, m.value")
    List<TMetadata> findByLookupCodeAndLanguage(
            @Param("lookupCode") String lookupCode, 
            @Param("language") String language);
    
    /**
     * Count metadata entries by lookup code and lookup code ID
     * 
     * @param lookupCode the lookup code
     * @param lookupCodeId the lookup code ID
     * @return count of matching entries
     */
    @Query("SELECT COUNT(m) FROM TMetadata m WHERE m.lookupCode = :lookupCode AND m.lookupCodeId = :lookupCodeId")
    Long countByLookupCodeAndLookupCodeId(
            @Param("lookupCode") String lookupCode, 
            @Param("lookupCodeId") String lookupCodeId);
}