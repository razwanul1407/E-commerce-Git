package com.example.razwanul1407.watch_admin;

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

public class addwatchdata extends AppCompatActivity {

    EditText name,productid,price,purl;
    Button submit,back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addwatchdata);

        name=(EditText)findViewById(R.id.addwatch_nameid);
        productid=(EditText)findViewById(R.id.addwatch_productid);
        price=(EditText)findViewById(R.id.addwatch_priceid);
        purl=(EditText)findViewById(R.id.addwatch_purlId);

        back=(Button)findViewById(R.id.addwatch_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), adminwatch.class));
                finish();
            }
        });

        submit=(Button)findViewById(R.id.addwatch_submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processinsert();
            }
        });
    }

    private void processinsert()
    {
        Map<String,Object> map=new HashMap<>();
        map.put("name",name.getText().toString());
        map.put("productid",productid.getText().toString());
        map.put("price",price.getText().toString());
        map.put("purl",purl.getText().toString());

        FirebaseDatabase.getInstance().getReference().child("Product_watch").push()
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