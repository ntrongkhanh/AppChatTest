package com.example.appchattest;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.appchattest.Model.ChatRoom;
import com.example.appchattest.Model.User;
import com.example.appchattest.R;

import java.util.ArrayList;
import java.util.List;

public class FriendActivity extends AppCompatActivity{
    private ImageView imageView_chatroom;
    private ImageView imageView_account_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        addControl();

        //==============================
        //!important
        //==============================
       // List<User> friends = getListData();
     //   final ListView listView = (ListView)findViewById(R.id.listView_friend);
       // listView.setAdapter(new CustomListFriendAdapter(this, friends));
        //==============================


        imageView_chatroom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FriendActivity.this, MainAppActivity.class);
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(FriendActivity.this);
                startActivity(intent, options.toBundle());
            }
        });

        imageView_account_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FriendActivity.this, AccountInfoActivity.class);
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(FriendActivity.this);
                startActivity(intent, options.toBundle());
            }
        });
    }

//    private List<User> getListData() //chi debug
//    {
////        List<User> list = new ArrayList<User>();
////        User friend_1 = new User("Administrator Leader", "admin@vnme.vn", "Nam", "07/12/2019","no_avatar",true);
////        User friend_2 = new User("Administrator Transformer", "admin@vnme.vn", "Nam", "07/12/2019","no_avatar3",true);
////        User friend_3 = new User("Administrator Batman", "admin@vnme.vn", "Nam", "07/12/2019","no_avatar1",false);
////        User friend_4 = new User("Administrator Iron Man", "admin@vnme.vn", "Nam", "07/12/2019","no_avatar2",true);
////        User friend_5 = new User("Thường dân", "google.com", "Nam", "07/12/2019","no_avatar2",false);
////        User friend_6 = new User("Thường dân", "google.com", "Nam", "07/12/2019","no_avatar2",false);
////        User friend_7 = new User("Thường dân", "google.com", "Nam", "07/12/2019","no_avatar2",false);
////        User friend_8 = new User("Thường dân", "google.com", "Nam", "07/12/2019","no_avatar2",false);
////        User friend_9 = new User("Thường dân", "google.com", "Nam", "07/12/2019","no_avatar2",false);
////        User friend_10 = new User("Thường dân", "google.com", "Nam", "07/12/2019","no_avatar2",false);
//
////        list.add(friend_1);
////        list.add(friend_2);
////        list.add(friend_3);
////        list.add(friend_4);
////        list.add(friend_5);
////        list.add(friend_6);
////        list.add(friend_7);
////        list.add(friend_8);
////        list.add(friend_9);
////        list.add(friend_10);
////
////        return list;
//    }

    private void addControl()
    {
        imageView_chatroom = findViewById(R.id.img_chatroom_fr);
        imageView_account_info = findViewById(R.id.img_account_info_fr);
    }

    //================================
    //event
    //================================
    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(FriendActivity.this, MainAppActivity.class);
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(FriendActivity.this);
        startActivity(intent, options.toBundle());
    }

}