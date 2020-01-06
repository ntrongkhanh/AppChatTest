package com.example.appchattest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appchattest.Model.Chat;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SendImageActivity extends AppCompatActivity {

    private TextView textViewSend,textViewCancel;
    private ImageView imageView;
    private String sImage;
    private  byte[] imageInByte;
    private DatabaseReference databaseReference;
    private String uidUser,uidFriend;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_send_image );
        textViewCancel=findViewById( R.id.textView_cancel_send_image );
        textViewSend=findViewById( R.id.textView_send_image );
        imageView=findViewById( R.id.image_send );
        Intent intent=getIntent();

        imageInByte=intent.getByteArrayExtra( "image" );
        uidUser=intent.getStringExtra( "uidUser" );
        uidFriend=intent.getStringExtra( "uidFriend" );
        Bitmap bmp = BitmapFactory.decodeByteArray(imageInByte, 0, imageInByte.length);
        imageView.setImageBitmap(bmp);









        try {
            sImage = new String( Base64.encode(imageInByte,Base64.DEFAULT), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }




        databaseReference= FirebaseDatabase.getInstance().getReference();
        textViewCancel.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        } );
        textViewSend.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                try {
                    sImage = new String( Base64.encode(imageInByte,Base64.DEFAULT), "UTF-8");
                }  catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                String key = databaseReference.child( "chats" ).child( uidUser ).child( uidFriend ).push().getKey();
                Date c = Calendar.getInstance().getTime();
                SimpleDateFormat df = new SimpleDateFormat( "dd-MM-yyyy HH:mm:s" );
                String formattedDate = df.format( c );
                Chat chat = new Chat( null, formattedDate,true,sImage, true );
                Map<String, Object> postValues = chat.toMap();
                Map<String, Object> childUpdates = new HashMap<>();
                childUpdates.put( "/chats/" + uidUser + "/" + uidFriend + "/" + key, postValues );
                databaseReference.updateChildren( childUpdates );


                //-------------------------------------------//


                String key1 = databaseReference.child( "chats" ).child( uidFriend ).child( uidUser ).push().getKey();
                Chat chat1 = new  Chat( null, formattedDate,true,sImage, false );
                Map<String, Object> postValues1 = chat1.toMap();
                Map<String, Object> childUpdates1 = new HashMap<>();

                childUpdates1.put( "/chats/" + uidFriend + "/" + uidUser + "/" + key1, postValues1 );
                databaseReference.updateChildren( childUpdates1 );


                finish();
            }
        } );
    }
}
