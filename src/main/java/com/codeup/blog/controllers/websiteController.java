package com.codeup.blog.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class websiteController {


    // send to profile
    @GetMapping("/profile")
    public String usersProfile(){
        return "profile";
    }


    // send to resume
    @GetMapping("/resume")
    public String resume(){
        return "resume";
    }

    // send to contact
    @GetMapping("/contact")
    public String contact(){
        return "contact";
    }

    // send to contact
    @GetMapping("/portfolio")
    public String portfolio(){
        return "portfolio";
    }
}
