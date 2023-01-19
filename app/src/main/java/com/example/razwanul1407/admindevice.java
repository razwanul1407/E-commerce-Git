package com.example.razwanul1407;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;

public class admindevice extends AppCompatActivity {

    RecyclerView admin_device_recview;
    admin_device_adapter adapter;
    FloatingActionButton fb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admindevice);

        //String databname=getIntent().getStringExtra("database");

        admin_device_recview=(RecyclerView)findViewById(R.id.admin_device_recview);
        admin_device_recview.setLayoutManager(new LinearLayoutManager(this));


        FirebaseRecyclerOptions<admin_device_model> options =
                new FirebaseRecyclerOptions.Builder<admin_device_model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("students"), admin_device_model.class)
                        .build();


        adapter = new admin_device_adapter(options);
        admin_device_recview.setAdapter (adapter);

        fb=(FloatingActionButton)findViewById(R.id.floatingActionButton);
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                   startActivity(new Intent(getApplicationContext(),adddevicedata.class));
            }
        });
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