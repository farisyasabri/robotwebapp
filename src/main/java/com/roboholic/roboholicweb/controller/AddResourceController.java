package com.roboholic.roboholicweb.controller;

// import org.hibernate.mapping.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.roboholic.roboholicweb.entity.Item;
import com.roboholic.roboholicweb.entity.Resource;
import java.util.List;
import com.roboholic.roboholicweb.service.ItemServiceImpl;
import com.roboholic.roboholicweb.service.ResourceServiceImpl;

@Controller
public class AddResourceController {
    private ResourceServiceImpl resourceserviceImpl;
    // private ItemServiceImpl itemserviceImpl;

    @Autowired
    public AddResourceController(ResourceServiceImpl resourceserviceImpl) {
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

    @PostMapping("/addnewresources")
    public String addNewResources(Resource resource) {
        resourceserviceImpl.addResource(resource);
        resourceserviceImpl.addDateUploaded();
        return "resourcelisting";
    }
    
}
