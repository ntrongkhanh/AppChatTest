package com.example.appchattest;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.appchattest.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;

public class SignUpActivity extends AppCompatActivity {

    private Button buttonDangki;
    private EditText editTextHoten,editTextMatkhau,editTextMaukhau2;
    private EditText editTextNam,editTextThang,editTextNgay,editTextEmail,editTextSDT;
    private RadioButton radioButtonNam,radioButtonNu;
    private ImageView imageView;
    private Calendar calendar;
    private FirebaseAuth firebaseAuth;
    private StorageReference firebaseStorage;
    private int PICK_IMAGE_REQUEST = 1;
    private static final int CAMERA_REQUEST = 1888;
    private DatePicker datePicker;



    private Uri uriImage;
    private boolean flagImage=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_sign_up );
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //editTextTuoi=findViewById( R.id.editText_tuoi_signup );
        firebaseStorage=FirebaseStorage.getInstance().getReference();
        firebaseAuth=FirebaseAuth.getInstance();
        addControl();



        // chọn ảnh
        imageView.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                showPopup( v );

            }
        } );


        buttonDangki.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if (validateForm()){


                        String pass1=editTextMatkhau.getText().toString().trim();
                        String pass2=editTextMaukhau2.getText().toString().trim( );

                        if (checkPassword(pass1,pass2)){
//                        calendar= Calendar.getInstance();
//                        calendar.set(Integer.parseInt(editTextNam.getText().toString()),Integer.parseInt(editTextThang.getText().toString()),Integer.parseInt(editTextNgay.getText().toString()) );
                        //Toast.makeText( getApplicationContext(),calendar.getTime().toString(),Toast.LENGTH_LONG ).show();
                            calendar=Calendar.getInstance();
                            calendar.set( datePicker.getYear(),datePicker.getMonth(),datePicker.getDayOfMonth() );
                            //calendar.set( 12,11,1999 );
                            String ten=editTextHoten.getText().toString().trim();
                            String gioitinh;
                            String email=editTextEmail.getText().toString().trim();
                            String sdt=editTextSDT.getText().toString().trim();
                            if (radioButtonNam.isChecked())
                               gioitinh="Nam";
                            else gioitinh="Nữ";
                            //Toast.makeText( getApplicationContext(),"đăng kí",Toast.LENGTH_LONG ).show();
                            //uploadFile( uriImage );

                            signUp(ten,gioitinh,calendar,pass1,email,sdt);



                        }
                        else {
                            Toast.makeText( getApplicationContext(),pass1
                                    +" "+pass2,Toast.LENGTH_LONG ).show();

                        }


                }
                else {
                    Toast.makeText( getApplicationContext(),"Vui lòng điền đầy đủ thông tin",Toast.LENGTH_LONG ).show();
                }


            }
        } );

    }

    //chọn ảnh
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult( requestCode, resultCode, data );
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            uriImage = data.getData();


            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uriImage);
                // Log.d(TAG, String.valueOf(bitmap));


                imageView.setImageBitmap(bitmap);
                flagImage=true;
               // uploadFile(uri);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK )
        {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(photo);
            flagImage=true;
        }
    }



    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, CAMERA_REQUEST);
        }
    }

    private  void dispatchPickImage()
    {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult( photoPickerIntent,PICK_IMAGE_REQUEST );
    }
    private void displayImage()
    {
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] data = baos.toByteArray();

       Intent intent=new Intent( SignUpActivity.this,ImageAvatarActivity.class );
        intent.putExtra( "image",data );
        startActivity( intent );
    }
    private void uploadFile(Uri uri)
    {

        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        String key=user.getUid();
        StorageReference fileRef=firebaseStorage.child( "avatar/"+key ).child("avatar.png");
       // Uri uri =Uri.parse( "android.resource://"+getPackageName()+"/"+R.drawable.no_avatar1 );


        fileRef.putFile( uri ).addOnSuccessListener( new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

            //    Toast.makeText( getApplicationContext(),"thành công",Toast.LENGTH_LONG ).show();
            }
        } ).addOnFailureListener( new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
               // Toast.makeText( getApplicationContext(),"thất bại",Toast.LENGTH_LONG ).show();
            }
        } );
    }
    private void uploadAvatar()
    {
//        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
//        String key=user.getUid();
//        StorageReference fileRef=firebaseStorage.child( "avatar/"+key ).child("avatar.png");
//
//        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
//        byte[] data = baos.toByteArray();
//        UploadTask uploadTask=fileRef.putBytes( data );
//        uploadTask.addOnSuccessListener( new OnSuccessListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                 //   Toast.makeText( getApplicationContext(),"Upload thanhf coong" ,Toast.LENGTH_LONG).show();
//            }
//        } ).addOnFailureListener( new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//               // Toast.makeText( getApplicationContext(),"Upload that bai" ,Toast.LENGTH_LONG).show();
//            }
//        } );
    }

    private void showPopup(View v)
    {
        PopupMenu popupMenu=new PopupMenu( this,v,Gravity.BOTTOM );

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

    private void signUp(final String ten, final String gioitinh, final Calendar ngaysinh, String matkhau, final String email, final String sdt) {
        final ProgressDialog dialog =  new ProgressDialog(SignUpActivity.this);
        dialog.show();
        firebaseAuth.createUserWithEmailAndPassword(email,matkhau).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                            .setDisplayName(ten).build();
                    user.updateProfile(profileUpdates);

                    //uploadAvatar();
//

                    writeUser(ten,email,gioitinh,ngaysinh,sdt);

                  //  writeUser( ten,email,gioitinh,ngaysinh,sdt );
                    dialog.dismiss();
                   // Toast.makeText(getApplicationContext(),"Đăng kí thành công",Toast.LENGTH_LONG).show();




                    Intent intent=new Intent(SignUpActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                 //   user=FirebaseAuth.getInstance().getCurrentUser();




                }else {
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(),"Đăng kí thất bại",Toast.LENGTH_SHORT).show();
                }
            }
        });
        firebaseAuth.signOut();
    }

    private void writeUser(String ten,String email,String gioitinh,Calendar ngaysinh,String sdt)
    {
        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference mData= FirebaseDatabase.getInstance().getReference();
        String date=ngaysinh.get( Calendar.DATE)+"/"+(ngaysinh.get(Calendar.MONTH)+1)+"/"+ngaysinh.get(Calendar.YEAR);
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress( Bitmap.CompressFormat.PNG, 100, baos);
        byte[] data = baos.toByteArray();
        String avatar = null;
        try {
            avatar = new String( Base64.encode(data,Base64.DEFAULT), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        ten=vietHoa( ten );
        User user2=new User(user.getUid(),ten,email,gioitinh,date,sdt,avatar);
        mData.child("users").child( user.getUid()).setValue( user2).addOnSuccessListener( new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
               Toast.makeText(getApplicationContext(),"Đăng kí thành công...........",Toast.LENGTH_LONG).show();
            }
        } ).addOnFailureListener( new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"Đăng kí thất bại...............",Toast.LENGTH_LONG).show();
            }
        } );
    }

    private boolean validateForm() {
        if (!editTextHoten.getText().toString().trim().isEmpty()

                && !editTextMatkhau.getText().toString().trim().isEmpty()
                && !editTextMaukhau2.getText().toString().trim().isEmpty()
                && !editTextSDT.getText().toString().trim().isEmpty()
                && (radioButtonNam.isChecked() ||radioButtonNu.isChecked())
        )
            return true;
        return false;
    }

    private boolean checkPassword(String pass1,String pass2)
    {
        if (pass1.equals( pass2 ))
            return true;
        return false;
    }
    private String vietHoa(String str)
    {
        String kq = "";
        str = str.toLowerCase();//
        for (int i = 0; i < str.length(); i++)
        {
            if (i == 0)

                kq += (str.charAt(i)+"").toUpperCase();
            else
                kq += str.charAt( i );
            if (str.charAt( i ) == ' ')
            {
                while (str.charAt( i ) == ' ')
                {
                    i++;
                }
                kq += (str.charAt( i )+"").toUpperCase();
            }
        }
        return kq;
    }
    private void addControl() {
        imageView=findViewById( R.id.imageView_account_avatar2 );


        buttonDangki=findViewById( R.id.button_dangki_signup );
       // editTextNam=findViewById( R.id.editText_namsinh_signup );
        //editTextThang=findViewById( R.id.editText_thangsinh_signup );
        //editTextNgay=findViewById( R.id.editText_ngaysinh_signup );
        editTextHoten=findViewById( R.id.editText_hoten_signup );
        editTextMatkhau=findViewById( R.id.editText_matkhau_signup );
        editTextMaukhau2=findViewById( R.id.editText_matkhaulai_signup );
        radioButtonNam=findViewById( R.id.radioButton_nam_signup );
        radioButtonNu=findViewById( R.id.radioButton_nu_signup );
        editTextEmail=findViewById( R.id.editText_email_signup );
        editTextSDT=findViewById( R.id.editText_SDT_signup );
        datePicker=findViewById( R.id.datePicker_signup );

    }
}
