package com.example.appchattest;

import android.app.Application;
import com.google.firebase.database.FirebaseDatabase;

public class VNMe extends Application {
    public VNMe() {
        super();
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
