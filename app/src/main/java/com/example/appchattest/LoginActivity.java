package com.example.appchattest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appchattest.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.example.appchattest.R.string.next_to_password;

public class LoginActivity extends AppCompatActivity implements ValueEventListener {

    private Button buttonLogin;
    private EditText editTextLogin;
    private TextView textViewCreateAcc;
    private FirebaseAuth firebaseAuth;
    private ArrayList<String> listMail=new ArrayList<>();
    private FirebaseDatabase database;
    private DatabaseReference mData;
    private String email="";
    private String password="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login );



        addControl();



        firebaseAuth=FirebaseAuth.getInstance();



        database= FirebaseDatabase.getInstance();
        mData=  database.getReference();
        //mData.addValueEventListener(this);

        mData.addValueEventListener( this );


        buttonLogin.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (buttonLogin.getText().toString().trim()!="ĐĂNG NHẬP"){
                    //kiểm tra email
                    email=editTextLogin.getText().toString().trim();

                    if (kiemtraEmail(email))
                    {




                        buttonLogin.setText("ĐĂNG NHẬP");
                        editTextLogin.setText("");
                        editTextLogin.setHint("password");

                    }
                    else {
                        Toast.makeText(getApplicationContext(),"Email không tồn tại",Toast.LENGTH_LONG ).show();
                    }
                }else {
                    // tiếp tục đăng nhập
                    //Toast.makeText(getApplicationContext(),"else",Toast.LENGTH_SHORT).show();
                    password=editTextLogin.getText().toString().trim();
                    dangNhap(email,password);


                    buttonLogin.setText( next_to_password );
                    editTextLogin.setText("");
                    editTextLogin.setHint("email");
                }

            }
        } );




    }

    private void dangNhap(String email, String password) {
        final ProgressDialog dialog =  new ProgressDialog(LoginActivity.this);
        dialog.show();
        firebaseAuth.signInWithEmailAndPassword( email,password ).addOnCompleteListener( this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                {
                    dialog.dismiss();
                    FirebaseUser user=firebaseAuth.getCurrentUser();
                    //updateUI(user);




                    Toast.makeText(getApplicationContext(),user.getDisplayName(),Toast.LENGTH_SHORT).show();




                }
                else {
                    // updateUI(null);
                    dialog.dismiss(Toast.makeText(getApplicationContext(), "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        } );
    }

    private boolean kiemtraEmail(String email) {

        if (listMail!=null) {
            if (listMail.indexOf(email) != -1) {
                return true;
            }else return false;
        }


        return false;
    }

    private void addControl() {
        buttonLogin=findViewById(R.id.button_login);
        editTextLogin=findViewById( R.id.editText_Login );
        textViewCreateAcc=findViewById( R.id.textView_CreateAcc );
    }


    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        listMail.clear();
        //dialog.show();

        Iterable<DataSnapshot> nodechild=dataSnapshot.child( "users" ).getChildren();
        for (DataSnapshot data:nodechild)
        {
            //Toast.makeText(getApplicationContext(),data,Toast.LENGTH_SHORT).show();
            User user=data.getValue(User.class);
            //arrayList.add(user);
            //String string=user.getChatContent();
            listMail.add( user.getEmail() );
        }
        // dialog.dismiss();




    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }
}
