package com.google.firebase.udacity.friendlychat;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class MessageAdapter extends ArrayAdapter<FriendlyMessage> {
    public MessageAdapter(Context context, int resource, List<FriendlyMessage> objects) {
        super(context, resource, objects);
    }

    private static final String TAG = "MessageAdapter";
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mMessagesDatabaseReference;
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {

            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.item_message, parent, false);
        }

        TextView messageTextView = (TextView) convertView.findViewById(R.id.messageTextView);
        TextView authorTextView = (TextView) convertView.findViewById(R.id.nameTextView);
        final TextView upvoteTextView = (TextView) convertView.findViewById(R.id.upvoteTextView);
        Button upvoteButton = (Button) convertView.findViewById(R.id.upvoteButton);

        final FriendlyMessage message = getItem(position);

        final String chatroom = message.getLocation();
        messageTextView.setVisibility(View.VISIBLE);
        messageTextView.setText(message.getText());
        authorTextView.setText(message.getName());
        upvoteTextView.setText(Integer.toString(message.getUpvote()));

        upvoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
