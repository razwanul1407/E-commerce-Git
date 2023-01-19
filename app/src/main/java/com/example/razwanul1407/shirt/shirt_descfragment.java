package com.example.razwanul1407.shirt;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.razwanul1407.R;
import com.example.razwanul1407.orderdetails;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class shirt_descfragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;
    String name,productid, price, purl;
    private Button addtocartbutton2,buynowbtn2;

    FirebaseDatabase rootnod;
    DatabaseReference referenc;


    private FirebaseUser UProfile;
    private DatabaseReference Ureference;
    private String UUserID;
    public shirt_descfragment() {

    }

    public shirt_descfragment(String name,String productid,String price,String purl) {
       this.name=name;
       this.productid=productid;
       this.price=price;
       this.purl=purl;
    }


    public static shirt_descfragment newInstance(String param1, String param2) {
        shirt_descfragment fragment = new shirt_descfragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_shirt_descfragment, container, false);

        ImageView imageholdershirt=view.findViewById(R.id.imageholdershirt);
        TextView nameholdershirt=view.findViewById(R.id.nameholdershirt);
        TextView productidholdershirt=view.findViewById(R.id.productidholdershirt);
        TextView priceholdershirt=view.findViewById(R.id.priceholdershirt);

        addtocartbutton2=(Button)view.findViewById(R.id.addtocartbtnshirt);
        buynowbtn2=(Button)view.findViewById(R.id.Buynowbtnshirt);

        nameholdershirt.setText(name);
        productidholdershirt.setText(productid);
        priceholdershirt.setText(price);

        Glide.with(getContext()).load(purl).into(imageholdershirt);
        UProfile = FirebaseAuth.getInstance().getCurrentUser();
        Ureference = FirebaseDatabase.getInstance().getReference("Users");
        UUserID = UProfile.getUid();
        addtocartbutton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rootnod = FirebaseDatabase.getInstance();
                referenc = rootnod.getReference("addtocart/"+UUserID);

               addUserHelperClass2 helperClass2=new addUserHelperClass2(name,productid,price,purl);
                referenc.child(productid).setValue(helperClass2);
                Toast.makeText(getActivity(),"Product is added to your cart",Toast.LENGTH_SHORT).show();
            }
        });

        buynowbtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), orderdetails.class);
                intent.putExtra("productid",productid);
                intent.putExtra("price",price);
                startActivity(intent);
            }
        });

        return view;
    }

    public void onBackPressed()
    {
        AppCompatActivity activity=(AppCompatActivity)getContext();
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.shirtwrapper,new shirt_recfragment()).addToBackStack(null).commit();
    }
}

class addUserHelperClass2
{
    String name,productid,price,purl;

    public addUserHelperClass2() {
    }

    public addUserHelperClass2(String name, String productid, String price, String purl) {
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