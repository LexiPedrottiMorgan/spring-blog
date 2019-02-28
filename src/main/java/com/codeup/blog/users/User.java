package com.codeup.blog.users;

import javax.persistence.*;


    @Entity
    @Table(name="users")
    public class User {
        @Id
        @GeneratedValue
        private long id;

        @Column(nullable=false, length=150, unique=true)
        private String username;

        @Column(nullable=false)
        private String password;

        @Column(nullable=false, length=150, unique=true)
        private String email;

        @Column
        private String profilePicture;

        //  default constructor
        public User(){}

        public User(String username, String password, String email, String profilePicture) {
            this.username = username;
            this.password = password;
            this.email = email;
            this.profilePicture = profilePicture;
        }

        public User(User copy) {
            id = copy.id; // This line is SUPER important! Many things won't work if it's absent
            email = copy.email;
            username = copy.username;
            password = copy.password;
            profilePicture = copy.profilePicture;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getProfilePicture() {
            return profilePicture;
        }

        public void setProfilePicture(String profilePicture) {
            this.profilePicture = profilePicture;
        }
    }
