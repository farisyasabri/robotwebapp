package com.roboholic.roboholicweb.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.roboholic.roboholicweb.entity.Resource;

@Service
public interface ResourceService {
    public Long addResource(Resource resource);
    public void deleteResource(long resource_id);
    public List<Resource> searchResourceByName(String search);
    public Resource searchResourcebyId(long resource_id);
    public Iterable<Resource> getAllResource();
    public LocalDateTime addDateUploaded();
}
