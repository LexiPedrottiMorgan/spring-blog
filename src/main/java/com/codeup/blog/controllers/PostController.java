package com.codeup.blog.controllers;
import com.codeup.blog.posts.Post;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PostController {

    //  posts page:
    @GetMapping("/posts")
    public String all(Model model) {
//    create a list:
        List<Post> postList = new ArrayList<>();
//    create ad objects:
        Post post1 = new Post("Title 1", "This is the body of Test 1", 1);
        Post post2 = new Post("Title 2", "This is the body of Test 2", 2);
//    add the post objects to the list:
        postList.add(post1);
        postList.add(post2);
//    send the list of post objects to the view:
        model.addAttribute("postList", postList);
        return "posts/index";
    }


//  individual post page:
    @GetMapping("/posts/{id}")
    public String show(@PathVariable long id, Model model) {
        Post postToView = new Post("Single Post Tester Title", "This is the tester body. This is a test blog post to show a single posts page.", id);
        String title = postToView.getTitle();
        String body = postToView.getBody();
        model.addAttribute("postToView", postToView);
        model.addAttribute("title", title);
        model.addAttribute("body", body);
        return "/posts/show";
    }

// send to profile
    @GetMapping("/profile")
        public String usersProfile(){
            return "profile";
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