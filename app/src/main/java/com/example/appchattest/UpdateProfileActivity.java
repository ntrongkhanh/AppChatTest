package com.example.appchattest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.appchattest.Model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Calendar;

public class UpdateProfileActivity extends AppCompatActivity {

    private Button button_ChangeProfile;
    private EditText editText_ChangeHoten;
    private EditText editTextEmail,editTextSDT;
    private RadioButton radioButtonNam,radioButtonNu;
    private ImageView imageViewAvatar;
    private Calendar calendar;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference mData;
    private StorageReference firebaseStorage;
    private FirebaseUser user;
    private String uidUser;

    private int PICK_IMAGE_REQUEST = 1;
    private static final int CAMERA_REQUEST = 1888;
    private DatePicker datePicker;

    private Uri uriImage;
    private boolean flagImage=false;

    private User userInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        firebaseStorage = FirebaseStorage.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        calendar = Calendar.getInstance();
        mData = FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid());

        addControl();

        mData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> nodechild=dataSnapshot.child( "users" ).getChildren();
                for (DataSnapshot data:nodechild)
                {
                    User user1=data.getValue(User.class);
                    if (user1.uid== FirebaseAuth.getInstance().getUid()) {
                        userInfo = user1;
                        break;
                    }
                }

                String sex_s;

                userInfo = dataSnapshot.getValue(User.class);

                editText_ChangeHoten.setText(userInfo.name);
                editTextSDT.setText(userInfo.phone);
                editTextEmail.setText(userInfo.email);
                sex_s = userInfo.sex;
                if (sex_s.equals("Nam"))
                    radioButtonNam.toggle();
                else radioButtonNu.toggle();

                byte a[] = Base64.decode(userInfo.avatar, Base64.DEFAULT);
                Bitmap bitmap = BitmapFactory.decodeByteArray(a, 0, a.length);
                imageViewAvatar.setImageBitmap(bitmap);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        button_ChangeProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateForm()){

                    calendar.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth());

                    String name = editText_ChangeHoten.getText().toString().trim();
                    String email = editTextEmail.getText().toString().trim();
                    String phone = editTextSDT.getText().toString().trim();
                    String sex_s;
                    if (radioButtonNam.isChecked())
                        sex_s = "Nam";
                    else sex_s = "Nữ";

                    Update(name, sex_s, calendar, email, phone);
                }
            }
        });
    }

    private void Update(final String name, final String sex, final Calendar birth, final String email, final String phone){
        Write(name, email, sex, birth, phone);
    }

    private void Write(String name,String email,String sex,Calendar birth,String phone){

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference mData= FirebaseDatabase.getInstance().getReference();

        String date = birth.get( Calendar.DATE) + "/" + (birth.get(Calendar.MONTH)+1) + "/" + birth.get(Calendar.YEAR);

        name = UpperCase(name);

        User user2 = new User(user.getUid(),name,email,sex,date,phone,userInfo.avatar);

        mData.child("users").child( user.getUid()).setValue( user2).addOnSuccessListener( new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getApplicationContext(),"Đổi thông tin thành công",Toast.LENGTH_LONG).show();
            }
        } ).addOnFailureListener( new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"Đổi thông tin thất bại",Toast.LENGTH_LONG).show();
            }
        } );
    }

    private String UpperCase(String str)
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

    private boolean validateForm() {
        if (!editText_ChangeHoten.getText().toString().trim().isEmpty()
                && !editTextSDT.getText().toString().trim().isEmpty()
                && (radioButtonNam.isChecked() ||radioButtonNu.isChecked())
        )
            return true;
        return false;
    }

    private void addControl()
    {
        button_ChangeProfile = findViewById(R.id.button_Update_Profile);
        editText_ChangeHoten = findViewById(R.id.editText_updateHoten);
        editTextEmail = findViewById(R.id.editText_email_Update);
        editTextSDT = findViewById(R.id.editText_SDT_Update);
        radioButtonNam = findViewById(R.id.radioButton_nam_Update);
        radioButtonNu = findViewById(R.id.radioButton_nu_Update);
        imageViewAvatar = findViewById(R.id.image_update_avatar);
        datePicker = findViewById(R.id.datePicker_Update);
    }
}
