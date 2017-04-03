package com.google.firebase.udacity.friendlychat;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            //after splah activity created this code will run after 4 seconds

            @Override
            public void run(){

                startActivity(new Intent(splash.this,Sign_in.class ));
                finish();
            }
        }, 1500);
    }
}
