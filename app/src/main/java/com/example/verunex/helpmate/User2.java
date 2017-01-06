package com.example.verunex.helpmate;

/**
 * Created by Verunex on 2017-01-05.
 */

public class User2 {

    private String address;
    private String category;
    private String desc;
    private String email;
    private String name;
    private String number;
    private String rate;
    private String service_state;
    private String uid;
    private String user_image;

    public User2 (){

    }

    public User2(String address, String category, String desc, String email, String name, String number, String rate, String service_state, String uid, String user_image) {
        this.address = address;
        this.category = category;
        this.desc = desc;
        this.email = email;
        this.name = name;
        this.number = number;
        this.rate = rate;
        this.service_state = service_state;
        this.uid = uid;
        this.user_image = user_image;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getService_state() {
        return service_state;
    }

    public void setService_state(String service_state) {
        this.service_state = service_state;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUser_image() {
        return user_image;
    }

    public void setUser_image(String user_image) {
        this.user_image = user_image;
    }
}
