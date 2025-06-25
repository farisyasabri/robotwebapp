// package com.roboholic.roboholicweb.controller;

// import java.util.List;

// import org.springframework.stereotype.Controller;
// import org.springframework.ui.Model;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.ModelAttribute;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PostMapping;
// // import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestParam;

// import com.roboholic.roboholicweb.entity.Item;
// import com.roboholic.roboholicweb.service.ItemServiceImpl;

// @Controller
// public class ItemController {
//     private ItemServiceImpl itemserviceImpl;

//     public ItemController(ItemServiceImpl itemservice){
//         this.itemserviceImpl = itemservice;
//     }

//     @GetMapping("/addnewproduct")
//     public String addNewProductForm(Model model) {
//         // Iterable<Item> items = itemservice.getAllItems();
//         // model.addAttribute("items", items);
//         model.addAttribute("items", new Item());
//         return "additem";
//     }

//     @GetMapping("/listing")
//     public String listing(Model model) {
//         model.addAttribute("items",itemserviceImpl.getAllItems());
//         return "listing";
//     }

//     @PostMapping("/addnewproduct")
//     public String addNewProduct(Item item) {
//         itemserviceImpl.addItem(item);
//         return "redirect:/listing";
//     }

//     @GetMapping("/listing/{id}/delete")
//     public String deleteproduct(@PathVariable (value = "id") Long id) {
//         itemserviceImpl.deleteItem(id);
//         return "redirect:/listing";
//     }
    

//     //update product page
//     @GetMapping("/listing/{id}/edit")
//     public String editproduct(@PathVariable (value = "id") Long id, Model model) {

//         Item item = itemserviceImpl.getItembyId(id);
//         model.addAttribute("items", item);

//         return "updateitem";
//     }

//     @PostMapping("/listing/{id}/edit/save")
//     public String editItem(@ModelAttribute Item item, @PathVariable("id") Long id){
//         itemserviceImpl.updateItem(item, id);
//         return "redirect:/listing";
//     }

//     //filter items by name
//     @GetMapping("/listing/searchname")
//     public String getListingByName(@RequestParam(required = false) String filter, Model model) {
//         List<Item> filteredNames;
//         if (filter != null && !filter.isEmpty()) {
//             filteredNames = itemserviceImpl.searchItemByName(filter);
//         }else{
//             filteredNames = itemserviceImpl.getAllItems();
//             // System.out.println();
//         }
//         model.addAttribute("items", filteredNames);
//         return "listing";
//     }

//     //filter items by price
//     @GetMapping("/listing/searchprice")
//     public String getListingByPrice(@RequestParam(required = false) String priceRange, Model model){
//         List<Item> filteredPrice;
//         switch (priceRange) {
//             case "under50":
//                 filteredPrice = itemserviceImpl.searchItemsByPrice(0.00,50.00); //boleh
//                 break;
//             case "50to100":
//                 filteredPrice = itemserviceImpl.searchItemsByPrice(50.01,100.00); // boleh
//                 break;
//             case "above100":
//                 filteredPrice = itemserviceImpl.searchItemsByPrice(100.01,1000.00);
//                 break;
//             default:    
//                 filteredPrice = itemserviceImpl.getAllItems();
//                 break;
//         }
//         model.addAttribute("items",filteredPrice);
//         return "listing";
//     }  
    
//     //catalog = view product details
//     @GetMapping("/listing/{id}/viewItem")
//     public String getProductDetails(@PathVariable(value = "id")Long id, Model model){
//         Item item = itemserviceImpl.getItembyId(id);
//         // model.addAttribute("items",itemserviceImpl.getAllItems());
//         model.addAttribute("items", item);
//         return "catalog";
//     }
    
// }

package com.roboholic.roboholicweb.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.BindingResultUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;

import com.roboholic.roboholicweb.entity.Item;
import com.roboholic.roboholicweb.service.CloudinaryService;
import com.roboholic.roboholicweb.service.ItemServiceImpl;

import jakarta.validation.Valid;

@Controller
public class ItemController {
    private ItemServiceImpl itemserviceImpl;

    @Autowired
    private CloudinaryService cloudinaryService;

    public ItemController(ItemServiceImpl itemservice){
        this.itemserviceImpl = itemservice;
    }

