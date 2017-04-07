package com.google.firebase.udacity.friendlychat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

import com.google.android.gms.common.api.GoogleApiClient;

public class Sign_Up extends AppCompatActivity {
    DatabaseHelper mydb;
    Spinner dropdown;
    String[] items={"Student", "Professor"};
    Spinner department;
    String[] items_2={"Department", "Computer_Engineering", "Electronics_and_Computer_Engineering", "Electronics_Engineering"};

    EditText first_name;
    EditText last_name;
    EditText email;
    EditText password;
    EditText confirm_password;
    Button sign_up;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client2;

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

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign__up);

        mydb=new DatabaseHelper(this);

        dropdown=(Spinner)findViewById(R.id.dropdown);
        ArrayAdapter<String>adapter = new ArrayAdapter<String>(Sign_Up.this, android.R.layout.simple_spinner_item,items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropdown.setAdapter(adapter);

        department=(Spinner)findViewById(R.id.department);
        ArrayAdapter<String>adapter_2 = new ArrayAdapter<String>(Sign_Up.this, android.R.layout.simple_spinner_item,items_2);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        department.setAdapter(adapter_2);

        first_name = (EditText) findViewById(R.id.first_name);
        last_name = (EditText) findViewById(R.id.last_name);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        confirm_password = (EditText) findViewById(R.id.confirm_password);
        sign_up = (Button) findViewById(R.id.sign_up);

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validEmail(email.getText().toString())){
                    if(first_name.getText().toString().equals("")||last_name.getText().toString().equals("")||email.getText().toString().equals("")||password.getText().toString().equals("")){
                        Toast.makeText(Sign_Up.this,"All fields are required",Toast.LENGTH_LONG).show();
                    }
                    else{
                        if(checkPassword(password.getText().toString())) {
                            String user = String.valueOf(dropdown.getSelectedItem());
                            String deparment = String.valueOf(department.getSelectedItem());
                            if (password.getText().toString().equals(confirm_password.getText().toString())) {

                                boolean isInserted = mydb.insert_users(first_name.getText().toString(), last_name.getText().toString(), email.getText().toString(), password.getText().toString(), user, deparment);
                                if (isInserted) {
//                        Toast.makeText(Sign_Up.this,"Data Inserted",Toast.LENGTH_LONG).show();
                                    Bundle send = new Bundle();
                                    send.putString("email", email.getText().toString());
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    intent.putExtras(send);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(Sign_Up.this, "Data not Inserted in Students", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(Sign_Up.this, "Password doesn't match", Toast.LENGTH_LONG).show();

                            }
                        }
                        else{
                            Toast.makeText(Sign_Up.this, "Enter better password", Toast.LENGTH_LONG).show();
                        }
                    }
                }
                else {
                    Toast.makeText(Sign_Up.this,"Enter valid E-Mail address",Toast.LENGTH_LONG).show();

                }

            }
        });
    }

    public boolean validEmail(String address){
        int addrLen = address.length();
        int atloc = address.indexOf('@');
        int ploc = address.indexOf("tcd.ie");

        if((address.indexOf(' ') >= 0) || (atloc <= 0) || (ploc <= 0) || (ploc == addrLen ) || ( atloc == addrLen) || (atloc > ploc)){
            return false;
        }

        return true;
    }

    public boolean checkPassword(String password){
        char ch = password.charAt(0);
        char ch1 = password.charAt(1);
        boolean goodPassword = false;
        boolean testCh = false;

        if (password.length() < 8)
        {
            Toast.makeText(Sign_Up.this,"Password must be at least 8 characters",Toast.LENGTH_LONG).show();
            return false;
        }

        while (!goodPassword)
        {
            if (!(Character.isLowerCase(ch)))
            {
                for (int i=1; i<password.length() ; i++) // iterate through each char in password
                {
                    ch = password.charAt(i);
                    if(Character.isLowerCase(ch)) // if the char is lower case == true
                        break;

                    if (i == password.length()) { // if on last char and no lower case found
                        Toast.makeText(Sign_Up.this, "Password must have at least one lower case letter", Toast.LENGTH_LONG).show();
                        return false;
                    }
                }
            }

            ch = password.charAt(0);
            testCh = false;
            if (!(Character.isUpperCase(ch))) {
                for (int i=1; i<password.length(); i++)
                {
                    ch = password.charAt(i);
                    if (Character.isUpperCase(ch))
                        break;

                    if (i==password.length())
                    {
                        Toast.makeText(Sign_Up.this,"Password must have at least one Upper case letter",Toast.LENGTH_LONG).show();
                        return false;
                    }
                }
            }

            ch = password.charAt(0);
            testCh = false;
            if (!(isSpecialChar(ch))){
                for (int i=1; i<password.length(); i++) {
                    ch = password.charAt(i);
                    if (isSpecialChar(ch))
                        break;

                    if (i == password.length()) {
                        Toast.makeText(Sign_Up.this, "Password must have at least one numeric digit", Toast.LENGTH_LONG).show();
                        return false;
                    }
                }
            }

            ch = password.charAt(0);
            testCh = false;
            if (!(Character.isDigit(ch))) {
                for (int i = 1; i < password.length(); i++) {
                    ch = password.charAt(i);
                    if (Character.isDigit(ch))
                        break;

                    if (i == password.length()) {
                        Toast.makeText(Sign_Up.this, "Password must have at least one numeric digit", Toast.LENGTH_LONG).show();
                        return false;
                    }
                }
            }

            ch = password.charAt(0);
            testCh = false;
            if(ch != ch1){
                for(int i = 1; i < password.length()-1; i++){
                    ch=password.charAt(i);
                    ch1=password.charAt(i+1);
                    if(ch == ch1){
                        Toast.makeText(Sign_Up.this, "Password can't have to same digits beside each other", Toast.LENGTH_LONG).show();
                        return false;
                    }

                }
            }

            goodPassword = true;
        }
        return true;
    }

    public boolean isSpecialChar(char ch){
        char[] specialChar = {'!', '£', '$', '€', '%', '^', '&', '&', '*','(', ')', '-', '_', '+', '+', '[','{', ']', '}', ':', ';', '@', '#', '~', ',', '<', '.', '>', '?', '/', '|'};

        for(int i =0; i < password.length(); i++){
            if (ch == specialChar[i])
                return true;
        }
        return  false;
    }
}

