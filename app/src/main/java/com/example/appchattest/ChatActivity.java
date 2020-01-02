package com.example.appchattest;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.appchattest.Adapter.ChatAdapter;
import com.example.appchattest.Model.Chat;
import com.example.appchattest.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.*;

import java.text.SimpleDateFormat;
import java.util.*;

public class ChatActivity extends AppCompatActivity {

    private String uidFriend="";
    private String uidUser;
    private String nameFriend;
    private User friend;
    private Bitmap avatarFriend=null;
    private TextView textViewNameFriend,textViewBack;
    private ListView listView;
    private EditText editTextContent;
    private ImageView buttonSend;
    private ArrayList<Chat> listChat=new ArrayList<>(  );

    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_chat );
        addControls();

        uidUser= FirebaseAuth.getInstance().getUid();
        Intent intent=getIntent();
        uidFriend=intent.getStringExtra( "uidFriend" );
        nameFriend=intent.getStringExtra( "nameFriend" );
        textViewNameFriend.setText( nameFriend );
        editTextContent.setText( "" );
        databaseReference= FirebaseDatabase.getInstance().getReference();
        scrollMyListViewToBottom();
        databaseReference.child( "users" ).child( uidFriend ).addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                friend=dataSnapshot.getValue( User.class );
                // lấy ảnh về
                byte[] a= Base64.decode( friend.avatar,Base64.DEFAULT );
                avatarFriend= BitmapFactory.decodeByteArray( a,0,a.length );
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        } );
        databaseReference.child( "chats" ).child( uidUser ).child( uidFriend ).addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> nodechild=dataSnapshot.getChildren();
                listChat.clear();

                for (DataSnapshot data:nodechild)
                {
                    try {
                        Chat chat=data.getValue(Chat.class);
                        listChat.add( chat );
                    }catch (Exception e)
                    {
                        
                    }

                }
                if (!uidFriend.equals( "" ) && avatarFriend!=null)
                listView.setAdapter( new ChatAdapter( getApplicationContext(),listChat,uidFriend,avatarFriend ) );
                scrollMyListViewToBottom();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );

        textViewBack.setOnClickListener( new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        finish();
        overridePendingTransition( R.anim.slide_in_from_left, R.anim.slide_out_to_right );
    }
} );

        buttonSend.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextContent.getText().toString().equals( "" ))
                    return;
                String key=databaseReference.child( "chats" ).child( uidUser ).child( uidFriend ).push().getKey();
                Date c = Calendar.getInstance().getTime();
                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:s");
                String formattedDate = df.format(c);
                Chat chat=new Chat( editTextContent.getText().toString(),formattedDate,true );
                Map<String, Object> postValues = chat.toMap();
                Map<String, Object> childUpdates = new HashMap<>();
                childUpdates.put("/chats/" + uidUser+"/"+uidFriend+"/"+key, postValues);
                childUpdates.put( "/chats/"+uidUser+"/"+uidFriend+"/lastcontents/",editTextContent.getText().toString() );
                databaseReference.updateChildren( childUpdates );


                //-------------------------------------------//


                String key1=databaseReference.child( "chats" ).child( uidFriend ).child( uidUser ).push().getKey();
                Chat chat1=new Chat( editTextContent.getText().toString(),formattedDate,false );
                Map<String, Object> postValues1 = chat1.toMap();
                Map<String, Object> childUpdates1 = new HashMap<>();

                childUpdates1.put("/chats/" + uidFriend+"/"+uidUser+"/"+key1, postValues1);
                childUpdates1.put( "/chats/"+uidFriend+"/"+uidUser+"/lastcontents/",editTextContent.getText().toString() );
                databaseReference.updateChildren( childUpdates1 );
                editTextContent.setText( "" );

            }
        } );
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition( R.anim.slide_in_from_left, R.anim.slide_out_to_right );
    }

    private void scrollMyListViewToBottom() {
        listView.post(new Runnable() {
            @Override
            public void run() {
                // Select the last row so it will scroll into view...
                listView.setSelection(listView.getCount() - 1);
            }
        });
    }
    private void addControls()
    {
        editTextContent=findViewById( R.id.editText_content_chat );
        buttonSend=findViewById( R.id.button_send_chat );
        textViewBack=findViewById( R.id.imageView_back_chat );
        textViewNameFriend=findViewById( R.id.title_name_chat );
        listView=findViewById( R.id.listView_chat );
    }


}
