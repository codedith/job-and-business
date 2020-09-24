package com.xcrino.moro.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xcrino.moro.Interface.ApiInterface;
import com.xcrino.moro.PojoModels.Campaign;
import com.xcrino.moro.PojoModels.CommonResponseModel;
import com.xcrino.moro.PojoModels.CompaignBlockedResponse;
import com.xcrino.moro.R;
import com.xcrino.moro.Utilities.ApiClients;
import com.xcrino.moro.Utilities.AppPreferencesShared;
import com.xcrino.moro.Utilities.NetworkStatus;
import com.xcrino.moro.ViewHolder.CampaignViewHolder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BlockedCampaignAdapter extends RecyclerView.Adapter<CampaignViewHolder> {

    private Context context;
    private List<Campaign> campaigns;
    private AppPreferencesShared appPreferencesShared;

    public BlockedCampaignAdapter(Context context, List<Campaign> campaigns) {
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

        holder.typeCompaignList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                appPreferencesShared.setJobId(savedJobList.get(position).getJobId());
                if (NetworkStatus.isNetworkAvailable(context)) {
                    postToggleBlockCampaign(holder, position);
                } else {
                    Toast.makeText(context, "Please Check Your Internet Connection !", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void postToggleBlockCampaign(CampaignViewHolder holder, int position) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        ApiInterface apiInterface = ApiClients.getClient().create(ApiInterface.class);
        Call<CommonResponseModel> compaignBlockedResponseCall = apiInterface.postToggleBlockCampaign(appPreferencesShared.getUserId(),
                campaigns.get(position).getBusinessCampaignId());

        compaignBlockedResponseCall.enqueue(new Callback<CommonResponseModel>() {
            @Override
            public void onResponse(Call<CommonResponseModel> call, Response<CommonResponseModel> response) {
                try {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    if (response.isSuccessful()) {
                        Intent intent = new Intent("custom-message");
                        intent.putExtra("response",true);
                        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                    } else {
                        Intent intent = new Intent("custom-message");
                        intent.putExtra("response",true);
                        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                }
            }

            @Override
            public void onFailure(Call<CommonResponseModel> call, Throwable t) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                Toast.makeText(context, "Something went wrong !", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return campaigns.size();
    }
}