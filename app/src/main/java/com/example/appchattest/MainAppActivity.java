package com.example.appchattest;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appchattest.Adapter.CustomListAdapter;
import com.example.appchattest.Model.ChatRoom;

import java.util.ArrayList;
import java.util.List;

public class MainAppActivity extends AppCompatActivity {
    private EditText editText_search_room;
    private ImageView imageView_friend;
    private ImageView imageView_account_info;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_app);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        addControl();

//        List<ChatRoom> chatRooms = getListData();//lay du lieu
//        final ListView listView = (ListView)findViewById(R.id.listView_chatroom); // lay view chatroom
//        listView.setAdapter(new CustomListAdapter(this, chatRooms));//Set custom view cho listview

        editText_search_room.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH)
                {
                    //Ham tim kieem room chat
                    return true;
                }
                return false;
            }
        });

        imageView_account_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainAppActivity.this, AccountInfoActivity.class);
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainAppActivity.this);
                startActivity(intent, options.toBundle());
            }
        });

        imageView_friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainAppActivity.this, FriendActivity.class);
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainAppActivity.this);
                startActivity(intent, options.toBundle());
            }
        });

    }

    private List<ChatRoom> getListData() //chi debug
    {
        List<ChatRoom> list = new ArrayList<ChatRoom>();
        ChatRoom chatRoom_1 = new ChatRoom("Song Luan dep trai vo doi", "no_avatar", "Tin tao di", "normal_chatroom");
        ChatRoom chatRoom_2 = new ChatRoom("Song luan dep trai cmnr", "no_avatar1", "Tin tao di", "newmess_chatroom");
        ChatRoom chatRoom_3 = new ChatRoom("Song luan dep trai vcl", "no_avatar2", "Chac nhu bap", "newmess_chatroom");
        ChatRoom chatRoom_4 = new ChatRoom("Song luan dep trai", "no_avatar3", "Dung roi", "normal_chatroom");

        list.add(chatRoom_1);
        list.add(chatRoom_2);
        list.add(chatRoom_3);
        list.add(chatRoom_4);

        return list;
    }

    private void addControl()
    {
        editText_search_room = findViewById(R.id.editText_search_chat);
        imageView_friend = findViewById(R.id.img_friend_rc); //image friend roomchat
        imageView_account_info = findViewById(R.id.img_account_info_rc);
    }

    //================================
    //event
    //================================
    boolean double_tap_exit_one = false;
    @Override
    public void onBackPressed()
    {
        if (double_tap_exit_one)
        {
            super.onBackPressed();
            this.finishAffinity();
            System.exit(0);
        }

        this.double_tap_exit_one = true;
        Toast.makeText(this, "Nhấn thêm lần nữa để thoát", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                double_tap_exit_one = false;
            }
        }, 2000);
    }
}
