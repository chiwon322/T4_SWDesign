package com.team4.finding_sw_developers;

public class ListItem {
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public ListItem(String title, String review, String price) {
        this.title = title;
        this.review = review;
        this.price = price;
    }

    String title;
    String review;
    String price;
}
