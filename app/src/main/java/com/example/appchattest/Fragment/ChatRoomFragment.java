package com.example.appchattest.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.*;
import android.widget.ListView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.appchattest.Adapter.ChatAdapter;
import com.example.appchattest.Adapter.ListChatRoomAdapter;
import com.example.appchattest.MainNavigationActivity;
import com.example.appchattest.Model.Chat;
import com.example.appchattest.Model.ChatRoom;
import com.example.appchattest.Model.User;
import com.example.appchattest.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.*;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class ChatRoomFragment extends Fragment implements ValueEventListener{
    private ListView listView;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private FirebaseUser user;
    private ArrayList<ChatRoom> chatRooms = new ArrayList<>();
    ListChatRoomAdapter adapter;

    //list name of friend have chat
    private ArrayList<String> uids = new ArrayList<>();

    public ChatRoomFragment(ArrayList<User> listFriends) {

    }

    public ChatRoomFragment() {

    }
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        View rootView = inflater.inflate( R.layout.fragment_chatroom, container, false );
        addControls(rootView);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();

        databaseReference.addValueEventListener(this);

        adapter = new ListChatRoomAdapter( getActivity(),getActivity(), chatRooms);


        //gắn adapter
        listView.setAdapter(adapter);

        // Inflate the layout for this fragment
        return rootView;
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        uids.clear();
        chatRooms.clear();

        Iterable<DataSnapshot> nodechild = dataSnapshot.child( "chats" ).child( user.getUid() ).getChildren();
        for (DataSnapshot data:nodechild)
        {
            String uid = data.getKey();
            uids.add(uid);
            Log.d("===================================", "LOGCAT");
        }

        Iterable<DataSnapshot> nodechilduser = dataSnapshot.child( "users" ).getChildren();
        for (DataSnapshot data:nodechilduser)
        {
            User user_ = data.getValue(User.class);
            for(String str:uids) {
                if (str.equals(user_.getUid())) {
                    ChatRoom chatRoom = new ChatRoom();
                    chatRoom.setUid( user_.getUid() );
                    chatRoom.setStr_RoomName(user_.getName());
                    chatRoom.setStr_Avatar(user_.getAvatar());
                    Log.d("+++++++++++++++++++", "LOAG");
                    chatRooms.add(chatRoom);
                }
            }
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }

    public void addControls(View view)
    {
        listView = view.findViewById(R.id.listView_chatroom);
    }
}
