package com.example.appchattest.Model;


import java.util.Calendar;

public class User {
    public String name;
    public String avatar;
    public String email;
    public String sex;
    public String birthday;
    private String state;


    public User() {
    }

    public User(String name, String email, String sex, String birthday) {
        this.name = name;
        this.email = email;
        this.sex = sex;
        this.birthday = birthday;
    }

    public User(String name, String email, String sex, String birthday, String avatar, boolean isOnline)
    {
        this.name = name;
        this.email = email;
        this.sex = sex;
        this.birthday = birthday;
        this.avatar = avatar;
        this.setOnlineState(isOnline);
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

    public void setOnlineState(boolean isOnline) {
        if (isOnline)
        {
            state = "Online";
        }
        else
        {
            state = "Offline";
        }
    }
    public String getState()
    {
        return state;
    }

    public void setAvatar(String avatar)
    {
        this.avatar = avatar;
    }
    public String getAvatar()
    {
        return this.avatar;
    }
}
