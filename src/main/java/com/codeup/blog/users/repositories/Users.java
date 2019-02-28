package com.codeup.blog.users.repositories;


import com.codeup.blog.users.User;
import org.springframework.data.repository.CrudRepository;


public interface Users extends CrudRepository<User, Long> {

    User findByUsername(String username);
}



