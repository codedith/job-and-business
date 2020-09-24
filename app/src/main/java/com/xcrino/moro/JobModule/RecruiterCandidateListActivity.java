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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xcrino.moro.Adapter.CandidateAdapter;
import com.xcrino.moro.Interface.ApiInterface;
import com.xcrino.moro.PojoModels.ReceivedSavedJobsCandidates;
import com.xcrino.moro.PojoModels.ReceivedSavedJobsCandidatesList;
import com.xcrino.moro.R;
import com.xcrino.moro.Utilities.ApiClients;
import com.xcrino.moro.Utilities.AppPreferencesShared;
import com.xcrino.moro.Utilities.NetworkStatus;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecruiterCandidateListActivity extends AppCompatActivity implements View.OnClickListener {

    AppPreferencesShared appPreferencesShared;
    Context context;
    RecyclerView recycler_view;
    private TextView not_available, toolbar_title;
    private List<ReceivedSavedJobsCandidatesList> receivedSavedJobsCandidatesLists;
    private ImageView back_arrow;

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
        setContentView(R.layout.activity_recruiter_candidate_list);

        context = this;
        appPreferencesShared = new AppPreferencesShared(context);
        getUiLayoutInit();

        back_arrow.setOnClickListener(this);
        toolbar_title.setText("Recruiter Candidate List");

        if (NetworkStatus.isNetworkAvailable(context)) {
            getCandidateSearchedListBySearchButton();
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
                postCandidateSearchByRecruiterMethod();
            } else {
                Toast.makeText(context, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();
            }
        }
    };

    private void getUiLayoutInit() {
        recycler_view = findViewById(R.id.recycler_view);
        not_available = findViewById(R.id.not_available);
        back_arrow = (ImageView) findViewById(R.id.back_arrow);
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);

    }

    private void getCandidateSearchedListBySearchButton() {
        if (appPreferencesShared.getSearchedList() != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<ReceivedSavedJobsCandidatesList>>() {
            }.getType();
            receivedSavedJobsCandidatesLists = gson.fromJson(appPreferencesShared.getSearchedList(), type);

            if (receivedSavedJobsCandidatesLists != null) {
                recycler_view.setLayoutManager(new LinearLayoutManager(context));
                recycler_view.setAdapter(new CandidateAdapter(context, receivedSavedJobsCandidatesLists));
                recycler_view.setHasFixedSize(true);
                recycler_view.setVisibility(View.VISIBLE);
                not_available.setVisibility(View.GONE);
            } else {
                recycler_view.setVisibility(View.GONE);
                not_available.setVisibility(View.VISIBLE);
                not_available.setText("No Candidates Found");
            }
        } else {
            recycler_view.setVisibility(View.GONE);
            not_available.setVisibility(View.VISIBLE);
            not_available.setText("No Candidates Found");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_arrow:
                onBackPressed();
                break;
        }
    }

    private void postCandidateSearchByRecruiterMethod() {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        String[] location = appPreferencesShared.getRecentSearchLocation().split(",");
        String countryName = location[0];
        String stateName = location[1];

        ApiInterface apiInterface = ApiClients.getClient().create(ApiInterface.class);
        Call<ReceivedSavedJobsCandidates> postCandidateSearchByRecruiterMethod = apiInterface.postCandidateSearchByRecruiterMethod("Mogo_api/search_candidates/" +
                appPreferencesShared.getUserId().toString(), appPreferencesShared.getRecentSearchName(), countryName, stateName);
        postCandidateSearchByRecruiterMethod.enqueue(new Callback<ReceivedSavedJobsCandidates>() {
            @Override
            public void onResponse(Call<ReceivedSavedJobsCandidates> call, Response<ReceivedSavedJobsCandidates> response) {
                try {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            if (response.body().getReceivedSavedJobsCandidatesLists() != null) {
                                Gson gson = new Gson();
                                String json = gson.toJson(response.body().getReceivedSavedJobsCandidatesLists());
                                appPreferencesShared.setSearchedList(json);

                                Intent intent = new Intent(context, RecruiterCandidateListActivity.class);
                                startActivity(intent);

                                if (NetworkStatus.isNetworkAvailable(context)) {
                                    getCandidateSearchedListBySearchButton();
                                } else {
                                    recycler_view.setVisibility(View.GONE);
                                    not_available.setVisibility(View.VISIBLE);
                                    not_available.setText("Please Check Your Internet Connection");
                                }
                            }
                        }
                    } else {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        Toast.makeText(context, "Something went wrong !", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    Toast.makeText(context, "Something went wrong !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ReceivedSavedJobsCandidates> call, Throwable t) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                Toast.makeText(context, "Something went wrong !", Toast.LENGTH_SHORT).show();

            }
        });
    }
}