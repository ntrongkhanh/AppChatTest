package com.example.appchattest;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appchattest.Adapter.ChatAdapter;
import com.example.appchattest.Model.Chat;
import com.example.appchattest.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {

    private String uidFriend = "";
    private String uidUser;
    private String nameFriend;
    private User friend;
    private Bitmap avatarFriend = null;
    private TextView textViewNameFriend, textViewBack;
    private ListView listView;
    private EditText editTextContent;
    private ImageButton buttonSend, buttonImage, buttonCamera;
    private ArrayList<Chat> listChat = new ArrayList<>();
    private float x1, x2, y1, y2;
    private DatabaseReference databaseReference;
    private int PICK_IMAGE_REQUEST = 1;
    private static final int CAMERA_REQUEST = 1888;
    private ChatAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_chat );
        addControls();
        buttonSend.setVisibility( View.INVISIBLE );
        uidUser = FirebaseAuth.getInstance().getUid();
        final Intent intent = getIntent();
        uidFriend = intent.getStringExtra( "uidFriend" );
        nameFriend = intent.getStringExtra( "nameFriend" );
        textViewNameFriend.setText( nameFriend );
        editTextContent.setText( "" );
        databaseReference = FirebaseDatabase.getInstance().getReference();
        //adapter=new ChatAdapter( getApplicationContext(), listChat, uidFriend, avatarFriend );

        editTextContent.addTextChangedListener( new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() != 0) {
                buttonSend.setVisibility( View.VISIBLE );
                buttonCamera.setVisibility( View.INVISIBLE );
                buttonImage.setVisibility( View.INVISIBLE );
            }else

                {
                    buttonSend.setVisibility( View.INVISIBLE );
                    buttonCamera.setVisibility( View.VISIBLE );
                    buttonImage.setVisibility( View.VISIBLE );
                }

            }
        } );


        listView.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (listChat.get( position ).isImage() == true) {
                    byte[] a = Base64.decode( listChat.get( position ).contentsImage, Base64.DEFAULT );


                    Intent intent = new Intent( ChatActivity.this, ImageAvatarActivity.class );
                    intent.putExtra( "image", a );
                    startActivity( intent );

                }
            }
        } );
        scrollMyListViewToBottom();


        databaseReference.child( "users" ).child( uidFriend ).addListenerForSingleValueEvent( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                friend = dataSnapshot.getValue( User.class );
                // lấy ảnh về
                byte[] a = Base64.decode( friend.avatar, Base64.DEFAULT );
                avatarFriend = BitmapFactory.decodeByteArray( a, 0, a.length );
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        } );
        databaseReference.child( "chats" ).child( uidUser ).child( uidFriend ).addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> nodechild = dataSnapshot.getChildren();
                listChat.clear();

                for (DataSnapshot data : nodechild) {
                    try {
                        Chat chat = data.getValue( Chat.class );
                        listChat.add( chat );
                    } catch (Exception e) {

                    }

                }
                if (!uidFriend.equals( "" ) && avatarFriend != null)
                    listView.setAdapter( new ChatAdapter( getApplicationContext(), listChat, uidFriend, avatarFriend ) );
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
        buttonImage.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchPickImage();
            }
        } );
        buttonCamera.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent( MediaStore.ACTION_IMAGE_CAPTURE );
                if (takePictureIntent.resolveActivity( getPackageManager() ) != null) {
                    startActivityForResult( takePictureIntent, CAMERA_REQUEST );
                }
            }
        } );
        buttonSend.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextContent.getText().toString().equals( "" ))
                    return;
                String key = databaseReference.child( "chats" ).child( uidUser ).child( uidFriend ).push().getKey();
                Date c = Calendar.getInstance().getTime();
                SimpleDateFormat df = new SimpleDateFormat( "dd-MM-yyyy HH:mm:s" );
                String formattedDate = df.format( c );
                Chat chat = new Chat( editTextContent.getText().toString(), formattedDate, false, null, true );
                Map<String, Object> postValues = chat.toMap();
                Map<String, Object> childUpdates = new HashMap<>();
                childUpdates.put( "/chats/" + uidUser + "/" + uidFriend + "/" + key, postValues );
                databaseReference.updateChildren( childUpdates );


                //-------------------------------------------//


                String key1 = databaseReference.child( "chats" ).child( uidFriend ).child( uidUser ).push().getKey();
                Chat chat1 = new Chat( editTextContent.getText().toString(), formattedDate, false, null, false );
                Map<String, Object> postValues1 = chat1.toMap();
                Map<String, Object> childUpdates1 = new HashMap<>();

                childUpdates1.put( "/chats/" + uidFriend + "/" + uidUser + "/" + key1, postValues1 );
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

    private void dispatchPickImage() {
        Intent photoPickerIntent = new Intent( Intent.ACTION_PICK );
        photoPickerIntent.setType( "image/*" );
        startActivityForResult( photoPickerIntent, PICK_IMAGE_REQUEST );
    }

    private void scrollMyListViewToBottom() {
        listView.post( new Runnable() {
            @Override
            public void run() {
                // Select the last row so it will scroll into view...
                listView.setSelection( listView.getCount() - 1 );
            }
        } );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult( requestCode, resultCode, data );
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == this.RESULT_OK && data != null && data.getData() != null) {
            Uri uriImage = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap( this.getContentResolver(), uriImage );
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress( Bitmap.CompressFormat.JPEG, 100, baos );
                byte[] bbitmap = baos.toByteArray();
                Intent intent = new Intent( this, SendImageActivity.class );

                intent.putExtra( "image", bbitmap );
                intent.putExtra( "uidUser", uidUser );
                intent.putExtra( "uidFriend", uidFriend );
                startActivity( intent );
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (requestCode == CAMERA_REQUEST && resultCode == this.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get( "data" );
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            photo.compress( Bitmap.CompressFormat.JPEG, 100, baos );
            byte[] bbitmap = baos.toByteArray();
            Intent intent = new Intent( this, SendImageActivity.class );

            intent.putExtra( "image", bbitmap );
            intent.putExtra( "uidUser", uidUser );
            intent.putExtra( "uidFriend", uidFriend );
            startActivity( intent );
        }
    }

    private void addControls() {
        editTextContent = findViewById( R.id.editText_content_chat );
        buttonSend = findViewById( R.id.button_send_chat );
        textViewBack = findViewById( R.id.imageView_back_chat );
        textViewNameFriend = findViewById( R.id.title_name_chat );
        listView = findViewById( R.id.listView_chat );
        buttonImage = findViewById( R.id.button_image_chat );
        buttonCamera = findViewById( R.id.button_camera_chat );
    }

}
