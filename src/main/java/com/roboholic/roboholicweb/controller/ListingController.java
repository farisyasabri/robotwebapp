package com.roboholic.roboholicweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.roboholic.roboholicweb.entity.Item;
import com.roboholic.roboholicweb.service.ItemService;

@Controller
public class ListingController {
    
    private ItemService itemservice;

    @Autowired
    public ListingController(ItemService itemservice){
        this.itemservice = itemservice;
    }

    // @GetMapping("/listing")
    // public String showListing(Model model){
    //     // Iterable<Item> items = itemservice.getAllItems();

    //     // model.addAttribute("items", items);
        // return "listing";
    // }
}
