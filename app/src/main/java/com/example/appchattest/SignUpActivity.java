package com.example.appchattest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.appchattest.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Date;

public class SignUpActivity extends AppCompatActivity {

    private Button buttonDangki;
    private EditText editTextHoten,editTextMatkhau,editTextMaukhau2;
    private EditText editTextNam,editTextThang,editTextNgay,editTextEmail;
    private RadioButton radioButtonNam,radioButtonNu;

    private Calendar calendar;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_sign_up );

        //editTextTuoi=findViewById( R.id.editText_tuoi_signup );

        firebaseAuth=FirebaseAuth.getInstance();
        addControl();

        buttonDangki.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if (validateForm()){

                    if(checkNgay( Integer.parseInt(editTextNgay.getText().toString()),Integer.parseInt(editTextThang.getText().toString()),Integer.parseInt(editTextNam.getText().toString()) ))
                    {
                        String pass1=editTextMatkhau.getText().toString().trim();
                        String pass2=editTextMaukhau2.getText().toString().trim( );
                        if (checkPassword(pass1,pass2)){
                        calendar= Calendar.getInstance();
                        calendar.set(Integer.parseInt(editTextNam.getText().toString()),Integer.parseInt(editTextThang.getText().toString()),Integer.parseInt(editTextNgay.getText().toString()) );
                        //Toast.makeText( getApplicationContext(),calendar.getTime().toString(),Toast.LENGTH_LONG ).show();
                        String ten=editTextHoten.getText().toString().trim();
                        String gioitinh;
                        String email=editTextEmail.getText().toString().trim();
                        if (radioButtonNam.isChecked())
                            gioitinh="Nam";
                        else gioitinh="Nữ";
                            //Toast.makeText( getApplicationContext(),"đăng kí",Toast.LENGTH_LONG ).show();
                            signUp(ten,gioitinh,calendar,pass1,email);



                        }
                        else {
                            Toast.makeText( getApplicationContext(),pass1
                                    +" "+pass2,Toast.LENGTH_LONG ).show();

                        }

                    }else {
                    Toast.makeText( getApplicationContext(),"Ngày sinh nhập sai",Toast.LENGTH_LONG ).show();
                    }
                }
                else {
                    Toast.makeText( getApplicationContext(),"Vui lòng điền đầy đủ thông tin",Toast.LENGTH_LONG ).show();
                }


            }
        } );

    }

    private void signUp(final String ten, final String gioitinh, final Calendar ngaysinh, String matkhau, final String email) {
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
                    writeUser(ten,email,gioitinh,ngaysinh);
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(),"Đăng kí thành công",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(SignUpActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();

                }else {
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(),"Đăng kí thất bại",Toast.LENGTH_SHORT).show();
                }
            }
        });
        firebaseAuth.signOut();
    }

    private void writeUser(String ten,String email,String gioitinh,Calendar ngaysinh)
    {
        FirebaseUser user=firebaseAuth.getCurrentUser();
        DatabaseReference mData= FirebaseDatabase.getInstance().getReference();
        String date=ngaysinh.get( Calendar.DATE)+"/"+ngaysinh.get(Calendar.MONTH)+"/"+ngaysinh.get(Calendar.YEAR);
        User user2=new User(ten,email,gioitinh,date);

        mData.child("users").child(user.getUid()).setValue(user2);


    }

    private boolean validateForm() {
        if (!editTextHoten.getText().toString().trim().isEmpty()
                && !editTextNgay.getText().toString().trim().isEmpty()
                && !editTextThang.getText().toString().trim().isEmpty()
                && !editTextNam.getText().toString().trim().isEmpty()
                && !editTextMatkhau.getText().toString().trim().isEmpty()
                && !editTextMaukhau2.getText().toString().trim().isEmpty()
                && (radioButtonNam.isChecked() ||radioButtonNu.isChecked())
        )
            return true;
        return false;
    }

    private boolean checkNgay(int ngay,int thang,int nam)
    {
        if(fun( thang,nam )==-1)
        {
            return false;
        }else {
            if (ngay<=fun( thang,nam ))
            {
                return true;
            }
            return false;
        }

    }
    private boolean checkPassword(String pass1,String pass2)
    {
        if (pass1.equals( pass2 ))
            return true;
        return false;
    }
    private boolean isCheck(int nam) {
        return ((nam % 4 == 0 && nam % 100 != 0) || nam % 400 == 0);
    }

    private int fun(int thang, int nam) {
        switch (thang)
        {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                return 31;
            case 4:
            case 6:
            case 9:
            case 11:
                return 30;
            case 2:
                if (isCheck(nam))
                    return 29;
                else
                    return 28;
            default:
                return -1;

        }
    }
    private void addControl() {
        buttonDangki=findViewById( R.id.button_dangki_signup );
        editTextNam=findViewById( R.id.editText_namsinh_signup );
        editTextThang=findViewById( R.id.editText_thangsinh_signup );
        editTextNgay=findViewById( R.id.editText_ngaysinh_signup );
        editTextHoten=findViewById( R.id.editText_hoten_signup );
        editTextMatkhau=findViewById( R.id.editText_matkhau_signup );
        editTextMaukhau2=findViewById( R.id.editText_matkhaulai_signup );
        radioButtonNam=findViewById( R.id.radioButton_nam_signup );
        radioButtonNu=findViewById( R.id.radioButton_nu_signup );
        editTextEmail=findViewById( R.id.editText_email_signup );

    }
}
