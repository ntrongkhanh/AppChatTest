package com.example.appchattest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.util.Base64Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ForgotPassActivity extends AppCompatActivity {

    private EditText editTextEmail_forgotPass;
    private Button button_Confirm_resetPass;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);

        editTextEmail_forgotPass = findViewById(R.id.editText_email_Confirm);
        button_Confirm_resetPass = findViewById(R.id.button_resetPass);

        firebaseAuth = FirebaseAuth.getInstance();

        button_Confirm_resetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editTextEmail_forgotPass.getText().toString().isEmpty()) {
                    firebaseAuth.sendPasswordResetEmail(editTextEmail_forgotPass.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getApplicationContext(), "Email đã được gửi!", Toast.LENGTH_SHORT).show();
                                        button_Confirm_resetPass.setText("Gửi lại");
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Đặt lại mật khẩu thất bại! Vui lòng kiểm tra lại email!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
                else {
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập email!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
