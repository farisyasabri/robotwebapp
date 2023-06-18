package com.roboholic.roboholicweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.roboholic.roboholicweb.entity.Item;
import com.roboholic.roboholicweb.service.ItemServiceImpl;

@Controller
// @RequestMapping("/items")
public class AddProductController {
    private ItemServiceImpl itemserviceiImpl;

    @Autowired
    public AddProductController(ItemServiceImpl itemservice){
        this.itemserviceiImpl = itemservice;
    }

    @GetMapping("/addnewproduct")
    public String addNewProductForm(Model model) {
        // Iterable<Item> items = itemservice.getAllItems();
        // model.addAttribute("items", items);
        model.addAttribute("items", new Item());
        return "additem";
    }

    @PostMapping("/addnewproduct")
    public String addNewProduct(Item item) {
        itemserviceiImpl.addItem(item);
        return "listing";
    }
    
}

