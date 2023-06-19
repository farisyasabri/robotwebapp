package com.roboholic.roboholicweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.roboholic.roboholicweb.dto.UserRegistrationDTO;
import com.roboholic.roboholicweb.entity.User;
import com.roboholic.roboholicweb.service.UserService;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/all-users")
    public ModelAndView showAllusers() {
        ModelAndView mav = new ModelAndView("all-users");
        mav.addObject("users", userService.getAllUsers());
        // model.addAttribute("users", userService.getAllUsers());
        // return "success";
        return mav;

    }

    @GetMapping("/register")
    public ModelAndView showRegistrationForm() {
        ModelAndView mav = new ModelAndView("register");
        mav.addObject("user", new UserRegistrationDTO());
        // model.addAttribute("user", new UserRegistrationDTO());
        // return "register";
        return mav;
    }

    @PostMapping("/register")
    public String registerAccount(@ModelAttribute("user") UserRegistrationDTO userRegistrationDTO) {
        // need to check user has existing account or not - by checking email exist in
        // database or not
        userService.add(userRegistrationDTO);
        return "redirect:/all-users";
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        // ModelAndView mav = new ModelAndView("login");
        // mav.addObject("user", mav);
        model.addAttribute("user", new UserRegistrationDTO());
        return "login";
        // return mav;
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("user") UserRegistrationDTO userRegistrationDTO) {
        // need to check user has register or not - by checking the email

        if (userService.getUserByEmail(userRegistrationDTO.getEmail()) != null) {
            return "redirect:/all-users";
        } else {
            return null;
        }

        // need to check password correct or not

    }

    @GetMapping("/profile/{id}")
    public ModelAndView showProfile(@PathVariable(value = "id") Long id) {
        ModelAndView mav = new ModelAndView("profile");
        User user = userService.getUserById(id);
        mav.addObject("user", user);
        return mav;
        // model.addAttribute("user", userService.getUserById(id));
        // return "profile";
    }

    // @GetMapping("/edit/{id}")
    // public String editUserProfile(@PathVariable(value = "id") Long id, Model
    // model) {
    // model.addAttribute("user", userService.getUserById(id));
    // return "edit-profile";
    // }

    @PostMapping("/profile/{id}/update")
    public String editProfile(@PathVariable("id") Long id, @ModelAttribute("user") User updatedUser) {
        userService.updateUserById(updatedUser.getUserID(), updatedUser);

        return "redirect:/profile/" + updatedUser.getUserID();
    }

    // @GetMapping("/delete/{id}")
    // public ModelAndView deleteUser(@PathVariable(value = "id") Long id) {
    //     ModelAndView mav = new ModelAndView("all-users");
    //     mav.
    //     // userService.deleteUserById(id);
    //     // return "redirect:/all-users";
    // }

    // @DeleteMapping("/delete/{id}")
    // public RedirectView deleteUser(@PathVariable("id") Long userId,
    // RedirectAttributes attributes) {
    // // Delete the user with the given ID
    // userService.deleteUserById(userId);

    // // Add a flash attribute to carry a message to the redirected page
    // attributes.addFlashAttribute("message", "User deleted successfully");

    // // Redirect to the desired page using a GET request
    // return new RedirectView("/all-users", true);
    // }

    // @GetMapping("/")
    // public String redirectByRole() {

    // if (userService.getCurrentUser().getRole().equals("ADMIN")) {
    // return "redirect:/admin/companies";
    // } else if (userService.getCurrentUser().getRole().equals("USER")) {
    // return "redirect:/user/mainSearchTrip";
    // }
    // return "redirect:/";
    // }
}
