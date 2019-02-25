package com.codeup.blog.controllers;

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
    public String postsPage() {
        return "posts index page";
    }


//  individual post page:
    @GetMapping("/posts/{id}")
    @ResponseBody
    public String individualPost(@PathVariable int id) {
        return "view an individual post id number: " + id;
    }


//  view form to create a post:
    @GetMapping("/posts/create")
    @ResponseBody
    public String createPost() {
        return "view the form for creating a post";
    }


//  create a new post:
    @PostMapping("/posts/create")
    @ResponseBody
    public String submitNewPost() {
        return "create a new post";
    }


// closes the class:
}