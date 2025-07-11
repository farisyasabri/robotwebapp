// package com.roboholic.roboholicweb.controller;

// import java.util.List;

// import org.springframework.stereotype.Controller;
// import org.springframework.ui.Model;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.ModelAttribute;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestParam;

// import com.roboholic.roboholicweb.entity.FAQ;
// import com.roboholic.roboholicweb.service.FAQServiceImpl;

// @Controller
// public class FAQController {
    
//     private FAQServiceImpl faqServiceImpl;

//     public FAQController(FAQServiceImpl faqServiceImpl) {
//         this.faqServiceImpl = faqServiceImpl;
//     }

//     @GetMapping("/faq")
//     public String viewAllFAQs(Model model) {
//         model.addAttribute("faqs", faqServiceImpl.getAllFAQs());
//         return "faq";
//     }

//     @GetMapping("/faq/add")
//     public String addFAQForm(Model model) {
//         model.addAttribute("faq", new FAQ());
//         return "addfaq";
//     }

//     @PostMapping("/faq/add")
//     public String addFAQ(@ModelAttribute FAQ faq) {
//         faqServiceImpl.addFAQ(faq);
//         return "redirect:/faq";
//     }

//     @GetMapping("/faq/{id}/delete")
//     public String deleteFAQ(@PathVariable(value = "id") Long id) {
//         faqServiceImpl.deleteFAQ(id);
//         return "redirect:/faq";
//     }

//     @GetMapping("/faq/{id}/edit")
//     public String editFAQForm(@PathVariable(value = "id") Long id, Model model) {
//         FAQ faq = faqServiceImpl.getFAQbyId(id);
//         model.addAttribute("faq", faq);
//         return "editfaq";
//     }

//     @PostMapping("/faq/{id}/edit/save")
//     public String editFAQ(@ModelAttribute FAQ faq, @PathVariable("id") Long id) {
//         faqServiceImpl.updateFAQ(faq, id);
//         return "redirect:/faq";
//     }

//     @GetMapping("/faq/search")
//     public String searchFAQs(@RequestParam(required = false) String category, 
//                             @RequestParam(required = false) String search, 
//                             Model model) {
//         List<FAQ> results;
//         if (category != null && !category.isEmpty()) {
//             results = faqServiceImpl.searchFAQsByCategory(category);
//         } else if (search != null && !search.isEmpty()) {
//             results = faqServiceImpl.searchFAQsByQuestion(search);
//         } else {
//             results = faqServiceImpl.getAllFAQs();
//         }
//         model.addAttribute("faqs", results);
//         return "faq";
//     }
// }

package com.roboholic.roboholicweb.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.security.access.prepost.PreAuthorize;

import com.roboholic.roboholicweb.entity.FAQ;
import com.roboholic.roboholicweb.service.FAQServiceImpl;

import jakarta.validation.Valid;

@Controller
public class FAQController {
    
    private static final Logger logger = LoggerFactory.getLogger(FAQController.class);
    private FAQServiceImpl faqServiceImpl;

    public FAQController(FAQServiceImpl faqServiceImpl) {
        this.faqServiceImpl = faqServiceImpl;
    }

    // Admin-only FAQ view
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/faq")
    public String viewAllFAQs(Model model) {
        try{
            model.addAttribute("faqs", faqServiceImpl.getAllFAQs());
            return "faq";
        } catch(DataAccessException e){
            logger.error("Database error while fetching FAQs", e);
            model.addAttribute("error", "Failed to load FAQs due to database error");
            return "faq";
        } catch (Exception e) {
            logger.error("Unexpected error while fetching FAQs", e);
            model.addAttribute("error", "An unexpected error occurred");
            return "faq";
        }
        
    }

