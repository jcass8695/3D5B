package com.google.firebase.udacity.friendlychat;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
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
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
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
    private static MessageAdapter mMessageAdapter;
    private ProgressBar mProgressBar;
    private EditText mMessageEditText;
    private Button mSendButton;
    private Button mSignout;
    private static List<FriendlyMessage> friendlyMessages;

    private String mUsername, roomName;

    // Firebase database
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mMessagesDatabaseReference;
    private ChildEventListener mChildEventListener;

    //for audio recording
    private Button mRecordButton = null;
    private MediaRecorder mRecorder;
    private String mFilename = null;
    private static final String LOG_TAG = "Record_log";
    private StorageReference mStorage;
    private ProgressDialog mProgress;
    private int mCounter;

    // Requesting permission to RECORD_AUDIO
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    private boolean permissionToRecordAccepted = false;
    private String[] permissions = {android.Manifest.permission.RECORD_AUDIO};

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_RECORD_AUDIO_PERMISSION:
                permissionToRecordAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                break;
        }
        if (!permissionToRecordAccepted) finish();

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // GUI MERGE
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
        }

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
        friendlyMessages = new ArrayList<>();
        mMessageAdapter = new MessageAdapter(this, R.layout.item_message, friendlyMessages);
        mMessageListView.setAdapter(mMessageAdapter);

        // Initialize progress bar
        mProgressBar.setVisibility(ProgressBar.INVISIBLE);

        // Initialize everything for recording audio
        mRecordButton = (Button) findViewById(R.id.RecordButton);
        mFilename = Environment.getExternalStorageDirectory().getAbsolutePath();
        mCounter = 1;
        mFilename += "/" + roomName + "_" + mCounter + ".3gp";
        mStorage = FirebaseStorage.getInstance().getReference();
        mProgress = new ProgressDialog(this);

        ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION);

        //Record Audio function
        mRecordButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    startRecording();
                    mMessageEditText.setHint("Recording...");
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    stopRecording();
                    mMessageEditText.setHint("");
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
                final String mType = "text";
                Toast.makeText(getBaseContext(), "Message Sent!", Toast.LENGTH_SHORT).show();
                // Create a new message object with text from the EditText widget and attach the username to it. Null for photo url
                FriendlyMessage friendlyMessage = new FriendlyMessage(mMessageEditText.getText().toString(), mUsername, mType, roomName);

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

                sortList(1);
                FriendlyMessage friendlyMessage = dataSnapshot.getValue(FriendlyMessage.class);

                // Loop to find position of old message, remove it and replace with the upvoted message
                for (int i = 0; i < mMessageAdapter.getCount(); i++) {
                    if (friendlyMessage.getFbaseKey().equals(mMessageAdapter.getItem(i).getFbaseKey())) {
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

    // VERY IMPORTANT, prevents multiple instatiations of the same listener, resulting in multiple messages being sent
    @Override
    protected void onPause() {
        super.onPause();
        mMessagesDatabaseReference.removeEventListener(mChildEventListener);
    }

    // Yash's fantastic sorting of messages by upvote count
    public static void sortList(int order) {
        Collections.sort(friendlyMessages, new Sorter(order));
        mMessageAdapter.notifyDataSetChanged();
    }

    static class Sorter implements Comparator<FriendlyMessage> {
        int order = -1;

        Sorter(int order) {
            this.order = order;
        }

        public int compare(FriendlyMessage message1, FriendlyMessage message2) {
            if (message1.getUpvote() > message2.getUpvote()) {
                return (-1 * order);
            } else
                return order;
        }
    }

    // GUI stuff, whose function I do not know
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

    // start recording audio
    private void startRecording() {
        //initialise everything
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setOutputFile(mFilename);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        //include exception handler
        try {
            mRecorder.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }

        //start recording
        mRecorder.start();
    }

    // stop recording audio
    private void stopRecording() {
        //finish recording, release memory
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;

        //upload recording to storage
        uploadAudio();
    }

    // upload audio to Firebase storage
    private void uploadAudio() {
        //show upload progress
        mProgress.setMessage("Uploading Recording...");
        mProgress.show();

        //get storage path
        final StorageReference filepath = mStorage.child("Audio").child(roomName).child(mCounter + ".3gp");
        StorageMetadata metadata = new StorageMetadata.Builder().setContentType("audio/3gpp").build();
        final String mURL = filepath.toString();
        final String mType = "audio/3gpp";

        //upload the file
        Uri uri = Uri.fromFile(new File(mFilename));
        filepath.putFile(uri, metadata).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //Handle unsuccessful uploads
            }
        })
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        //Handle successful uploads

                        //send URL as message

                        FriendlyMessage friendlyMessage = new FriendlyMessage(mURL, mUsername, mType, roomName);

                        String messageKey = mMessagesDatabaseReference.push().getKey();
                        friendlyMessage.setFbaseKey(messageKey);
                        mMessagesDatabaseReference.child(messageKey).setValue(friendlyMessage);

                        Log.d(TAG, messageKey);

                        // dismiss upload progress
                        mProgress.dismiss();

                        //increment counter
                        mCounter += 1;
                    }
                });
    }
}



