package com.google.firebase.udacity.friendlychat;

/**
 * Created by HughLavery on 13/02/2017.
 */

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class Sign_in extends AppCompatActivity {
    database db;
    public static String mUsername;

    ImageView image_1;
    TextView text_1;
    EditText e_mail;
    EditText password;
    Button sign_in;
    TextView sign_up;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);

        SharedPreferences sharedPref = getSharedPreferences("mypref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        String colour = sharedPref.getString("colour", "");
        Toast.makeText(this, "theme====: " + colour, Toast.LENGTH_LONG).show();

        if(colour == "red"){
            Toast.makeText(this, "theme= in if =: " + colour, Toast.LENGTH_LONG).show();
            setTheme(R.style.RedTheme);
        }

        image_1=(ImageView)findViewById(R.id.image_1);
        e_mail=(EditText)findViewById(R.id.e_mail);
        sign_in=(Button)findViewById(R.id.sign_in);
        sign_up=(TextView)findViewById(R.id.sign_up);

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(), Sign_Up.class);
                startActivityForResult(intent, 0);
            }
        });

        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_1 = new Intent(getApplicationContext(), MainActivity.class);
                String name = e_mail.getText().toString();
                intent_1.putExtra(mUsername, name);
                startActivityForResult(intent_1, 0);
            }
        });

    }
}
