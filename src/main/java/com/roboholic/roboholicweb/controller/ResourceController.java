package com.roboholic.roboholicweb.controller;

// import org.hibernate.mapping.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.roboholic.roboholicweb.entity.Item;
import com.roboholic.roboholicweb.entity.Resource;
import java.util.List;
import com.roboholic.roboholicweb.service.ItemServiceImpl;
import com.roboholic.roboholicweb.service.ResourceServiceImpl;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class ResourceController {
    private ResourceServiceImpl resourceserviceImpl;
    // private ItemServiceImpl itemserviceImpl;

    @Autowired
    public ResourceController(ResourceServiceImpl resourceserviceImpl) {
        this.resourceserviceImpl = resourceserviceImpl;
        // this.itemserviceImpl = itemserviceImpl;
    }

    @GetMapping("/addnewresources")
    public String addNewResourceForm(Model model) {
        // List <Item> items = itemserviceImpl.getItemsName(); // to display in dropdown button
        // model.addAttribute("items", items);
        model.addAttribute("resources", new Resource());
        return "addResources";
    }

    @GetMapping("/resourcelisting")
    public String listing(Model model) {
        model.addAttribute("resources", resourceserviceImpl.getAllResource());
        return "resourcelisting";
    }

    @PostMapping("/resourcelisting")
    public String addNewResources(Resource resource) {
        resourceserviceImpl.addResource(resource);
        resourceserviceImpl.addDateUploaded();
        return "resourcelisting";
    }

    @GetMapping("/resourcelisting/{id}/delete")
    public String deleteproduct(@PathVariable (value = "id") Long id) {
        resourceserviceImpl.deleteResource(id);
        return "redirect:/resourcelisting";
    }

    //update resource page
    @GetMapping("/resourcelisting/{id}/update")
    public String updateResourceForm(@PathVariable (value = "id") Long id, Model model) {
        Resource resource = resourceserviceImpl.getResourcebyId(id);
        model.addAttribute("resources", resource);
        return  "updateResources";
    }

    @PostMapping("/resourcelisting/{id}/update/save")
        public String updateResource(@ModelAttribute Resource resource, @PathVariable("id") Long id) {
            resourceserviceImpl.updateResource(resource,id);
            return "redirect:/resourcelisting";
        }

    //search item by name
    @GetMapping("/resourcelisting/searchresource")
    public String getResourceByName(@RequestParam(required = false) String filter, Model model) {
        List<Resource> filteredResources;
        if (filter != null && !filter.isEmpty()) {
            filteredResources = resourceserviceImpl.searchResourceByName(filter);
        }else{
            filteredResources = resourceserviceImpl.getAllResource();
        }
        model.addAttribute("resources", filteredResources);
        return "resourcelisting";
    }
    
    
}
