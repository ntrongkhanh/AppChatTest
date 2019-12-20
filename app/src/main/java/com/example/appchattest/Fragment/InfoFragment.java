package com.example.appchattest.Fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.appchattest.LoginActivity;
import com.example.appchattest.MainNavigationActivity;
import com.example.appchattest.Model.User;
import com.example.appchattest.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


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
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate( R.layout.fragment_info, container, false );

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );





    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated( view, savedInstanceState );


        addControls(view);
         firebaseAuth = FirebaseAuth.getInstance();


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        mData=  FirebaseDatabase.getInstance().getReference().child( "users" ).child( user.getUid() );
        //mData.addValueEventListener(this);

        mData.addValueEventListener( this );
        textViewEmail.setText(user.getEmail() );
        textViewName.setText( user.getDisplayName() );

        StorageReference flieRef=FirebaseStorage.getInstance().getReference().child("avatar/"+user.getUid()+"/avatar.png");
        long megabyte=1024*1024;
        flieRef.getBytes( megabyte ).addOnSuccessListener( new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap= BitmapFactory.decodeByteArray( bytes,0,bytes.length );
                imageViewAvatar.setImageBitmap( bitmap );
            }
        } ).addOnFailureListener( new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText( getContext(),"load thất bại",Toast.LENGTH_LONG ).show();
            }
        } );


        buttonLogout.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        } );

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
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }


    private void logout()
    {
        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        firebaseAuth.signOut();
        Intent intent = new Intent( getActivity(),LoginActivity.class );
        startActivity(intent);
        getActivity().finish();

    }
}
