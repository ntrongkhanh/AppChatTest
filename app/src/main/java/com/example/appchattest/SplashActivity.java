package com.example.appchattest;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import com.google.firebase.database.FirebaseDatabase;

public class SplashActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        startActivity(new Intent(SplashActivity.this, LoginActivity.class));

        finish();
    }
}
