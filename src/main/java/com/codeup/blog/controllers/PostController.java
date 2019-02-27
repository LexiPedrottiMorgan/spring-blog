package com.codeup.blog.controllers;
import com.codeup.blog.posts.Post;
import com.codeup.blog.posts.PostRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PostController {

    private final PostRepository postDao;

    public PostController(PostRepository postDao){
        this.postDao = postDao;
    }


    //  posts page:
    @GetMapping("/posts")
    public String all(Model model) {
        Iterable<Post> posts = postDao.findAll();
        model.addAttribute("posts", posts);
        return "posts/index";
    }


//  individual post page:
    @GetMapping("/posts/{id}")
    public String show(@PathVariable long id, Model model) {
        Post post = postDao.findOne(id);
        model.addAttribute("post", post);
        return "posts/show";
    }


//  view form to create a post:
    @GetMapping("/posts/create")
    public String showForm() {
        return "posts/create";
    }


//  create a new post:
    @PostMapping("/posts/create")
    public String create(@RequestParam(name= "title") String title, @RequestParam(name="body") String body) {
        Post newPost = new Post(title, body);
        postDao.save(newPost);
        return "redirect:/posts";


//     TODO:add in feature to upload images:
//    // if no image is uploaded then apply the default placeholder image:
//        if(image==null){
//            image = "/img/computer.jpg";
//        }
    }


    @GetMapping("/posts/delete")
    public String getPostToDelete(@RequestParam(name="id") long id, Model model){
        Post post = postDao.findOne(id);
        model.addAttribute("post", post);
        return "posts/delete";
    }

    @PostMapping("/posts/delete")
    public String delete(@RequestParam(name="id") long id){
        Post deleteThis = postDao.findOne(id);
        postDao.delete(deleteThis);
        return "posts/delete-success";
    }


    @GetMapping("/posts/edit")
    public String editForm(@RequestParam(name="id") long id, Model model){
        Post editThisPost = postDao.findOne(id);
        model.addAttribute("post", editThisPost);
        return "posts/edit";
    }


    @PostMapping("/posts/edit")
    public String edit(@RequestParam(name="id") long id, @RequestParam(name= "title") String title, @RequestParam(name="body") String body){
        Post editedPost = postDao.findOne(id);
        editedPost.setTitle(title);
        editedPost.setBody(body);
        postDao.save(editedPost);
        return "redirect:/posts";
    }



// closes the class:
}