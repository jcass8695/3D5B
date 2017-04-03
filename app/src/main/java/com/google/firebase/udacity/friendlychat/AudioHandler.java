package com.google.firebase.udacity.friendlychat;

import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

/**
 * Created by Aoife on 03/04/2017.
 */

public class AudioHandler {

    public String mType = FriendlyMessage.getType();
    public String mText = FriendlyMessage.getText();
    private MediaPlayer mPlayer;

    if (mType=="audio/3gpp"){
        //get storage reference
        StorageReference mStorage = storage.getReferenceFromUrl(mText);

        //memory leak prevention stuff
        final long ONE_MEGABYTE = 1024 * 1024;
        mStorage.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //Handle errors
            }
        });

        //store the audio file
        File localaudio = File.createTempFile("audio", "3gpp");
        mStorage.getFile(localaudio).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                //Local temp file created
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //Handle errors
            }
        });


        //create mediaplayer
        mPlayer = new MediaPlayer();

        try{
            mPlayer.prepare();
        } catch(IOException e){
            Log.e(LOG_TAG, "prepare() failed")
        }

        mPlayer.start();

    }


}
