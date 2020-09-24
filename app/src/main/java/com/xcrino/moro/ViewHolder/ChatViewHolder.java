package com.xcrino.moro.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.xcrino.moro.R;

public class ChatViewHolder extends RecyclerView.ViewHolder {

    public CardView mCard_view;
    public ImageView mUser_image;
    public TextView mUsername_tv, mMessage_tv, mMsg_count_tv, mMsg_time_tv;

    public ChatViewHolder(@NonNull View itemView) {
        super(itemView);

        mCard_view = itemView.findViewById(R.id.card_view);
        mUser_image = itemView.findViewById(R.id.user_image);
        mUsername_tv = itemView.findViewById(R.id.username_tv);
        mMessage_tv = itemView.findViewById(R.id.message_tv);
        mMsg_count_tv = itemView.findViewById(R.id.msg_count_tv);
        mMsg_time_tv = itemView.findViewById(R.id.msg_time_tv);
    }
}
