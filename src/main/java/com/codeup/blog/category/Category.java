package com.codeup.blog.category;

import com.codeup.blog.posts.Post;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="categories")
public class Category {

        @Id
        @GeneratedValue
        private long id;

        @Column(nullable=false)
        private String category;

        @Column(nullable=false)
        private String icon;

        @ManyToMany(mappedBy="categories")
        private List<Post> posts;


        public Category(String category, String icon, List<Post> posts) {
                this.category = category;
                this.icon = icon;
                this.posts = posts;
        }

        public Category() {}

        public long getId() {
                return id;
        }

        public void setId(long id) {
                this.id = id;
        }

        public String getCategory() {
                return category;
        }

        public void setCategory(String category) {
                this.category = category;
        }

        public String getIcon() {
                return icon;
        }

        public void setIcon(String icon) {
                this.icon = icon;
        }

        public List<Post> getPosts() {
                return posts;
        }

        public void setPosts(List<Post> posts) {
                this.posts = posts;
        }
}


//TODO:
//make a column for icons ---> DONE
//name=category for all of them
//value = each categories id from the db
//create a for loop to display all of the checkboxes that it pulls from the db
//dont have the categories hard coded in the html
//save the class for each icon in a column and then display that on the page