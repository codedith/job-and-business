package com.xcrino.moro.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.xcrino.moro.BusinessModules.CampaignDetailActivity;
import com.xcrino.moro.PojoModels.Campaign;
import com.xcrino.moro.R;
import com.xcrino.moro.Utilities.AppPreferencesShared;
import com.xcrino.moro.ViewHolder.CampaignViewHolder;

import java.util.List;

public class PreviousSelectionAdapter extends RecyclerView.Adapter<CampaignViewHolder> {

    private Context context;
    private List<Campaign> campaigns;
    AppPreferencesShared appPreferencesShared;

    public PreviousSelectionAdapter(Context context, List<Campaign> campaigns) {
        this.context = context;
        this.campaigns = campaigns;
        appPreferencesShared = new AppPreferencesShared(context);
    }

    @NonNull
    @Override
    public CampaignViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_campaign, parent, false);
        CampaignViewHolder campaignViewHolder = new CampaignViewHolder(view);
        return campaignViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CampaignViewHolder holder, int position) {
        holder.type_of_campaign.setText(campaigns.get(position).getBusinessCampaignMode() == null ? "" : "Type of Campaign : " + campaigns.get(position).getBusinessCampaignMode().toString());
        if (campaigns.get(position).getBusinessCampaignStatus().equals("0")) {
            holder.active_inactive_tv.setText("Inactive");
            holder.active_inactive_tv.setTextColor(context.getResources().getColor(R.color.dark_red));
            holder.active_inactive_img.setColorFilter(ContextCompat.getColor(context, R.color.dark_red));
        } else {
            holder.active_inactive_tv.setText("Active");
            holder.active_inactive_tv.setTextColor(context.getResources().getColor(R.color.dark_green));
            holder.active_inactive_img.setColorFilter(ContextCompat.getColor(context, R.color.dark_green));
        }

        holder.views_count.setText(campaigns.get(position).getBusinessCampaignViews() == null ? "" : campaigns.get(position).getBusinessCampaignViews().toString());
        holder.likes_count.setText(campaigns.get(position).getLikes() == null ? "" : campaigns.get(position).getLikes().toString());
        holder.dislikes_count.setText(campaigns.get(position).getDislikes() == null ? "" : campaigns.get(position).getDislikes().toString());
//        holder.replies_count.setText(campaigns.get(position).getDislikes() == null ? "" : campaigns.get(position).getDislikes().toString());
        holder.blocks_count.setText(campaigns.get(position).getBlockByUsers() == null ? "" : campaigns.get(position).getBlockByUsers().toString());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CampaignDetailActivity.class);
                appPreferencesShared.setCampaignId(campaigns.get(position).getBusinessCampaignId());
                appPreferencesShared.setPageDirection("Previous");
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return campaigns.size();
    }
}
