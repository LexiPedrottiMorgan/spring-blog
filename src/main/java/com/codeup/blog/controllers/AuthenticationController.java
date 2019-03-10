package com.codeup.blog.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthenticationController {

    // login form:
    @GetMapping("/login")
    public String showLoginForm() {
        return "user/login";
    }


}
