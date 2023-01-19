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
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity  implements View.OnClickListener {
    private EditText signUpEmailEditText, signUpPasswordEditText,signupNameEditText,signupPhoneEditText,signupConfirmpasswordEditText;
    private TextView signInTextView;
    private Button signUpButton;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        mAuth = FirebaseAuth.getInstance();
        progressBar =  findViewById(R.id.progressbarId);
        signUpEmailEditText = (EditText) findViewById(R.id.signUpEmailEditTextId);
        signUpPasswordEditText = (EditText) findViewById(R.id.signUpPasswordEditTextId);
        signupNameEditText=(EditText)findViewById(R.id.signUpFullnameTextId);
        signupPhoneEditText=(EditText)findViewById(R.id.signupPhoneNumberEditTextId);
        signupConfirmpasswordEditText=(EditText)findViewById(R.id.signUpConfirmPasswordEditTextId);
        signUpButton = (Button) findViewById(R.id.signUpButtonId);
        signInTextView = (TextView) findViewById(R.id.signInTextViewId);

        signInTextView.setOnClickListener(this);
        signUpButton.setOnClickListener(this);
       

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.signUpButtonId:
                userregister();
                break;
            case R.id.signInTextViewId:
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                break;
        }

    }

    private void userregister() {
        final String name=signupNameEditText.getText().toString().trim();
        final String phone=signupPhoneEditText.getText().toString().trim();
        final String email = signUpEmailEditText.getText().toString().trim();
        final String password = signUpPasswordEditText.getText().toString().trim();
        final String confim_password=signupConfirmpasswordEditText.getText().toString().trim();
        if(name.isEmpty())
        {
            signupNameEditText.setError("Enter your name");
            signupNameEditText.requestFocus();
            return;
        }

        if(email.isEmpty())
        {
            signUpEmailEditText.setError("Enter an email address");
            signUpEmailEditText.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            signUpEmailEditText.setError("Enter a valid email address");
            signUpEmailEditText.requestFocus();
            return;
        }


        if(password.isEmpty())
        {
            signUpPasswordEditText.setError("Enter a password");
            signUpPasswordEditText.requestFocus();
            return;
        }

        if(password.length()<6)
        {
            signUpPasswordEditText.setError("Minimum lenth of a password should be 6");
            signUpPasswordEditText.requestFocus();
            return;
        }
        if(!confim_password.equals(password))
        {
            signupConfirmpasswordEditText.setError("Enter a confirm_password");
            signupNameEditText.requestFocus();
            return;
        }
        if(phone.isEmpty())
        {
            signupPhoneEditText.setError("Enter your phone number");
            signupPhoneEditText.requestFocus();
            return;
        }
         progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                 progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()) {


                    User user = new User (confim_password,email,name, password,phone);

                    FirebaseDatabase.getInstance().getReference("Users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override

                        public void onComplete(@NonNull Task<Void> task) {
                            // progressBar.setVisibility(View.GONE);
                            if(task.isSuccessful()){
                                Toast.makeText(getApplicationContext(),"Register is successful",Toast.LENGTH_SHORT).show();

                                Intent intent= new Intent(getApplicationContext(),Pictureadd.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);


                            }else{

                                if(task.getException()instanceof FirebaseAuthUserCollisionException)
                                {
                                    Toast.makeText(getApplicationContext(), "User is already Registered", Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    Toast.makeText(getApplicationContext(), "Error : "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });

                }
            }
        });



    }
}
class User {
    public String confim_password,email,name, password,phone;
    public User(){
    }

    public User(String confim_password, String email, String name, String password, String phone) {
        this.confim_password = confim_password;
        this.email = email;
        this.name = name;
        this.password = password;
        this.phone = phone;
    }

    public String getConfim_password() {
        return confim_password;
    }

    public void setConfim_password(String confim_password) {
        this.confim_password = confim_password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
/*
package com.example.ilham;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class SignUpActivity extends AppCompatActivity  implements View.OnClickListener {
    private EditText signUpEmailEditText, signUpPasswordEditText;
    private TextView signInTextView;
    private Button signUpButton;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        this.setTitle("Sign In Activity");

        mAuth = FirebaseAuth.getInstance();
        progressBar =  findViewById(R.id.progressbarId);
        signUpEmailEditText = (EditText) findViewById(R.id.signUpEmailEditTextId);
        signUpPasswordEditText = (EditText) findViewById(R.id.signUpPasswordEditTextId);
        signUpButton = (Button) findViewById(R.id.signUpButtonId);
        signInTextView = (TextView) findViewById(R.id.signInTextViewId);

        signInTextView.setOnClickListener(this);
        signUpButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.signUpButtonId:
                userregister();
                break;
            case R.id.signInTextViewId:
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                break;
        }

    }

    private void userregister() {
        String email = signUpEmailEditText.getText().toString().trim();
        String password = signUpPasswordEditText.getText().toString().trim();

        if(email.isEmpty())
        {
            signUpEmailEditText.setError("Enter an email address");
            signUpEmailEditText.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            signUpEmailEditText.setError("Enter a valid email address");
            signUpEmailEditText.requestFocus();
            return;
        }


        if(password.isEmpty())
        {
            signUpPasswordEditText.setError("Enter a password");
            signUpPasswordEditText.requestFocus();
            return;
        }
        if(password.length()<6)
        {
            signUpPasswordEditText.setError("Minimum lenth of a password should be 6");
            signUpPasswordEditText.requestFocus();
            return;
        }
         progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()) {

                    Toast.makeText(getApplicationContext(),"Register is successful",Toast.LENGTH_SHORT).show();
                    Intent intent= new Intent(getApplicationContext(),Profile.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } else {
                      if(task.getException()instanceof FirebaseAuthUserCollisionException)
                      {
                          Toast.makeText(getApplicationContext(), "User is already Registered", Toast.LENGTH_SHORT).show();
                      }
                      else
                      {
                          Toast.makeText(getApplicationContext(), "Error : "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                      }
                }
            }
        });



    }
}
 */