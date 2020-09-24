package com.xcrino.moro.JobModule;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.xcrino.moro.Adapter.CandidateAdapter;
import com.xcrino.moro.Interface.ApiInterface;
import com.xcrino.moro.PojoModels.ReceivedSavedJobsCandidates;
import com.xcrino.moro.PojoModels.ReceivedSavedJobsCandidatesList;
import com.xcrino.moro.R;
import com.xcrino.moro.Utilities.ApiClients;
import com.xcrino.moro.Utilities.AppPreferencesShared;
import com.xcrino.moro.Utilities.NetworkStatus;

import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CandidateSearchByIndustry extends AppCompatActivity implements View.OnClickListener {

    AppPreferencesShared appPreferencesShared;
    Context context;
    RecyclerView recycler_view;
    private TextView not_available, toolbar_title;
    private List<ReceivedSavedJobsCandidatesList> receivedSavedJobsCandidatesLists;
    private ImageView back_arrow;
    CandidateAdapter candidateAdapter;

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
        setContentView(R.layout.activity_candidate_search_by_industry);
        context = this;
        appPreferencesShared = new AppPreferencesShared(context);
        getUiLayoutInit();
        back_arrow.setOnClickListener(this);
        toolbar_title.setText("Recruiter Candidate List");

        if (NetworkStatus.isNetworkAvailable(context)) {
            postCandidateSearchByIndustry();
        } else {
            recycler_view.setVisibility(View.GONE);
            not_available.setVisibility(View.VISIBLE);
            not_available.setText("Please Check Your Internet Connection");
        }

        LocalBroadcastManager.getInstance(context).registerReceiver(mMessageReceiver,
                new IntentFilter("custom-message"));
    }

    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String ItemName = intent.getStringExtra("response");
            if (!receivedSavedJobsCandidatesLists.isEmpty()) {
                receivedSavedJobsCandidatesLists.clear();
            }
            if (NetworkStatus.isNetworkAvailable(context)) {
                postCandidateSearchByIndustry();
            } else {
                recycler_view.setVisibility(View.GONE);
                not_available.setVisibility(View.VISIBLE);
                not_available.setText("Please Check Your Internet Connection");
            }
        }
    };

    private void getUiLayoutInit() {
        recycler_view = findViewById(R.id.recycler_view);
        not_available = findViewById(R.id.not_available);
        back_arrow = (ImageView) findViewById(R.id.back_arrow);
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_arrow:
                onBackPressed();
                break;
        }
    }

    private void postCandidateSearchByIndustry() {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        ApiInterface apiInterface = ApiClients.getClient().create(ApiInterface.class);
        Call<ReceivedSavedJobsCandidates> postCandidateSearchByIndustry = apiInterface.postCandidateSearchByIndustry("Mogo_api/search_candidate_by_industry/" + appPreferencesShared.getUserId(), appPreferencesShared.getIndustryName().toString());
        postCandidateSearchByIndustry.enqueue(new Callback<ReceivedSavedJobsCandidates>() {
            @Override
            public void onResponse(Call<ReceivedSavedJobsCandidates> call, Response<ReceivedSavedJobsCandidates> response) {
                try {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            if (response.body().getReceivedSavedJobsCandidatesLists() != null) {
                                receivedSavedJobsCandidatesLists = response.body().getReceivedSavedJobsCandidatesLists();
                                recycler_view.setVisibility(View.VISIBLE);
                                not_available.setVisibility(View.GONE);
                                recycler_view.setLayoutManager(new LinearLayoutManager(context));
                                recycler_view.setAdapter(new CandidateAdapter(context, receivedSavedJobsCandidatesLists));
                                recycler_view.setHasFixedSize(true);
                            }
                        } else {
                            recycler_view.setVisibility(View.GONE);
                            not_available.setVisibility(View.VISIBLE);
                            not_available.setText("No Candidates Found");
                        }
                    } else {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        recycler_view.setVisibility(View.GONE);
                        not_available.setVisibility(View.VISIBLE);
                        not_available.setText("No Candidates Found");
                    }

                } catch (Exception e) {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    e.getMessage();
                    recycler_view.setVisibility(View.GONE);
                    not_available.setVisibility(View.VISIBLE);
                    not_available.setText("No Candidates Found");
                }
            }

            @Override
            public void onFailure(Call<ReceivedSavedJobsCandidates> call, Throwable t) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                recycler_view.setVisibility(View.GONE);
                not_available.setVisibility(View.VISIBLE);
                not_available.setText("No Candidates Found");

            }
        });
    }
}
