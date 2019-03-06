package com.codeup.blog.posts;


import com.codeup.blog.category.Category;
import com.codeup.blog.users.User;

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

    @ManyToOne
    @JoinColumn (name = "user_id")
    private User user;


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

    public Post(String title, String body, String image, User user) {
        this.title = title;
        this.body = body;
        this.image = image;
        this.user = user;
    }

    public Post(String title, String body, String image, User user, List<Category> categories) {
        this.title = title;
        this.body = body;
        this.image = image;
        this.user = user;
        this.categories = categories;
    }

    public Post(String title, String body, long id) {
        this.title = title;
        this.body = body;
        this.id = id;
    }

    public Post(String title, String body, long id, User user) {
        this.title = title;
        this.body = body;
        this.id = id;
        this.user= user;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<Category> getCategories() {
        return categories;
    }
}
