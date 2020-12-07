package com.team4.finding_sw_developers.Models;

public class Chatlist {
    public String id;
    public long timestamp;

    public Chatlist(String id, long timestamp){
        this.id = id;
        this.timestamp = timestamp;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public Chatlist(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
