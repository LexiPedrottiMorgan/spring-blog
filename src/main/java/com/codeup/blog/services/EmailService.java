package com.codeup.blog.services;

import com.codeup.blog.posts.Post;
import com.codeup.blog.users.User;
import com.codeup.blog.users.repositories.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service("mailService")
public class EmailService {


   @Autowired
   public JavaMailSender emailSender;

    @Autowired
    private EmailService emailService;


    @Autowired
    private Users usersDao;

   @Value("${spring.mail.from}")
   private String from;

   public void prepareAndSend(Post post, String subject, String body) {
       Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
       String username = ((UserDetails)principal).getUsername();
       User user = usersDao.findByUsername(username);

       SimpleMailMessage msg = new SimpleMailMessage();
       msg.setFrom(from);
       msg.setTo(user.getEmail());
       msg.setSubject(subject);
       msg.setText(body);
       try{
           this.emailSender.send(msg);
       } catch (MailException ex) {
           System.err.println(ex.getMessage());
       }

    }

}
