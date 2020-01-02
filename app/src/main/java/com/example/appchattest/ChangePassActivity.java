package com.example.appchattest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ChangePassActivity extends AppCompatActivity {

    private EditText editTextPasswordChange;
    private EditText editTextNewPassword;
    private EditText editTextNewPassword2;
    private Button buttonConfirmChange;
    private Button buttonCancel;

    private FirebaseUser user;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference mData;
    private String uidUser;
    private String pass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_change_pass);

        editTextNewPassword = findViewById(R.id.editText_Change_NewPass);
        editTextNewPassword2 = findViewById(R.id.editText_Change_NewPass_2);
        editTextPasswordChange = findViewById(R.id.editText_Change_Pass);
        buttonConfirmChange = findViewById(R.id.button_changePass_Confirm);
        buttonCancel = findViewById(R.id.button_changePass_cancel);

        uidUser = FirebaseAuth.getInstance().getUid();
        user = FirebaseAuth.getInstance().getCurrentUser();
        mData = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        buttonConfirmChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String newPass1 = editTextNewPassword.getText().toString().trim();
                String newPass2 = editTextNewPassword2.getText().toString().trim();

                if (editTextPasswordChange.getText().toString().trim().isEmpty())
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập mật khẩu!", Toast.LENGTH_SHORT).show();
                else if (editTextNewPassword.getText().toString().trim().isEmpty())
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập mật khẩu mới!", Toast.LENGTH_SHORT).show();
                else if (editTextNewPassword2.getText().toString().trim().isEmpty())
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập lại mật khẩu mới!", Toast.LENGTH_SHORT).show();
                if (!editTextPasswordChange.getText().toString().trim().isEmpty() ||
                        !editTextNewPassword.getText().toString().trim().isEmpty() ||
                        !editTextNewPassword2.getText().toString().trim().isEmpty()) {
                    if (newPass1.equals(newPass2)) {
                        if (user != null && user.getEmail() != null){
                            AuthCredential credential = EmailAuthProvider
                                    .getCredential(user.getEmail(), editTextPasswordChange.getText().toString());

                            user.reauthenticate(credential)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()){
                                                Toast.makeText(getApplicationContext(), "Re-Au success", Toast.LENGTH_SHORT).show();

                                                user.updatePassword(editTextNewPassword.getText().toString())
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful()) {
                                                                    Toast.makeText(getApplicationContext(), "Change pass success", Toast.LENGTH_SHORT).show();

                                                                    firebaseAuth.signOut();
                                                                    Intent intent = new Intent(ChangePassActivity.this, LoginActivity.class);
                                                                    startActivity(intent);
                                                                }
                                                            }
                                                        });
                                            }
                                            else {
                                                Toast.makeText(getApplicationContext(), "Re-Au failed", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        }else{

                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Mật khẩu không giống! Vui lòng kiểm tra lại!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
