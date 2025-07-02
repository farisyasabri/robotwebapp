package com.roboholic.roboholicweb.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// @Service
// public class CloudinaryService {

//     @Autowired
//     private Cloudinary cloudinary;

//     public List<String> uploadImages(MultipartFile[] files) throws IOException {
//         List<String> imageUrls = new ArrayList<>();
        
//         for (MultipartFile file : files) {
//             if (!file.isEmpty()) {
//                 Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
//                 imageUrls.add((String) uploadResult.get("url"));
//             }
//         }
        
//         return imageUrls;
//     }
// }

@Service
public class CloudinaryService {
    private static final Logger logger = LoggerFactory.getLogger(CloudinaryService.class);
    private final Cloudinary cloudinary;

    @Autowired
    public CloudinaryService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public List<String> uploadImages(MultipartFile[] files) throws IOException {
        List<String> imageUrls = new ArrayList<>();
        
        if (files == null || files.length == 0) {
            return imageUrls;
        }
        
        for (MultipartFile file : files) {
            if (file.isEmpty()) {
                continue;
            }
            
            try {
                Map<String, Object> uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
                
                String url = (String) uploadResult.get("secure_url");
                if (url != null) {
                    imageUrls.add(url);
                    logger.info("Successfully uploaded image: {}", url);
                } else {
                    logger.error("Cloudinary upload succeeded but returned no URL for file: {}", 
                               file.getOriginalFilename());
                }
            } catch (Exception e) {
                logger.error("Failed to upload image {} to Cloudinary: {}", 
                           file.getOriginalFilename(), e.getMessage());
                throw new IOException("Failed to upload image " + file.getOriginalFilename() + 
                                    " to Cloudinary. Please try again.", e);
            }
        }
        
        if (imageUrls.isEmpty()) {
            throw new IOException("No images were successfully uploaded");
        }
        
        return imageUrls;
    }

    public List<String> uploadDocuments(MultipartFile[] files) throws IOException {
        List<String> documentUrls = new ArrayList<>();
        
        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                Map uploadResult = cloudinary.uploader().upload(file.getBytes(), 
                    ObjectUtils.asMap(
                        "resource_type", "auto", // Let Cloudinary detect the file type
                        "format", "pdf" // Optional: force PDF format
                    ));
                documentUrls.add((String) uploadResult.get("url"));
            }
        }
        
        return documentUrls;
    }
}