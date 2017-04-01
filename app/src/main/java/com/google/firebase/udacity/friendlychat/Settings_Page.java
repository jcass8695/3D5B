package com.google.firebase.udacity.friendlychat;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class Settings_Page extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Toast.makeText(getApplicationContext(), "in settings class", Toast.LENGTH_SHORT);
        SharedPreferences sharedPref = getSharedPreferences("mypref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        String colour = sharedPref.getString("colour", "");

        if (colour.equals("Red")) {
            setTheme(R.style.RedThemeWithActionBar);
        }
        if (colour.equals("Pink")) {
            setTheme(R.style.PinkThemeWithActionBar);
        }
        if (colour.equals("Blue Sky")) {
            setTheme(R.style.BlueThemeWithActionBar);
        } else {

        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings__page);
        setTitle("Settings");

        Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
        spinner2.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.colours_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter);

        /*Spinner spinner3 = (Spinner) findViewById(R.id.spinner3);
        spinner3.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this,
                R.array.text_array, android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3.setAdapter(adapter3);*/
    }
    //public void onItemSelected(AdapterView<?> arg0, View view, int position, long id) {
    // On selecting a spinner item
    // Toast.makeText(arg0.getContext(), "ARG0" + arg0.getId(), Toast.LENGTH_LONG);
    //switch (arg0.getId()) {


    //  case R.id.spinner3:
        /*Toast.makeText(arg0.getContext(),"selected text spinner", Toast.LENGTH_LONG);
                    String item2 = arg0.getItemAtPosition(position).toString();
                    if(item2.equals("Select Text Size")){
                        //do nothing
                       // Toast.makeText(arg0.getContext(), "jfeikjf", Toast.LENGTH_LONG);
                   } else {
                        SharedPreferences sharedPref2 = getSharedPreferences("mypref2", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor2 = sharedPref2.edit();
                        editor2.putString("text", item2);
                        editor2.apply();
                        Toast.makeText(arg0.getContext(), "selected text/colour:" + sharedPref2.getString("colour", ""), Toast.LENGTH_LONG);
                    }*/

    // case R.id.spinner2:
    //Toast.makeText(arg0.getContext(),"selected colour spinner", Toast.LENGTH_LONG);
    //String item = arg0.getItemAtPosition(position).toString();
    //if (item.equals("Select Theme") || item.equals("Select Text Size")) {
    //do nothing
    //} //else {
    //selecting the theme
    //SharedPreferences sharedPref = getSharedPreferences("mypref", Context.MODE_PRIVATE);
    //SharedPreferences.Editor editor = sharedPref.edit();
    // if(item.equals("small")||item.equals("medium")||item.equals("large")){
    // Toast.makeText(arg0.getContext(), "in if", Toast.LENGTH_SHORT);
    //      SharedPreferences sharedPref = getSharedPreferences("mypref", Context.MODE_PRIVATE);
    //    SharedPreferences.Editor editor = sharedPref.edit();
    //  editor.putString("colour", item);
    // editor.apply();
    //Toast.makeText(arg0.getContext(), "Selected: " + sharedPref.getString("colour", "") + " restart app", Toast.LENGTH_SHORT);
    //                  }
//
                /* else   if(item.equals("purple space")||item.equals("red")||item.equals("pink")||item.equals("blue sky")) {
                        SharedPreferences sharedPref = getSharedPreferences("mypref", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString("colour", item);
                        editor.apply();
                        Toast.makeText(arg0.getContext(), "Selected colour: " + sharedPref.getString("colour", "") + " restart app", Toast.LENGTH_SHORT).show();
                    }*/
    //else {}*/
    // Showing selected spinner item
    //Toast.makeText(arg0.getContext(), "Selected: " + sharedPref.getString("colour", "") + " restart app", Toast.LENGTH_LONG).show();
    // }
    //default: Toast.makeText(arg0.getContext(), "nothing selected", Toast.LENGTH_LONG);

    //}

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
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
            Toast.makeText(parent.getContext(), "Selected: " + sharedPref.getString("colour", "")+" restart app", Toast.LENGTH_LONG).show();

        }
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }
}
