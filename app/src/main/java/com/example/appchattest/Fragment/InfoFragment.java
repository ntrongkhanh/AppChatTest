package com.example.appchattest.Fragment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.appchattest.ChangePassActivity;
import com.example.appchattest.ImageAvatarActivity;
import com.example.appchattest.LoginActivity;
import com.example.appchattest.Model.User;
import com.example.appchattest.R;

import com.example.appchattest.SignUpActivity;
import com.example.appchattest.UpdateProfileActivity;
import com.google.android.gms.common.util.Clock;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import static android.app.Activity.RESULT_OK;


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
    private Button buttonChangePass;
    private Button buttonUpdateProfile;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference mData;
    private User userInfo;
    private int PICK_IMAGE_REQUEST = 1;
    private static final int CAMERA_REQUEST = 1888;
    private Dialog dialog;

    public InfoFragment(User currentUser) {
        this.userInfo = currentUser;
    }

    public InfoFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate( R.layout.fragment_info, container, false );

        return view;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated( view, savedInstanceState );
        addControls( view );
        firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        mData = FirebaseDatabase.getInstance().getReference().child( "users" ).child( user.getUid() );

        mData.addValueEventListener( this );
        imageViewAvatar.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog( getContext() );
                dialog.setContentView( R.layout.custom_menu );
                dialog.getWindow().setBackgroundDrawable( new ColorDrawable( Color.TRANSPARENT ) );
                dialog.setCanceledOnTouchOutside( true );
                dialog.show();
                Button bt_xem = dialog.findViewById( R.id.button_seePhoto_custom_menu );
                Button bt_chon = dialog.findViewById( R.id.button_availablePhoto_custom_menu );
                Button bt_chup = dialog.findViewById( R.id.button_newPhoto_custom_menu );

                Button bt_huy = dialog.findViewById( R.id.button_cancel_custom_menu );
                bt_xem.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {  dialog.dismiss();
                        displayImage();

                    }
                } );
                bt_chon.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {  dialog.dismiss();
                        dispatchPickImage();

                    }
                } );
                bt_chup.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        dispatchTakePictureIntent();

                    }
                } );
                bt_huy.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                } );

            }
        } );
        buttonLogout.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mData.child( "status" ).setValue( "Offline" );
                logout();
            }
        } );
        buttonChangePass.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePass();
            }

        });
        buttonUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateProfileUser();
            }
        });
    }

    private void addControls(View view) {
        imageViewAvatar=view.findViewById( R.id.imageView_account_avatar1 );
        textViewName=view.findViewById( R.id.textView_account_name );
        textViewEmail=view.findViewById( R.id.textView_account_email );
        textViewGioiTinh=view.findViewById( R.id.textView_account_sex ) ;
        textViewNgaySinh=view.findViewById( R.id.textView_account_birthday  );
        textViewSDT=view.findViewById( R.id.textView_account_phone_number );
        buttonLogout=view.findViewById( R.id.button_logout );
        buttonChangePass=view.findViewById(R.id.button_changePassword);
        buttonUpdateProfile=view.findViewById(R.id.button_changeProfile);

    }


    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        userInfo = dataSnapshot.getValue( User.class );
        textViewNgaySinh.setText( userInfo.birthday );
        textViewGioiTinh.setText( userInfo.sex );
        textViewSDT.setText( userInfo.phone );
        textViewName.setText( userInfo.name );
        textViewEmail.setText( userInfo.email );
        // lấy ảnh về
        byte[] a = Base64.decode( userInfo.avatar, Base64.DEFAULT );
        Bitmap bitmap1 = BitmapFactory.decodeByteArray( a, 0, a.length );
        imageViewAvatar.setImageBitmap( bitmap1 );
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult( requestCode, resultCode, data );

        if (requestCode == PICK_IMAGE_REQUEST && resultCode ==getActivity().RESULT_OK && data != null && data.getData() != null) {
            Uri uriImage = data.getData();
            try {
                final Bitmap photo = MediaStore.Images.Media.getBitmap( getActivity().getContentResolver(), uriImage );
                // Log.d(TAG, String.valueOf(bitmap));
                imageViewAvatar.setImageBitmap( photo );

            } catch (Exception e) {
                e.printStackTrace();
            }
            uploadAvatar();
        } else if (requestCode == CAMERA_REQUEST && resultCode == getActivity().RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get( "data" );
            imageViewAvatar.setImageBitmap( photo );
            // imageViewAvatar.setImageURI( uriImage );
            uploadAvatar();
        }
        System.out.println("###mskdasdadajd");
      //  uploadAvatar();

    }


    private void uploadAvatar() {

        Bitmap bitmap = ((BitmapDrawable) imageViewAvatar.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress( Bitmap.CompressFormat.JPEG, 100, baos );
        byte[] data = baos.toByteArray();
        String avatar = null;
        try {
            avatar = new String( Base64.encode( data, Base64.DEFAULT ), "UTF-8" );
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        userInfo.avatar = avatar;

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child( "users" );
        databaseReference.child( userInfo.uid ).setValue( userInfo );
    }




    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent( MediaStore.ACTION_IMAGE_CAPTURE );
        if (takePictureIntent.resolveActivity( getActivity().getPackageManager() ) != null) {
            startActivityForResult( takePictureIntent, CAMERA_REQUEST );
        }

    }

    private void dispatchPickImage() {
        Intent photoPickerIntent = new Intent( Intent.ACTION_PICK );
        photoPickerIntent.setType( "image/*" );
        startActivityForResult( photoPickerIntent, PICK_IMAGE_REQUEST );
    }

    private void displayImage() {
        Bitmap bitmap = ((BitmapDrawable) imageViewAvatar.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress( Bitmap.CompressFormat.JPEG, 100, baos );
        byte[] data = baos.toByteArray();
        Intent intent = new Intent( getActivity(), ImageAvatarActivity.class );
        intent.putExtra( "image", data );
        startActivity( intent );
    }


    private void logout() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent( getActivity(), LoginActivity.class );
        startActivity( intent );
    }

    private void changePass() {
        Intent intent = new Intent( getActivity(), ChangePassActivity.class );
        startActivity( intent );
    }
    private void UpdateProfileUser()
    {
        Intent intent = new Intent(getContext(), UpdateProfileActivity.class);
        startActivity(intent);
    }
}
