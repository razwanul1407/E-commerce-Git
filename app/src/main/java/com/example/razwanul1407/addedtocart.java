package com.example.razwanul1407;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class addedtocart extends AppCompatActivity {

    RecyclerView addrecview;
    addmyadapter adapter;
    private FirebaseUser UProfile;
    private DatabaseReference Ureference;
    private String UUserID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addedtocart);

        addrecview=(RecyclerView)findViewById(R.id.addtorecview);
        addrecview.setLayoutManager(new LinearLayoutManager(this));
        UProfile = FirebaseAuth.getInstance().getCurrentUser();
        Ureference = FirebaseDatabase.getInstance().getReference("Users");
        UUserID = UProfile.getUid();
        FirebaseRecyclerOptions<addmodel> options =
                new FirebaseRecyclerOptions.Builder<addmodel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("addtocart/"+UUserID), addmodel.class)
                        .build();


        adapter=new addmyadapter(options);
        addrecview.setAdapter(adapter);

    }
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}