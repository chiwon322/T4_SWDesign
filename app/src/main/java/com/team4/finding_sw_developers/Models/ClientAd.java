package com.team4.finding_sw_developers.Models;

import java.io.Serializable;

public class ClientAd implements Serializable {
    private String userId;
    private String clientcontext;
    private String clientregion;
    private String clientstarttime;
    private String clientendtime;
    private String clientprepare;
    private String clientbudget;
    private String clienttitle;
    private int clientcategory;

    public String getClienttitle() {
        return clienttitle;
    }

    public void setClienttitle(String clienttitle) {
        this.clienttitle = clienttitle;
    }

    private String key;

    public String getClientbudget() {
        return clientbudget;
    }

    public void setClientbudget(String clientbudget) {
        this.clientbudget = clientbudget;
    }



    public int getClientcategory() {
        return clientcategory;
    }

    public void setClientcategory(int clientcategory) {
        this.clientcategory = clientcategory;
    }


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


    public String getClientcontext() {
        return clientcontext;
    }

    public void setClientcontext(String clientcontext) {
        this.clientcontext = clientcontext;
    }

    public String getClientregion() {
        return clientregion;
    }

    public void setClientregion(String clientregion) {
        this.clientregion = clientregion;
    }

    public String getClientstarttime() {
        return clientstarttime;
    }

    public void setClientstarttime(String clientstarttime) {
        this.clientstarttime = clientstarttime;
    }

    public String getClientendtime() {
        return clientendtime;
    }

    public void setClientendtime(String clientendtime) {
        this.clientendtime = clientendtime;
    }

    public String getClientprepare() {
        return clientprepare;
    }

    public void setClientprepare(String clientprepare) {
        this.clientprepare = clientprepare;
    }

}
