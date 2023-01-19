package com.example.razwanul1407.sports_admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.razwanul1407.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;

public class adminsports extends AppCompatActivity {

    RecyclerView admin_sports_recview;
    admin_sports_adapter adapter;
    FloatingActionButton fb4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminsports);

        admin_sports_recview=(RecyclerView)findViewById(R.id.admin_sports_recview);
        admin_sports_recview.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<admin_sports_model> options =
                new FirebaseRecyclerOptions.Builder<admin_sports_model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Product_sports"), admin_sports_model.class)
                        .build();

        adapter= new admin_sports_adapter(options);
        admin_sports_recview.setAdapter(adapter);

        fb4=(FloatingActionButton)findViewById(R.id.sportsfloatingActionButton);
        fb4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), addsportsdata.class));
            }
        });
    }
    @Override
    protected void onStart(){
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop(){
        super.onStop();
        adapter.stopListening();
    }
}

class admin_sports_model{
    String name,productid,price,purl;

    public admin_sports_model()
    {

    }
    public admin_sports_model(String name, String productid, String price, String purl) {
        this.name = name;
        this.productid = productid;
        this.price = price;
        this.purl = purl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPurl() {
        return purl;
    }

    public void setPurl(String purl) {
        this.purl = purl;
    }
}