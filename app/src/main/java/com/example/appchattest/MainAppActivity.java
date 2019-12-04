package com.example.appchattest;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainAppActivity extends AppCompatActivity {
    private EditText editText_search_room;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_app);

        addControl();

        List<ChatRoom> chatRooms = getListData();//lay du lieu
        final ListView listView = (ListView)findViewById(R.id.listView_chatroom); // lay view chatroom
        listView.setAdapter(new CustomListAdapter(this, chatRooms));//Set custom view cho listview

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

    private void addControl() {
       editText_search_room = findViewById(R.id.editText_search_chat);
    }
}
