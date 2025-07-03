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

    // public List<String> uploadDocuments(MultipartFile[] files) throws IOException {
    //     List<String> documentUrls = new ArrayList<>();
        
    //     for (MultipartFile file : files) {
    //         if (!file.isEmpty()) {
    //             Map uploadResult = cloudinary.uploader().upload(file.getBytes(), 
    //                 ObjectUtils.asMap(
    //                     "resource_type", "auto", // Let Cloudinary detect the file type
    //                     "format", "pdf" // Optional: force PDF format
    //                 ));
    //             documentUrls.add((String) uploadResult.get("url"));
    //         }
    //     }
        
    //     return documentUrls;
    // }

    // Update your CloudinaryService.java with this improved document upload method
    public List<String> uploadDocuments(MultipartFile[] files) throws IOException {
        List<String> documentUrls = new ArrayList<>();
        
        if (files == null || files.length == 0) {
            throw new IOException("No files provided for upload");
        }

        for (MultipartFile file : files) {
            if (file.isEmpty()) {
                continue; // Skip empty files
            }

            try {
                // Validate file type
                String contentType = file.getContentType();
                if (contentType == null || 
                    !(contentType.equals("application/pdf") || 
                    contentType.equals("application/msword") || 
                    contentType.equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document") || 
                    contentType.equals("application/vnd.ms-powerpoint") || 
                    contentType.equals("application/vnd.openxmlformats-officedocument.presentationml.presentation"))) {
                    throw new IOException("Invalid file type. Only PDF, DOC, DOCX, PPT, PPTX are allowed.");
                }

                // Validate file size (10MB max)
                if (file.getSize() > 10 * 1024 * 1024) {
                    throw new IOException("File size exceeds 10MB limit: " + file.getOriginalFilename());
                }

                Map<String, Object> uploadResult = cloudinary.uploader().upload(file.getBytes(), 
                    ObjectUtils.asMap(
                        "resource_type", "auto",
                        "format", getFileFormat(file.getOriginalFilename())
                    ));
                    
                String url = (String) uploadResult.get("secure_url");
                if (url == null) {
                    logger.error("Cloudinary upload succeeded but returned no URL for file: {}", 
                            file.getOriginalFilename());
                    throw new IOException("Failed to get URL for uploaded document: " + file.getOriginalFilename());
                }
                
                documentUrls.add(url);
                logger.info("Successfully uploaded document: {}", url);
            } catch (Exception e) {
                logger.error("Failed to upload document {} to Cloudinary: {}", 
                        file.getOriginalFilename(), e.getMessage());
                throw new IOException("Failed to upload document " + file.getOriginalFilename() + 
                                    ". Error: " + e.getMessage(), e);
            }
        }

        if (documentUrls.isEmpty()) {
            throw new IOException("No valid documents were uploaded");
        }

        return documentUrls;
    }

    private String getFileFormat(String filename) {
        if (filename.toLowerCase().endsWith(".pdf")) return "pdf";
        if (filename.toLowerCase().endsWith(".doc") || filename.toLowerCase().endsWith(".docx")) return "doc";
        if (filename.toLowerCase().endsWith(".ppt") || filename.toLowerCase().endsWith(".pptx")) return "ppt";
        return "auto"; // let Cloudinary detect
    }
}