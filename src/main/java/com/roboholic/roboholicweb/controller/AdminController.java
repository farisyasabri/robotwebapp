// package com.roboholic.roboholicweb.controller;

// import org.springframework.stereotype.Controller;
// import org.springframework.web.bind.annotation.GetMapping;

// @Controller
// // @RequestMapping("/admin")
// public class AdminController {

//     @GetMapping("/index")
//     public String dashboard() {
//         return "index";
//     }
// }

package com.roboholic.roboholicweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @GetMapping("/admin/dashboard")
    public String dashboard() {
        return "admin-dashboard";
    }
}