package com.example.appchattest.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.appchattest.Adapter.ListFriendsAdapter;
import com.example.appchattest.FindFriendsActivity;
import com.example.appchattest.FriendRequestActivity;
import com.example.appchattest.Model.Contacts;
import com.example.appchattest.Model.User;
import com.example.appchattest.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.*;

import java.util.ArrayList;


public class FriendFragment extends Fragment implements ValueEventListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private ListView listView;
    private ImageView imageViewFindFriend;
    private TextView textView;
    private DatabaseReference databaseReference;
    private ArrayList<User> listFriends=new ArrayList<>(  );
    private ArrayList<String> listUidFriend=new ArrayList<>(  );
    private FirebaseUser user;
    private ListFriendsAdapter adapter;


    public FriendFragment(ArrayList<String> listuidFriends) {
        this.listUidFriend=listuidFriends;
    }

    public FriendFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        // Inflate the layout for this fragment
        View rootView=inflater.inflate( R.layout.fragment_friend, container, false );
        addControls(rootView);

        imageViewFindFriend.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent( getActivity(), FindFriendsActivity.class );
                startActivity( intent );
            }
        } );
        user=FirebaseAuth.getInstance().getCurrentUser();
        textView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent( getActivity(), FriendRequestActivity.class );
                startActivity( intent );
            }
        } );
        databaseReference=FirebaseDatabase.getInstance().getReference();
        // Vấn đề nan giải
        databaseReference.addValueEventListener( this);

        adapter = new ListFriendsAdapter(getActivity(),listFriends );
        listView.setAdapter( adapter );

        return rootView;
    }



    private void addControls(View view) {
        imageViewFindFriend=view.findViewById( R.id.imageView_addfriend );
        textView=view.findViewById( R.id.button_item_Loimoiketban );
        listView=view.findViewById( R.id.listView_friend );
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

        int x=0;
        listFriends.clear();
        listUidFriend.clear();
        Iterable<DataSnapshot> nodechild=dataSnapshot.child( "friends" ).child( user.getUid() ).getChildren();
        for (DataSnapshot data:nodechild)
        {
            String uid=data.getKey();
            listUidFriend.add( uid );

        }

        Iterable<DataSnapshot> nodechild1=dataSnapshot.child( "users" ).getChildren();
        for (DataSnapshot data:nodechild1)
        {
            User user=data.getValue(User.class);
            for (String c: listUidFriend)
            {
                if (c.equals( user.getUid() ))
                {
                    listFriends.add( user );
                }
            }
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }
}
