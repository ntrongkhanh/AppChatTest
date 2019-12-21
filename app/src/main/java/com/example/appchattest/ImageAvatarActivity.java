package com.example.appchattest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

public class ImageAvatarActivity extends AppCompatActivity {
    private ImageView imageView;
    private  byte[] imageInByte;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_image_avatar );
        imageView=findViewById( R.id.imageAvatar );
        Intent intent=getIntent();
        imageInByte=intent.getByteArrayExtra( "image" );
        Bitmap bmp = BitmapFactory.decodeByteArray(imageInByte, 0, imageInByte.length);
        imageView.setImageBitmap(bmp);
    }
}
