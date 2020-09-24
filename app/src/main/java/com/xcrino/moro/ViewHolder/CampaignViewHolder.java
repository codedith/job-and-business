package com.xcrino.moro.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.xcrino.moro.R;

public class CampaignViewHolder extends RecyclerView.ViewHolder {

    public TextView type_of_campaign, active_inactive_tv, views_count, likes_count, dislikes_count, replies_count, blocks_count;
    public ImageView active_inactive_img;
    public CardView typeCompaignList;

    public CampaignViewHolder(@NonNull View itemView) {
        super(itemView);
        typeCompaignList = (CardView) itemView.findViewById(R.id.typeCompaignList);
        type_of_campaign = itemView.findViewById(R.id.type_of_campaign);
        active_inactive_img = itemView.findViewById(R.id.active_inactive_img);
        active_inactive_tv = itemView.findViewById(R.id.active_inactive_tv);
        views_count = itemView.findViewById(R.id.views_count);
        likes_count = itemView.findViewById(R.id.likes_count);
        dislikes_count = itemView.findViewById(R.id.dislikes_count);
        replies_count = itemView.findViewById(R.id.replies_count);
        blocks_count = itemView.findViewById(R.id.blocks_count);
    }
}
