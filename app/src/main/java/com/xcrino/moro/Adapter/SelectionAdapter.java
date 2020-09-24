package com.xcrino.moro.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xcrino.moro.R;
import com.xcrino.moro.ViewHolder.SelectionViewHolder;

public class SelectionAdapter extends RecyclerView.Adapter<SelectionViewHolder> {

    Context mContext;
    private String[] campaign_mode;
    private String[] campaign_date;

    public SelectionAdapter(Context mContext, String[] campaign_mode, String[] campaign_date) {
        this.mContext = mContext;
        this.campaign_mode = campaign_mode;
        this.campaign_date = campaign_date;
    }

    @NonNull
    @Override
    public SelectionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.previous_selection_card, parent, false);
        return new SelectionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectionViewHolder holder, int position) {
        holder.campaign_mode.setText(campaign_mode[position]);
        holder.campaign_date.setText(campaign_date[position]);
    }

    @Override
    public int getItemCount() {
        return campaign_date.length;
    }
}
