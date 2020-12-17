package com.team4.finding_sw_developers.Models;

import java.io.Serializable;

public class ExpertAd  implements Serializable {
    private String userId;
    private String expertcontext;
    private String expertregion;
    private String expertstarttime;
    private String expertendtime;
    private String expertprepare;
    private String expertbudget;
    private String experttitle;
    private int expertcategory;
    private String expertkey;
    private String id;

    public Boolean getExpertmatching() {
        return expertmatching;
    }

    public void setExpertmatching(Boolean expertmatching) {
        this.expertmatching = expertmatching;
    }

    private Boolean expertmatching;



    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getExpertcontext() {
        return expertcontext;
    }

    public void setExpertcontext(String expertcontext) {
        this.expertcontext = expertcontext;
    }

    public String getExpertregion() {
        return expertregion;
    }

    public void setExpertregion(String expertregion) {
        this.expertregion = expertregion;
    }

    public String getExpertstarttime() {
        return expertstarttime;
    }

    public void setExpertstarttime(String expertstarttime) {
        this.expertstarttime = expertstarttime;
    }

    public String getExpertendtime() {
        return expertendtime;
    }

    public void setExpertendtime(String expertendtime) {
        this.expertendtime = expertendtime;
    }

    public String getExpertprepare() {
        return expertprepare;
    }

    public void setExpertprepare(String expertprepare) {
        this.expertprepare = expertprepare;
    }

    public String getExpertbudget() {
        return expertbudget;
    }

    public void setExpertbudget(String expertbudget) {
        this.expertbudget = expertbudget;
    }

    public String getExperttitle() {
        return experttitle;
    }

    public void setExperttitle(String experttitle) {
        this.experttitle = experttitle;
    }

    public int getExpertcategory() {
        return expertcategory;
    }

    public void setExpertcategory(int expertcategory) {
        this.expertcategory = expertcategory;
    }

    public String getExpertkey() {
        return expertkey;
    }

    public void setExpertkey(String expertkey) {
        this.expertkey = expertkey;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getExpertvisit() {
        return expertvisit;
    }

    public void setExpertvisit(int expertvisit) {
        this.expertvisit = expertvisit;
    }

    private int expertvisit;
}
