package com.google.firebase.udacity.friendlychat;

import android.os.Bundle;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by evakn on 30/03/2017.
 */

public class Answers extends AppCompatActivity {


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.answers);
        Bundle extras = getIntent().getExtras();
    }
}


