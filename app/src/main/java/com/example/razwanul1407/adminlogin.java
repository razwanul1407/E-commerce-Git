package com.example.razwanul1407;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class adminlogin extends AppCompatActivity {

    private EditText adminemailEdittext,adminpasswordEdittext;
    private Button adminsigninbutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminlogin);


        adminemailEdittext=(EditText)findViewById(R.id.adminsignInEmailEditTextId);
        adminpasswordEdittext=(EditText) findViewById(R.id.adminsignInPasswordEditTextId);

        adminsigninbutton=(Button)findViewById(R.id.adminsignInButtonId);



        adminsigninbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adminloginfunction();
            }
        });
    }

    private void adminloginfunction() {

        String email = adminemailEdittext.getText().toString().trim();
        String password = adminpasswordEdittext.getText().toString().trim();

        if(email.isEmpty())
        {
            adminemailEdittext.setError("Enter an email address");
            adminpasswordEdittext.requestFocus();
            return;
        }
        if(password.isEmpty())
        {
            adminpasswordEdittext.setError("Enter a password");
            adminpasswordEdittext.requestFocus();
            return;
        }
        if(password.length()<6)
        {
            adminpasswordEdittext.setError("Minimum lenth of a password should be 6");
            adminpasswordEdittext.requestFocus();
            return;
        }
        if(email.equals("admin@gmail.com") && password.equals("1234567"))
        {
            Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(adminlogin.this,admindeshboard.class);
            startActivity(intent);
        }
    }
}