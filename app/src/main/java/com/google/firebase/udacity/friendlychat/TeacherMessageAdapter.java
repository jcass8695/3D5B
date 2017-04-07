package com.google.firebase.udacity.friendlychat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import static com.google.firebase.udacity.friendlychat.Sign_in.mUsername;

/**
 * Created by evakn on 31/03/2017.
 */


public class TeacherMessageAdapter extends ArrayAdapter<FriendlyMessage> {
    public TeacherMessageAdapter(Context context, int resource, List<FriendlyMessage> objects) {
        super(context, resource, objects);
    }

    private static final String TAG = "TeacherMessageAdapter";
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mMessagesDatabaseReference;
    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        if (convertView == null) {
               // LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.teacher_item_message, parent, false);
        }
                //convertView = inflater.inflate(R.layout.teacher_item_message, parent, false);


        TextView messageTextView = (TextView) convertView.findViewById(R.id.messageTextView);
        TextView authorTextView = (TextView) convertView.findViewById(R.id.nameTextView);
        final TextView upvoteTextView = (TextView) convertView.findViewById(R.id.upvoteTextView);
        Button upvoteButton = (Button) convertView.findViewById(R.id.upvoteButton);
        Button mAnswerButton = (Button) convertView.findViewById(R.id.answerButton);
        Button deleteButton = (Button) convertView.findViewById(R.id.blockButton);
        final FriendlyMessage message = getItem(position);

        final String chatroom = message.getLocation();
        messageTextView.setVisibility(View.VISIBLE);
        messageTextView.setText(message.getText());
        authorTextView.setText(message.getName());
       upvoteTextView.setText(Integer.toString(message.getUpvote()));

        mAnswerButton.setEnabled(true);
        mAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View convertView) {

                Intent intent =new Intent(getContext(), Answers.class);
                getContext().startActivity(intent);
            }
        });

        deleteButton.setEnabled(true);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View convertView) {
                mFirebaseDatabase = FirebaseDatabase.getInstance();
                mMessagesDatabaseReference = mFirebaseDatabase.getReference().child(chatroom).child(message.getFbaseKey());
                mMessagesDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        mMessagesDatabaseReference.getRef().removeValue();
                        message.setText("MESSAGE REMOVED");
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
        upvoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View convertView) {
                mFirebaseDatabase = FirebaseDatabase.getInstance();
                mMessagesDatabaseReference = mFirebaseDatabase.getReference().child(chatroom).child(message.getFbaseKey());

                message.incrementUpvote();
                upvoteTextView.setText(Integer.toString(message.getUpvote()));
                mMessagesDatabaseReference.setValue(message);
            }
        });

        return convertView;
    }
}
