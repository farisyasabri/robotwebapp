package com.roboholic.roboholicweb.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.roboholic.roboholicweb.entity.Item;
import com.roboholic.roboholicweb.service.ItemServiceImpl;

@Controller
public class ItemController {
    private ItemServiceImpl itemserviceImpl;

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

    //filter items by price
    @GetMapping("/listing/{from}/{to}")
    public String filterPrice(@PathVariable int from, int to, Model model){
        List<Item> filteredItem = itemserviceImpl.filterItemsByPrice(from, to);
        model.addAttribute("item",filteredItem);
        return "listing";
    }

    //filter items by name
    @GetMapping("/listing/search")
    public String getListingByName(@RequestParam(required = false) String filter, Model model) {
        List<Item> filteredNames;
        if (filter != null && !filter.isEmpty()) {
            filteredNames = itemserviceImpl.searchItemByName(filter);
        }else{
            filteredNames = itemserviceImpl.getAllItems();
            System.out.println();
        }
        model.addAttribute("items", filteredNames);
        return "listing";
    }

}