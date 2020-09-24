package com.xcrino.moro.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xcrino.moro.R;
import com.xcrino.moro.ViewHolder.ChattingViewHolder;

public class ChattingAdapter extends RecyclerView.Adapter<ChattingViewHolder> {

    Context mContext;

    public ChattingAdapter(Context context) {
        this.mContext = context;
    }

    @NonNull
    @Override
    public ChattingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.card_chatting, parent, false);
        ChattingViewHolder chattingViewHolder = new ChattingViewHolder(view);
        return chattingViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ChattingViewHolder holder, int position) {
        if ( position % 2 == 0 ){
            holder.mLeft_view.setVisibility(View.VISIBLE);
            holder.mRight_view.setVisibility(View.GONE);
        }
        else {
            holder.mLeft_view.setVisibility(View.GONE);
            holder.mRight_view.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
