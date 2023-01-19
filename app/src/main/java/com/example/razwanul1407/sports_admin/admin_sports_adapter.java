package com.example.razwanul1407.sports_admin;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.razwanul1407.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;

public class admin_sports_adapter extends FirebaseRecyclerAdapter<admin_sports_model,admin_sports_adapter.myviewholder>
{
    public admin_sports_adapter(@NonNull FirebaseRecyclerOptions<admin_sports_model> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final myviewholder holder, final int position, @NonNull final admin_sports_model model)
    {
        holder.name.setText(model.getName());
        holder.productid.setText(model.getProductid());
        holder.price.setText(model.getPrice());
        Glide.with(holder.img.getContext()).load(model.getPurl()).into(holder.img);

                holder.edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       final DialogPlus dialogPlus= DialogPlus.newDialog(holder.img.getContext())
                               .setContentHolder(new ViewHolder(R.layout.sports_dialogcontent))
                               .setExpanded(true,1500)
                               .create();

                       View myview=dialogPlus.getHolderView();
                        final EditText purl=myview.findViewById(R.id.sports_purl);
                        final EditText name=myview.findViewById(R.id.sports_name);
                        final EditText productid=myview.findViewById(R.id.sports_productid);
                        final EditText price=myview.findViewById(R.id.sports_price);
                        Button submit=myview.findViewById(R.id.sports_sumbit);


                        purl.setText(model.getPurl());
                        name.setText(model.getName());
                        productid.setText(model.getProductid());
                        price.setText(model.getPrice());


                        dialogPlus.show();

                        submit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Map<String,Object> map=new HashMap<>();
                                map.put("purl",purl.getText().toString());
                                map.put("name",name.getText().toString());
                                map.put("price",price.getText().toString());
                                map.put("productid",productid.getText().toString());

                                FirebaseDatabase.getInstance().getReference().child("Product_sports")
                                        .child(getRef(position).getKey()).updateChildren(map)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                dialogPlus.dismiss();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                dialogPlus.dismiss();
                                            }
                                        });
                            }
                        });
                    }
                });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(holder.img.getContext());
                builder.setTitle("Delete Panel");
                builder.setMessage("Delete Product.. ?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseDatabase.getInstance().getReference().child("Product_sports")
                                .child(getRef(position).getKey()).removeValue();
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
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

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_sports_singlerow,parent,false);
        return new myviewholder(view);
    }

    class myviewholder extends RecyclerView.ViewHolder
    {
        ImageView img;
        TextView name,productid,price;
        ImageView edit,delete;
        public myviewholder(@NonNull View itemView) {
            super(itemView);
            img=(ImageView)itemView.findViewById(R.id.adm_img11sports);
            name=(TextView)itemView.findViewById(R.id.adm_nametext11sports);
            productid=(TextView)itemView.findViewById(R.id.adm_coursetext11sports);
            price=(TextView)itemView.findViewById(R.id.adm_emailtext11sports);

            edit=(ImageView)itemView.findViewById(R.id.edit_icon);
            delete=(ImageView)itemView.findViewById(R.id.delete_icon);
        }
    }

}
