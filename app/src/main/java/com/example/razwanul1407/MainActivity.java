package com.example.razwanul1407;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener {
    private EditText signInEmailEditText, signInPasswordEditText;
    private TextView signUpTextView;
    private TextView adminlogintextView;
    private Button signInButton;
    ProgressBar progressBar;
    private FirebaseAuth mAuth;

    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        signInEmailEditText = (EditText) findViewById(R.id.signInEmailEditTextId);
        signInPasswordEditText = (EditText) findViewById(R.id.signInPasswordEditTextId);
        signInButton = (Button) findViewById(R.id.signInButtonId);
        signUpTextView = (TextView) findViewById(R.id.signUpTextViewId);
        adminlogintextView=(TextView)findViewById(R.id.adminlogintextViewid);
        progressBar = findViewById(R.id.progressbarId);
        signUpTextView.setOnClickListener(this);
        signInButton.setOnClickListener(this);

        adminlogintextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in = new Intent(getApplicationContext(),adminlogin.class);
                startActivity(in);
            }
        });
    }
    private static final int TIME_INTERVAL = 2000;
    private long backPressed;

    @Override
    public void onBackPressed() {
        if(backPressed + TIME_INTERVAL > System.currentTimeMillis()){
            super.onBackPressed();
            moveTaskToBack(true);
            return;
        }else {
            Toast.makeText(getBaseContext(), "Please click BACK again to exit!", Toast.LENGTH_SHORT).show();
        }
        backPressed = System.currentTimeMillis();
    }

    @Override
    public void onClick(View v) {
      switch (v.getId())
      {
          case R.id.signInButtonId:
              userLogin();
              break;
          case R.id.signUpTextViewId:
              Intent intent = new Intent(getApplicationContext(),SignUpActivity.class);
              startActivity(intent);
              break;
      }

    }

    private void userLogin() {
        String email = signInEmailEditText.getText().toString().trim();
        String password = signInPasswordEditText.getText().toString().trim();

        if(email.isEmpty())
        {
            signInEmailEditText.setError("Enter an email address");
            signInEmailEditText.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            signInEmailEditText.setError("Enter a valid email address");
            signInEmailEditText.requestFocus();
            return;
        }


        if(password.isEmpty())
        {
            signInPasswordEditText.setError("Enter a password");
            signInPasswordEditText.requestFocus();
            return;
        }
        if(password.length()<6)
        {
            signInPasswordEditText.setError("Minimum lenth of a password should be 6");
            signInPasswordEditText.requestFocus();
            return;
        }
       progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
               if(task.isSuccessful())
               {
                  Intent intent= new Intent(getApplicationContext(),Deshboard.class);
                  intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                  startActivity(intent);
               }
               else
               {
                   Toast.makeText(getApplicationContext(), "Login Unsuccessful", Toast.LENGTH_SHORT).show();
               }
            }
        });
    }
}