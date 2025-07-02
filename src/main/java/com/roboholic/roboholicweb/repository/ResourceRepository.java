package com.roboholic.roboholicweb.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.roboholic.roboholicweb.entity.Resource;

import jakarta.transaction.Transactional;

public interface ResourceRepository extends JpaRepository<Resource,Long>{
        
        @Transactional
        List<Resource> findByResourceNameContaining(String resourceName);

        @Query("SELECT r FROM Resource r LEFT JOIN FETCH r.documentUrls WHERE r.resourceID = :id")
        Optional<Resource> findByIdWithDocuments(@Param("id") Long id);
        // Resource save(Resource resource, LocalDateTime dateUploaded);
}
