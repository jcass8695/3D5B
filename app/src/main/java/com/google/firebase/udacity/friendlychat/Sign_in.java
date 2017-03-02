package com.google.firebase.udacity.friendlychat;

/**
 * Created by HughLavery on 13/02/2017.
 */

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class Sign_in extends AppCompatActivity {
    login_database db;
    public static String mUsername;

    TextView text_1;
    EditText e_mail;
    EditText password;
    Button sign_in;
    TextView sign_up;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);

        text_1=(TextView)findViewById(R.id.text_1);
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
