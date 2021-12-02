package com.example.climbup1.Model;

public class userProfile {
    public String id;
    public String email;
    public String name;
    public String imageurl;
    public  String bio;
    public String filed;

    public userProfile(String id, String email, String name, String imageurl, String bio, String filed) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.imageurl = imageurl;
        this.bio = bio;
        this.filed = filed;
    }

    public userProfile() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getFiled() {
        return filed;
    }

    public void setFiled(String filed) {
        this.filed = filed;
    }
}
