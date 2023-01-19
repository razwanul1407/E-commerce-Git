package com.example.razwanul1407.shoe_admin;

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

public class adminshoe extends AppCompatActivity {

    RecyclerView admin_shoe_recview;
    FloatingActionButton fb2;
    admin_shoe_adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminshoe);

        admin_shoe_recview = (RecyclerView)findViewById(R.id.admin_shoe_recview);
        admin_shoe_recview.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<admin_shoe_model> options =
                new FirebaseRecyclerOptions.Builder<admin_shoe_model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Product_shoe"), admin_shoe_model.class)
                        .build();

        adapter=new admin_shoe_adapter(options);
        admin_shoe_recview.setAdapter(adapter);

        fb2=(FloatingActionButton)findViewById(R.id.shoefloatingActionButton);
        fb2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), addshoedata.class));
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






class admin_shoe_model{
    String name,productid,price,purl;

    public admin_shoe_model()
    {

    }
    public admin_shoe_model(String name, String productid, String price, String purl) {
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