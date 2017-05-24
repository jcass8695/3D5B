package com.google.firebase.udacity.friendlychat;

import android.app.Activity;
import android.content.Context;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static java.security.AccessController.getContext;
import java.util.List;

public class MessageAdapter extends ArrayAdapter<FriendlyMessage> {
    private Context mContext;
    public MessageAdapter(Context context, int resource, List<FriendlyMessage> objects) {
        super(context, resource, objects);
        this.mContext = context;
    }

    private static final String TAG = "MessageAdapter";
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mMessagesDatabaseReference;
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_message, parent, false);
            holder = createViewHolder(convertView);
            convertView.setTag(holder);
        }

        else
            holder = (ViewHolder) convertView.getTag();

        // Get the message
        final FriendlyMessage message = getItem(position);

        // GUI STUFF
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) holder.contentWithBG.getLayoutParams();
        layoutParams.gravity = Gravity.RIGHT;
        holder.contentWithBG.setLayoutParams(layoutParams);

        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) holder.content.getLayoutParams();
        lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 0);
        lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        holder.content.setLayoutParams(lp);
        layoutParams = (LinearLayout.LayoutParams) holder.txtMessage.getLayoutParams();
        layoutParams.gravity = Gravity.LEFT;

        // holder replaces the commented lines just below, I think
        holder.txtMessage.setLayoutParams(layoutParams);
        holder.txtMessage.setText(message.getText());
        holder.contentWithBG.setBackgroundResource(R.drawable.patch);
        holder.upvotes.setText(Integer.toString(message.getUpvote()));
        //TextView messageTextView = (TextView) convertView.findViewById(R.id.messageTextView);
        //TextView authorTextView = (TextView) convertView.findViewById(R.id.nameTextView);
        //final TextView upvoteTextView = (TextView) convertView.findViewById(R.id.upvoteTextView);
        ImageButton upvoteButton = (ImageButton) convertView.findViewById(R.id.upvoteButton);
        final String chatroom = message.getLocation();
        //messageTextView.setVisibility(View.VISIBLE);
        //messageTextView.setText(message.getText());
        //authorTextView.setText(message.getName());
        //upvoteTextView.setText(Integer.toString(message.getUpvote()));

        upvoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFirebaseDatabase = FirebaseDatabase.getInstance();
                mMessagesDatabaseReference = mFirebaseDatabase.getReference().child(chatroom).child(message.getFbaseKey());

                message.incrementUpvote();
                holder.upvotes.setText(Integer.toString(message.getUpvote()));
                mMessagesDatabaseReference.setValue(message);
            }
        });
        return convertView;
    }

    private ViewHolder createViewHolder(View v) {
        ViewHolder holder = new ViewHolder();
        holder.txtMessage = (TextView) v.findViewById(R.id.messageTextView);
        holder.contentWithBG = (LinearLayout) v.findViewById(R.id.contentWithBackground);
        holder.content = (LinearLayout) v.findViewById(R.id.content);
        holder.upvotes = (TextView) v.findViewById(R.id.upvoteTextView);
        return holder;
    }

    private static class ViewHolder {
        public TextView txtMessage;
        public LinearLayout contentWithBG;
        public LinearLayout content;
        public TextView upvotes;

    }
}
