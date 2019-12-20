package com.example.appchattest.Model;


import android.net.Uri;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Calendar;
public  class User {
    public String name;



    public String email;
    public String sex;
    public String birthday;
    public String phone;

    public User(String name, String email, String sex, String birthday, String phone) {
        this.name = name;
        this.email = email;
        this.sex = sex;
        this.birthday = birthday;
        this.phone = phone;
    }

    public User() {
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    // public String state;
  //  public Uri photoUri;



//    public User(String name, String email, String sex, String birthday, String avatar,Uri uri, boolean isOnline)
//    {
//        this.name = name;
//        this.email = email;
//        this.sex = sex;
//        this.birthday = birthday;
//        this.avatar = avatar;
//        this.photoUri=uri;
//        this.setOnlineState(isOnline);
//    }
//    public void setOnlineState(boolean isOnline) {
//        if (isOnline)
//        {
//            state = "Online";
//        }
//        else
//        {
//            state = "Offline";
//        }
//    }


//    public String getAvatar() {
//        return avatar;
//    }
//
//    public void setAvatar(String avatar) {
//        this.avatar = avatar;
//    }



   // public String getState() {
   //     return state;
   // }

   // public void setState(String state) {
  //      this.state = state;
  //  }

  //  public Uri getPhotoUri() {
  //      return photoUri;
  //  }

   // public void setPhotoUri(Uri photoUri) {
   //     this.photoUri = photoUri;
    //}
}
