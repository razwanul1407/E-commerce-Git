package com.example.razwanul1407;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity implements View.OnClickListener {
    private Button exitbutton;
    private Button addFirebasebtn,jobutton,deletetofirebase;
    private EditText textView1;
    private EditText textView2;
    private Button plusbutton,minusbutton;
    FirebaseDatabase rootnode,deleterootnode;
    DatabaseReference reference,deletereference;
    private TextView text;
    private FirebaseUser SProfile;
    private DatabaseReference Sreference;
    private String SUserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


         plusbutton=(Button)findViewById(R.id.button);
         minusbutton=(Button)findViewById(R.id.button2);


        exitbutton = (Button) findViewById(R.id.exitbuttonId);
        addFirebasebtn=(Button)findViewById(R.id.addtofirebasebuttonId);
        deletetofirebase=(Button)findViewById(R.id.deletetofirebasebuttonId);

        jobutton=(Button)findViewById(R.id.buttonjo);

        textView1=(EditText) findViewById(R.id.textView1Id);
        textView2=(EditText) findViewById(R.id.textView2Id);
        text=(TextView)findViewById(R.id.textView2);

        exitbutton.setOnClickListener(this);

        addFirebasebtn.setOnClickListener(this);
        deletetofirebase.setOnClickListener(this);
        jobutton.setOnClickListener(this);

        plusbutton.setOnClickListener(this);
        minusbutton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        final TextView textview3=(TextView)findViewById(R.id.textViewIddd);
        final TextView textview4=(TextView)findViewById(R.id.textViewIddd2);
        if(v.getId()==R.id.button)
        {

        }
        if(v.getId()==R.id.deletetofirebasebuttonId)
        {
            deleterootnode= FirebaseDatabase.getInstance();
            deletereference = rootnode.getReference("information");
            String phone=textView1.getText().toString();
            String address=textView2.getText().toString();
            deletereference.child(phone).removeValue();
        }
        if(v.getId()==R.id.buttonjo)
        {

            SProfile = FirebaseAuth.getInstance().getCurrentUser();
            Sreference = FirebaseDatabase.getInstance().getReference("Users");
            SUserID = SProfile.getUid();
            Sreference.child(SUserID).addListenerForSingleValueEvent(new ValueEventListener() {

                @SuppressLint("SetTextI18n")
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    UserForStudent userProfileForStudent = snapshot.getValue(UserForStudent.class);
                    if(userProfileForStudent != null){
                        String profile_email = userProfileForStudent.email;
                        String profile_password = userProfileForStudent.password;

                        textview3.setText(profile_email);
                        textview4.setText(profile_password);

                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(Profile.this,"Something wrong happened!",Toast.LENGTH_SHORT).show();
                }
            });

            Toast.makeText(Profile.this,"Hello",Toast.LENGTH_SHORT).show();

        }
        if(v.getId() ==R.id.exitbuttonId)
        {
            Intent intent= new Intent(getApplicationContext(),MainActivity2.class);
            startActivity(intent);
        }
        if(v.getId()==R.id.addtofirebasebuttonId)
        {
             rootnode = FirebaseDatabase.getInstance();
             reference = rootnode.getReference("information");
             String phone=textView1.getText().toString();
             String address=textView2.getText().toString();
             UserHelperClass helperClass = new UserHelperClass(phone,address);
             reference.child(phone).setValue(helperClass);
            //1werfert
            DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("Data");
           final int childCount[] = {0};

            database.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot snap: dataSnapshot.getChildren()) {
                        childCount[0] += snap.getChildrenCount();
                    }
                    String x=Integer.toString(childCount[0]);
                    textview3.setText(x);
                    Toast.makeText(getApplicationContext(), "Hello "+childCount[0], Toast.LENGTH_LONG).show();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }

            });

        }
    }
}
class UserForStudent {
    public String email, password;
    public UserForStudent(){
    }


    public UserForStudent(String email, String password) {
        this.email = email;
        this.password = password;
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
import com.google.firebase.database.FirebaseDatabase;

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
       final String email = signUpEmailEditText.getText().toString().trim();
       final String password = signUpPasswordEditText.getText().toString().trim();

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
        // progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
               //  progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()) {


                    User user = new User (email, password);

                    FirebaseDatabase.getInstance().getReference("Users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override

                        public void onComplete(@NonNull Task<Void> task) {
                        // progressBar.setVisibility(View.GONE);
                            if(task.isSuccessful()){
                                Toast.makeText(getApplicationContext(),"Register is successful",Toast.LENGTH_SHORT).show();

                                Intent intent= new Intent(getApplicationContext(),Profile.class);
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
class User {
    public String name,Phone, email, password;
    public User(){
    }
    public User(String email, String password){

        this.email = email;
        this.password = password;

    }
}
 */