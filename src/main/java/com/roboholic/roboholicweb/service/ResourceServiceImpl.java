package com.roboholic.roboholicweb.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.roboholic.roboholicweb.entity.Resource;
import com.roboholic.roboholicweb.repository.ResourceRepository;

@Service
public class ResourceServiceImpl implements ResourceService{
    
    private static final Logger logger = LoggerFactory.getLogger(FAQServiceImpl.class);
    @Autowired
    private ResourceRepository resourceRepo;

    @Override
    public Long addResource(Resource resource){
        try{
            long resource_id = ((Resource) resourceRepo.save(resource)).getResourceID();
            return resource_id;
        }  catch (DataIntegrityViolationException e) {
            logger.error("Data integrity violation while adding new resource", e);
            throw new RuntimeException("Failed to add new resource due to data integrity violation");
        } catch (Exception e) {
            logger.error("Unexpected error while adding new resource", e);
            throw new RuntimeException("Failed to add new resource");
        }
        
    }

    @Override
    public void deleteResource(long resource_id) {
        try{
            if (!resourceRepo.existsById(resource_id)) {
                throw new RuntimeException("Resource not found with id: " + resource_id);
            }
            resourceRepo.deleteById(resource_id);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Resource not found with id: " + resource_id, e);
            throw new RuntimeException("Resource not found with id: " + resource_id);
        } catch (Exception e) {
            logger.error("Unexpected error while deleting resource", e);
            throw new RuntimeException("Failed to delete resource");
        }
    }

    @Override
    public Resource getResourcebyId(long resource_id) {
        // Optional<Resource> optional = resourceRepo.findById(resource_id);
        // Optional<Resource> optional = resourceRepo.findByIdWithDocuments(resource_id);
        // Resource res = null;
        // if (optional.isPresent()){
        //     res = optional.get();
        // } else{
        //     throw new RuntimeException(" product is not found for id:: "+ resource_id);
        // }
        // return res;

        try{
            return resourceRepo.findByIdWithDocuments(resource_id)
                .orElseThrow(() -> new RuntimeException("Resource details not found for id: " + resource_id));
        } catch (Exception e) {
            logger.error("Error fetching Resource details with id: " + resource_id, e);
            throw new RuntimeException("Error fetching Resource details");
        }

    }

    @Override
    public List<Resource> searchResourceByName(String search) {
        try{
            return resourceRepo.findByResourceNameContaining(search);
        } catch (Exception e) {
            logger.error("Error searching resource(s) by name: " + search, e);
            throw new RuntimeException("Error searching resource(s)");
        }
    }

    @Override
    public List<Resource> getAllResource(){
        try{
            List<Resource> res = new ArrayList<Resource>();
            resourceRepo.findAll().forEach(res::add);
            return res;
        } catch (Exception e) {
            logger.error("Error fetching all resources", e);
            throw new RuntimeException("Error fetching resources");
        }
        
    }

    @Override
    public LocalDateTime addDateUploaded(){
        try{
            return LocalDateTime.now();   
        }  catch (Exception e) {
            logger.error("Error fetching date", e);
            throw new RuntimeException("Error fetching date");
        }
    }

    @Override
    public Long updateResource(Resource resource, Long id){
        try{
            Optional<Resource> optional = resourceRepo.findById(id);

            if(optional.isPresent()){
                Resource res = optional.get();
                res.setResourceName(resource.getResourceName());
                res.setResourceDescription(resource.getResourceDescription());
                res.setLinkUrl(resource.getLinkUrl());
                res.setDateUploaded(resource.getDateUploaded());
                resourceRepo.save(res);
            }

            return id;
        } catch (Exception e) {
            logger.error("Error updating resource with id: " + id, e);
            throw new RuntimeException("Error updating resource");
        }
        
    }
}