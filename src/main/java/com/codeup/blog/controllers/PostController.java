package com.codeup.blog.controllers;
import com.codeup.blog.category.Category;
import com.codeup.blog.category.CategoryRepository;
import com.codeup.blog.posts.Post;
import com.codeup.blog.posts.PostRepository;
import com.codeup.blog.services.EmailService;
import com.codeup.blog.users.User;
import com.codeup.blog.users.repositories.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

@Controller
public class PostController {

    private final PostRepository postDao;

    private final CategoryRepository categoryDao;




    public PostController(PostRepository postDao, CategoryRepository categoryDao){
        this.postDao = postDao;
        this.categoryDao = categoryDao;
    }

    @Autowired
    private Users usersDao;

    @Autowired
    private EmailService emailService;


    //  posts page:
    @GetMapping("/posts")
    public String all(Model model) {
        Iterable<Post> posts = postDao.findAll();
        model.addAttribute("posts", posts);
//        get the logged in user to see if they are the owner of the posts to decide which buttons to show:
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails)principal).getUsername();
        User user = usersDao.findByUsername(username);
        long userId = user.getId();

        boolean showEditDelete = false;
        for(Post post : posts){
            if(userId == post.getUserId()){
                showEditDelete = true;
            } else {
                showEditDelete = false;
            }
        }
        model.addAttribute("showEditDelete", showEditDelete);
        return "posts/index";
    }



//  individual post page:
    @GetMapping("/posts/{id}")
    public String show(@PathVariable long id, Model model) {
        Post post = postDao.findOne(id);
        model.addAttribute("post", post);
    //  to display the username on the post for author
        String username = usersDao.findOne(post.getUserId()).getUsername();
        model.addAttribute("username", username);
        return "posts/show";
    }


//  view form to create a post:
    @GetMapping("/posts/create")
    public String showForm(Model model) {
        Iterable<Category> categories = categoryDao.findAll();
//      show the categories in the form
        model.addAttribute("categories", categories);
        return "posts/create";
    }


//  create a new post with an image upload:
//  this is the file upload path to be used in uploading post pictures:
    @Value("${post-file-upload-path}")
    private String uploadPath;

    @PostMapping("/posts/create")
    public String create(@RequestParam(name= "title") String title, @RequestParam(name="body") String body, @RequestParam(name = "file") MultipartFile uploadedFile, Model model, @RequestParam(name = "category") List<Category> categories) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails)principal).getUsername();
        User user = usersDao.findByUsername(username);
        long userId = user.getId();

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

        Post newPost = new Post(title, body, filename, userId);
        List <Category> saveCategories = categories;
        System.out.println(newPost);
        newPost.setCategories(saveCategories);
        Post savedPost = postDao.save(newPost);
        long newPostView = savedPost.getId();
//        sends a confirmation email to the user that the post was created
        emailService.prepareAndSend(newPost, "Post created successfully", "The post was created with the id: " + newPost.getId());

        return "redirect:/posts/" + newPostView;
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