package com.codeup.blog.controllers;
import org.springframework.stereotype.Controller;
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
    @ResponseBody
    public String all() {
        return "posts index page";
    }


//  individual post page:
    @GetMapping("/posts/{id}")
    @ResponseBody
    public String show(@PathVariable long id) {
        return "view an individual post id number: " + id;
    }


//  view form to create a post:
    @GetMapping("/posts/create")
    @ResponseBody
    public String showForm() {
        return "view the form for creating a post";
    }


//  create a new post:
    @PostMapping("/posts/create")
    @ResponseBody
    public String create() {
        return "create a new post";
    }



// closes the class:
}