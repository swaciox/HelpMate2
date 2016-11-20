package com.example.verunex.helpmate;

/**
 * Created by Verunex on 2016-11-17.
 */

public class User {

    private String category;
    private String image;
    private String name;
    private String number;
    private String rate;

    public User(){

    }

    public User(String category, String image, String name, String number) {
        this.category = category;
        this.image = image;
        this.name = name;
        this.number = number;
        this.rate = rate;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
