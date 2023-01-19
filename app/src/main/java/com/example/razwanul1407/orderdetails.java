package com.example.razwanul1407;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class orderdetails extends AppCompatActivity {

    TextView productidtextview,pricetextview,quantitytextid;
    Button plusbtn,minusbtn,orderbutton;
    EditText ordernameedittext,orderaddressedittext,ordercityedittext,orderphonenumberedittext;
    private FirebaseUser UProfile;
    private DatabaseReference Ureference;
    private String UUserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderdetails);
        productidtextview=(TextView)findViewById(R.id.orderproductidedittextid);
        pricetextview=(TextView)findViewById(R.id.ordertotallamounttextid);

        ordernameedittext=(EditText)findViewById(R.id.ordernameedittextid);
        orderaddressedittext=(EditText)findViewById(R.id.orderaddressedittextid);
        ordercityedittext=(EditText)findViewById(R.id.ordercityedittextid);
        orderphonenumberedittext=(EditText)findViewById(R.id.orderphonenumberedittextid);
        quantitytextid=(TextView)findViewById(R.id.orderquantitytextid);

        plusbtn=(Button)findViewById(R.id.orderplusbtn);
        minusbtn=(Button)findViewById(R.id.orderminusbtn);
        orderbutton=(Button)findViewById(R.id.orderbuttonid);


        String proid=getIntent().getStringExtra("productid");
        final String priid=getIntent().getStringExtra("price");
        final int priceint= new Integer(priid.replaceAll("[\\D]" , ""));


        productidtextview.setText(proid);
        pricetextview.setText(Integer.toString(priceint)+" tk");


        plusbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String val=quantitytextid.getText().toString();
                int value=new Integer(val);
                value+=1;
                val=Integer.toString(value);
                String amount=pricetextview.getText().toString();
                int amount1=new Integer(amount.replaceAll("[\\D]",""));
                pricetextview.setText(Integer.toString(amount1+priceint)+" tk");
                quantitytextid.setText(val);

            }
        });

        minusbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String val=quantitytextid.getText().toString();
                String amount=pricetextview.getText().toString();
                int amount1=new Integer(amount.replaceAll("[\\D]",""));

                int value=new Integer(val);
                if(value>1) {
                    value -= 1;
                    val = Integer.toString(value);
                    quantitytextid.setText(val);
                    pricetextview.setText(Integer.toString(amount1-priceint)+" tk");
                }
            }
        });

        orderbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderdocuments();
            }
        });





    }

    private void orderdocuments() {

        UProfile = FirebaseAuth.getInstance().getCurrentUser();
        Ureference = FirebaseDatabase.getInstance().getReference("Users");
        UUserID = UProfile.getUid();

        Map<String,Object> map=new HashMap<>();
        map.put("Buyer_name",ordernameedittext.getText().toString());
        map.put("Address",orderaddressedittext.getText().toString());
        map.put("City",ordercityedittext.getText().toString());
        map.put("phoneNumber",orderphonenumberedittext.getText().toString());
        map.put("Product_id",productidtextview.getText().toString());
        final String pid=productidtextview.getText().toString();
        map.put("totallprice",pricetextview.getText().toString());
        map.put("Quantity",quantitytextid.getText().toString());

        FirebaseDatabase.getInstance().getReference().child("Deliverable_Customer/"+UUserID+"/"+pid)
                .setValue(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        ordernameedittext.setText("");
                        orderaddressedittext.setText("");
                        ordercityedittext.setText("");
                        orderphonenumberedittext.setText("");
                        productidtextview.setText("");
                        pricetextview.setText("");
                        quantitytextid.setText("");

                        Toast.makeText(getApplicationContext(),"Payment information is added",Toast.LENGTH_LONG).show();
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