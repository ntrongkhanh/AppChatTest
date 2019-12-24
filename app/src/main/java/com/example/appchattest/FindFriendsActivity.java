package com.example.appchattest;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.appchattest.Adapter.ListSearchFriendAdapter;
import com.example.appchattest.Model.Contacts;
import com.example.appchattest.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.*;

import java.util.ArrayList;

public class FindFriendsActivity extends AppCompatActivity implements ValueEventListener {

    private ArrayList<User> listUser=new ArrayList<>(  );
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ListView listView;
    private TextView textView;
    private  String textSearch="";
    private ArrayList<Contacts> listContacts=new ArrayList<>(  );
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_find_friends );

        listView=findViewById( R.id.recycler_find_friends );
        textView=findViewById( R.id.editText_search_find_friend );
        imageView=findViewById( R.id.image_icon_search_find_friends );

        listView = findViewById(R.id.recycler_find_friends); // lay view chatroom
        databaseReference=FirebaseDatabase.getInstance().getReference();
        databaseReference.child( "contacts" ).addValueEventListener( this );

        listView.setAdapter(new ListSearchFriendAdapter(this, listUser,listContacts));//Set custom view cho listview

        imageView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchFriends();
            }
        } );

        textView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH)
                {
                    searchFriends();
                    return true;
                }
                return false;
            }
        });
    }

    public void searchFriends()
    {
        textSearch=textView.getText().toString();
        dismissKeyboard( FindFriendsActivity.this );
        databaseReference.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listUser.clear();
                Iterable<DataSnapshot> nodechild=dataSnapshot.child( "users" ).getChildren();
                for (DataSnapshot data:nodechild)
                {
                    User user=data.getValue(User.class);
                    if(textSearch != "")
                    {

                        if ((user.name.toUpperCase()).contains(textSearch.toUpperCase()) && !user.uid.equals(FirebaseAuth.getInstance().getUid()))
                        {
                            listUser.add( user );
                        }
                        else if(textSearch.equals( user.phone ))
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
    }

    public void dismissKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(getApplication().INPUT_METHOD_SERVICE);
        if (null != activity.getCurrentFocus())
            imm.hideSoftInputFromWindow(activity.getCurrentFocus()
                    .getApplicationWindowToken(), 0);
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        Iterable<DataSnapshot> nodechild=dataSnapshot.getChildren();
        listContacts.clear();
        for (DataSnapshot data:nodechild)
        {

            Contacts contact=data.getValue(Contacts.class);

            listContacts.add( contact );
        }
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }


//    @Override
//    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//        listUser.clear();
//           //listUser.clear();
//        //dialog.show();
//        Iterable<DataSnapshot> nodechild=dataSnapshot.child( "users" ).getChildren();
//        for (DataSnapshot data:nodechild)
//        {
//            User user=data.getValue(User.class);
//            if(Ten!="")
//            {
//                if (user.name==Ten)
//                    listUser.add( user );
//            }
//            else
//
//            listUser.add( user );
//
//
//
//            //listMail.add(user.email );
//        }
//
//       // Toast.makeText( getApplicationContext(),listUser.size(),Toast.LENGTH_LONG ).show();
//    }

//    @Override
//    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//    }
}
