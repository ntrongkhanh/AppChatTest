package com.example.appchattest.Model;

import com.bumptech.glide.annotation.Excludes;

import java.util.HashMap;
import java.util.Map;

public class Contacts {
    public String userID;
    public String contactID;
    public boolean status;

    public Contacts() {
    }

    public Contacts(String userID, String contactID) {
        this.userID = userID;
        this.contactID = contactID;
        this.status = false;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getContactID() {
        return contactID;
    }

    public void setContactID(String contactID) {
        this.contactID = contactID;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("userID", userID);
        result.put("contactID", contactID);
        result.put("status", status);


        return result;
    }
}
