package com.roboholic.roboholicweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.roboholic.roboholicweb.service.ItemService;


@Controller
public class ItemController {

    private ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    // @GetMapping("/catalog")
    // public String showCatalog(Model model) {
    //     // Retrieve the list of items from the itemService
    //     Iterable<Item> items = itemService.getAllItem();

    //     // Add the items to the model
    //     model.addAttribute("items", items);

    //     // Return the view name for displaying the catalog
    //     return "catalog";
    // }


}
