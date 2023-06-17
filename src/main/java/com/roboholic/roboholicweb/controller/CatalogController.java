package com.roboholic.roboholicweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.roboholic.roboholicweb.service.ItemService;


@Controller
@RequestMapping("/catalog")
public class CatalogController {
    private ItemService itemservice;

    @Autowired
    public CatalogController(ItemService itemservice){
        this.itemservice = itemservice;
    }

    @GetMapping("/catalog")
    public String showCatalog(Model model){
        model.addAttribute("items", itemservice.getAllItems());
        return "catalog";
    }
}
