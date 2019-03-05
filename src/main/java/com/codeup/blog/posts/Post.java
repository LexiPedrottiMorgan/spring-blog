package com.codeup.blog.posts;


import com.codeup.blog.category.Category;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="posts")
public class Post {
    @Id @GeneratedValue
    private long id;

    @Column(nullable=false, length=150)
    private String title;

    @Column(nullable=false, columnDefinition = "TEXT")
    private String body;


    @Column
    private String image;

    @Column
    private long user_id;


    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name="posts_categories",
            joinColumns={@JoinColumn(name="post_id")},
            inverseJoinColumns={@JoinColumn(name="category_id")}
    )


    private List<Category> categories;






    //  default constructor
    public Post(){}

    public Post(String title, String body) {
        this.title = title;
        this.body = body;
    }

    public Post(String title, String body, String image, long user_id) {
        this.title = title;
        this.body = body;
        this.image = image;
        this.user_id = user_id;
    }

    public Post(String title, String body, String image, long user_id, List<Category> categories) {
        this.title = title;
        this.body = body;
        this.image = image;
        this.user_id = user_id;
        this.categories = categories;
    }

    public Post(String title, String body, long id) {
        this.title = title;
        this.body = body;
        this.id = id;
    }

    public Post(String title, String body, long id, long user_id) {
        this.title = title;
        this.body = body;
        this.id = id;
        this.user_id = user_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getImage() { return image; }

    public void setImage(String image) {
        this.image = image;
    }

    public long getUserId() {
        return user_id;
    }

    public void setUserId(long user_id) {
        this.user_id = user_id;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<Category> getCategories() {
        return categories;
    }
}
