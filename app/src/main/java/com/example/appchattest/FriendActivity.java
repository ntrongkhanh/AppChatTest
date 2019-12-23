package com.example.appchattest;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appchattest.Adapter.ListFriendsAdapter;
import com.example.appchattest.Model.ChatRoom;
import com.example.appchattest.Model.Contacts;
import com.example.appchattest.Model.User;
import com.example.appchattest.R;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FriendActivity extends AppCompatActivity  {
    private ImageView imageView_chatroom;
    private ImageView imageView_account_info;
    private ListView listView;
    private ArrayList<User> listUser=new ArrayList<>(  );
    private ArrayList<String> listContacts=new ArrayList<>(  );
    private FirebaseUser user;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_friend );
       // addControl();
        //databaseReference = FirebaseDatabase.getInstance().getReference();
        // Vấn đề nan giải
       // listView.setAdapter( new ListFriendsAdapter( this, listUser ) );
        //
//        databaseReference.addValueEventListener( new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                listContacts.clear();
//                listUser.clear();
//                Iterable<DataSnapshot> nodechild = dataSnapshot.child( "contacts" ).getChildren();
//                for (DataSnapshot data : nodechild) {
//                    Contacts contact = data.getValue( Contacts.class );
//                    if (contact.isStatus() == true) {
//                        if ((contact.userID.equals( user.getUid() ))) {
//                            listContacts.add( contact.contactID );
//                        } else if (contact.contactID.equals( user.getUid() )) {
//                            listContacts.add( contact.userID );
//                        }
//                    }
//
//                }
//                Iterable<DataSnapshot> nodechild1 = dataSnapshot.child( "users" ).getChildren();
//                for (DataSnapshot data : nodechild1) {
//                    User user = data.getValue( User.class );
//
//
//                    for (String c : listContacts) {
//                        if (c.equals( user.getUid() )) {
//
//                            listUser.add( user );
//                        }
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        } );
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
        listView=findViewById( R.id.listView_friend1 );

    }

    //================================
    //event
    //================================



}