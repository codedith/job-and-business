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

public class EmployeeSavedJobActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView toolbar_title, employeesavedjobactivity_notavailable;
    private ImageView back_arrow;
    private Context context;
    RecyclerView recycler_view;
    private AppPreferencesShared appPreferencesShared;
    List<GetSavedJobListModel> savedJobsList = new ArrayList<>();
    private RelativeLayout employeesavedjobactivity_available;


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
        setContentView(R.layout.activity_employee_saved_job);
        context = this;

        getLayoutUiId();

        toolbar_title.setText("Saved Jobs");

        back_arrow.setOnClickListener(this);

        if (NetworkStatus.isNetworkAvailable(context)) {
            getSavedJobList();
        } else {
            Toast.makeText(context, "Please Check Internet Connection", Toast.LENGTH_SHORT).show();
            employeesavedjobactivity_available.setVisibility(View.GONE);
            employeesavedjobactivity_notavailable.setVisibility(View.VISIBLE);
            employeesavedjobactivity_notavailable.setText("Please Check Your Internet Connection");
        }

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("custom-message"));
    }

    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            String ItemName = intent.getStringExtra("response");
            if (!savedJobsList.isEmpty()) {
                savedJobsList.clear();
            }
            if (NetworkStatus.isNetworkAvailable(context)) {
                getSavedJobList();
            } else {
                Toast.makeText(context, "Please Check Internet Connection", Toast.LENGTH_SHORT).show();
                employeesavedjobactivity_available.setVisibility(View.GONE);
                employeesavedjobactivity_notavailable.setVisibility(View.VISIBLE);
                employeesavedjobactivity_notavailable.setText("Please Check Your Internet Connection");
            }
        }
    };

    private void getLayoutUiId() {
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        back_arrow = (ImageView) findViewById(R.id.back_arrow);
        recycler_view = findViewById(R.id.recycler_view);
        employeesavedjobactivity_notavailable = (TextView) findViewById(R.id.employeesavedjobactivity_notavailable);
        employeesavedjobactivity_available = (RelativeLayout) findViewById(R.id.employeesavedjobactivity_available);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_arrow:
                onBackPressed();
                break;

        }
    }

    private void getSavedJobList() {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        ApiInterface apiInterface = ApiClients.getClient().create(ApiInterface.class);
        Call<PostedJobsList> call = apiInterface.getSavedJoblist("Mogo_api/get_saved_job_list/" + appPreferencesShared.getUserId());
        call.enqueue(new Callback<PostedJobsList>() {
            @Override
            public void onResponse(Call<PostedJobsList> call, Response<PostedJobsList> response) {
                try {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            if (response.body().getData() != null) {
                                savedJobsList = response.body().getData();
                                recycler_view.setLayoutManager(new LinearLayoutManager(context));
                                recycler_view.setHasFixedSize(true);
                                recycler_view.setAdapter(new JobAdapter(context, savedJobsList));

                                employeesavedjobactivity_available.setVisibility(View.VISIBLE);
                                employeesavedjobactivity_notavailable.setVisibility(View.GONE);

                            } else {
                                if (progressDialog.isShowing()){
                                    progressDialog.dismiss();
                                }
                                employeesavedjobactivity_available.setVisibility(View.GONE);
                                employeesavedjobactivity_notavailable.setVisibility(View.VISIBLE);
                                employeesavedjobactivity_notavailable.setText("No Saved Job Found");
                            }
                        } else {
                            if (progressDialog.isShowing()){
                                progressDialog.dismiss();
                            }
                            employeesavedjobactivity_available.setVisibility(View.GONE);
                            employeesavedjobactivity_notavailable.setVisibility(View.VISIBLE);
                            employeesavedjobactivity_notavailable.setText("No Saved Job Found");
                        }
                    }

                } catch (Exception e) {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    employeesavedjobactivity_available.setVisibility(View.GONE);
                    employeesavedjobactivity_notavailable.setVisibility(View.VISIBLE);
                    employeesavedjobactivity_notavailable.setText("No Saved Job Found");

                }
            }

            @Override
            public void onFailure(Call<PostedJobsList> call, Throwable t) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                employeesavedjobactivity_available.setVisibility(View.GONE);
                employeesavedjobactivity_notavailable.setVisibility(View.VISIBLE);
                employeesavedjobactivity_notavailable.setText("No Saved Job Found");
            }
        });
    }

}
