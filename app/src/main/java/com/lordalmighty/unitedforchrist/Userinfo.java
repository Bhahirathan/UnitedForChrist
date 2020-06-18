package com.lordalmighty.unitedforchrist;

public class Userinfo {
    ShowProfileActivity showProfileActivity=new ShowProfileActivity();
    public String username;
    public String phone;

    public ShowProfileActivity getShowProfileActivity() {
        return showProfileActivity;
    }

    public void setShowProfileActivity(ShowProfileActivity showProfileActivity) {
        this.showProfileActivity = showProfileActivity;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Userinfo(){

    }

    public Userinfo(String username, String phone) {
        this.username = username;
        this.phone = phone;
    }

}