    // Admin-only views and operations
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/addnewproduct")
    public String addNewProductForm(Model model) {
        model.addAttribute("items", new Item());
        return "additem";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/listing")
    public String adminListing(Model model) {
        model.addAttribute("items", itemserviceImpl.getAllItems());
        return "listing";
    }

    // @PreAuthorize("hasRole('ADMIN')")
    // @PostMapping("/addnewproduct")
    // public String addNewProduct(Item item) {
    //     itemserviceImpl.addItem(item);
    //     return "redirect:/listing";
    // }

    // @PreAuthorize("hasRole('ADMIN')")
    // @PostMapping("/addnewproduct")
    // public String addNewProduct(@Valid @ModelAttribute("items") Item item, BindingResult result, Model model) {

    //     if (result.hasErrors()) {
    //         return "additem"; // return to form if validation
    //     }
    //     itemserviceImpl.addItem(item);
    //     return "redirect:/listing";
    // }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/addnewproduct")
    public String addNewProduct(@Valid @ModelAttribute("items") Item item, 
                              BindingResult result, 
                              Model model,
                              @RequestParam(value = "productImages" , required = false) MultipartFile[] files) 
                              throws IOException {
        
        if (result.hasErrors()) {
            return "additem";
        }
        
        // Upload images to Cloudinary
        if (files != null && files.length > 0 && !files[0].isEmpty()) {
            List<String> imageUrls = cloudinaryService.uploadImages(files);
            item.setImageUrls(imageUrls);
        }
        
        itemserviceImpl.addItem(item);
        return "redirect:/listing";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/listing/{id}/delete")
    public String deleteproduct(@PathVariable (value = "id") Long id) {
        itemserviceImpl.deleteItem(id);
        return "redirect:/listing";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/listing/{id}/edit")
    public String editproduct(@PathVariable (value = "id") Long id, Model model) {
        Item item = itemserviceImpl.getItembyId(id);
        model.addAttribute("items", item);
        return "updateitem";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/listing/{id}/edit/save")
    public String editItem(@ModelAttribute Item item, @PathVariable("id") Long id) {
        itemserviceImpl.updateItem(item, id);
        return "redirect:/listing";
    }

    // Public user view
    @GetMapping("/listingUserView")
    public String userListing(Model model) {
        model.addAttribute("items", itemserviceImpl.getAllItems());
        return "listingUserView";
    }

    @GetMapping("/listingUserView/searchname")
    public String publicGetListingByName(@RequestParam(required = false) String filter, Model model) {
        List<Item> filteredNames = filter != null && !filter.isEmpty() 
            ? itemserviceImpl.searchItemByName(filter) 
            : itemserviceImpl.getAllItems();
        model.addAttribute("items", filteredNames);
        return "listingUserView"; 
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/listing/searchname")
    public String adminGetListingByName(@RequestParam(required = false) String filter, Model model) {
        List<Item> filteredNames = filter != null && !filter.isEmpty() 
            ? itemserviceImpl.searchItemByName(filter) 
            : itemserviceImpl.getAllItems();
        model.addAttribute("items", filteredNames);
        return "listing"; 
    }

    @GetMapping("/listingUserView/searchprice")
    public String publicGetListingByPrice(@RequestParam(required = false) String priceRange, Model model) {
        List<Item> filteredPrice;
        switch (priceRange) {
            case "under50": filteredPrice = itemserviceImpl.searchItemsByPrice(0.00,50.00); break;
            case "50to100": filteredPrice = itemserviceImpl.searchItemsByPrice(50.01,100.00); break;
            case "above100": filteredPrice = itemserviceImpl.searchItemsByPrice(100.01,1000.00); break;
            default: filteredPrice = itemserviceImpl.getAllItems(); break;
        }
        model.addAttribute("items", filteredPrice);
        return "listingUserView"; // Or conditionally return different views based on role
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/listing/searchprice")
    public String adminGetListingByPrice(@RequestParam(required = false) String priceRange, Model model) {
        List<Item> filteredPrice;
        switch (priceRange) {
            case "under50": filteredPrice = itemserviceImpl.searchItemsByPrice(0.00,50.00); break;
            case "50to100": filteredPrice = itemserviceImpl.searchItemsByPrice(50.01,100.00); break;
            case "above100": filteredPrice = itemserviceImpl.searchItemsByPrice(100.01,1000.00); break;
            default: filteredPrice = itemserviceImpl.getAllItems(); break;
        }
        model.addAttribute("items", filteredPrice);
        return "listing";
    }  
    

    @GetMapping("/listingUserView/{id}/viewItem")
    public String publicGetProductDetails(@PathVariable(value = "id") Long id, Model model) {
        Item item = itemserviceImpl.getItembyId(id);
        model.addAttribute("items", item);
        return "catalogUserView";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/listing/{id}/viewItem")
    public String adminGetProductDetails(@PathVariable(value = "id") Long id, Model model) {
        Item item = itemserviceImpl.getItembyId(id);
        model.addAttribute("items", item);
        return "catalog";
    }
}