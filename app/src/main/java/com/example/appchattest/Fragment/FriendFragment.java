package com.example.appchattest.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appchattest.Adapter.FriendRequestAdapter;
import com.example.appchattest.Adapter.ListFriendsAdapter;
import com.example.appchattest.Adapter.ListSearchFriendAdapter;
import com.example.appchattest.FindFriendsActivity;
import com.example.appchattest.FriendRequestActivity;
import com.example.appchattest.Model.Contacts;
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
import java.util.List;


public class FriendFragment extends Fragment implements ValueEventListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private ListView listView;
    private ImageView imageViewFindFriend;
    private TextView textView;
    private DatabaseReference databaseReference;
    private ArrayList<User> listUser=new ArrayList<>(  );
    private ArrayList<String> listContacts=new ArrayList<>(  );
    private FirebaseUser user;


    public FriendFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
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

        return rootView;
    }



    private void addControls(View view) {
        imageViewFindFriend=view.findViewById( R.id.imageView_addfriend );
        textView=view.findViewById( R.id.button_item_Loimoiketban );
        listView=view.findViewById( R.id.listView_friend );
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        listContacts.clear();
        listUser.clear();
        Iterable<DataSnapshot> nodechild=dataSnapshot.child( "contacts" ).getChildren();
        for (DataSnapshot data: nodechild)
        {
            Contacts contact=data.getValue(Contacts.class);
            if (contact.isStatus()==true)
            {
                if ((contact.userID.equals(user.getUid())))
                {
                    listContacts.add( contact.contactID );
                }
                else if (contact.contactID.equals( user.getUid() ))
                {
                    listContacts.add( contact.userID );
                }
            }

        }
        Iterable<DataSnapshot> nodechild1=dataSnapshot.child( "users" ).getChildren();
        for (DataSnapshot data:nodechild1)
        {
            User user=data.getValue(User.class);


            for (String c: listContacts)
            {
                if (c.equals( user.getUid() ))
                {
                    listUser.add( user );
                }
            }
        }
        listView.setAdapter( new ListFriendsAdapter(getActivity(),listUser ) );
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }
}
