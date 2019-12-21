package com.example.appchattest.Model;


import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public  class User {
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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



    public String uid;
    public String name;



    public String email;
    public String sex;
    public String birthday;
    public String phone;


    public User() {
    }

    public User(String uid, String name, String email, String sex, String birthday, String phone) {
        this.uid = uid;
        this.name = name;
        this.email = email;
        this.sex = sex;
        this.birthday = birthday;
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
