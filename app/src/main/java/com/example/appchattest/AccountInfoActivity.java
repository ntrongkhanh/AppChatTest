package com.example.appchattest;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class AccountInfoActivity extends AppCompatActivity{
    private ImageView imageView_chatroom;
    private ImageView imageView_friend;
    private Button button_Logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_info);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        addControl();
        imageView_friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AccountInfoActivity.this, FriendActivity.class);
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(AccountInfoActivity.this);
                startActivity(intent, options.toBundle());
            }
        });

        imageView_chatroom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AccountInfoActivity.this, MainAppActivity.class);
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(AccountInfoActivity.this);
                startActivity(intent, options.toBundle());
            }
        });

        button_Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AccountInfoActivity.this, LoginActivity.class);
                AccountInfoActivity.this.finishAffinity();
                startActivity(intent);
            }
        });
    }

    private void addControl()
    {
        imageView_friend = findViewById(R.id.img_friend_ac);
        imageView_chatroom = findViewById(R.id.img_chatroom_ac);
        button_Logout = findViewById(R.id.button_logout);
    }
    //================================
    //event
    //================================
    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(AccountInfoActivity.this, MainAppActivity.class);
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(AccountInfoActivity.this);
        startActivity(intent, options.toBundle());
    }
}