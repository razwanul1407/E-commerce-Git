package com.example.razwanul1407.camera;

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


public class camera_descfragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;
    String name,productid,price,purl;
    private Button addtocartbutton1,buynowbtn1;
    FirebaseDatabase rootnod;
    DatabaseReference referenc;


    private FirebaseUser UProfile;
    private DatabaseReference Ureference;
    private String UUserID;
    public camera_descfragment() {

    }
    public camera_descfragment(String name,String productid,String price,String purl) {
          this.name=name;
          this.productid=productid;
          this.price=price;
          this.purl=purl;
    }

       public static camera_descfragment newInstance(String param1, String param2) {
        camera_descfragment fragment = new camera_descfragment();
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

        View view= inflater.inflate(R.layout.fragment_camera_descfragment, container, false);

        ImageView imageholdercamera=view.findViewById(R.id.imageholdercamera);
        TextView nameholdercamera=view.findViewById(R.id.nameholdercamera);
        TextView productidholdercamera=view.findViewById(R.id.productidholdercamera);
        TextView priceholdercamera=view.findViewById(R.id.priceholdercamera);

        addtocartbutton1=(Button)view.findViewById(R.id.addtocartbtncamera);
        buynowbtn1=(Button)view.findViewById(R.id.Buynowbtncamera);


        nameholdercamera.setText(name);
        productidholdercamera.setText(productid);
        priceholdercamera.setText(price);
        Glide.with(getContext()).load(purl).into(imageholdercamera);


        UProfile = FirebaseAuth.getInstance().getCurrentUser();
        Ureference = FirebaseDatabase.getInstance().getReference("Users");
        UUserID = UProfile.getUid();

        addtocartbutton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rootnod = FirebaseDatabase.getInstance();
                referenc = rootnod.getReference("addtocart/"+UUserID);

                addUserHelperClass1 helperClass1 =new addUserHelperClass1(name,productid,price,purl);
                referenc.child(productid).setValue(helperClass1);
                Toast.makeText(getActivity(),"Product is added to your cart",Toast.LENGTH_SHORT).show();

            }
        });

        buynowbtn1.setOnClickListener(new View.OnClickListener() {
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
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.camerawrapper,new camera_recfragment()).addToBackStack(null).commit();

    }
}

class addUserHelperClass1
{
    String name,productid,price,purl;

    public addUserHelperClass1() {
    }

    public addUserHelperClass1(String name, String productid, String price, String purl) {
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