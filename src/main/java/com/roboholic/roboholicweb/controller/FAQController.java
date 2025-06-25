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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.access.prepost.PreAuthorize;

import com.roboholic.roboholicweb.entity.FAQ;
import com.roboholic.roboholicweb.service.FAQServiceImpl;

@Controller
public class FAQController {
    
    private FAQServiceImpl faqServiceImpl;

    public FAQController(FAQServiceImpl faqServiceImpl) {
        this.faqServiceImpl = faqServiceImpl;
    }

    // Admin-only FAQ view
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/faq")
    public String viewAllFAQs(Model model) {
        model.addAttribute("faqs", faqServiceImpl.getAllFAQs());
        return "faq";
    }

    // Public FAQ view
    @GetMapping("/faqUserView")
    public String viewPublicFAQs(Model model) {
        model.addAttribute("faqs", faqServiceImpl.getAllFAQs());
        return "faqUserView";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/faq/add")
    public String addFAQForm(Model model) {
        model.addAttribute("faq", new FAQ());
        return "addfaq";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/faq/add")
    public String addFAQ(@ModelAttribute FAQ faq) {
        faqServiceImpl.addFAQ(faq);
        return "redirect:/faq";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/faq/{id}/delete")
    public String deleteFAQ(@PathVariable(value = "id") Long id) {
        faqServiceImpl.deleteFAQ(id);
        return "redirect:/faq";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/faq/{id}/edit")
    public String editFAQForm(@PathVariable(value = "id") Long id, Model model) {
        FAQ faq = faqServiceImpl.getFAQbyId(id);
        model.addAttribute("faq", faq);
        return "editfaq";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/faq/{id}/edit/save")
    public String editFAQ(@ModelAttribute FAQ faq, @PathVariable("id") Long id) {
        faqServiceImpl.updateFAQ(faq, id);
        return "redirect:/faq";
    }

    @GetMapping("/faqUserView/search")
    public String publicSearchFAQs(@RequestParam(required = false) String category, 
                            @RequestParam(required = false) String search, 
                            Model model) {
        List<FAQ> results;
        if (category != null && !category.isEmpty()) {
            results = faqServiceImpl.searchFAQsByCategory(category);
        } else if (search != null && !search.isEmpty()) {
            results = faqServiceImpl.searchFAQsByQuestion(search);
        } else {
            results = faqServiceImpl.getAllFAQs();
        }
        model.addAttribute("faqs", results);
        return "faqUserView";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/faq/search")
    public String adminSearchFAQs(@RequestParam(required = false) String category, 
                            @RequestParam(required = false) String search, 
                            Model model) {
        List<FAQ> results;
        if (category != null && !category.isEmpty()) {
            results = faqServiceImpl.searchFAQsByCategory(category);
        } else if (search != null && !search.isEmpty()) {
            results = faqServiceImpl.searchFAQsByQuestion(search);
        } else {
            results = faqServiceImpl.getAllFAQs();
        }
        model.addAttribute("faqs", results);
        return "faq";
    }
}