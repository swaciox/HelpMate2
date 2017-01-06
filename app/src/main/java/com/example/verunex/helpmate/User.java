package com.example.verunex.helpmate;

/**
 * Created by Verunex on 2016-11-17.
 */

public class User {

    private String category;
    private String user_image;
    private String name;
    private String number;
    private String rate;
    private String description;
    private String user_id;
    private String email;


    public User(){

    }

    public User(String category, String user_image, String name, String number, String rate, String description, String user_id, String email) {
        this.category = category;
        this.user_image = user_image;
        this.name = name;
        this.number = number;
        this.rate = rate;
        this.description = description;
        this.user_id = user_id;
        this.email = email;

    }

    public String getEmail() {return email;}
    public void setEmail(String email) {
        this.email = email;
    }

    public String getUser_id() {
        return user_id;
    }

 //   public void setUser_id(String user_id) {
   //     this.user_id = user_id;
  //  }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        return user_image;
    }

    public void setImage(String user_image
    ) {
        this.user_image = user_image;
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
