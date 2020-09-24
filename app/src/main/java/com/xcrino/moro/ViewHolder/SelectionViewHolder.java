package com.xcrino.moro.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xcrino.moro.R;

public class SelectionViewHolder extends RecyclerView.ViewHolder {

    public TextView campaign_mode, campaign_date;

    public SelectionViewHolder(@NonNull View itemView) {
        super(itemView);
        campaign_mode = itemView.findViewById(R.id.campaign_mode);
        campaign_date = itemView.findViewById(R.id.campaign_date);

    }
}
