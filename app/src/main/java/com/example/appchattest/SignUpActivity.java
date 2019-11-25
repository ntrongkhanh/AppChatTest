package com.example.appchattest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class SignUpActivity extends AppCompatActivity {

    private Button buttonDangki;
    private EditText editTextHoten,editTextMatkhau,editTextMaukhau2;
    private EditText editTextNam,editTextThang,editTextNgay;
    private RadioButton radioButtonNam,radioButtonNu;
    private CalendarView calendarView;
    private Calendar calendar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_sign_up );

        //editTextTuoi=findViewById( R.id.editText_tuoi_signup );

        addControl();

        buttonDangki.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar= Calendar.getInstance();
                calendar.set(Integer.parseInt(editTextNam.getText().toString()),Integer.parseInt(editTextThang.getText().toString()),Integer.parseInt(editTextNgay.getText().toString()) );
                Date date=new Date(  );
                
                Toast.makeText( getApplicationContext(),calendar.toString(),Toast.LENGTH_LONG ).show();
            }
        } );

    }

    private void addControl() {
        buttonDangki=findViewById( R.id.button_dangki_signup );
        editTextNam=findViewById( R.id.editText_namsinh_signup );
        editTextThang=findViewById( R.id.editText_thangsinh_signup );
        editTextNgay=findViewById( R.id.editText_ngaysinh_signup );
    }
}
