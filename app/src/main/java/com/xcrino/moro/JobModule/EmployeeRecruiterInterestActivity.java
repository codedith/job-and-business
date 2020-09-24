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

import com.xcrino.moro.Adapter.EmployeeRecruiterInterestAdapter;
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

public class EmployeeRecruiterInterestActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView toolbar_title;
    private ImageView back_arrow;
    private Context context;
    RecyclerView recycler_view;
    private AppPreferencesShared appPreferencesShared;
    private List<GetSavedJobListModel> recruiterinterest =new ArrayList<GetSavedJobListModel>();
    private RelativeLayout employeerecruiterinterest_available;
    private TextView employeerecruiterinterest_notavailable;

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
        setContentView(R.layout.activity_employee_recruiter_interest);

        context = this;

        getLayoutUiId();
        if (NetworkStatus.isNetworkAvailable(context)) {

            getInterestRecruiters();
        }
        else {
            Toast.makeText(getApplicationContext(),"Check your Internet Connection",Toast.LENGTH_SHORT).show();
            employeerecruiterinterest_available.setVisibility(View.GONE);
            employeerecruiterinterest_notavailable.setVisibility(View.VISIBLE);
            employeerecruiterinterest_notavailable.setText("Check your Internet Connection");
        }

        toolbar_title.setText("Recruiter's Interest");



        back_arrow.setOnClickListener(this);
    }

    private void getLayoutUiId() {
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        back_arrow = (ImageView) findViewById(R.id.back_arrow);
        recycler_view = findViewById(R.id.recycler_view);
        employeerecruiterinterest_available  = (RelativeLayout)findViewById(R.id.employeerecruiterinterest_available);
        employeerecruiterinterest_notavailable = (TextView)findViewById(R.id.employeerecruiterinterest_notavailable);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_arrow:
                onBackPressed();
                break;
        }
    }

    private void getInterestRecruiters() {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        ApiInterface apiInterface = ApiClients.getClient().create(ApiInterface.class);
        Call<PostedJobsList> RecruiterInterest = apiInterface.getInterestRecruiters("Mogo_api/get_interest_recruiters_list/" + appPreferencesShared.getUserId());
        RecruiterInterest.enqueue(new Callback<PostedJobsList>() {
            @Override
            public void onResponse(Call<PostedJobsList> call, Response<PostedJobsList> response) {
                try {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    if (response.isSuccessful()) {
                        if (response.body().getData() != null) {
                            recruiterinterest = response.body().getData();
                            employeerecruiterinterest_available.setVisibility(View.VISIBLE);
                            employeerecruiterinterest_notavailable.setVisibility(View.GONE);
                            recycler_view.setVisibility(View.VISIBLE);
                            recycler_view.setHasFixedSize(true);
                            recycler_view.setLayoutManager(new LinearLayoutManager(context));
                            recycler_view.setAdapter(new EmployeeRecruiterInterestAdapter(context, recruiterinterest));
                        } else {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                            recycler_view.setVisibility(View.GONE);
                            employeerecruiterinterest_available.setVisibility(View.GONE);
                            employeerecruiterinterest_notavailable.setVisibility(View.VISIBLE);
                            employeerecruiterinterest_notavailable.setText("No Recruiter has Shown Interest in You");
                        }
                    } else {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        recycler_view.setVisibility(View.GONE);
                        employeerecruiterinterest_available.setVisibility(View.GONE);
                        employeerecruiterinterest_notavailable.setVisibility(View.VISIBLE);
                        employeerecruiterinterest_notavailable.setText("No Recruiter has Shown Interest in You");
                    }
                } catch (Exception e) {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                    employeerecruiterinterest_available.setVisibility(View.GONE);
                    employeerecruiterinterest_notavailable.setVisibility(View.VISIBLE);
                    employeerecruiterinterest_notavailable.setText("No Recruiter has Shown Interest in You");
                }
            }

            @Override
            public void onFailure(Call<PostedJobsList> call, Throwable t) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                Toast.makeText(getApplicationContext(), "Something Went Wrong", Toast.LENGTH_SHORT).show();
                employeerecruiterinterest_available.setVisibility(View.GONE);
                employeerecruiterinterest_notavailable.setVisibility(View.VISIBLE);
                employeerecruiterinterest_notavailable.setText("No Recruiter has Shown Interest in You");
            }
        });

    }

}
