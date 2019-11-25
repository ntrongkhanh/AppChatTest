package com.example.appchattest;

import android.content.Intent;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void SignInWithAnonymous(View view) {
    }

    public void SignUp(View view) {
        startActivity(new Intent(MainActivity.this,  CreateAccount.class));
    }

    public void ContinueToEnterPassword(View view) {
    }
}
