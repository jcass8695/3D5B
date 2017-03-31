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

import android.content.Intent;
import android.os.Bundle;
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

import static com.google.firebase.udacity.friendlychat.Sign_in.mUsername;


public class MainActivity extends AppCompatActivity {



    private String mUsername;

    private boolean inSession;
    private ListView listView;
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> list_of_rooms = new ArrayList<>();
    private String name;
    private DatabaseReference root = FirebaseDatabase.getInstance().getReference().getRoot();
    private String userType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bundle extras = getIntent().getExtras();
        mUsername = (String) extras.get(mUsername);

        listView = (ListView) findViewById(R.id.moduleListView);

        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                list_of_rooms);

        listView.setAdapter(arrayAdapter);
        final String modules[] = new String[]{"3C1", "3C2", "3D1", "3D2", "3D5B"};



       root.addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {
               Set<String> set = new HashSet<String>();
               Iterator i = dataSnapshot.getChildren().iterator();

               while (i.hasNext()){// && isItemInArray(((DataSnapshot)i.next()).getKey(), modules, 5 )){
                   set.add(((DataSnapshot)i.next()).getKey());
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
                Intent intent = new Intent(getApplicationContext(), TeacherChatroom.class);
                intent.putExtra("room_name",((TextView)view).getText().toString() );
                intent.putExtra("user_name", mUsername);
                intent.putExtra("session_status", inSession);
                intent.putExtra("user_type", userType);
                startActivity(intent);

            }

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sign_out_menu:
                Intent intent = new Intent(getApplicationContext(), Sign_in.class);
                startActivityForResult(intent, 0);

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public boolean isItemInArray(String s, String[] mod, int size){
        for(int i = 0; i< size;i++)
        {
            if(mod[i]==s)
                return true;
        }
        return false;
    }
}
