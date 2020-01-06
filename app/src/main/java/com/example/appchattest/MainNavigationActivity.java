package com.example.appchattest;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import com.example.appchattest.Fragment.ChatRoomFragment;
import com.example.appchattest.Fragment.FriendFragment;
import com.example.appchattest.Fragment.InfoFragment;
import com.example.appchattest.Model.Contacts;
import com.example.appchattest.Model.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainNavigationActivity extends AppCompatActivity{

    private ChatRoomFragment chatRoomFragment;
    private InfoFragment infoFragment;
    private FriendFragment friendFragment;
    private BottomNavigationView bottomNavigationView;
    private DatabaseReference databaseReference;
    private User currentUser;
    private FirebaseUser user_FireBase;
    private  ArrayList<String> listFriends=new ArrayList<>(  );
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );

        user_FireBase = FirebaseAuth.getInstance().getCurrentUser();
        setContentView( R.layout.navigation );
        bottomNavigationView=findViewById( R.id.Bottom_nagivition);
        databaseReference= FirebaseDatabase.getInstance().getReference();
        databaseReference.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> nodechild=dataSnapshot.child( "users" ).getChildren();
                for (DataSnapshot data:nodechild)
                {
                    User user=data.getValue(User.class);
                    if (user.uid== FirebaseAuth.getInstance().getUid()) {
                        currentUser = user;
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );

        DatabaseReference connectedRef = FirebaseDatabase.getInstance().getReference(".info/connected");
        connectedRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean connected = snapshot.getValue(Boolean.class);
                if (connected) {
                    Log.d("TAG", "connected");
                    if (user_FireBase != null)
                    {
                        databaseReference.child("users").child(user_FireBase.getUid()).child("status").setValue("Online");
                    }
                } else {
                    Log.d("TAG", "not connected");
                    databaseReference.child("users").child(user_FireBase.getUid()).child("status").setValue("Offline");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("TAGGG", "Listener was cancelled");
            }
        });

        final DatabaseReference authStatus = FirebaseDatabase.getInstance().getReference().child("users").child(user_FireBase.getUid());
        authStatus.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                authStatus.child("status").onDisconnect().setValue("Offline");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        chatRoomFragment=new ChatRoomFragment();
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener );
        getSupportFragmentManager().beginTransaction().replace(R.id.frament,chatRoomFragment).commit();
    }
    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener=
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {


                    switch (menuItem.getItemId())
                    {
                        case R.id.nav_chat:
                            getSupportFragmentManager().beginTransaction().replace(R.id.frament,chatRoomFragment).commit();

                            break;
                        case R.id.nav_friend:
                            if (friendFragment==null)
                            friendFragment=new FriendFragment();
                            getSupportFragmentManager().beginTransaction().replace( R.id.frament,friendFragment ).commit();
                            break;
                        case R.id.nav_info:
                            if (infoFragment==null)
                            infoFragment=new InfoFragment(currentUser);
                            getSupportFragmentManager().beginTransaction().replace( R.id.frament,infoFragment ).commit();
                            break;
                    }
                    return true;
                }
            };
}
