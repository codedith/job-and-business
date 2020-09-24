//package com.xcrino.chatterapp.JobModule;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.app.AppCompatDelegate;
//import androidx.localbroadcastmanager.content.LocalBroadcastManager;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import android.app.ProgressDialog;
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.content.IntentFilter;
//import android.content.res.Configuration;
//import android.content.res.Resources;
//import android.os.Bundle;
//import android.util.DisplayMetrics;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.google.android.material.floatingactionbutton.FloatingActionButton;
//import com.google.gson.Gson;
//import com.google.gson.reflect.TypeToken;
//import com.xcrino.chatterapp.Adapter.JobAdapter;
//import com.xcrino.chatterapp.Interface.ApiInterface;
//import com.xcrino.chatterapp.PojoModels.GetSavedJobListModel;
//import com.xcrino.chatterapp.PojoModels.PostedJobsList;
//import com.xcrino.chatterapp.R;
//import com.xcrino.chatterapp.Utilities.ApiClients;
//import com.xcrino.chatterapp.Utilities.AppPreferencesShared;
//import com.xcrino.chatterapp.Utilities.NetworkStatus;
//
//import java.lang.reflect.Type;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Locale;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//public class EmployeeNewJobActivity extends AppCompatActivity implements View.OnClickListener {
//
//    private TextView toolbar_title, not_available;
//    private ImageView back_arrow;
//    private Context context;
//    RecyclerView recycler_view;
//    private AppPreferencesShared appPreferencesShared;
//    private List<GetSavedJobListModel> postedJobsListData = new ArrayList<>();
//    private JobAdapter jobAdapter;
//    private RelativeLayout available;
//    FloatingActionButton filter;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        appPreferencesShared = new AppPreferencesShared(this);
//        Locale myLocale = new Locale(appPreferencesShared.getLocale());
//        Resources res = getResources();
//        DisplayMetrics dm = res.getDisplayMetrics();
//        Configuration conf = res.getConfiguration();
//        conf.locale = myLocale;
//        res.updateConfiguration(conf, dm);
//        if (appPreferencesShared.getmDayNightMode()) {
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//        } else {
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//        }
//        setContentView(R.layout.activity_employee_new_job);
//        context = this;
//
//        getLayoutUiId();
//
//        if (appPreferencesShared.getPageDirection().equals("Apply Jobs")) {
//            filter.show();
//        }
//        else {
//            filter.hide();
//        }
//
//        toolbar_title.setText("Jobs");
//        back_arrow.setOnClickListener(this);
//        filter.setOnClickListener(this);
//    }
//
//    private void getLayoutUiId() {
//        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
//        back_arrow = (ImageView) findViewById(R.id.back_arrow);
//        recycler_view = findViewById(R.id.recycler_view);
//        available = (RelativeLayout) findViewById(R.id.available);
//        not_available = (TextView) findViewById(R.id.not_available);
//        filter = (FloatingActionButton) findViewById(R.id.filter);
//    }
//
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.back_arrow:
//                onBackPressed();
//                break;
//
//            case R.id.filter:
//                Intent intent = new Intent(context, FiltersActivity.class);
//                appPreferencesShared.setFilterDirection("Job Filter");
//                finish();
//                startActivity(intent);
//                break;
//        }
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        if (NetworkStatus.isNetworkAvailable(context)) {
//            switch (appPreferencesShared.getPageDirection()) {
//                case "Recommended Jobs":
//                    getJobsListByCandidateSkillsMethod();
//                    break;
//
//                case "Hot Jobs":
//                    getJobsListByCandidateLocationMethod();
//                    break;
//
//                case "Apply Jobs":
//                    if (appPreferencesShared.getFilterDirection().equals("Job Filter")) {
//                        getFilteredJobList(getIntent().getStringExtra("filterSkills"), getIntent().getStringExtra("filterCountry"),
//                                getIntent().getStringExtra("filterState"), getIntent().getStringExtra("filterQualification"));
//                    }
//                    else {
//                        appPreferencesShared.setFilterDirection("");
//                        getAllJobsListMethod();
//                    }
//                    break;
//
//                case "Job Search By Industry":
//                    getJobListByIndustry();
//                    break;
//
//                case "Job Search By Search Button":
//                    getJobSearchedListBySearchButton();
//                    break;
//            }
//        } else {
//            Toast.makeText(getApplicationContext(), "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
//            available.setVisibility(View.GONE);
//            not_available.setVisibility(View.VISIBLE);
//            not_available.setText("Please Check your Internet Connection");
//        }
//        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
//                new IntentFilter("custom-message"));
//    }
//
//    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            // Get extra data included in the Intent
//            String ItemName = intent.getStringExtra("response");
//            if (!postedJobsListData.isEmpty()) {
//                postedJobsListData.clear();
//            }
//            if (NetworkStatus.isNetworkAvailable(context)) {
//                switch (appPreferencesShared.getPageDirection()) {
//                    case "Recommended Jobs":
//                        getJobsListByCandidateSkillsMethod();
//                        break;
//
//                    case "Hot Jobs":
//                        getJobsListByCandidateLocationMethod();
//                        break;
//
//                    case "Apply Jobs":
//                        getAllJobsListMethod();
//                        break;
//
//                    case "Job Search By Industry":
//                        getJobListByIndustry();
//                        break;
//
//                    case "Job Search By Search Button":
//                        postJobSearchByCandidateMethod();
//                        break;
//                }
//            } else {
//                Toast.makeText(getApplicationContext(), "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
//                available.setVisibility(View.GONE);
//                not_available.setVisibility(View.VISIBLE);
//                not_available.setText("Please Check your Internet Connection");
//            }
//        }
//    };
//
//    private void getAllJobsListMethod() {
//        ProgressDialog progressDialog = new ProgressDialog(context);
//        progressDialog.setMessage("Please Wait...");
//        progressDialog.setCancelable(false);
//        progressDialog.show();
//
//        ApiInterface apiInterface = ApiClients.getClient().create(ApiInterface.class);
//        Call<PostedJobsList> postedJobsListCall = apiInterface.getAllJobsListMethod("Mogo_api/get_job_list_by_recruiter/" + "1/" + appPreferencesShared.getUserId());
//
//        postedJobsListCall.enqueue(new Callback<PostedJobsList>() {
//            @Override
//            public void onResponse(Call<PostedJobsList> call, Response<PostedJobsList> response) {
//                try {
//                    if (progressDialog.isShowing()) {
//                        progressDialog.dismiss();
//                    }
//                    if (response.isSuccessful()) {
//                        if (response.body().getData() != null) {
//                            postedJobsListData = response.body().getData();
//                            recycler_view.setVisibility(View.VISIBLE);
//                            available.setVisibility(View.VISIBLE);
//                            not_available.setVisibility(View.GONE);
//
//                            recycler_view.setLayoutManager(new LinearLayoutManager(context));
//                            recycler_view.setHasFixedSize(true);
//                            recycler_view.setAdapter(new JobAdapter(context, postedJobsListData));
//
//                        } else {
//                            if (progressDialog.isShowing()) {
//                                progressDialog.dismiss();
//                            }
//                            recycler_view.setVisibility(View.GONE);
//                            available.setVisibility(View.GONE);
//                            not_available.setVisibility(View.VISIBLE);
//                            not_available.setText("No Jobs Found");
//
//                        }
//                    } else {
//                        if (progressDialog.isShowing()) {
//                            progressDialog.dismiss();
//                        }
//                        recycler_view.setVisibility(View.GONE);
//                        available.setVisibility(View.GONE);
//                        not_available.setVisibility(View.VISIBLE);
//                        not_available.setText("No Jobs Found");
//
//                    }
//                } catch (Exception e) {
//                    if (progressDialog.isShowing()) {
//                        progressDialog.dismiss();
//                    }
//                    e.getMessage();
//                    available.setVisibility(View.GONE);
//                    not_available.setVisibility(View.VISIBLE);
//                    not_available.setText("No Jobs Found");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<PostedJobsList> call, Throwable t) {
//                if (progressDialog.isShowing()) {
//                    progressDialog.dismiss();
//                }
//                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
//                available.setVisibility(View.GONE);
//                not_available.setVisibility(View.VISIBLE);
//                not_available.setText("No Jobs Found");
//
//            }
//        });
//    }
//
//    private void getJobsListByCandidateSkillsMethod() {
//        ProgressDialog progressDialog = new ProgressDialog(context);
//        progressDialog.setMessage("Please Wait...");
//        progressDialog.setCancelable(false);
//        progressDialog.show();
//
//        ApiInterface apiInterface = ApiClients.getClient().create(ApiInterface.class);
//        Call<PostedJobsList> jobListcandidateSkill = apiInterface.getJobsListByCandidateSkillsMethod("Mogo_api/get_job_list_by_candidate_skills/" + appPreferencesShared.getUserId());
//        jobListcandidateSkill.enqueue(new Callback<PostedJobsList>() {
//            @Override
//            public void onResponse(Call<PostedJobsList> call, Response<PostedJobsList> response) {
//                try {
//                    if (progressDialog.isShowing()) {
//                        progressDialog.dismiss();
//                    }
//                    if (response.isSuccessful()) {
//                        if (response.body().getData() != null) {
//                            postedJobsListData = response.body().getData();
//                            recycler_view.setVisibility(View.VISIBLE);
//                            available.setVisibility(View.VISIBLE);
//                            not_available.setVisibility(View.GONE);
//                            recycler_view.setLayoutManager(new LinearLayoutManager(context));
//                            recycler_view.setHasFixedSize(true);
//                            recycler_view.setAdapter(new JobAdapter(context, postedJobsListData));
//
//
//                        } else {
//                            if (progressDialog.isShowing()) {
//                                progressDialog.dismiss();
//                            }
//                            recycler_view.setVisibility(View.GONE);
//                            available.setVisibility(View.GONE);
//                            not_available.setVisibility(View.VISIBLE);
//                            not_available.setText("No Jobs Found");
//                        }
//                    } else {
//                        if (progressDialog.isShowing()) {
//                            progressDialog.dismiss();
//                        }
//                        recycler_view.setVisibility(View.GONE);
//                        available.setVisibility(View.GONE);
//                        not_available.setVisibility(View.VISIBLE);
//                        not_available.setText("No Jobs Found");
//                    }
//                } catch (Exception e) {
//                    if (progressDialog.isShowing()) {
//                        progressDialog.dismiss();
//                    }
//                    Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
//                    available.setVisibility(View.GONE);
//                    not_available.setVisibility(View.VISIBLE);
//                    not_available.setText("No Jobs Found");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<PostedJobsList> call, Throwable t) {
//                if (progressDialog.isShowing()) {
//                    progressDialog.dismiss();
//                }
//                Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
//                available.setVisibility(View.GONE);
//                not_available.setVisibility(View.VISIBLE);
//                not_available.setText("No Jobs Found");
//            }
//        });
//
//    }
//
//    private void getJobsListByCandidateLocationMethod() {
//        ProgressDialog progressDialog = new ProgressDialog(context);
//        progressDialog.setMessage("Please Wait...");
//        progressDialog.setCancelable(false);
//        progressDialog.show();
//
//        ApiInterface apiInterface = ApiClients.getClient().create(ApiInterface.class);
//        Call<PostedJobsList> joblistbyCandidateLocation = apiInterface.getJobsListByCandidateLocationMethod("Mogo_api/get_job_list_by_candidate_location/" + appPreferencesShared.getUserId().toString());
//        joblistbyCandidateLocation.enqueue(new Callback<PostedJobsList>() {
//            @Override
//            public void onResponse(Call<PostedJobsList> call, Response<PostedJobsList> response) {
//                try {
//                    if (progressDialog.isShowing()) {
//                        progressDialog.dismiss();
//                    }
//                    if (response.isSuccessful()) {
//                        if (response.body() != null) {
//                            if (response.body().getData() != null) {
//                                postedJobsListData = response.body().getData();
//                                recycler_view.setVisibility(View.VISIBLE);
//                                available.setVisibility(View.VISIBLE);
//                                not_available.setVisibility(View.GONE);
//                                recycler_view.setLayoutManager(new LinearLayoutManager(context));
//                                recycler_view.setHasFixedSize(true);
//                                recycler_view.setAdapter(new JobAdapter(context, postedJobsListData));
//
//                            } else {
//                                if (progressDialog.isShowing()) {
//                                    progressDialog.dismiss();
//                                }
//                                recycler_view.setVisibility(View.GONE);
//                                available.setVisibility(View.GONE);
//                                not_available.setVisibility(View.VISIBLE);
//                                not_available.setText("No Jobs Found");
//                            }
//                        }
//                    } else {
//                        if (progressDialog.isShowing()) {
//                            progressDialog.dismiss();
//                        }
//                        recycler_view.setVisibility(View.GONE);
//                        available.setVisibility(View.GONE);
//                        not_available.setVisibility(View.VISIBLE);
//                        not_available.setText("No Jobs Foundd");
//                    }
//                } catch (Exception e) {
//                    if (progressDialog.isShowing()) {
//                        progressDialog.dismiss();
//                    }
//                    Toast.makeText(getApplicationContext(), "Something Went Wrong", Toast.LENGTH_SHORT).show();
//                    available.setVisibility(View.GONE);
//                    not_available.setVisibility(View.VISIBLE);
//                    not_available.setText("No Jobs Found");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<PostedJobsList> call, Throwable t) {
//                if (progressDialog.isShowing()) {
//                    progressDialog.dismiss();
//                }
//
//                Toast.makeText(getApplicationContext(), "Something Went Wrong", Toast.LENGTH_SHORT).show();
//                available.setVisibility(View.GONE);
//                not_available.setVisibility(View.VISIBLE);
//                not_available.setText("No Jobs Found");
//            }
//        });
//
//
//    }
//
//    private void getJobListByIndustry() {
//        ProgressDialog progressDialog = new ProgressDialog(context);
//        progressDialog.setMessage("Please Wait...");
//        progressDialog.setCancelable(false);
//        progressDialog.show();
//
//        ApiInterface apiInterface = ApiClients.getClient().create(ApiInterface.class);
//        Call<PostedJobsList> jobListByIndustry = apiInterface.getJobListByIndustry("Mogo_api/search_job_by_industry/" + appPreferencesShared.getUserId().toString(), appPreferencesShared.getIndustryName().toString());
//        jobListByIndustry.enqueue(new Callback<PostedJobsList>() {
//            @Override
//            public void onResponse(Call<PostedJobsList> call, Response<PostedJobsList> response) {
//                try {
//                    if (progressDialog.isShowing()) {
//                        progressDialog.dismiss();
//                    }
//                    if (response.isSuccessful()) {
//                        if (response.body() != null) {
//                            if (response.body().getData() != null) {
//                                postedJobsListData = response.body().getData();
//                                recycler_view.setVisibility(View.VISIBLE);
//                                available.setVisibility(View.VISIBLE);
//                                not_available.setVisibility(View.GONE);
//
//                                recycler_view.setLayoutManager(new LinearLayoutManager(context));
//                                recycler_view.setHasFixedSize(true);
//                                recycler_view.setAdapter(new JobAdapter(context, postedJobsListData));
//
//                            } else {
//                                if (progressDialog.isShowing()) {
//                                    progressDialog.dismiss();
//                                }
//                                recycler_view.setVisibility(View.GONE);
//                                available.setVisibility(View.GONE);
//                                not_available.setVisibility(View.VISIBLE);
//                                not_available.setText("No Jobs Found");
//                            }
//                        }
//                    } else {
//                        if (progressDialog.isShowing()) {
//                            progressDialog.dismiss();
//                        }
//                        recycler_view.setVisibility(View.GONE);
//                        available.setVisibility(View.GONE);
//                        not_available.setVisibility(View.VISIBLE);
//                        not_available.setText("No Jobs Found");
//                    }
//                } catch (Exception e) {
//                    if (progressDialog.isShowing()) {
//                        progressDialog.dismiss();
//                    }
//                    Toast.makeText(getApplicationContext(), "Something Went Wrong", Toast.LENGTH_SHORT).show();
//                    available.setVisibility(View.GONE);
//                    not_available.setVisibility(View.VISIBLE);
//                    not_available.setText("No Jobs Found");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<PostedJobsList> call, Throwable t) {
//                if (progressDialog.isShowing()) {
//                    progressDialog.dismiss();
//                }
//
//                Toast.makeText(getApplicationContext(), "Something Went Wrong", Toast.LENGTH_SHORT).show();
//                available.setVisibility(View.GONE);
//                not_available.setVisibility(View.VISIBLE);
//                not_available.setText("No Jobs Found");
//            }
//        });
//
//    }
//
//    private void getJobSearchedListBySearchButton() {
//        if (appPreferencesShared.getSearchedList() != null) {
//            Gson gson = new Gson();
//            Type type = new TypeToken<List<GetSavedJobListModel>>() {
//            }.getType();
//            postedJobsListData = gson.fromJson(appPreferencesShared.getSearchedList(), type);
//
//            if (postedJobsListData != null) {
//                recycler_view.setLayoutManager(new LinearLayoutManager(context));
//                recycler_view.setHasFixedSize(true);
//                recycler_view.setAdapter(new JobAdapter(context, postedJobsListData));
//                available.setVisibility(View.VISIBLE);
//                not_available.setVisibility(View.GONE);
//            }
//            else {
//                available.setVisibility(View.GONE);
//                not_available.setVisibility(View.VISIBLE);
//                not_available.setText("No Jobs Found");
//            }
//        } else {
//            available.setVisibility(View.GONE);
//            not_available.setVisibility(View.VISIBLE);
//            not_available.setText("No Jobs Found");
//        }
//
//    }
//
//    private void postJobSearchByCandidateMethod() {
//        ProgressDialog progressDialogstate = new ProgressDialog(context);
//        progressDialogstate.setMessage("Please Wait...");
//        progressDialogstate.setCancelable(false);
//        progressDialogstate.show();
//
//        String[] location = appPreferencesShared.getRecentSearchLocation().split(",");
//
//        ApiInterface apiInterface = ApiClients.getClient().create(ApiInterface.class);
//        Call<PostedJobsList> postJobSearchByCandidate = apiInterface.postJobSearchByCandidateMethod("Mogo_api/job_search_by_candidate/" +
//                appPreferencesShared.getUserId().toString(),appPreferencesShared.getRecentSearchName(), location[0].trim(), location[1].trim());
//        postJobSearchByCandidate.enqueue(new Callback<PostedJobsList>() {
//            @Override
//            public void onResponse(Call<PostedJobsList> call, Response<PostedJobsList> response) {
//                try {
//                    if (progressDialogstate.isShowing()) {
//                        progressDialogstate.dismiss();
//                    }
//                    if (response.isSuccessful()) {
//                        if (response.body() != null) {
//                            Gson gson = new Gson();
//                            String json = gson.toJson(response.body().getData());
//                            appPreferencesShared.setSearchedList(json);
//                            appPreferencesShared.setPageDirection("Job Search By Search Button");
//                            getJobSearchedListBySearchButton();
//                        }
//                    } else {
//                        Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
//                    }
//                } catch (Exception e) {
//                    if (progressDialogstate.isShowing()) {
//                        progressDialogstate.dismiss();
//                    }
//                    Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<PostedJobsList> call, Throwable t) {
//                if (progressDialogstate.isShowing()) {
//                    progressDialogstate.dismiss();
//                }
//                Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    private void getFilteredJobList(String skills, String country, String state, String qualification) {
//        ProgressDialog progressDialog = new ProgressDialog(context);
//        progressDialog.setMessage("Please Wait...");
//        progressDialog.setCancelable(false);
//        progressDialog.show();
//
//        ApiInterface apiInterface = ApiClients.getClient().create(ApiInterface.class);
//        Call<PostedJobsList> filteredJobs = apiInterface.postFilterJobList("Mogo_api/filter_jobs/1", skills, country, state, qualification);
//        filteredJobs.enqueue(new Callback<PostedJobsList>() {
//            @Override
//            public void onResponse(Call<PostedJobsList> call, Response<PostedJobsList> response) {
//                try {
//                    if (progressDialog.isShowing()) {
//                        progressDialog.dismiss();
//                    }
//                    if (response.isSuccessful()) {
//                        if (response.body() != null) {
//                            if (response.body().getData() != null) {
//                                postedJobsListData = response.body().getData();
//                                recycler_view.setVisibility(View.VISIBLE);
//                                available.setVisibility(View.VISIBLE);
//                                not_available.setVisibility(View.GONE);
//
//                                recycler_view.setLayoutManager(new LinearLayoutManager(context));
//                                recycler_view.setHasFixedSize(true);
//                                recycler_view.setAdapter(new JobAdapter(context, postedJobsListData));
//
//                            } else {
//                                if (progressDialog.isShowing()) {
//                                    progressDialog.dismiss();
//                                }
//                                recycler_view.setVisibility(View.GONE);
//                                available.setVisibility(View.GONE);
//                                not_available.setVisibility(View.VISIBLE);
//                                not_available.setText("No Jobs Found");
//                            }
//                        }
//                    } else {
//                        if (progressDialog.isShowing()) {
//                            progressDialog.dismiss();
//                        }
//                        recycler_view.setVisibility(View.GONE);
//                        available.setVisibility(View.GONE);
//                        not_available.setVisibility(View.VISIBLE);
//                        not_available.setText("No Jobs Found");
//                    }
//                } catch (Exception e) {
//                    if (progressDialog.isShowing()) {
//                        progressDialog.dismiss();
//                    }
//                    Toast.makeText(getApplicationContext(), "Something Went Wrong", Toast.LENGTH_SHORT).show();
//                    available.setVisibility(View.GONE);
//                    not_available.setVisibility(View.VISIBLE);
//                    not_available.setText("No Jobs Found");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<PostedJobsList> call, Throwable t) {
//                if (progressDialog.isShowing()) {
//                    progressDialog.dismiss();
//                }
//
//                Toast.makeText(getApplicationContext(), "Something Went Wrong", Toast.LENGTH_SHORT).show();
//                available.setVisibility(View.GONE);
//                not_available.setVisibility(View.VISIBLE);
//                not_available.setText("No Jobs Found");
//            }
//        });
//    }
//
//}
