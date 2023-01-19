package com.example.razwanul1407;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class myadapter extends FirebaseRecyclerAdapter<model,myadapter.myviewholder> {

    public myadapter(@NonNull FirebaseRecyclerOptions<model> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull final model model) {
        holder.nametext.setText(model.getName());
        holder.productidtext.setText(model.getProductid());
        holder.pricetext.setText(model.getPrice());
        Glide.with(holder.img1.getContext()).load(model.getPurl()).into(holder.img1);

        holder.img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity)v.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.wrapper,new descfragment(model.getName(),model.getProductid(),model.getPrice(),model.getPurl())).addToBackStack(null).commit();
            }
        });
    }

     @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerowdesign,parent,false);

        return new myviewholder(view);
    }

    public class myviewholder extends RecyclerView.ViewHolder
    {
        ImageView img1;
        TextView pricetext,productidtext;
        TextView nametext;
        public myviewholder(@NonNull View itemView) {
            super(itemView);
            img1= itemView.findViewById(R.id.img1);
            nametext= itemView.findViewById(R.id.nametext);
            productidtext= itemView.findViewById(R.id.productidtext);
            pricetext = itemView.findViewById(R.id.pricetext);
        }
    }
}
