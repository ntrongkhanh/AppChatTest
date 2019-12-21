package com.example.appchattest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appchattest.Adapter.AdapterRecyclerDanhSachBan;
import com.example.appchattest.Adapter.CustomListAdapter;
import com.example.appchattest.Model.ChatRoom;
import com.example.appchattest.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FindFriendsActivity extends AppCompatActivity {

    private ArrayList<User> listUser=new ArrayList<>(  );
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ListView listView;
    private TextView textView;
    private  String textSearch="";

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_find_friends );

        listView=findViewById( R.id.recycler_find_friends );
        textView=findViewById( R.id.editText_search_find_friend );
        imageView=findViewById( R.id.image_icon_search_find_friends );
        databaseReference=FirebaseDatabase.getInstance().getReference();

        listView = findViewById(R.id.recycler_find_friends); // lay view chatroom
        listView.setAdapter(new CustomListAdapter(this, listUser));//Set custom view cho listview
        final CustomListAdapter customListAdapter=new CustomListAdapter(this,listUser);
        imageView.setOnClickListener( new View.OnClickListener() {


            @Override
            public void onClick(View v) {
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
                            if(textSearch!="")
                            {

                                if (textSearch.equals( user.name ))
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
        } );




    }
    public void dismissKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(getApplication().INPUT_METHOD_SERVICE);
        if (null != activity.getCurrentFocus())
            imm.hideSoftInputFromWindow(activity.getCurrentFocus()
                    .getApplicationWindowToken(), 0);
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
