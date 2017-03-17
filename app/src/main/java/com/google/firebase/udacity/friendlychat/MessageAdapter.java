package com.google.firebase.udacity.friendlychat;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

import static java.security.AccessController.getContext;


public class MessageAdapter extends BaseAdapter {
    private Context mContext;
    private List<FriendlyMessage> mMessages;

    public MessageAdapter(Context context, int resource, List<FriendlyMessage> objects) {
        super();
        this.mContext = context;
        this.mMessages = objects;
    }
    @Override
    public int getCount() {
        return mMessages.size();
    }

    @Override
    public Object getItem(int position) {
        return mMessages.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        FriendlyMessage message = (FriendlyMessage) this.getItem(position);

        ViewHolder    holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_message, parent, false);
            holder = createViewHolder(convertView);
            convertView.setTag(holder);
        }
        else
            holder = (ViewHolder) convertView.getTag();

       // TextView messageTextView = (TextView) convertView.findViewById(R.id.messageTextView);
        //TextView authorTextView = (TextView) convertView.findViewById(R.id.nameTextView);
        LinearLayout.LayoutParams layoutParams =
                (LinearLayout.LayoutParams) holder.contentWithBG.getLayoutParams();
        layoutParams.gravity = Gravity.RIGHT;
        holder.contentWithBG.setLayoutParams(layoutParams);

        RelativeLayout.LayoutParams lp =
                (RelativeLayout.LayoutParams) holder.content.getLayoutParams();
        lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 0);
        lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        holder.content.setLayoutParams(lp);
        layoutParams = (LinearLayout.LayoutParams) holder.txtMessage.getLayoutParams();
        layoutParams.gravity = Gravity.LEFT;
        holder.txtMessage.setLayoutParams(layoutParams);

        holder.txtMessage.setText(message.getText());
        holder.contentWithBG.setBackgroundResource(R.drawable.patch);
        //holder.msg.setTextColor(R.color.black);
        //messageTextView.setVisibility(View.VISIBLE);
        //messageTextView.setText(message.getText());
        //TextView authorTextView = (TextView) convertView.findViewById(R.id.nameTextView);
        //authorTextView.setText(message.getName());

        return convertView;
    }


    private ViewHolder createViewHolder(View v) {
        ViewHolder holder = new ViewHolder();
        holder.txtMessage = (TextView) v.findViewById(R.id.messageTextView);
        holder.contentWithBG = (LinearLayout) v.findViewById(R.id.contentWithBackground);
        holder.content = (LinearLayout) v.findViewById(R.id.content);
        return holder;
    }

    private static class ViewHolder {
        public TextView txtMessage;
        public LinearLayout contentWithBG;

        public LinearLayout content;

    }
    public void add(FriendlyMessage message) {
        mMessages.add(message);
    }
    @Override
    public long getItemId(int position) {
        //Unimplemented, because we aren't using Sqlite.
        return position;
    }
}

