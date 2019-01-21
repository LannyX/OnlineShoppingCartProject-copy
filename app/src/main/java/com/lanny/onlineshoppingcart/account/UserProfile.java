package com.lanny.onlineshoppingcart.account;

public class UserProfile {

    String fname, lname, address, password, email, mobile;

    public UserProfile(String fname, String lname, String address, String password, String email, String mobile) {
        this.fname = fname;
        this.lname = lname;
        this.address = address;
        this.password = password;
        this.email = email;
        this.mobile = mobile;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
