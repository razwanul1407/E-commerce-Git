package com.example.razwanul1407.sports_admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.razwanul1407.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class addsportsdata extends AppCompatActivity {

    EditText name,productid,price,purl;
    Button submit,back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addsportsdata);


        name=(EditText)findViewById(R.id.addsports_nameid);
        productid=(EditText)findViewById(R.id.addsports_productid);
        price=(EditText)findViewById(R.id.addsports_priceid);
        purl=(EditText)findViewById(R.id.addsports_purlId);

        back=(Button)findViewById(R.id.addsports_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),adminsports.class));
                finish();
            }
        });
        
        submit=(Button)findViewById(R.id.addsports_submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processInsert();
            }
        });

    }

    private void processInsert() {

        Map<String,Object> map=new HashMap<>();
        map.put("name",name.getText().toString());
        map.put("productid",productid.getText().toString());
        map.put("price",price.getText().toString());
        map.put("purl",purl.getText().toString());

        FirebaseDatabase.getInstance().getReference().child("Product_sports").push()
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
