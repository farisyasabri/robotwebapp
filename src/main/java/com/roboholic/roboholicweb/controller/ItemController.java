package com.roboholic.roboholicweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.roboholic.roboholicweb.entity.Item;
import com.roboholic.roboholicweb.service.ItemServiceImpl;

@Controller
public class ItemController {
    private ItemServiceImpl itemserviceImpl;

    @Autowired
    public ItemController(ItemServiceImpl itemservice){
        this.itemserviceImpl = itemservice;
    }

    @GetMapping("/addnewproduct")
    public String addNewProductForm(Model model) {
        // Iterable<Item> items = itemservice.getAllItems();
        // model.addAttribute("items", items);
        model.addAttribute("items", new Item());
        return "additem";
    }

    @GetMapping("/listing")
    public String listing(Model model) {
        model.addAttribute("items",itemserviceImpl.getAllItems());
        return "listing";
    }

    @PostMapping("/addnewproduct")
    public String addNewProduct(Item item) {
        itemserviceImpl.addItem(item);
        return "redirect:/listing";
    }

    @GetMapping("/listing/{id}/delete")
    public String deleteproduct(@PathVariable (value = "id") Long id) {
        itemserviceImpl.deleteItem(id);
        return "redirect:/listing";
    }
    

    //update product page
    @GetMapping("/listing/{id}/edit")
    public String editproduct(@PathVariable (value = "id") Long id, Model model) {

        Item item = itemserviceImpl.getItembyId(id);
        model.addAttribute("items", item);

        return "updateitem";
    }

    @PostMapping("/listing/{id}/edit/save")
    public String editItem(@ModelAttribute Item item, @PathVariable("id") Long id){
        itemserviceImpl.updateItem(item, id);
        return "redirect:/listing";
    }

}