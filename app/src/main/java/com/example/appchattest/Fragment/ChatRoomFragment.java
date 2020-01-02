package com.example.appchattest.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.appchattest.Adapter.ListChatRoomAdapter;

import com.example.appchattest.Model.Chat;
import com.example.appchattest.Model.ChatRoom;
import com.example.appchattest.Model.User;
import com.example.appchattest.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import java.util.Collections;
import java.util.List;



public class ChatRoomFragment extends Fragment implements ValueEventListener {
    private ListView listView;
    private DatabaseReference databaseReference;
    private FirebaseUser user;
    private ArrayList<ChatRoom> chatRooms = new ArrayList<>();
    ListChatRoomAdapter adapter;
    List<Chat> listChat;

    //list name of friend have chat
    private ArrayList<String> uids = new ArrayList<>();


    public ChatRoomFragment() {

    }
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN );
        View rootView = inflater.inflate( R.layout.fragment_chatroom, container, false );
        addControls( rootView );

        databaseReference = FirebaseDatabase.getInstance().getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference.addValueEventListener( this );

        adapter = new ListChatRoomAdapter( getActivity(), getActivity(), chatRooms );


        //gắn adapter
        listView.setAdapter( adapter );
        // Inflate the layout for this fragment
        return rootView;
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        uids.clear();
        chatRooms.clear();

        Iterable<DataSnapshot> nodechild = dataSnapshot.child( "chats" ).child( user.getUid() ).getChildren();
        for (DataSnapshot data : nodechild) {
            String uid = data.getKey();


            uids.add(uid);
            Log.d("===================================", "CHAT ROOM DATACHANGE");
        }

        Iterable<DataSnapshot> nodechilduser = dataSnapshot.child( "users" ).getChildren();
        for (DataSnapshot data : nodechilduser) {
            User user_ = data.getValue( User.class );
            for (String str : uids) {
                if (str.equals( user_.getUid() )) {
                    ChatRoom chatRoom = new ChatRoom();
                    chatRoom.setUid( user_.getUid() );
                    chatRoom.setStr_RoomName(user_.getName());
                    chatRoom.setStr_Avatar(user_.getAvatar());

                    chatRooms.add(chatRoom);
                }
            }
        }

//        List<Chat> listChatTemp = null;
//        listChatTemp.addAll( listChat );
//        List<ChatRoom> listChatRoomTemp = null;
//        listChatRoomTemp.addAll( chatRooms );
//        Collections.sort( listChat );
//        for (int i = 0; i < chatRooms.size(); i++) {
//            for (int j = 0; i < chatRooms.size(); i++) {
//                if (listChat.get( i ).getTime().equals( listChatTemp.get( i ).getTime() ) && listChat.get( i ).getContents().equals( listChatTemp.get( i ).getContents() )) {
//                    chatRooms.set( i, listChatRoomTemp.get( j ) );
//                }
//
//
//            }
//        }
        adapter.notifyDataSetChanged();
//        listChat.clear();
//        for (int i = 0; i < chatRooms.size(); i++) {
//            Query lastQuery = databaseReference.child( "chats" ).child( FirebaseAuth.getInstance().getUid() ).child( chatRooms.get( i ).getUid() ).orderByKey().limitToLast( 1 );
//            lastQuery.addListenerForSingleValueEvent( new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
//                        try {
//                            Chat chat = dataSnapshot1.getValue( Chat.class );
//                            listChat.add( chat );
//
//                        } catch (Exception e) {
//
//                        }
//                    }
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                }
//            } );
//        }
        // List<Chat> listChatTemp = null;
        //  listChatTemp.addAll( listChat );
        //List<ChatRoom> listChatRoomTemp=null;
        //listChatRoomTemp.addAll( chatRooms );
        //Collections.sort( listChat );
//        for (int i=0;i<chatRooms.size();i++) {
//            for (int j=0;i<chatRooms.size();i++) {
//                if (listChat.get( i ).getTime().equals( listChatTemp.get( i ).getTime() ) && listChat.get( i ).getContents().equals( listChatTemp.get( i ).getContents() )){
//                    chatRooms.set( i ,listChatRoomTemp.get( j ));
//                }
//
//
//
//            }
//        }

//        Query lastQuery = databaseReference.child( "chats" ).child( user.getUid() ).orderByChild("time").limitToLast( 1 );
//        lastQuery.addListenerForSingleValueEvent( new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                //String x= (String) dataSnapshot.child("lastcontents").getValue();
//                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
//                    try {
//                        Chat chat = dataSnapshot1.getValue( Chat.class );
//                        holder.textView_lastContent.setText( chat.getContents() );
//                        if (chat.isSender()) {
//                            holder.textView_name.setText( "Bạn:" );
//                        } else holder.textView_name.setText( chatRoom.getStr_RoomName() + ":" );
//
//
//                        Date currentdate = Calendar.getInstance().getTime();
//                        Date c = Calendar.getInstance().getTime();
//                        SimpleDateFormat df = new SimpleDateFormat( "dd-MM-yyyy HH:mm:s" );
//                        String formattedDate = df.format( c );
//                        holder.textView_time.setText( sosanhDate( chat.getTime(), formattedDate ) );
//
//
//                    } catch (Exception e) {
//
//                    }
//                }
//
//
//                //  holder.textView_lastContent.setText( x );
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        } );


        // adapter.notifyDataSetChanged();
    }
 
    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }

    public void addControls(View view) {
        listView = view.findViewById( R.id.listView_chatroom );
    }
}
