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
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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


public class FriendFragment extends Fragment  {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private ListView listView;
    private ImageView imageViewFindFriend;
    private TextView textView;
    private DatabaseReference databaseReference;
    private ArrayList<User> listUser=new ArrayList<>(  );
    private ArrayList<String> listContacts=new ArrayList<>(  );
    private FirebaseUser user;
    private int x=0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        // Inflate the layout for this fragment
        return inflater.inflate( R.layout.fragment_friend, container, false );
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated( view, savedInstanceState );
        addControls(view);
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
        listView.setAdapter(new ListFriendsAdapter(getActivity(),listUser ));


        //
        databaseReference.addValueEventListener( new ValueEventListener() {
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


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );
       // databaseReference= FirebaseDatabase.getInstance().getReference();
        //databaseReference.addValueEventListener( this );
        //listView.setAdapter( new ListFriendsAdapter(getActivity(),listUser) );
    }

    private void addControls(View view) {
        imageViewFindFriend=view.findViewById( R.id.imageView_addfriend );
        textView=view.findViewById( R.id.textView_item_loimoiketban );
        listView=view.findViewById( R.id.listView_friend );
    }
}
