package com.example.appchattest.Model;


public class User {
    public String name;
    public String email;

    public User() {
    }

    public User(String name, String email) {
        this.email=email;
        this.name = name;

    }

    public String getName() {
        return name;
    }
    public String getEmail()
    {
        return email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
