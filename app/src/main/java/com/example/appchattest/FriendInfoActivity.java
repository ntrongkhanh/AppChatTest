package com.example.appchattest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appchattest.Model.Contacts;
import com.example.appchattest.Model.User;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class FriendInfoActivity extends AppCompatActivity {

    private Button buttonMessage;
    private Button buttonUnfriend;
    private ImageView imageViewAvatar;
    private TextView textViewName;
    private TextView textViewEmail;
    private TextView textViewSDT;
    private TextView textViewNgaySinh;
    private TextView textViewGioiTinh;
    private String uidUser;
    private String uidFriend = "";

    private DatabaseReference mData;
    private FirebaseUser user;

    private User friend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_info);

        imageViewAvatar = findViewById(R.id.imageView_friend_avatar_info);
        textViewName = findViewById(R.id.textView_friend_name);
        textViewEmail = findViewById(R.id.textView_friend_email);
        textViewSDT = findViewById(R.id.textView_friend_phone_number);
        textViewNgaySinh = findViewById(R.id.textView_friend_birthday);
        textViewGioiTinh = findViewById(R.id.textView_friend_sex);

        buttonMessage = findViewById(R.id.bt_message);
        buttonUnfriend = findViewById(R.id.bt_unfriend);

        uidUser = FirebaseAuth.getInstance().getUid();
        user = FirebaseAuth.getInstance().getCurrentUser();
        mData = FirebaseDatabase.getInstance().getReference();

        final Intent intent = getIntent();

        uidFriend = intent.getStringExtra("uidFriend");
        textViewName.setText(intent.getStringExtra("nameFriend"));
        textViewEmail.setText(intent.getStringExtra("emailFriend"));
        textViewGioiTinh.setText(intent.getStringExtra("sexFriend"));
        textViewSDT.setText(intent.getStringExtra("phoneFriend"));
        textViewNgaySinh.setText(intent.getStringExtra("birthFriend"));

        mData.child("users").child(uidFriend).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                friend = dataSnapshot.getValue(User.class);
                byte a[] = Base64.decode(friend.avatar, Base64.DEFAULT);

                Bitmap bitmap = BitmapFactory.decodeByteArray(a, 0, a.length);
                imageViewAvatar.setImageBitmap(bitmap);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        buttonMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(FriendInfoActivity.this, ChatActivity.class);

                intent1.putExtra("nameFriend", intent.getStringExtra("nameFriend"));
                intent1.putExtra("uidFriend", intent.getStringExtra("uidFriend"));

                startActivity(intent1);
            }
        });

        buttonUnfriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mData.child("friends").child(uidUser).child(uidFriend).removeValue();
                mData.child("friends").child(uidFriend).child(uidUser).removeValue();

                finish();
            }
        });
    }
}