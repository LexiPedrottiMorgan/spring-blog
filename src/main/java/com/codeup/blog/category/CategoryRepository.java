package com.codeup.blog.category;

import com.codeup.blog.posts.Post;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CategoryRepository extends CrudRepository<Category, Long>  {

}


