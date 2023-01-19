package com.example.razwanul1407;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class adddevicedata extends AppCompatActivity {

    EditText name,productid,price,purl;
    Button submit,back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adddevicedata);


        name=(EditText)findViewById(R.id.addevice_nameid);
        productid=(EditText)findViewById(R.id.adddevice_productid);
        price=(EditText)findViewById(R.id.adddevice_priceid);
        purl=(EditText)findViewById(R.id.adddevice_purlId);


        back = (Button)findViewById(R.id.adddevice_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),admindevice.class));
                finish();
            }
        });


        submit=(Button)findViewById(R.id.adddevice_submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                processinsert();
            }
        });

    }

    private void processinsert() {

        final String names=name.getText().toString().trim();
        final String productids= productid.getText().toString().trim();
        final String prices = price.getText().toString().trim();
        final String purls = purl.getText().toString().trim();

        if(names.isEmpty())

        {
            name.setError("Enter Product name");
            name.requestFocus();
            return;
        }
        if(productids.isEmpty())
        {
            productid.setError("Enter Product Id");
            productid.requestFocus();
            return;
        }
        if(prices.isEmpty())
        {
            price.setError("Enter Product price");
            price.requestFocus();
            return;
        }
        if(purls.isEmpty())
        {
            purl.setError("Enter Product url");
            purl.requestFocus();
            return;
        }
        Map<String,Object> map=new HashMap<>();
        map.put("name",name.getText().toString());
        map.put("productid",productid.getText().toString());
        map.put("price",price.getText().toString());
        map.put("purl",purl.getText().toString());

        FirebaseDatabase.getInstance().getReference().child("students").push()
                .setValue(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        name.setText("");
                        productid.setText("");
                        price.setText("");
                        purl.setText("");

                        Toast.makeText(getApplicationContext(),"Product Inserted successfully",Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),"Could not insert",Toast.LENGTH_LONG).show();
                    }
                });
    }
}