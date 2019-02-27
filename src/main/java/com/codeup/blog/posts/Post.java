package com.codeup.blog.posts;
import javax.persistence.*;

@Entity
@Table(name="posts")
public class Post {
    @Id @GeneratedValue
    private long id;

    @Column(nullable=false, length=150)
    private String title;

    @Column(nullable=false)
    private String body;


//  TODO:add in feature to upload images:
    @Column
    private String image;


//  default constructor
    public Post(){}

    public Post(String title, String body) {
        this.title = title;
        this.body = body;
    }

    public Post(String title, String body, String image) {
        this.title = title;
        this.body = body;
        this.image = image;
    }

    public Post(String title, String body, long id) {
        this.title = title;
        this.body = body;
        this.id = id;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
