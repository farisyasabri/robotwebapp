package com.roboholic.roboholicweb.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.roboholic.roboholicweb.entity.Resource;

public interface ResourceRepository extends JpaRepository<Resource,Long>{
        List<Resource> findByResourceNameContaining(String resourceName);

        // Resource save(Resource resource, LocalDateTime dateUploaded);
}
