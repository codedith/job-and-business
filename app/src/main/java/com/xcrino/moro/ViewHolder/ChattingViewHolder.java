package com.xcrino.moro.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xcrino.moro.R;

public class ChattingViewHolder extends RecyclerView.ViewHolder {

    public RelativeLayout mLeft_view, mRight_view;
    public ImageView mUser_image, mYour_image;
    public TextView mUser_name, mUser_chat, mYour_name, mYour_chat;

    public ChattingViewHolder(@NonNull View itemView) {
        super(itemView);

        mLeft_view = itemView.findViewById(R.id.left_view);
        mUser_image = itemView.findViewById(R.id.user_image);
        mUser_name = itemView.findViewById(R.id.user_name);
        mUser_chat = itemView.findViewById(R.id.user_chat);

        mRight_view = itemView.findViewById(R.id.right_view);
        mYour_image = itemView.findViewById(R.id.your_image);
        mYour_name = itemView.findViewById(R.id.your_name);
        mYour_chat = itemView.findViewById(R.id.your_chat);
    }
}
