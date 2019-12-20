package com.example.appchattest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.appchattest.Fragment.ChatRoomFragment2;
import com.example.appchattest.Fragment.FriendFragment;
import com.example.appchattest.Fragment.InfoFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainNavigationActivity extends AppCompatActivity {

    private Fragment fragment;
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.navigation );

        bottomNavigationView=findViewById( R.id.Bottom_nagivition);

        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener );
        fragment=new ChatRoomFragment2();
        getSupportFragmentManager().beginTransaction().replace(R.id.frament,fragment).commit();
    }
    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener=
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                     fragment=null;

                    switch (menuItem.getItemId())
                    {
                        case R.id.nav_chat:
                            fragment=new ChatRoomFragment2();
                            break;
                        case R.id.nav_friend:
                            fragment=new FriendFragment();
                            break;
                        case R.id.nav_info:
                            fragment=new InfoFragment();
                            break;
                    }getSupportFragmentManager().beginTransaction().replace(R.id.frament,fragment).commit();
                    return true;
                }

            };
}
