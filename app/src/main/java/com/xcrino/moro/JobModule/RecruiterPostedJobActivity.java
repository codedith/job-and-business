package com.xcrino.moro.JobModule;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xcrino.moro.Adapter.JobAppliedAdapter;
import com.xcrino.moro.Interface.ApiInterface;
import com.xcrino.moro.PojoModels.GetSavedJobListModel;
import com.xcrino.moro.PojoModels.PostedJobsList;
import com.xcrino.moro.R;
import com.xcrino.moro.Utilities.ApiClients;
import com.xcrino.moro.Utilities.AppPreferencesShared;
import com.xcrino.moro.Utilities.NetworkStatus;

import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecruiterPostedJobActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView toolbar_title, not_available;
    private ImageView back_arrow;
    private Context context;
    RecyclerView recycler_view;
    private AppPreferencesShared appPreferencesShared;
    private List<GetSavedJobListModel> postedJobsListData;
    private JobAppliedAdapter jobAppliedAdapter;

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
        setContentView(R.layout.activity_recruiter_posted_job);
        context = this;

        getLayoutUiId();

        toolbar_title.setText("Posted Jobs");

        if (NetworkStatus.isNetworkAvailable(context)) {
            getPostedJobsListMethod();
        } else {
            Toast.makeText(context, "Please Check Your Internet Connection !", Toast.LENGTH_SHORT).show();
            recycler_view.setVisibility(View.GONE);
            not_available.setVisibility(View.VISIBLE);
            not_available.setText("Please Check Your Internet Connection");
        }
        back_arrow.setOnClickListener(this);

    }

    private void getPostedJobsListMethod() {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        ApiInterface apiInterface = ApiClients.getClient().create(ApiInterface.class);
        Call<PostedJobsList> postedJobsListCall = apiInterface.getPostedJobsListMethod("Mogo_api/get_job_list_by_recruiter/" + "2/" + appPreferencesShared.getUserId());

        postedJobsListCall.enqueue(new Callback<PostedJobsList>() {
            @Override
            public void onResponse(Call<PostedJobsList> call, Response<PostedJobsList> response) {
                try {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    if (response.isSuccessful()) {
                        if (response.body().getData() != null) {
                            postedJobsListData = response.body().getData();
                            recycler_view.setVisibility(View.VISIBLE);
                            not_available.setVisibility(View.GONE);
                            setPostedJobsListDataLayout();
                        } else {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                            recycler_view.setVisibility(View.GONE);
                            not_available.setVisibility(View.VISIBLE);
                            not_available.setText("No Jobs Posted Yet");
                        }
                    } else {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        recycler_view.setVisibility(View.GONE);
                        not_available.setVisibility(View.VISIBLE);
                        not_available.setText("No Jobs Posted Yet");
                    }
                } catch (Exception e) {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    e.getMessage();
                    recycler_view.setVisibility(View.GONE);
                    not_available.setVisibility(View.VISIBLE);
                    not_available.setText("No Jobs Posted Yet");
                }
            }

            @Override
            public void onFailure(Call<PostedJobsList> call, Throwable t) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                recycler_view.setVisibility(View.GONE);
                not_available.setVisibility(View.VISIBLE);
                not_available.setText("No Jobs Posted Yet");

            }
        });
    }

    private void setPostedJobsListDataLayout() {
        jobAppliedAdapter = new JobAppliedAdapter(context, postedJobsListData);
        LinearLayoutManager linearHorizontal = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recycler_view.setLayoutManager(linearHorizontal);
        recycler_view.setAdapter(jobAppliedAdapter);
        recycler_view.setHasFixedSize(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit:
                Intent intent = new Intent(context, RecruiterAddJobActivity.class);
                appPreferencesShared.setRecruiterAddUpdateJob(true);
                startActivity(intent);
                break;
        }
        return true;
    }

    private void getLayoutUiId() {
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        back_arrow = (ImageView) findViewById(R.id.back_arrow);
        recycler_view = findViewById(R.id.recycler_view);
        not_available = findViewById(R.id.not_available);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_arrow:
                onBackPressed();
                break;
        }
    }
}