package com.xcrino.moro.JobModule;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.xcrino.moro.Adapter.PostedJobListAdapter;
import com.xcrino.moro.Interface.ApiInterface;
import com.xcrino.moro.PojoModels.GetCompanyDetailsModel;
import com.xcrino.moro.PojoModels.JobDetails;
import com.xcrino.moro.R;
import com.xcrino.moro.Utilities.ApiClients;
import com.xcrino.moro.Utilities.AppPreferencesShared;
import com.xcrino.moro.Utilities.NetworkStatus;

import java.util.List;
import java.util.Locale;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecruiterProfileDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView toolbar_title;
    private ImageView back_arrow, company_img;
    private Context context;
    Toolbar toolbar;
    private AppPreferencesShared appPreferencesShared;
    TextView company_name, company_website, company_location, email_id, phone_number, about_company, address;
    LinearLayout posted_jobs_layout, candidate_info;
    private RecyclerView posted_jobs;

    private PostedJobListAdapter postedJobListAdapter;
    private List<JobDetails> jobList;
    private Button my_subscription;

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
        setContentView(R.layout.activity_recruiter_profile_details);

        getLayoutUiId();

        context = this;
        setSupportActionBar(toolbar);

        toolbar_title.setText("Company Profile");
        back_arrow.setOnClickListener(this);
        my_subscription.setOnClickListener(this);
        candidate_info.setOnClickListener(this);

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
                Intent intent = new Intent(context, RecruiterCompanyProfileActivity.class);
                appPreferencesShared.setCreateCompanyProfile(true);
                startActivity(intent);
                break;
        }
        return true;
    }

    private void getLayoutUiId() {
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        back_arrow = (ImageView) findViewById(R.id.back_arrow);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        company_name = findViewById(R.id.company_name);
        company_website = findViewById(R.id.company_website);
        company_location = findViewById(R.id.company_location);
        email_id = findViewById(R.id.email_id);
        phone_number = findViewById(R.id.phone_number);
        about_company = findViewById(R.id.about_company);
        address = findViewById(R.id.address);
        company_img = findViewById(R.id.company_img);
        posted_jobs_layout = findViewById(R.id.posted_jobs_layout);
        posted_jobs = findViewById(R.id.posted_jobs);
        my_subscription = findViewById(R.id.my_subscription);
        candidate_info = (LinearLayout) findViewById(R.id.candidate_info);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_arrow:
                onBackPressed();
                break;

            case R.id.my_subscription:
                Intent intent = new Intent(context, RecruiterSubscriptionWalletActivity.class);
                startActivity(intent);
                break;

            case R.id.candidate_info:
                break;
        }
    }

    private void getCompanyDetails() {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        ApiInterface apiInterface = ApiClients.getClient().create(ApiInterface.class);
        Call<GetCompanyDetailsModel> call = apiInterface.getCompanydetails("Mogo_api/get_company_profile/" + appPreferencesShared.getUserId());
        call.enqueue(new Callback<GetCompanyDetailsModel>() {
            @Override
            public void onResponse(Call<GetCompanyDetailsModel> call, Response<GetCompanyDetailsModel> response) {
                try {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            if (response.body().getData() != null) {
                                if (response.body().getData().getCompanyDetails() != null) {
                                    company_name.setText(response.body().getData().getCompanyDetails().getCompanyName());
                                    company_website.setText(response.body().getData().getCompanyDetails().getCompanyWebsite());
                                    company_location.setText(response.body().getData().getCompanyDetails().getState() + ", " +
                                            response.body().getData().getCompanyDetails().getCountry());
                                    email_id.setText(response.body().getData().getCompanyDetails().getCompanyEmail());
                                    phone_number.setText(response.body().getData().getCompanyDetails().getCompanyPhone());
                                    about_company.setText(response.body().getData().getCompanyDetails().getAboutCompany());
                                    address.setText(response.body().getData().getCompanyDetails().getCompanyAddress() + ", " +
                                            response.body().getData().getCompanyDetails().getState() + ", " +
                                            response.body().getData().getCompanyDetails().getCountry());
                                    if (response.body().getData().getCompanyDetails().getCompanyLogo() != null) {
                                        Picasso.with(context).load(response.body().getData().getCompanyDetails().getCompanyLogo()).
                                                resize(175, 175).transform(new CropCircleTransformation()).
                                                placeholder(R.drawable.user_dummy).into(company_img);
                                    }

                                } else {
                                    company_name.setText("");
                                    company_website.setText("");
                                    company_location.setText("");
                                    email_id.setText("");
                                    phone_number.setText("");
                                    about_company.setText("");
                                    address.setText("");
                                }
                                if (response.body().getData().getJobList() != null && !response.body().getData().getJobList().isEmpty()) {
                                    jobList = response.body().getData().getJobList();
                                    setDataPostedJobAdapterinLayout();
                                } else {
                                    posted_jobs_layout.setVisibility(View.GONE);
                                }

                                GetCompanyDetailsModel companyDetailsModel = response.body();
                                Gson gson = new Gson();
                                String json = gson.toJson(companyDetailsModel);
                                appPreferencesShared.setCompanyDetails(json);

                            } else {
                                Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetCompanyDetailsModel> call, Throwable t) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setDataPostedJobAdapterinLayout() {
        postedJobListAdapter = new PostedJobListAdapter(context, jobList);
        LinearLayoutManager linearHorizontal = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        posted_jobs.setLayoutManager(linearHorizontal);
        posted_jobs.setAdapter(postedJobListAdapter);
        posted_jobs.setHasFixedSize(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (NetworkStatus.isNetworkAvailable(context)) {
            getCompanyDetails();
        } else {
            Toast.makeText(context, "Please Check Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

}
