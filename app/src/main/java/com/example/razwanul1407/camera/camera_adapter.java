package com.example.razwanul1407.camera;

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

public class camera_adapter extends FirebaseRecyclerAdapter<camera_model,camera_adapter.myviewholder> {

    public camera_adapter(@NonNull FirebaseRecyclerOptions<camera_model> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull final camera_model model) {
        holder.name.setText(model.getName());
        holder.productid.setText(model.getProductid());
        holder.price.setText(model.getPrice());
        Glide.with(holder.img.getContext()).load(model.getPurl()).into(holder.img);

        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity=(AppCompatActivity)v.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.camerawrapper,new camera_descfragment(model.getName(),model.getProductid(),model.getPrice(),model.getPurl())).addToBackStack(null).commit();

            }
        });
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.camera_singlerow,parent,false);
        return new myviewholder(view);
    }

    public class myviewholder extends RecyclerView.ViewHolder
    {
        ImageView img;
        TextView name,productid,price;
        public myviewholder(@NonNull View itemView) {
            super(itemView);

            img=itemView.findViewById(R.id.img1camera);
            name=itemView.findViewById(R.id.nametextcamera);
            productid=itemView.findViewById(R.id.productidtextcamera);
            price=itemView.findViewById(R.id.pricetextcamera);
        }
    }
}
