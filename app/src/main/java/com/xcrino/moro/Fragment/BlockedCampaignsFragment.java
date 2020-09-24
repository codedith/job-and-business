package com.xcrino.moro.Fragment;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.xcrino.moro.Adapter.BlockedCampaignAdapter;
import com.xcrino.moro.Interface.ApiInterface;
import com.xcrino.moro.PojoModels.Campaign;
import com.xcrino.moro.PojoModels.CampaignResponseModel;
import com.xcrino.moro.R;
import com.xcrino.moro.Utilities.ApiClients;
import com.xcrino.moro.Utilities.AppPreferencesShared;
import com.xcrino.moro.Utilities.NetworkStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BlockedCampaignsFragment extends Fragment {

    private Context mContext;
    private AppPreferencesShared appPreferencesShared;
    private RecyclerView recyler_view;
    private BlockedCampaignAdapter blockedCampaignAdapter;
    private List<Campaign> campaigns = new ArrayList<>();
    private TextView no_campaign_found;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blocked_campaigns, container, false);
        mContext = getContext();
        appPreferencesShared = new AppPreferencesShared(mContext);
        Locale myLocale = new Locale(appPreferencesShared.getLocale());
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        if (appPreferencesShared.getmDayNightMode()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        getLayoutInitUI(view);

        if (NetworkStatus.isNetworkAvailable(mContext)) {
            getBlockedCampaign();
        } else {
            Toast.makeText(mContext, "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
            recyler_view.setVisibility(View.GONE);
            no_campaign_found.setVisibility(View.VISIBLE);
            no_campaign_found.setText("Please Check your Internet Connection");
        }

        LocalBroadcastManager.getInstance(mContext).registerReceiver(mMessageReceiver,
                new IntentFilter("custom-message"));
        return view;
    }

    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String ItemName = intent.getStringExtra("response");
            if (!campaigns.isEmpty()) {
                campaigns.clear();
            }
            if (NetworkStatus.isNetworkAvailable(mContext)) {
                getBlockedCampaign();
            } else {
                Toast.makeText(mContext, "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
                recyler_view.setVisibility(View.GONE);
                no_campaign_found.setVisibility(View.VISIBLE);
                no_campaign_found.setText("Please Check your Internet Connection");
            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        if (NetworkStatus.isNetworkAvailable(mContext)) {
            getBlockedCampaign();
        } else {
            Toast.makeText(mContext, "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
            recyler_view.setVisibility(View.GONE);
            no_campaign_found.setVisibility(View.VISIBLE);
            no_campaign_found.setText("Please Check your Internet Connection");
        }
    }

    private void getBlockedCampaign() {
        ProgressDialog progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        ApiInterface apiInterface = ApiClients.getClient().create(ApiInterface.class);
        Call<CampaignResponseModel> campaignResponseModelCall = apiInterface.getBlockedCampaign("Business-module-api/get-user-blocked-campaign/" +
                appPreferencesShared.getUserId());
        campaignResponseModelCall.enqueue(new Callback<CampaignResponseModel>() {
            @Override
            public void onResponse(Call<CampaignResponseModel> call, Response<CampaignResponseModel> response) {
                try {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    if (response.body().getCampaigns() != null) {
                        campaigns = response.body().getCampaigns();
                        recyler_view.setVisibility(View.VISIBLE);
                        no_campaign_found.setVisibility(View.GONE);
                        setBlockedcampaign();
                    } else {
                        recyler_view.setVisibility(View.GONE);
                        no_campaign_found.setVisibility(View.VISIBLE);
                        no_campaign_found.setText("No Campaign Found");
                    }
                } catch (Exception e) {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    e.getMessage();
                }
            }

            @Override
            public void onFailure(Call<CampaignResponseModel> call, Throwable t) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                Toast.makeText(mContext, "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setBlockedcampaign() {
        blockedCampaignAdapter = new BlockedCampaignAdapter(mContext, campaigns);
        LinearLayoutManager linearHorizontal = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        recyler_view.setLayoutManager(linearHorizontal);
        recyler_view.setAdapter(blockedCampaignAdapter);
        recyler_view.setHasFixedSize(true);
    }

    private void getLayoutInitUI(View view) {
        recyler_view = view.findViewById(R.id.recyler_view);
        no_campaign_found = view.findViewById(R.id.no_campaign_found);
    }
}
