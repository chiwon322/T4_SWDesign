package com.team4.finding_sw_developers;

import java.util.ArrayList;

public class User {
    private String id;
    private String username;
    private String imageURL;
    private String status;
    private String auth;
    private ArrayList<String> visitlist;
   /* private ArrayList<String> interestfiled;

    public ArrayList<String> getInterest_field() {
        return interestfiled;
    }

    public void setInterest_field(ArrayList<String> interestfiled) {
        this.interestfiled = interestfiled;
    }*/

    public ArrayList<String> getVisitlist() {
        return visitlist;
    }

    public void setVisitlist(ArrayList<String> visitlist) {
        this.visitlist = visitlist;
    }


    public String getId() {
        return id;
    }

    public User() {

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
