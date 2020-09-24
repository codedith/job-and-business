package com.xcrino.moro.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xcrino.moro.Activity.ChattingActivity;
import com.xcrino.moro.R;
import com.xcrino.moro.ViewHolder.ChatViewHolder;

public class StartChatAdapter extends RecyclerView.Adapter<ChatViewHolder> {

    Context mContext;

    public StartChatAdapter(Context context) {
        this.mContext = context;
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.card_chat_list, parent, false);
        ChatViewHolder chatViewHolder = new ChatViewHolder(view);
        return chatViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        holder.mMsg_count_tv.setVisibility(View.GONE);
        holder.mMsg_time_tv.setVisibility(View.GONE);
        holder.mCard_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ChattingActivity.class);
                ((Activity) mContext).finish();
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 10;
    }
}
