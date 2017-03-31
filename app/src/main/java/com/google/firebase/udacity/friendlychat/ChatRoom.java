package com.google.firebase.udacity.friendlychat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lavelld on 17/02/17.
 */

public class ChatRoom extends AppCompatActivity {

    private static final String TAG = "ChatRoom";
    //private static final int RC_PHOTO_PICKER = 2;

    public static final String ANONYMOUS = "anonymous";
    public static final String JACK = "Jack";
    public static final int DEFAULT_MSG_LENGTH_LIMIT = 1000;

    private ListView mMessageListView;
    private MessageAdapter mMessageAdapter;
    private ProgressBar mProgressBar;
    private EditText mMessageEditText;
    private Button mSendButton;
    private Button mSignout;

    private String mUsername, roomName;

    // Firebase database
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mMessagesDatabaseReference;
    private ChildEventListener mChildEventListener;

    //for audio recording
    private RecordButton mRecordButton = null;
    private MediaRecorder mRecorder;
    private TextView mRecordLabel;
    private String mFilename = null;
    private static final String LOG_TAG = "Record_log";
    private StorageReference mStorage;
    private ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_room);
        Bundle extras = getIntent().getExtras();
        mUsername = (String) extras.get("user_name");
        roomName = (String) extras.get("room_name");
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

        //mMessageListView.setVisibility(View.INVISIBLE);

        // Initialize message ListView and its adapter
        List<FriendlyMessage> friendlyMessages = new ArrayList<>();
        mMessageAdapter = new MessageAdapter(this, R.layout.item_message, friendlyMessages);
        mMessageListView.setAdapter(mMessageAdapter);

        // Initialize progress bar
        mProgressBar.setVisibility(ProgressBar.INVISIBLE);

        // Initialize everything for recording audio
        mRecordLabel = (TextView) findViewById(R.id.RecordLabel)
        mRecordButton = (Button) findViewById(R.id.RecordButton);
        mFilename = Environment.getExternalStorageDirectory().getAbsolutePath();
        mFilename += "/recorded_audio.3gp";
        mStorage = FirebaseStorage.getInstance().getReference();
        mProgress = new ProgressDialog(this);

        //Record Audio function
        mRecordButton.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if(motionEvent.getAction()== MotionEvent.ACTION_DOWN ){
                    startRecording();

                    mRecordLabel.setText("Recording...");

                }
                else if (motionEvent.getAction() == MotionEvent.ACTION_UP){
                    stopRecording();
                    mRecordLabel.setText("Finished Recording");
                }

                return false;
            }
        });

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
                FriendlyMessage friendlyMessage = dataSnapshot.getValue(FriendlyMessage.class);

                // Loop to find position of old message, remove it and replace with the upvoted message
                for(int i = 0; i < mMessageAdapter.getCount(); i++)
                {
                    if(friendlyMessage.getFbaseKey().equals(mMessageAdapter.getItem(i).getFbaseKey()))
                    {
                        FriendlyMessage oldMessage = mMessageAdapter.getItem(i);
                        mMessageAdapter.remove(oldMessage);
                        mMessageAdapter.insert(friendlyMessage, i);
                        break;
                    }
                }
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

    // start recording audio
    private void startRecording() {
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setOutputFile(mFilename);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mRecorder.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }

        mRecorder.start();
    }

    // stop recording audio
    private void stopRecording() {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;

        uploadAudio();
    }

    // upload audio to Firebase storage
    private void uploadAudio() {
        mProgress.setMessage("Uploading Recording...");
        mProgress.show();

        StorageReference filepath = mStorage.child("Audio").child("new_audio.3gp");

        Uri uri = uri.fromFile(new File(mFilename));
        filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>(){
            @Override
            public void onSuccess (UploadTask.TaskSnapshot taskSnapshot) {
                mProgress.dismiss();
                mRecordLabel.setText("Upload Complete");
            }
        });
    }
}