    // Public FAQ view
    @GetMapping("/faqUserView")
    public String viewPublicFAQs(Model model) {
        try{
            model.addAttribute("faqs", faqServiceImpl.getAllFAQs());
            return "faqUserView";
        } catch (Exception e) {
            logger.error("Error loading public FAQs", e);
            model.addAttribute("error", "Failed to load FAQs");
            return "faqUserView";
        }
        
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/faq/add")
    public String addFAQForm(Model model) {
        model.addAttribute("faq", new FAQ());
        return "addfaq";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/faq/add")
    public String addFAQ(@Valid @ModelAttribute FAQ faq, 
                        BindingResult result,
                        RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "addfaq";
        }
        
        try {
            faqServiceImpl.addFAQ(faq);
            redirectAttributes.addFlashAttribute("success", "FAQ added successfully");
            return "redirect:/faq";
        } catch (DataAccessException e) {
            logger.error("Database error while adding FAQ", e);
            redirectAttributes.addFlashAttribute("error", "Failed to add FAQ due to database error");
            return "redirect:/faq/add";
        } catch (Exception e) {
            logger.error("Unexpected error while adding FAQ", e);
            redirectAttributes.addFlashAttribute("error", "Failed to add FAQ");
            return "redirect:/faq/add";
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/faq/{id}/delete")
    public String deleteFAQ(@PathVariable(value = "id") Long id, 
                          RedirectAttributes redirectAttributes) {
        try {
            faqServiceImpl.deleteFAQ(id);
            redirectAttributes.addFlashAttribute("success", "FAQ deleted successfully");
        } catch (DataAccessException e) {
            logger.error("Database error while deleting FAQ with id: " + id, e);
            redirectAttributes.addFlashAttribute("error", "Failed to delete FAQ due to database error");
        } catch (Exception e) {
            logger.error("Unexpected error while deleting FAQ with id: " + id, e);
            redirectAttributes.addFlashAttribute("error", "Failed to delete FAQ");
        }
        return "redirect:/faq";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/faq/{id}/edit")
    public String editFAQForm(@PathVariable(value = "id") Long id, Model model) {
        try {
            FAQ faq = faqServiceImpl.getFAQbyId(id);
            model.addAttribute("faq", faq);
            return "editfaq";
        } catch (RuntimeException e) {
            logger.error("FAQ not found with id: " + id, e);
            model.addAttribute("error", "FAQ not found");
            return "redirect:/faq";
        } catch (Exception e) {
            logger.error("Error loading FAQ for editing with id: " + id, e);
            model.addAttribute("error", "Error loading FAQ");
            return "redirect:/faq";
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/faq/{id}/edit/save")
    public String editFAQ(@Valid @ModelAttribute FAQ faq, 
                        BindingResult result,
                        @PathVariable("id") Long id,
                        RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "editfaq";
        }
        
        try {
            faqServiceImpl.updateFAQ(faq, id);
            redirectAttributes.addFlashAttribute("success", "FAQ updated successfully");
            return "redirect:/faq";
        } catch (DataAccessException e) {
            logger.error("Database error while updating FAQ with id: " + id, e);
            redirectAttributes.addFlashAttribute("error", "Failed to update FAQ due to database error");
            return "redirect:/faq/" + id + "/edit";
        } catch (Exception e) {
            logger.error("Unexpected error while updating FAQ with id: " + id, e);
            redirectAttributes.addFlashAttribute("error", "Failed to update FAQ");
            return "redirect:/faq/" + id + "/edit";
        }
    }
    @GetMapping("/faqUserView/search")
    public String publicSearchFAQs(@RequestParam(required = false) String category, 
                                 @RequestParam(required = false) String search, 
                                 Model model) {
        try {
            List<FAQ> results = getSearchResults(category, search);
            model.addAttribute("faqs", results);
            return "faqUserView";
        } catch (Exception e) {
            logger.error("Error searching FAQs", e);
            model.addAttribute("error", "Error searching FAQs");
            model.addAttribute("faqs", faqServiceImpl.getAllFAQs());
            return "faqUserView";
        }
    }

    private List<FAQ> getSearchResults(String category, String search) {
        if (category != null && !category.isEmpty()) {
            return faqServiceImpl.searchFAQsByCategory(category);
        } else if (search != null && !search.isEmpty()) {
            return faqServiceImpl.searchFAQsByQuestion(search);
        } else {
            return faqServiceImpl.getAllFAQs();
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/faq/search")
    public String adminSearchFAQs(@RequestParam(required = false) String category, 
                                @RequestParam(required = false) String search, 
                                Model model) {
        try {
            List<FAQ> results = getSearchResults(category, search);
            model.addAttribute("faqs", results);
            return "faq";
        } catch (Exception e) {
            logger.error("Error searching FAQs", e);
            model.addAttribute("error", "Error searching FAQs");
            model.addAttribute("faqs", faqServiceImpl.getAllFAQs());
            return "faq";
        }
    }
}