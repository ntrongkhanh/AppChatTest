package com.example.appchattest;

import android.os.Bundle;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainAppActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_app);

        List<ChatRoom> chatRooms = getListData();//lay du lieu
        final ListView listView = (ListView)findViewById(R.id.listView_chatroom); // lay view chatroom
        listView.setAdapter(new CustomListAdapter(this, chatRooms));//Set custom view cho listview
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
}
