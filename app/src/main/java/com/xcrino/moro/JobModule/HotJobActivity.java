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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xcrino.moro.Adapter.JobAdapter;
import com.xcrino.moro.Interface.ApiInterface;
import com.xcrino.moro.PojoModels.GetSavedJobListModel;
import com.xcrino.moro.PojoModels.PostedJobsList;
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

public class HotJobActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView toolbar_title, not_available;
    private ImageView back_arrow;
    private Context context;
    RecyclerView recycler_view;
    private AppPreferencesShared appPreferencesShared;
    private List<GetSavedJobListModel> postedJobsListData = new ArrayList<>();
    private JobAdapter jobAdapter;
    private RelativeLayout available;

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
        setContentView(R.layout.activity_hot_job);
        context = this;
        getLayoutUiId();
        toolbar_title.setText("Jobs");
        back_arrow.setOnClickListener(this);
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("custom-message"));
    }

    private void getLayoutUiId() {
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        back_arrow = (ImageView) findViewById(R.id.back_arrow);
        recycler_view = findViewById(R.id.recycler_view);
        available = (RelativeLayout) findViewById(R.id.available);
        not_available = (TextView) findViewById(R.id.not_available);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_arrow:
                onBackPressed();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (NetworkStatus.isNetworkAvailable(context)) {
            getJobsListByCandidateLocationMethod();
        } else {
            Toast.makeText(getApplicationContext(), "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
            available.setVisibility(View.GONE);
            not_available.setVisibility(View.VISIBLE);
            not_available.setText("Please Check your Internet Connection");
        }
    }

    private void getJobsListByCandidateLocationMethod() {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        ApiInterface apiInterface = ApiClients.getClient().create(ApiInterface.class);
        Call<PostedJobsList> joblistbyCandidateLocation = apiInterface.getJobsListByCandidateLocationMethod("Mogo_api/get_job_list_by_candidate_location/" + appPreferencesShared.getUserId().toString());
        joblistbyCandidateLocation.enqueue(new Callback<PostedJobsList>() {
            @Override
            public void onResponse(Call<PostedJobsList> call, Response<PostedJobsList> response) {
                try {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            if (response.body().getData() != null) {
                                postedJobsListData = response.body().getData();
                                recycler_view.setVisibility(View.VISIBLE);
                                available.setVisibility(View.VISIBLE);
                                not_available.setVisibility(View.GONE);
                                recycler_view.setLayoutManager(new LinearLayoutManager(context));
                                recycler_view.setHasFixedSize(true);
                                recycler_view.setAdapter(new JobAdapter(context, postedJobsListData));

                            } else {
                                if (progressDialog.isShowing()) {
                                    progressDialog.dismiss();
                                }
                                recycler_view.setVisibility(View.GONE);
                                available.setVisibility(View.GONE);
                                not_available.setVisibility(View.VISIBLE);
                                not_available.setText("No Jobs Found");
                            }
                        }
                    } else {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        recycler_view.setVisibility(View.GONE);
                        available.setVisibility(View.GONE);
                        not_available.setVisibility(View.VISIBLE);
                        not_available.setText("No Jobs Foundd");
                    }
                } catch (Exception e) {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    Toast.makeText(getApplicationContext(), "Something Went Wrong", Toast.LENGTH_SHORT).show();
                    available.setVisibility(View.GONE);
                    not_available.setVisibility(View.VISIBLE);
                    not_available.setText("No Jobs Found");
                }
            }

            @Override
            public void onFailure(Call<PostedJobsList> call, Throwable t) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }

                Toast.makeText(getApplicationContext(), "Something Went Wrong", Toast.LENGTH_SHORT).show();
                available.setVisibility(View.GONE);
                not_available.setVisibility(View.VISIBLE);
                not_available.setText("No Jobs Found");
            }
        });


    }

    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (!postedJobsListData.isEmpty()) {
                postedJobsListData.clear();
            }
            if (NetworkStatus.isNetworkAvailable(context)) {
                getJobsListByCandidateLocationMethod();
            } else {
                Toast.makeText(getApplicationContext(), "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
                available.setVisibility(View.GONE);
                not_available.setVisibility(View.VISIBLE);
                not_available.setText("Please Check your Internet Connection");
            }
        }
    };
}
