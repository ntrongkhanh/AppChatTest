package com.example.appchattest.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.content.AsyncTaskLoader;

import android.os.AsyncTask;

import com.example.appchattest.ImageAvatarActivity;
import com.example.appchattest.LoginActivity;
import com.example.appchattest.Model.User;
import com.example.appchattest.R;
import com.example.appchattest.SignUpActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;


public class InfoFragment extends Fragment implements ValueEventListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private ImageView imageViewAvatar;
    private TextView textViewName;
    private TextView textViewEmail;
    private TextView textViewSDT;
    private TextView textViewNgaySinh;
    private TextView textViewGioiTinh;
    private Button buttonLogout;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference mData;
    private User userInfo;
    private int PICK_IMAGE_REQUEST = 1;
    private static final int CAMERA_REQUEST = 1888;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=  inflater.inflate( R.layout.fragment_info, container, false );
        addControls(view);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        mData=  FirebaseDatabase.getInstance().getReference().child( "users" ).child( user.getUid() );
        mData.addValueEventListener( this );
        imageViewAvatar.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu=new PopupMenu( getContext(),v, Gravity.BOTTOM );

                popupMenu.setOnMenuItemClickListener( new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId())
                        {
                            case R.id.seePhoto_popupmenu:
                                displayImage();
                                return true;
                            case R.id.newPhoto_popupmenu:
                                dispatchTakePictureIntent();
                                return true;
                            case R.id.availablePhoto_popuptmenu:
                                dispatchPickImage();
                                return true;
                            case R.id.cancel_popupmenu:

                                return true;
                            default:
                                return false;
                        }
                    }
                } );
                popupMenu.inflate( R.menu.popupmenu_avatar );

                popupMenu.show();
            }
        } );
        buttonLogout.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        } );
        return view;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated( view, savedInstanceState );
    }

    private void addControls(View view) {
        imageViewAvatar=view.findViewById( R.id.imageView_account_avatar1 );
        textViewName=view.findViewById( R.id.textView_account_name );
        textViewEmail=view.findViewById( R.id.textView_account_email );
        textViewGioiTinh=view.findViewById( R.id.textView_account_sex ) ;
        textViewNgaySinh=view.findViewById( R.id.textView_account_birthday  );
        textViewSDT=view.findViewById( R.id.textView_account_phone_number );
        buttonLogout=view.findViewById( R.id.button_logout );
    }


    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        userInfo=dataSnapshot.getValue( User.class );
        textViewNgaySinh.setText( userInfo.birthday );
        textViewGioiTinh.setText( userInfo.sex );
        textViewSDT.setText( userInfo.phone );
        textViewName.setText( userInfo.name );
        textViewEmail.setText( userInfo.email );
        // lấy ảnh về
        byte[] a= Base64.decode( userInfo.avatar,Base64.DEFAULT );
        Bitmap bitmap1= BitmapFactory.decodeByteArray( a,0,a.length );
        imageViewAvatar.setImageBitmap( bitmap1 );
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult( requestCode, resultCode, data );

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == getActivity().RESULT_OK && data != null && data.getData() != null) {
            Uri uriImage = data.getData();


            try {
                final Bitmap photo = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uriImage);
                // Log.d(TAG, String.valueOf(bitmap));
                        imageViewAvatar.setImageBitmap(photo);

                uploadAvatar();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (requestCode == CAMERA_REQUEST && resultCode == getActivity().RESULT_OK )
        {
            final Bitmap photo = (Bitmap) data.getExtras().get("data");


                    imageViewAvatar.setImageBitmap(photo);

            uploadAvatar();

        }
    }


    private void uploadAvatar()
    {

        Bitmap bitmap = ((BitmapDrawable) imageViewAvatar.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress( Bitmap.CompressFormat.PNG, 100, baos);
        byte[] data = baos.toByteArray();
        String avatar = null;
        try {
            avatar = new String( Base64.encode(data,Base64.DEFAULT), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        userInfo.avatar=avatar;

        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference().child( "users" );
        databaseReference.child( userInfo.uid).setValue( userInfo);
    }
    //chọn ảnh

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent( MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, CAMERA_REQUEST);
        }
    }

    private  void dispatchPickImage()
    {
        Intent photoPickerIntent = new Intent( Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult( photoPickerIntent,PICK_IMAGE_REQUEST );
    }
    private void displayImage()
    {
        Bitmap bitmap = ((BitmapDrawable) imageViewAvatar.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress( Bitmap.CompressFormat.PNG, 100, baos);
        byte[] data = baos.toByteArray();

        Intent intent=new Intent(getActivity(), ImageAvatarActivity.class);
        intent.putExtra( "image",data );
        startActivity( intent );
    }



    private void logout()
    {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent( getActivity(), LoginActivity.class );
        startActivity(intent);


    }
}
