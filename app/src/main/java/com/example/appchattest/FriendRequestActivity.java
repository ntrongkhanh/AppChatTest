package com.example.appchattest;

import android.os.Bundle;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.appchattest.Adapter.FriendRequestAdapter;
import com.example.appchattest.Model.Contacts;
import com.example.appchattest.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.*;

import java.util.ArrayList;

public class FriendRequestActivity extends AppCompatActivity {
    private ListView listView;
    private ArrayList<Contacts> listContacts=new ArrayList<>(  );
    private ArrayList<User> listUser=new ArrayList<>(  );
    private DatabaseReference databaseReference;
    private FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_friend_request );
        listView=findViewById( R.id.listVewLoimoiketban );
        databaseReference= FirebaseDatabase.getInstance().getReference();
        user= FirebaseAuth.getInstance().getCurrentUser();
        databaseReference.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listContacts.clear();
                listUser.clear();
                Iterable<DataSnapshot> nodechild=dataSnapshot.child( "contacts" ).getChildren();
                for (DataSnapshot data:nodechild)
                {
                    Contacts contact=data.getValue(Contacts.class);
                    if(contact.contactID.equals(user.getUid()) && contact.status==false)
                    {
                        listContacts.add( contact );
                    }
                }
                Iterable<DataSnapshot> nodechild1=dataSnapshot.child( "users" ).getChildren();
                for (DataSnapshot data:nodechild1)
                {
                    User user=data.getValue(User.class);
                    for (Contacts c: listContacts)
                    {
                        if(c.userID.equals( user.getUid() ))
                            listUser.add( user );
                    }
                }
                listView.setAdapter(new FriendRequestAdapter(getApplicationContext(), listUser));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );
    }
}
