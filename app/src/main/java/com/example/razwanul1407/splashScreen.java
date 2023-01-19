package com.example.razwanul1407;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class splashScreen extends AppCompatActivity {
    int SPLASH_TIMER = 5000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        View backgroundImage = findViewById(R.id.imageView);
        Animation sideAnim = AnimationUtils.loadAnimation(this, R.anim.slide_animation);

        //setAnimation on elements
        backgroundImage.setAnimation(sideAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_TIMER);
    }

}

/*
int SPLASH_TIMER = 5000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_sreen);

        View backgroundImage = findViewById(R.id.imageView);

        //Animation
        Animation sideAnim = AnimationUtils.loadAnimation(this, R.anim.slide_anim);

        //setAnimation on elements
        backgroundImage.setAnimation(sideAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_TIMER);
    }
 */