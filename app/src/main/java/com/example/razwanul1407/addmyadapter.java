package com.example.razwanul1407;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class addmyadapter extends FirebaseRecyclerAdapter<addmodel,addmyadapter.myviewholder> {

    public addmyadapter(@NonNull FirebaseRecyclerOptions<addmodel> options) {

        super(options);
    }
    @Override
    protected void onBindViewHolder(@NonNull final myviewholder holder, @SuppressLint("RecyclerView") final int position, @NonNull addmodel model) {


             holder.name.setText(model.getName());
             holder.productid.setText(model.getProductid());
             holder.price.setText(model.getPrice());
             Glide.with(holder.img.getContext()).load(model.getPurl()).into(holder.img);

             FirebaseUser UProfile;

             final String UUserID;
                UProfile = FirebaseAuth.getInstance().getCurrentUser();

                UUserID = UProfile.getUid();

             holder.deleteitemicon.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     AlertDialog.Builder builder=new AlertDialog.Builder(holder.img.getContext());
                     builder.setTitle("Delete Panel");
                     builder.setMessage("Do you want to discart?");

                     builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                         @Override
                         public void onClick(DialogInterface dialog, int which) {
                             FirebaseDatabase.getInstance().getReference().child("addtocart/"+UUserID)
                                     .child(getRef(position).getKey()).removeValue();
                         }
                     });

                     builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                         @Override
                         public void onClick(DialogInterface dialog, int which) {

                         }
                     });
                    builder.show();
                 }
             });

    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.addsinglerow,parent,false);
        return  new myviewholder(view);
    }

    class myviewholder extends RecyclerView.ViewHolder
    {

        ImageView img;
        TextView name,price,productid;
        ImageView deleteitemicon;

        public myviewholder(@NonNull View itemView) {

            super(itemView);

            img=(ImageView)itemView.findViewById(R.id.img11);
            name=(TextView)itemView.findViewById(R.id.nametext11);
            productid=(TextView)itemView.findViewById(R.id.coursetext11);
            price=(TextView)itemView.findViewById(R.id.emailtext11);
            deleteitemicon=(ImageView)itemView.findViewById(R.id.delete_icon);
        }
    }
}
