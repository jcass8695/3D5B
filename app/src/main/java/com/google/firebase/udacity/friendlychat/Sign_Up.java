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
    private database db;
    public static String NAME;

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

       /* Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.colours_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);*/


        first_name = (EditText) findViewById(R.id.first_name);
        last_name = (EditText) findViewById(R.id.last_name);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        confirm_password = (EditText) findViewById(R.id.confirm_password);
        sign_up = (Button) findViewById(R.id.sign_up);

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(), "Sign Up Successful!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    String name = first_name.getText().toString();
                    intent.putExtra(NAME, name);
                    startActivityForResult(intent, 0);
            }
        });
    }

    /*public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        if(item.equals("Select Theme")) {
            //do nothing
        }
        else {
            //selecting the theme
            SharedPreferences sharedPref = getSharedPreferences("mypref", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("colour", item);
            editor.apply();
            // Showing selected spinner item
            Toast.makeText(parent.getContext(), "Selected: " + sharedPref.getString("theme", "")+" restart app", Toast.LENGTH_LONG).show();

        }
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }*/
}

