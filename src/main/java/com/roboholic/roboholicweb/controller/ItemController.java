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

    //filter items by name
    @GetMapping("/listing/searchname")
    public String getListingByName(@RequestParam(required = false) String filter, Model model) {
        List<Item> filteredNames;
        if (filter != null && !filter.isEmpty()) {
            filteredNames = itemserviceImpl.searchItemByName(filter);
        }else{
            filteredNames = itemserviceImpl.getAllItems();
            // System.out.println();
        }
        model.addAttribute("items", filteredNames);
        return "listing";
    }

    //filter items by price
    @GetMapping("/listing/searchprice")
    public String getListingByPrice(@RequestParam(required = false) String priceRange, Model model){
        List<Item> filteredPrice;
        switch (priceRange) {
            case "under50":
                filteredPrice = itemserviceImpl.searchItemsByPrice(0.00,50.00); //boleh
                break;
            case "50to100":
                filteredPrice = itemserviceImpl.searchItemsByPrice(50.01,100.00); // boleh
                break;
            case "above100":
                filteredPrice = itemserviceImpl.searchItemsByPrice(100.01,1000.00);
                break;
            default:    
                filteredPrice = itemserviceImpl.getAllItems();
                break;
        }
        model.addAttribute("items",filteredPrice);
        return "listing";
    }  
    
    //catalog = view product details
    @GetMapping("/listing/{id}/viewItem")
    public String getProductDetails(@PathVariable(value = "id")Long id, Model model){
        Item item = itemserviceImpl.getItembyId(id);
        // model.addAttribute("items",itemserviceImpl.getAllItems());
        model.addAttribute("items", item);
        return "catalog";
    }
    
}