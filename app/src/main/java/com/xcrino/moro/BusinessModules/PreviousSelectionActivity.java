package com.xcrino.moro.BusinessModules;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xcrino.moro.Adapter.PreviousSelectionAdapter;
import com.xcrino.moro.Interface.ApiInterface;
import com.xcrino.moro.PojoModels.Campaign;
import com.xcrino.moro.PojoModels.CampaignResponseModel;
import com.xcrino.moro.R;
import com.xcrino.moro.Utilities.ApiClients;
import com.xcrino.moro.Utilities.AppPreferencesShared;
import com.xcrino.moro.Utilities.NetworkStatus;

import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PreviousSelectionActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView toolbar_title;
    private ImageView back_arrow;
    private Context mContext;
    private AppPreferencesShared appPreferencesShared;
    private RecyclerView apc_recyclerview;
    private PreviousSelectionAdapter previousSelectionAdapter;
    private List<Campaign> campaigns;
    private TextView no_previous_campaign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appPreferencesShared = new AppPreferencesShared(this);
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
        setContentView(R.layout.activity_previous_selection);
        getInitUi();
        mContext = this;

        /*LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        apc_recyclerview.setLayoutManager(linearLayoutManager);

        String[] campaign_mode = {"Image Mode", "Video Mode", "Mix Mode", "Video Mode", "Video Image Mode"};
        String[] campaign_date = {"20mar2020", "20april2020", "20april2020", "20april2020", "20april2020"};

        SelectionAdapter selectionAdapter = new SelectionAdapter(mContext, campaign_mode, campaign_date);
        apc_recyclerview.setAdapter(selectionAdapter);*/

        toolbar_title.setText("Select From  Previous Campaign");
        back_arrow.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (NetworkStatus.isNetworkAvailable(mContext)) {
            getCampaignByUser();
        } else {
            Toast.makeText(mContext, "Please Check Your Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }

    private void getCampaignByUser() {
        ProgressDialog progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        ApiInterface apiInterface = ApiClients.getClient().create(ApiInterface.class);
        Call<CampaignResponseModel> campaignResponseModelCall = apiInterface.getCampaignByUser("Business-module-api/get-campaign-by-user/"
                + appPreferencesShared.getUserId());
        campaignResponseModelCall.enqueue(new Callback<CampaignResponseModel>() {
            @Override
            public void onResponse(Call<CampaignResponseModel> call, Response<CampaignResponseModel> response) {
                try {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    if (response.isSuccessful()) {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        } if (response.body().getCampaigns()!=null){
                            apc_recyclerview.setVisibility(View.VISIBLE);
                            no_previous_campaign.setVisibility(View.GONE);
                            campaigns = response.body().getCampaigns();
                            setPreviousSelectionDataLayout();
                        } else {
                            apc_recyclerview.setVisibility(View.GONE);
                            no_previous_campaign.setVisibility(View.VISIBLE);
                            no_previous_campaign.setText("No Previous Campaigns Found");
                        }

//                        plan_list.setVisibility(View.VISIBLE);
//                        plan_listNotFound.setVisibility(View.GONE);

                    } else {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        Toast.makeText(mContext, "Something went wrong !", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setPreviousSelectionDataLayout() {
        previousSelectionAdapter = new PreviousSelectionAdapter(mContext, campaigns);
        LinearLayoutManager linearHorizontal = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        apc_recyclerview.setLayoutManager(linearHorizontal);
        apc_recyclerview.setAdapter(previousSelectionAdapter);
        apc_recyclerview.setHasFixedSize(true);

    }

    private void getInitUi() {
        apc_recyclerview = findViewById(R.id.apc_recyclerview);
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        back_arrow = (ImageView) findViewById(R.id.back_arrow);
        no_previous_campaign = findViewById(R.id.no_previous_campaign);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_arrow:
                onBackPressed();
                break;
        }
    }
}