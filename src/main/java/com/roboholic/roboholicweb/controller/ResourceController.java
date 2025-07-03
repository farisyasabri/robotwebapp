// package com.roboholic.roboholicweb.controller;

// // import org.hibernate.mapping.List;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Controller;
// import org.springframework.ui.Model;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.ModelAttribute;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PostMapping;

// import com.roboholic.roboholicweb.entity.Item;
// import com.roboholic.roboholicweb.entity.Resource;
// import java.util.List;
// import com.roboholic.roboholicweb.service.ItemServiceImpl;
// import com.roboholic.roboholicweb.service.ResourceServiceImpl;
// import org.springframework.web.bind.annotation.RequestParam;


// @Controller
// public class ResourceController {
//     private ResourceServiceImpl resourceserviceImpl;
//     // private ItemServiceImpl itemserviceImpl;

//     @Autowired
//     public ResourceController(ResourceServiceImpl resourceserviceImpl) {
//         this.resourceserviceImpl = resourceserviceImpl;
//         // this.itemserviceImpl = itemserviceImpl;
//     }

//     @GetMapping("/addnewresources")
//     public String addNewResourceForm(Model model) {
//         // List <Item> items = itemserviceImpl.getItemsName(); // to display in dropdown button
//         // model.addAttribute("items", items);
//         model.addAttribute("resources", new Resource());
//         return "addResources";
//     }

//     @GetMapping("/resourcelisting")
//     public String listing(Model model) {
//         model.addAttribute("resources", resourceserviceImpl.getAllResource());
//         return "resourcelisting";
//     }

//     @PostMapping("/resourcelisting")
//     public String addNewResources(Resource resource) {
//         resourceserviceImpl.addResource(resource);
//         resourceserviceImpl.addDateUploaded();
//         return "resourcelisting";
//     }

//     @GetMapping("/resourcelisting/{id}/delete")
//     public String deleteproduct(@PathVariable (value = "id") Long id) {
//         resourceserviceImpl.deleteResource(id);
//         return "redirect:/resourcelisting";
//     }

//     //update resource page
//     @GetMapping("/resourcelisting/{id}/update")
//     public String updateResourceForm(@PathVariable (value = "id") Long id, Model model) {
//         Resource resource = resourceserviceImpl.getResourcebyId(id);
//         model.addAttribute("resources", resource);
//         return  "updateResources";
//     }

//     @PostMapping("/resourcelisting/{id}/update/save")
//         public String updateResource(@ModelAttribute Resource resource, @PathVariable("id") Long id) {
//             resourceserviceImpl.updateResource(resource,id);
//             return "redirect:/resourcelisting";
//         }

//     //search item by name
//     @GetMapping("/resourcelisting/searchresource")
//     public String getResourceByName(@RequestParam(required = false) String filter, Model model) {
//         List<Resource> filteredResources;
//         if (filter != null && !filter.isEmpty()) {
//             filteredResources = resourceserviceImpl.searchResourceByName(filter);
//         }else{
//             filteredResources = resourceserviceImpl.getAllResource();
//         }
//         model.addAttribute("resources", filteredResources);
//         return "resourcelisting";
//     }
    
    
// }


package com.roboholic.roboholicweb.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;

import com.roboholic.roboholicweb.entity.Item;
import com.roboholic.roboholicweb.entity.Resource;

import java.io.IOException;
import java.util.List;

import com.roboholic.roboholicweb.service.CloudinaryService;
import com.roboholic.roboholicweb.service.ResourceServiceImpl;

@Controller
public class ResourceController {
    private ResourceServiceImpl resourceserviceImpl;
    private static final Logger logger = LoggerFactory.getLogger(FAQController.class);

    @Autowired
    private CloudinaryService cloudinaryService;

