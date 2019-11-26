package com.example.appchattest.Model;


import java.util.Calendar;

public class User {
    public String name;
    public String email;
    public String sex;
    public String birthday;


    public User() {
    }

    public User(String name, String email, String sex, String birthday) {
        this.name = name;
        this.email = email;
        this.sex = sex;
        this.birthday = birthday;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
}
