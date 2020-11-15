package com.team4.finding_sw_developers;

public class User {
    private String id;
    private String username;
    private String imageURL;
    private String status;
    private String auth;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public User(String id, String username, String imageURL, String status, String auth) {
        this.id = id;
        this.username = username;
        this.imageURL = imageURL;
        this.status = status;
        this.auth = auth;
    }
}