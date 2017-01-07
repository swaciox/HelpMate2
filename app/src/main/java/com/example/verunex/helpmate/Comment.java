package com.example.verunex.helpmate;

public class Comment {

    private String desc;
    private String name;
    private String user_image;
    private String rate;
    private String user_id;
    private String date;

    public Comment (){


    }

    public Comment(String desc, String name, String user_image, String rate, String user_id,String date) {
        this.desc = desc;
        this.name = name;
        this.user_image = user_image;
        this.rate = rate;
        this.user_id = user_id;
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUser_image() {
        return user_image;
    }

    public void setUser_image(String user_image) {
        this.user_image = user_image;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }
}
