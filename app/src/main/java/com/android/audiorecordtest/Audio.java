package com.android.audiorecordtest;

/**
 * Created by Aoife on 24/03/2017.
 */

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.udacity.friendlychat.R;

import org.w3c.dom.Text;

import java.io.IOException;

// press record button
// record audio
// upload recording to Firebase storage
// send message containing recording URL in storage
// receive audio-type message
// download audio from URL
// press button to play audio

public class Audio extends AppCompatActivity {

    private RecordButton mRecordButton = null;
    private TextView mRecordLabel;
    private MediaRecorder mRecorder = null;

    private PlayButton   mPlayButton = null;
    private MediaPlayer   mPlayer = null;

    private String mFilename = null;

    private static final String LOG_TAG = "Record_log";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecordLabel = (TextView) findViewById(R.id.RecordLabel)
        mRecordButton = (Button) findViewById(R.id.RecordButton);
        mFilename = Environment.getExternalStorageDirectory().getAbsolutePath();
        mFilename += "/recorded_audio.3gp";

        mRecordButton.setOnTouchListener(new View.OnTouchListener()){
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
        }
    }


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

    private void stopRecording() {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
    }

}
