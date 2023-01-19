package com.example.razwanul1407;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class userprofile extends AppCompatActivity {

    private FirebaseUser UProfile = FirebaseAuth.getInstance().getCurrentUser();
    private DatabaseReference Ureference = FirebaseDatabase.getInstance().getReference("Users");
    private String UUserID = UProfile.getUid();
    private TextView profilename,profileemail,profilephone,profilepassword;
    private Button Edituserbutton;

    private CircleImageView img;
    private FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference=firebaseDatabase.getReference();
    private DatabaseReference first = databaseReference.child("UUU/"+UUserID+"/pimage");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userprofile);

        Thread myThread = null;
        Runnable myRunnableThread = new CountDownRunner();
        myThread = new Thread(myRunnableThread);
        myThread.start();

        img=findViewById(R.id.imageView1234);
        profilename=findViewById(R.id.profile_nametextid);
        profileemail=findViewById(R.id.profile_emailtextid);
        profilephone=findViewById(R.id.profile_phonetextid);
        profilepassword=findViewById(R.id.profile_passwordtextid);
        Edituserbutton=findViewById(R.id.Edituserbuttonid);



        Edituserbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(profilename.getContext())
                        .setContentHolder(new ViewHolder(R.layout.edituserprofile))
                        .setExpanded(true)
                        .setGravity(Gravity.CENTER)
                        .setCancelable(true)
                        .create();

                View myviwe = dialogPlus.getHolderView();
                final EditText name1 = (EditText)myviwe.findViewById(R.id.edituser_name);
                final EditText email1 = (EditText)myviwe.findViewById(R.id.edituser_email);
                final EditText phone1 = (EditText)myviwe.findViewById(R.id.edituser_phone);
                Button useredit=(Button)myviwe.findViewById(R.id.user_submit);



                Ureference.child(UUserID).addListenerForSingleValueEvent(new ValueEventListener() {

                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User_profile userProfileFor = snapshot.getValue(User_profile.class);
                        if(userProfileFor != null){

                            String pp_email=userProfileFor.email;
                            String pp_name=userProfileFor.name;
                            String pp_password = userProfileFor.password;
                            String pp_phone=userProfileFor.phone;

                            name1.setText(pp_name);
                            email1.setText(pp_email);
                            phone1.setText(pp_phone);
                            //  profilepassword.setText("Password: "+p_password);


                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(userprofile.this,"Something wrong happened!",Toast.LENGTH_SHORT).show();
                    }
                });

                dialogPlus.show();


                useredit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Map<String, Object> map = new HashMap<>();
                        map.put("name", name1.getText().toString());
                        map.put("email", email1.getText().toString());
                        map.put("phone", phone1.getText().toString());


                        FirebaseDatabase.getInstance().getReference()
                                .child("Users/"+UUserID)
                                .updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        dialogPlus.dismiss();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                dialogPlus.dismiss();
                            }
                        });

                    }
                });




            }
        });




    }

    public void doWork() {
        runOnUiThread(new Runnable() {
            public void run() {
                try {
                    UProfile = FirebaseAuth.getInstance().getCurrentUser();
                    Ureference = FirebaseDatabase.getInstance().getReference("Users");
                    UUserID = UProfile.getUid();
                    Ureference.child(UUserID).addListenerForSingleValueEvent(new ValueEventListener() {

                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            User_profile userProfileFor = snapshot.getValue(User_profile.class);
                            if(userProfileFor != null){

                                String P_email=userProfileFor.email;
                                String p_name=userProfileFor.name;
                                String p_password = userProfileFor.password;
                                String p_phone=userProfileFor.phone;

                                profilename.setText(p_name);
                                profileemail.setText(P_email);
                                profilephone.setText(p_phone);
                                //  profilepassword.setText("Password: "+p_password);


                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(userprofile.this,"Something wrong happened!",Toast.LENGTH_SHORT).show();
                        }
                    });


                } catch (Exception e) {

                }
            }
        });
    }

    class CountDownRunner implements Runnable {
        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    doWork();
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } catch (Exception e) {
                }
            }
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        first.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String link = dataSnapshot.getValue(String.class);
                Picasso.get().load(link).into(img);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}


class User_profile{
    public String email,name, password,phone;
    public User_profile(){
    }

    public User_profile(String email, String name, String password, String phone) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.phone = phone;
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

