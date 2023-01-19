package com.example.razwanul1407.sports;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.razwanul1407.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class sports_adapter extends FirebaseRecyclerAdapter<sports_model,sports_adapter.myviewholder> {


    public sports_adapter(@NonNull FirebaseRecyclerOptions<sports_model> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull final sports_model model) {
         holder.name.setText(model.getName());
         holder.productid.setText(model.getProductid());
         holder.price.setText(model.getPrice());
         Glide.with(holder.img.getContext()).load(model.getPurl()).into(holder.img);

         holder.img.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 AppCompatActivity activity=(AppCompatActivity)v.getContext();
                 activity.getSupportFragmentManager().beginTransaction().replace(R.id.sportswrapper,new sports_descfragment(model.name,model.getProductid(),model.getPrice(),model.getPurl())).addToBackStack(null).commit();
             }
         });

    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.sports_singlerow,parent,false);
        return new myviewholder(view);
    }

    public class myviewholder extends RecyclerView.ViewHolder
    {
        ImageView img;
        TextView name,productid,price;
        public myviewholder(@NonNull View itemView) {
            super(itemView);

            img=itemView.findViewById(R.id.img1sports);
            name=itemView.findViewById(R.id.nametextsports);
            productid=itemView.findViewById(R.id.productidtextsports);
            price=itemView.findViewById(R.id.pricetextsports);

        }
    }
}
