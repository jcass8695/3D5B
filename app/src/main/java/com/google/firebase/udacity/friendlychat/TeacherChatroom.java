package com.google.firebase.udacity.friendlychat;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

//
/**
 * Created by evakn on 31/03/2017.
 */

public class TeacherChatroom extends AppCompatActivity {

    private static final String TAG = "TeacherChatroom";
    //private static final int RC_PHOTO_PICKER = 2;

    public static final String ANONYMOUS = "anonymous";
    public static final String JACK = "Jack";
    public static final int DEFAULT_MSG_LENGTH_LIMIT = 1000;

    private ListView mMessageListView;
    //private TeacherMessageAdapter mMessageAdapter;
    private TeacherMessageAdapter mMessageAdapter;
    private ProgressBar mProgressBar;
    private EditText mMessageEditText;
    private Button mSendButton;
    private Button mAnswerButton;
    private Button mBlockButton;
    private Button mSignout;
    private boolean inSession;
    private String mUsername, roomName;


    // Firebase database
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mMessagesDatabaseReference;
    private ChildEventListener mChildEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_chat_room);


        Bundle extras = getIntent().getExtras();
        mUsername = (String) extras.get("user_name");
        roomName = (String) extras.get("room_name");
        inSession = (boolean) extras.get("session_status");
        //mUsername = JACK;
        setTitle(" Room - " + roomName);
        // References to Firebase realtime database.
        // mFirebaseDatabase = access to root of database, mMessagesDatabaseReference = access to "messages" portion of database
        mFirebaseDatabase = FirebaseDatabase.getInstance();

        mMessagesDatabaseReference = mFirebaseDatabase.getReference().child(roomName);

        // Initialize references to views
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mMessageListView = (ListView) findViewById(R.id.messageListView);
        mMessageEditText = (EditText) findViewById(R.id.messageEditText);
        mSendButton = (Button) findViewById(R.id.sendButton);
        mSignout = (Button) findViewById(R.id.sign_out_menu);
        mAnswerButton = (Button) findViewById(R.id.answerButton);
        //mBlockButton = (Button) findViewById(R.id.blockButton);

        //mMessageListView.setVisibility(View.INVISIBLE);

        // Initialize message ListView and its adapter
        List<FriendlyMessage> friendlyMessages = new ArrayList<>();
        //mMessageAdapter = new TeacherMessageAdapter(this, R.layout.teacher_item_message, friendlyMessages);
        mMessageAdapter = new TeacherMessageAdapter(
                this, R.layout.teacher_item_message, friendlyMessages);

        mMessageListView.setAdapter(mMessageAdapter);

        // Initialize progress bar
        mProgressBar.setVisibility(ProgressBar.INVISIBLE);

        // Enable Send button when there's text to send
        mMessageEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            // Enables/Disables send button depending on if there are characters in the edit text field
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().length() > 0) {
                    mSendButton.setEnabled(true);
                } else {
                    mSendButton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        mMessageEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(DEFAULT_MSG_LENGTH_LIMIT)});

        // Send button sends a message and clears the EditText
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create a new message object with text from the EditText widget and attach the username to it. Null for photo url
                FriendlyMessage friendlyMessage = new FriendlyMessage(mMessageEditText.getText().toString(), mUsername, roomName);

                String messageKey = mMessagesDatabaseReference.push().getKey();
                friendlyMessage.setFbaseKey(messageKey);
                mMessagesDatabaseReference.child(messageKey).setValue(friendlyMessage);

                Log.d(TAG, messageKey);
                // Clear input box
                mMessageEditText.setText("");
            }
        });

        mChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                FriendlyMessage friendlyMessage = dataSnapshot.getValue(FriendlyMessage.class);
                mMessageAdapter.add(friendlyMessage);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        // Attach childEventListener to the "messages" section of the dbase
        mMessagesDatabaseReference.addChildEventListener(mChildEventListener);



    }

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
            case R.id.start_session:
                if(inSession){
                    inSession = false;
                    mMessageListView.setVisibility(View.INVISIBLE);

                }
                else{
                    inSession = true;
                    mMessageListView.setVisibility(View.VISIBLE);
                }
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
