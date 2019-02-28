package com.codeup.blog.controllers;
import com.codeup.blog.posts.Post;
import com.codeup.blog.posts.PostRepository;
import com.codeup.blog.users.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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


//  create a new post with an image upload:
//  this is the file upload path to be used in uploading post pictures:
    @Value("${post-file-upload-path}")
    private String uploadPath;

    @PostMapping("/posts/create")
    public String create(@RequestParam(name= "title") String title, @RequestParam(name="body") String body, @RequestParam(name = "file") MultipartFile uploadedFile, Model model) {

        String filename = uploadedFile.getOriginalFilename();
        String filepath = Paths.get(uploadPath, filename).toString();
        File destinationFile = new File(filepath);

        try {
            uploadedFile.transferTo(destinationFile);
            model.addAttribute("message", "File successfully uploaded!");
        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("message", "Oops! Something went wrong! " + e);
        }

        Post newPost = new Post(title, body, filename);
        long newPostView = newPost.getId();
        System.out.println(newPost);
        postDao.save(newPost);
        return "redirect:/posts";
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
    public String edit(@RequestParam(name="id") long id, @RequestParam(name= "title") String title, @RequestParam(name="body") String body, @RequestParam(name = "file") MultipartFile uploadedFile, Model model){
        Post editedPost = postDao.findOne(id);

        String filename = uploadedFile.getOriginalFilename();
        String filepath = Paths.get(uploadPath, filename).toString();
        File destinationFile = new File(filepath);

        try {
            uploadedFile.transferTo(destinationFile);
            model.addAttribute("message", "File successfully uploaded!");
        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("message", "Oops! Something went wrong! " + e);
        }

        editedPost.setImage(filename);
        editedPost.setTitle(title);
        editedPost.setBody(body);
        postDao.save(editedPost);
        return "redirect:/posts/" + id;
    }


// closes the class:
}