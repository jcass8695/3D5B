/**
 * Copyright Google Inc. All Rights Reserved.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.firebase.udacity.friendlychat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static android.R.id.list;
import static com.google.firebase.udacity.friendlychat.R.id.spinner;
import static com.google.firebase.udacity.friendlychat.Sign_in.mUsername;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper mydb=new DatabaseHelper(this);
    private String mUsername;
    private ListView listView;
    private boolean inSession=true;
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> list_of_rooms = new ArrayList<>();
    private String name;
    private DatabaseReference root = FirebaseDatabase.getInstance().getReference().getRoot();
    public final ArrayList<String> modules = new ArrayList<String>();
    String user;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // GUI STUFF
        SharedPreferences sharedPref = getSharedPreferences("mypref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        String colour = sharedPref.getString("colour", "");
        String text = sharedPref.getString("text", "");
        Toast.makeText(getApplicationContext(), "text:" + text, Toast.LENGTH_LONG);
        if(colour.equals("Red")){
            setTheme(R.style.RedThemeWithActionBar);
        }
        if(colour.equals("Pink")){
            setTheme(R.style.PinkThemeWithActionBar);
        }
        if(colour.equals("Blue Sky")){
            setTheme(R.style.BlueThemeWithActionBar);
        }
        else {

        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle extras = getIntent().getExtras();
        mUsername = (String) extras.get("email");

        setTitle("Modules");
        listView = (ListView) findViewById(R.id.moduleListView);
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list_of_rooms);
        listView.setAdapter(arrayAdapter);

        StringBuffer buffer=new StringBuffer();
        Cursor res=mydb.check_users(mUsername);
        String department_table;
        while(res.moveToNext()) {
            user=res.getString(4);
            department_table = res.getString(5);
            Cursor res_2 = mydb.get_all_modules(department_table);
            while (res_2.moveToNext()) {
                modules.add(res_2.getString(0));
//                buffer.append(res_2.getString(0)+"\n");
            }
//            show_message("modules", buffer.toString());
        }

    root.addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            Set<String> set = new HashSet<String>();
            Iterator i = dataSnapshot.getChildren().iterator();

            //final String[] chatRooms = new String[9];D
            final ArrayList<String> chatRooms = new ArrayList<String>();
            int k = 0;

            while(i.hasNext()){
                chatRooms.add(((DataSnapshot)i.next()).getKey());
                k++;
            }

            for(int I = 0; I < modules.size(); I++){
                System.out.println(modules.get(I));
            }

            for ( int j = 0; j<k; j++)
            {
                // String curr = chatRooms.get(j);
                if(isItemInArray( chatRooms.get(j), modules, modules.size())) {
                    set.add( chatRooms.get(j));
                }
            }

            list_of_rooms.clear();
            list_of_rooms.addAll(set);
            arrayAdapter.notifyDataSetChanged();
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
        }
    });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l){

                Intent intent;
                if(user.equals("Student")){
                    intent = new Intent(getApplicationContext(), ChatRoom.class);
                    intent.putExtra("room_name",((TextView)view).getText().toString() );
                    intent.putExtra("user_name", mUsername);
                    intent.putExtra("session_status", inSession);
                    startActivity(intent);

                }
                else {
                    intent = new Intent(getApplicationContext(), TeacherChatroom.class);
                    intent.putExtra("room_name",((TextView)view).getText().toString() );
                    intent.putExtra("user_name", mUsername);
                    intent.putExtra("session_status", inSession);
                    startActivity(intent);
                }

            }

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.student_main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sign_out_menu:
                Intent intent = new Intent(getApplicationContext(), Sign_in.class);
                startActivityForResult(intent, 0);
            case R.id.settings_menu:
                Toast.makeText(getApplicationContext(), "selected settings", Toast.LENGTH_SHORT);
                Intent intent2 = new Intent(getApplicationContext(), Settings_Page.class);
                startActivityForResult(intent2, 0);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public boolean isItemInArray(String s, ArrayList mod, int size){
        String curr;
        for(int i = 0; i< size;i++)
        {
            curr = mod.get(i).toString();
            if(curr.equals(s)) {
                return true;
            }
        }
        return false;
    }
    public void show_message(String title, String message){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}
