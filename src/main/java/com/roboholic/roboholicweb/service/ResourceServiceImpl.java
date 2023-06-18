package com.roboholic.roboholicweb.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.roboholic.roboholicweb.entity.Resource;
import com.roboholic.roboholicweb.repository.ResourceRepository;

@Service
public class ResourceServiceImpl implements ResourceService{
    @Autowired
    private ResourceRepository resourceRepo;

    @Override
    public Long addResource(Resource resource){
        long resource_id = ((Resource) resourceRepo.save(resource)).getResourceID();
        return resource_id;
    }

    @Override
    public void deleteResource(long resource_id) {
        resourceRepo.deleteById(resource_id);
        // throw new UnsupportedOperationException("Unimplemented method 'deleteItem'");
    }

    @Override
    public Resource searchResourcebyId(long resource_id) {
        Optional<Resource> optional = resourceRepo.findById(resource_id);
        Resource res = null;
        if (optional.isPresent()){
            res = optional.get();
        } else{
            throw new RuntimeException(" product is not found for id:: "+ resource_id);
        }
        return res;
    }

    @Override
    public List<Resource> searchResourceByName(String search) {
        return resourceRepo.findByResourceNameContaining(search);
    }

    @Override
    public Iterable<Resource> getAllResource(){
        return resourceRepo.findAll();
    }

    @Override
    public LocalDateTime addDateUploaded(){
        return LocalDateTime.now();
    }
}