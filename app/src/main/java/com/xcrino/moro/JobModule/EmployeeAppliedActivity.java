package com.xcrino.moro.JobModule;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xcrino.moro.Adapter.JobAppliedAdapter;
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

public class EmployeeAppliedActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView toolbar_title;
    private ImageView back_arrow;
    private Context context;
    RecyclerView recycler_view;
    private AppPreferencesShared appPreferencesShared;
    private List<PostedJobsList> postedJobsListData;
    private JobAppliedAdapter jobAppliedAdapter;
    List<GetSavedJobListModel> appliedjob = new ArrayList<>();
    private RelativeLayout employeeappliedactivity_available;
    private TextView employeeappliedactivity_notavailable;

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
        setContentView(R.layout.activity_employee_applied);
        context = this;

        getLayoutUiId();
        if (NetworkStatus.isNetworkAvailable(context)) {
            getAppliedJobs();
        } else {
            Toast.makeText(context, "Please Check Your Internet Connection !", Toast.LENGTH_SHORT).show();
            employeeappliedactivity_available.setVisibility(View.GONE);
            employeeappliedactivity_notavailable.setVisibility(View.VISIBLE);
            employeeappliedactivity_notavailable.setText("Please Check Your Internet Connection");
        }

        toolbar_title.setText("Applied Jobs");

        //recycler_view.setHasFixedSize(true);
        //recycler_view.setLayoutManager(new LinearLayoutManager(context));
        // recycler_view.setAdapter(new JobAppliedAdapter(context);

        back_arrow.setOnClickListener(this);
    }

    private void getLayoutUiId() {
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        back_arrow = (ImageView) findViewById(R.id.back_arrow);
        recycler_view = findViewById(R.id.recycler_view);
        employeeappliedactivity_available = findViewById(R.id.employeeappliedactivity_available);
        employeeappliedactivity_notavailable = findViewById(R.id.employeeappliedactivity_notavailable);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_arrow:
                onBackPressed();
                break;
        }
    }

    private void getAppliedJobs() {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        ApiInterface apiInterface = ApiClients.getClient().create(ApiInterface.class);
        Call<PostedJobsList> GetAppliedJobs = apiInterface.GetAppliedJobs("Mogo_api/get_applied_job_list/" + appPreferencesShared.getUserId()); //done by sangam plss review it  ("get_applied_job_list/" + "2")
        GetAppliedJobs.enqueue(new Callback<PostedJobsList>() {
            @Override
            public void onResponse(Call<PostedJobsList> call, Response<PostedJobsList> response) {
                try {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            if (response.body().getData() != null) {
                                appliedjob = response.body().getData();
                                employeeappliedactivity_available.setVisibility(View.VISIBLE);
                                employeeappliedactivity_notavailable.setVisibility(View.GONE);
                                recycler_view.setLayoutManager(new LinearLayoutManager(context));
                                recycler_view.setHasFixedSize(true);
                                recycler_view.setAdapter(new JobAppliedAdapter(context, appliedjob));

                            } else {
                                if (progressDialog.isShowing()) {
                                    progressDialog.dismiss();
                                }
                                employeeappliedactivity_available.setVisibility(View.GONE);
                                employeeappliedactivity_notavailable.setVisibility(View.VISIBLE);
                                employeeappliedactivity_notavailable.setText("No Applied Job Found");

                            }
                        } else {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                            employeeappliedactivity_available.setVisibility(View.GONE);
                            employeeappliedactivity_notavailable.setVisibility(View.VISIBLE);
                            employeeappliedactivity_notavailable.setText("No Applied Job Found");
                        }
                    } else {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        employeeappliedactivity_available.setVisibility(View.GONE);
                        employeeappliedactivity_notavailable.setVisibility(View.VISIBLE);
                        employeeappliedactivity_notavailable.setText("No Applied Job Found");
                    }
                } catch (Exception e) {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    } else {
                        Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                        employeeappliedactivity_available.setVisibility(View.GONE);
                        employeeappliedactivity_notavailable.setVisibility(View.VISIBLE);
                        employeeappliedactivity_notavailable.setText("No Applied Job Found");
                    }
                }
            }

            @Override
            public void onFailure(Call<PostedJobsList> call, Throwable t) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                Toast.makeText(getApplicationContext(), "Something Went Wrong", Toast.LENGTH_SHORT).show();
                employeeappliedactivity_available.setVisibility(View.GONE);
                employeeappliedactivity_notavailable.setVisibility(View.VISIBLE);
                employeeappliedactivity_notavailable.setText("Something Went Wrong");
            }
        });


    }

}