    @Autowired
    public ResourceController(ResourceServiceImpl resourceserviceImpl) {
        this.resourceserviceImpl = resourceserviceImpl;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/addnewresources")
    public String addNewResourceForm(Model model) {
        model.addAttribute("resources", new Resource());
        return "addResources";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/resourcelisting")
    public String adminResourceListing(Model model) {
        try{
            model.addAttribute("resources", resourceserviceImpl.getAllResource());
            return "resourcelisting";
        } catch(DataAccessException e){
            logger.error("Database error while fetching resources", e);
            model.addAttribute("error", "Failed to load Resources due to database error");
            return "resourcelisting";
        } catch (Exception e) {
            logger.error("Unexpected error while fetching listing", e);
            model.addAttribute("error", "An unexpected error occurred");
            return "resourcelisting";
        }
        
    }

    @GetMapping("/resourcelistingUserView")
    public String publicResourceListing(Model model) {
        model.addAttribute("resources", resourceserviceImpl.getAllResource());
        return "resourcelistingUserView";
    }

    // @PreAuthorize("hasRole('ADMIN')")
    // @PostMapping("/addnewresources")
    // public String addNewResources(Resource resource) {
    //     resourceserviceImpl.addResource(resource);
    //     resourceserviceImpl.addDateUploaded();
    //     return "redirect:/resourcelisting";
    // }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/addnewresources")
    public String addNewResources(@ModelAttribute Resource resource, BindingResult result,
                                @RequestParam("resourceDocuments") MultipartFile[] files,
                                RedirectAttributes redirectAttributes) throws IOException {
        
                                    
        if (result.hasErrors()) {
            return "addResources";
        }

        try{
            // Upload documents to Cloudinary
            if (files != null && files.length > 0 && !files[0].isEmpty()) {
                List<String> documentUrls = cloudinaryService.uploadDocuments(files);
                resource.setDocumentUrls(documentUrls);
            }
            
            resource.setDateUploaded(resourceserviceImpl.addDateUploaded());
            resourceserviceImpl.addResource(resource);
            return "redirect:/resourcelisting";
        } catch (DataAccessException e) {
            logger.error("Database error while adding resource", e);
            redirectAttributes.addFlashAttribute("error", "Failed to add resource due to database error");
            return "redirect:/addnewresources";
        } catch (Exception e) {
            logger.error("Unexpected error while adding product", e);
            redirectAttributes.addFlashAttribute("error", "Failed to add resource");
            return "redirect:/addnewresources";
        }
        
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/resourcelisting/{id}/delete")
    public String deleteResource(@PathVariable (value = "id") Long id) {
        resourceserviceImpl.deleteResource(id);
        return "redirect:/resourcelisting";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/resourcelisting/{id}/update")
    public String updateResourceForm(@PathVariable (value = "id") Long id, Model model) {
        Resource resource = resourceserviceImpl.getResourcebyId(id);
        model.addAttribute("resources", resource);
        return "updateResources";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/resourcelisting/{id}/update/save")
    public String updateResource(@ModelAttribute Resource resource, @PathVariable("id") Long id) {
        resourceserviceImpl.updateResource(resource,id);
        return "redirect:/resourcelisting";
    }

    @GetMapping("/resourcelistingUserView/searchresource")
    public String publicGetResourceByName(@RequestParam(required = false) String filter, Model model) {
        List<Resource> filteredResources;
        if (filter != null && !filter.isEmpty()) {
            filteredResources = resourceserviceImpl.searchResourceByName(filter);
        } else {
            filteredResources = resourceserviceImpl.getAllResource();
        }
        model.addAttribute("resources", filteredResources);
        // Return appropriate view based on user role
        return "resourcelistingUserView";
    }
   
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/resourcelisting/searchresource")
    public String adminGetResourceByName(@RequestParam(required = false) String filter, Model model) {
        List<Resource> filteredResources;
        if (filter != null && !filter.isEmpty()) {
            filteredResources = resourceserviceImpl.searchResourceByName(filter);
        } else {
            filteredResources = resourceserviceImpl.getAllResource();
        }
        model.addAttribute("resources", filteredResources);
        // Return appropriate view based on user role
        return "resourcelisting";
    }

    // @PreAuthorize("hasRole('ADMIN')")
    // @GetMapping("/resourcelisting/{id}/viewResource")
    // public String adminGetResourceDetails(@PathVariable(value = "id") Long id, Model model) {
    //     try{
    //         Resource resource = resourceserviceImpl.getResourcebyId(id);
    //         model.addAttribute("resource", resource);
    //         return "resourceDetails";
    //     } catch (Exception e) {
    //         Resource resource = resourceserviceImpl.getResourcebyId(id);
    //         logger.error("Error view resource details", e);
    //         model.addAttribute("error", "Error view resource details "+ resource.getResourceName());
    //         model.addAttribute("resources", resourceserviceImpl.getAllResource());
    //         return "resourcelisting";
    //     }
        
    // }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/resourcelisting/{id}/viewResource")
    public String adminGetResourceDetails(@PathVariable(value = "id") Long id, Model model) {
        try {
            Resource resource = resourceserviceImpl.getResourcebyId(id);
            model.addAttribute("resource", resource);
            return "resourceDetails";
        } catch (Exception e) {
            logger.error("Error viewing resource details", e);
            model.addAttribute("error", "Error viewing resource details");
            return "redirect:/resourcelisting";
        }
    }

    @GetMapping("/resourcelistingUserView/{id}/viewResource")
    public String publicGetProductDetails(@PathVariable(value = "id") Long id, Model model) {
        
        try {
            Resource resource = resourceserviceImpl.getResourcebyId(id);
            model.addAttribute("resource", resource);
            return "resourceDetailsUserView";
        } catch (Exception e) {
            logger.error("Error viewing resource details", e);
            model.addAttribute("error", "Error viewing resource details");
            return "resourcelistingUserView";
        }
        
    }
}