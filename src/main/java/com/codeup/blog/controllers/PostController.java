package com.codeup.blog.controllers;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PostController {

//  posts page:
    @GetMapping("/posts")
    public String all() {
        return "posts/index";
    }


//  individual post page:
    @GetMapping("/posts/{id}")
    public String show(@PathVariable long id, Model model) {
        model.addAttribute("id", id);
        return "/posts/show";
    }


//  view form to create a post:
    @GetMapping("/posts/create")
    public String showForm() {
        return "/posts/create";
    }


//  create a new post:
    @PostMapping("/posts/create")
    @ResponseBody
    public String create() {
        return "created a new post";
    }



// closes the class:
}