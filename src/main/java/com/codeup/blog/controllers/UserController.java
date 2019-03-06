package com.codeup.blog.controllers;

import com.codeup.blog.posts.Post;
import com.codeup.blog.posts.PostRepository;
import com.codeup.blog.users.User;
import com.codeup.blog.users.repositories.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;


@Controller
public class UserController {

    private Users users;

    private PasswordEncoder passwordEncoder;

    public UserController(Users users, PasswordEncoder passwordEncoder){
        this.users = users;
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    private PostRepository postDao;


//  view form to create a user:
    @GetMapping("/sign-up")
    public String showSignupForm(Model model) {
        model.addAttribute("user", new User());
        return "user/sign-up";
    }


//  create a new user with a profile picture:
    @Value("${file-upload-path}")
    private String uploadPath;


    @PostMapping("/sign-up")
    public String saveUser(@ModelAttribute User user, @RequestParam(name = "file") MultipartFile uploadedFile, Model model) {
        String hash = passwordEncoder.encode(user.getPassword());
        user.setPassword(hash);

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

        user.setProfilePicture(filename);
        users.save(user);
        return "redirect:/login";
    }


    @GetMapping("/profile")
    public String currentUser(Model model){
    User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    model.addAttribute("user", loggedInUser);
    User dbUser = users.findOne(loggedInUser.getId());
//  get the users ads and display them on the users page:
    List <Post> usersPosts = dbUser.getPosts();
    model.addAttribute("usersPosts", usersPosts);
    return "profile";
    }

//   closes the class:
}