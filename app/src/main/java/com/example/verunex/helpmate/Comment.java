package com.example.verunex.helpmate;

/**
 * Created by Verunex on 2016-11-30.
 */

public class Comment {

    private String image;
    private String desc;
    private String name;
    private String rate;

    public Comment (){

    }

    public Comment(String image, String desc, String name, String rate) {
        this.image = image;
        this.desc = desc;
        this.name = name;
        this.rate = rate;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }






}
