package com.codeup.blog.controllers;
import com.codeup.blog.posts.Post;
import com.codeup.blog.posts.PostRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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


//  create a new post:
    @PostMapping("/posts/create")
    public String create(@RequestParam(name= "title") String title, @RequestParam(name="body") String body) {
        Post newPost = new Post(title, body);
        System.out.println(newPost);
//        postDao.save(newPost);
        return "redirect:/posts";
    }

//    Trying to create with a file uploaded: not finished
//    @PostMapping("/posts/create")
//    public String create(@RequestParam(name= "title") String title, @RequestParam(name="body") String body, Path image) {
//        image = path;
//        String imageString = image.toString();
//        System.out.println(imageString);
//
//        Post newPost = new Post(title, body, imageString);
//        System.out.println(newPost);
////        postDao.save(newPost);
//        return "redirect:/posts";
//    }


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
        return "redirect:/posts/" + id;
    }


//    IMAGE UPLOADING:

//    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
//    public String submit(@RequestParam("file") MultipartFile file, ModelMap modelMap) {
//        modelMap.addAttribute("file", file);
//        return "fileUploadView";
//    }

        private static String UPLOADED_FOLDER = "/static/uploaded-img/";

        @GetMapping("/upload")
        public String index() {
            return "upload";
        }


//Path is out here so that it can be accessed in the create method
        Path path;
        @PostMapping("/upload")
        public String singleFileUpload(@RequestParam("file") MultipartFile file,
                                       RedirectAttributes redirectAttributes) {


            if (file.isEmpty()) {
                redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
                return "redirect:uploadStatus";
            }

            try {

                // Get the file and save it somewhere
                byte[] bytes = file.getBytes();
                path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
                Files.write(path, bytes);


                redirectAttributes.addFlashAttribute("message",
                        "You successfully uploaded '" + file.getOriginalFilename() + "'");

            } catch (IOException e) {
                e.printStackTrace();
            }

            return "redirect:/uploadStatus";
        }

    @GetMapping("/uploadStatus")
    public String uploadStatus() {
        return "uploadStatus";
    }



// closes the class:
}