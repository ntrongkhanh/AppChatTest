package com.example.appchattest;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class CreateAccount extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account);

        Init();
    }

    private EditText editText_email;
    private EditText editText_password;
    private EditText editText_conPassword;

    private TextView textView_email;
    private TextView textView_password;
    private TextView textView_conpassword;

    private void Init()
    {
        editText_email = (EditText) findViewById(R.id.regis_edit_email);
        editText_password = (EditText) findViewById(R.id.regis_edit_password);
        editText_conPassword = (EditText) findViewById(R.id.regis_edit_conpassword);

        textView_email = (TextView) findViewById(R.id.regis_text_email);
        textView_password = (TextView) findViewById(R.id.regis_text_password);
        textView_conpassword = (TextView) findViewById((R.id.regis_text_conpassword));
    }
}
