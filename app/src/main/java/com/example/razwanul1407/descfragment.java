package com.example.razwanul1407;

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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class descfragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;
    String name,productid,price,purl;
    private Button addtocartbutton,buynowbtn;
    private TextView quantitytext;
    FirebaseDatabase rootnod;
    DatabaseReference referenc;


    private FirebaseUser UProfile;
    private DatabaseReference Ureference;
    private String UUserID;
    public descfragment() {

    }
    public descfragment(String name,String productid,String price,String purl) {
      this.name=name;
      this.productid=productid;
      this.price=price;
      this.purl=purl;
    }

    public static descfragment newInstance(String param1, String param2) {
        descfragment fragment = new descfragment();
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

        View view= inflater.inflate(R.layout.fragment_descfragment, container, false);
        ImageView imageholder = view.findViewById(R.id.imageholder);
        final TextView nameholder = view.findViewById(R.id.nameholder);
        final TextView productidholder = view.findViewById(R.id.productidholder);
        TextView priceholder = view.findViewById(R.id.priceholder);
        addtocartbutton=(Button)view.findViewById(R.id.addtocartbtn);
        buynowbtn=(Button)view.findViewById(R.id.Buynowbtn);

        nameholder.setText(name);
        productidholder.setText(productid);
        priceholder.setText(price);
        Glide.with(getContext()).load(purl).into(imageholder);


        UProfile = FirebaseAuth.getInstance().getCurrentUser();
        Ureference = FirebaseDatabase.getInstance().getReference("Users");
        UUserID = UProfile.getUid();
        addtocartbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rootnod = FirebaseDatabase.getInstance();
                referenc = rootnod.getReference("addtocart/"+UUserID);


                addUserHelperClass helperClass = new addUserHelperClass(name,productid,price,purl);
                referenc.child(productid).setValue(helperClass);
                Toast.makeText(getActivity(),"Product is added to your cart",Toast.LENGTH_SHORT).show();
            }
        });

        buynowbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),orderdetails.class);
                intent.putExtra("productid",productid);
                intent.putExtra("price",price);
                startActivity(intent);
            }
        });


        return view;
    }
    public void onBackPressed()
    {
        AppCompatActivity activity = (AppCompatActivity)getContext();
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.wrapper,new recfragment()).addToBackStack(null).commit();
    }

}
class addUserHelperClass {
    String name,productid,price,purl;

    public addUserHelperClass()
    {

    }

    public addUserHelperClass(String name, String productid, String price, String purl) {
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
