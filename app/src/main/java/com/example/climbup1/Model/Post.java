package com.example.climbup1.Model;

public class Post {
    private String postid;
    private String postimage;
    private String description;
    private String publiser;

    public Post(String postid, String postimage, String description, String publiser) {
        this.postid = postid;
        this.postimage = postimage;
        this.description = description;
        this.publiser = publiser;
    }

    public Post() {
    }

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }

    public String getPostimage() {
        return postimage;
    }

    public void setPostimage(String postimage) {
        this.postimage = postimage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPubliser() {
        return publiser;
    }

    public void setPubliser(String publiser) {
        this.publiser = publiser;
    }
}
