package com.google.firebase.udacity.friendlychat;

/**
 * Created by HughLavery on 13/02/2017.
 */

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Sign_in extends AppCompatActivity {
    public static String mUsername;

    DatabaseHelper mydb = new DatabaseHelper(this);
    ImageView image_1;
    TextView text_1;
    EditText e_mail;
    EditText password;
    Button sign_in;
    TextView sign_up;
    Button view_students;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences sharedPref = getSharedPreferences("mypref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        String colour = sharedPref.getString("colour", "");

        if(colour.equals("Red")){
            setTheme(R.style.RedThemeNoActionBar);
        }
        if(colour.equals("Pink")){
            setTheme(R.style.PinkThemeNoActionBar);
        }
        if(colour.equals("Blue Sky")){
            setTheme(R.style.BlueThemeNoActionBar);
        }
        else {

        }

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);

        image_1=(ImageView)findViewById(R.id.image_1);
        e_mail=(EditText)findViewById(R.id.e_mail);
        sign_in=(Button)findViewById(R.id.sign_in);
        sign_up=(TextView)findViewById(R.id.sign_up);
        password=(EditText)findViewById(R.id.password);
//        view_students=(Button)findViewById(R.id.view_students);
//        view_all();

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

                Cursor res_1=mydb.check_users(e_mail.getText().toString());
                if(res_1.getCount()!=0){
                    String pass=password.getText().toString();
                    String email, check_pass;
                    while(res_1.moveToNext()) {
                        email = res_1.getString(2);
                        check_pass = res_1.getString(3);
                        if (check_pass.equals(pass)) {
                            Bundle send = new Bundle();
                            send.putString("email", email);
                            Toast.makeText(getBaseContext(), "Sign In Successful!", Toast.LENGTH_SHORT).show();
                            Intent intent_1 = new Intent(getApplicationContext(), MainActivity.class);
                            intent_1.putExtras(send);
                            startActivityForResult(intent_1, 0);
                        } else {
                            Toast.makeText(Sign_in.this, "The password you’ve entered is incorrect", Toast.LENGTH_LONG).show();
                        }
                    }
                }
                else{
                    Toast.makeText(Sign_in.this,"The email you’ve entered doesn’t match any account. Sign up for an account.",Toast.LENGTH_LONG).show();
                }



            }
        });
    }

    public void view_all(){
        view_students.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res_1=mydb.get_all_users();
                if(res_1.getCount()==0){
                    //message
                    show_message("Error", "Nothing Found");
                    return;
                }

                StringBuffer buffer=new StringBuffer();
                while(res_1.moveToNext()){
                    buffer.append("First Name: "+res_1.getString(0)+"\n");
                    buffer.append("Last Name: "+res_1.getString(1)+"\n");
                    buffer.append("Email: "+res_1.getString(2)+"\n");
                    buffer.append("Password: "+res_1.getString(3)+"\n");
                    buffer.append("User: "+res_1.getString(4)+"\n");
                    buffer.append("Department: "+res_1.getString(5)+"\n\n");
                }
//                show all data
                show_message("Data", buffer.toString());
            }
        });
    }

    public void show_message(String title, String message){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

}
