package com.example.razwanul1407;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Toast;

import com.example.razwanul1407.camera.cameraActivity;
import com.example.razwanul1407.shirt.shirtActivity;
import com.example.razwanul1407.shoe.shoeActivity;
import com.example.razwanul1407.sports.sportsActivity;
import com.example.razwanul1407.watch.watchActivity;

public class Deshboard extends AppCompatActivity {

    GridLayout mainGridLayout;
    private Button add_tocart;
    private Button profile_btn;
    private Button logout_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deshboard);
        mainGridLayout =(GridLayout) findViewById(R.id.mainGridLayout);
        setSingleEvent(mainGridLayout);
        add_tocart=(Button)findViewById(R.id.addcart);
        profile_btn=(Button)findViewById(R.id.profileid);
        logout_btn=(Button)findViewById(R.id.logout);

      add_tocart.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Toast.makeText(Deshboard.this, "Hello", Toast.LENGTH_SHORT).show();
              Intent intent= new Intent(Deshboard.this,addedtocart.class);
              startActivity(intent);
          }
      });
      profile_btn.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Toast.makeText(Deshboard.this, "Hello", Toast.LENGTH_SHORT).show();
              Intent intent= new Intent(Deshboard.this,userprofile.class);
              startActivity(intent);
          }
      });
      logout_btn.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              AlertDialog.Builder builder=new AlertDialog.Builder(logout_btn.getContext());
              builder.setTitle("logout Panel");
              builder.setMessage("Do you want to logout..?");
              builder.setCancelable(false);
              builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                  @Override
                  public void onClick(DialogInterface dialog, int which) {
                      Intent intent = new Intent(Deshboard.this,MainActivity.class);
                      startActivity(intent);
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

    private void setSingleEvent(GridLayout mainGridLayout) {

        for(int i=0;i<mainGridLayout.getChildCount();i++)
        {
            CardView cardView = (CardView)mainGridLayout.getChildAt(i);

            final int finalI = i;

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(finalI==0)
                    {
                        Intent intent= new Intent(Deshboard.this,MainActivity2.class);
                        startActivity(intent);
                    }
                    else if(finalI==1)
                    {
                        Intent intent= new Intent(Deshboard.this, shirtActivity.class);
                        startActivity(intent);
                    }
                    else if(finalI==2)
                    {
                        Intent intent= new Intent(Deshboard.this, shoeActivity.class);
                        startActivity(intent);
                    }
                    else if(finalI==3)
                    {
                        Intent intent= new Intent(Deshboard.this, cameraActivity.class);
                        startActivity(intent);
                    }
                    else if(finalI==4)
                    {
                        Intent intent= new Intent(Deshboard.this, sportsActivity.class);
                        startActivity(intent);
                    }
                    else if(finalI==5)
                    {
                        Intent intent= new Intent(Deshboard.this, watchActivity.class);
                        startActivity(intent);
                    }
                }
            });
        }
    }
}
/*
GridLayout mainGridLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deshboard);
    mainGridLayout =(GridLayout) findViewById(R.id.mainGridLayout);
    setSingleEvent(mainGridLayout);

    }

    private void setSingleEvent(GridLayout mainGridLayout) {

        for(int i=0;i<mainGridLayout.getChildCount();i++)
        {
            CardView cardView = (CardView)mainGridLayout.getChildAt(i);

            final int finalI = i;

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(finalI==0)
                    {
                        Intent intent = new Intent(MainActivity.this,StudyActivity.class);
                        startActivity(intent);
                    }
                    else if(finalI==1)
                    {
                        Intent intent = new Intent(MainActivity.this,World.class);
                        startActivity(intent);
                    }
                }
            });
        }
    }
 */