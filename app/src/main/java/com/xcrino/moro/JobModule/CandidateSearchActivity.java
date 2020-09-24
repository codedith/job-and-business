package com.xcrino.moro.JobModule;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.xcrino.moro.Adapter.CandidateIndustryAdapter;
import com.xcrino.moro.Interface.ApiInterface;
import com.xcrino.moro.PojoModels.DefaultData;
import com.xcrino.moro.PojoModels.ReceivedSavedJobsCandidates;
import com.xcrino.moro.PojoModels.StateListData;
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

public class CandidateSearchActivity extends AppCompatActivity implements View.OnClickListener {

    Context mContext;
    ImageView back_arrow;
    TextView toolbar_title, searched_job, searched_job_location;
    RecyclerView recycler_view;
    private AppPreferencesShared appPreferencesShared;
    LinearLayout ll_recent_search, ll_industry;
    Spinner country_spinner, searchField, state_spinner;
    String[] countryNameArray, skillsArray, statenameArray;
    private List<String> CountryIdList = new ArrayList<String>();
    String countryId, countryName, skill, stateName;
    ArrayList<String> industryNameArrayList = new ArrayList<>();
    Button search_button;
    CardView recent_search;

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
        setContentView(R.layout.activity_candidate_search);
        mContext = this;
        getLayoutInitUI();

        if (appPreferencesShared.getRecentSearchName() != null || appPreferencesShared.getRecentSearchLocation() != null) {
            ll_recent_search.setVisibility(View.VISIBLE);
            if (appPreferencesShared.getRecentSearchName() != null) {
                searched_job.setText(appPreferencesShared.getRecentSearchName().toString());
            } else {
                searched_job.setText("");
            }
            if (appPreferencesShared.getRecentSearchLocation() != null) {
                searched_job_location.setText(appPreferencesShared.getRecentSearchLocation());
            } else {
                searched_job_location.setText("");
            }
        } else {
            ll_recent_search.setVisibility(View.GONE);
        }
        toolbar_title.setText("Search");
        back_arrow.setOnClickListener(this);
        search_button.setOnClickListener(this);
        recent_search.setOnClickListener(this);
        appPreferencesShared.setSearchedList("");
    }

    private void getLayoutInitUI() {
        back_arrow = findViewById(R.id.back_arrow);
        toolbar_title = findViewById(R.id.toolbar_title);
        recycler_view = findViewById(R.id.recycler_view);
        ll_recent_search = findViewById(R.id.ll_recent_search);
        ll_industry = findViewById(R.id.ll_industry);
        country_spinner = findViewById(R.id.country_spinner);
        searchField = findViewById(R.id.searchField);
        state_spinner = findViewById(R.id.state_spinner);
        searched_job = findViewById(R.id.searched_job);
        searched_job_location = findViewById(R.id.searched_job_location);
        search_button = findViewById(R.id.search_button);
        recent_search = findViewById(R.id.recent_search);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_arrow:
                onBackPressed();
                break;

            case R.id.search_button:
                if (NetworkStatus.isNetworkAvailable(mContext)) {
                    postCandidateSearchByRecruiterMethod();
                } else {
                    Toast.makeText(mContext, "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.recent_search:
                skill = appPreferencesShared.getRecentSearchName();
                String[] location = appPreferencesShared.getRecentSearchLocation().split(",");
                countryName = location[0];
                stateName = location[1];
                if (NetworkStatus.isNetworkAvailable(mContext)) {
                    postCandidateSearchByRecruiterMethod();
                } else {
                    Toast.makeText(mContext, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();
                }
                break;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (NetworkStatus.isNetworkAvailable(mContext)) {
            getDefaultData();
        } else {
            Toast.makeText(mContext, "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    private void getDefaultData() {
        ProgressDialog progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        ApiInterface apiInterface = ApiClients.getClient().create(ApiInterface.class);
        Call<DefaultData> defaultDataCall = apiInterface.getDefaultDataMethod();

        defaultDataCall.enqueue(new Callback<DefaultData>() {
            @Override
            public void onResponse(Call<DefaultData> call, Response<DefaultData> response) {
                try {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            if (response.body().getData() != null) {
                                if (response.body().getData().getCountryList() != null) {
                                    countryNameArray = new String[response.body().getData().getCountryList().size()];
                                    for (int i = 0; i < response.body().getData().getCountryList().size(); i++) {
                                        CountryIdList.add(response.body().getData().getCountryList().get(i).getId());
                                        countryNameArray[i] = response.body().getData().getCountryList().get(i).getName();
                                    }
                                    ArrayAdapter<String> countryAdapter = new ArrayAdapter<>(mContext, R.layout.spinner_item, countryNameArray);
                                    country_spinner.setAdapter(countryAdapter);
                                    country_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                            countryId = CountryIdList.get(i);
                                            countryName = countryNameArray[i];
                                            getStatesListMethod();
                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> adapterView) {

                                        }
                                    });
                                }
                                if (response.body().getData().getIndustryList() != null) {
                                    ll_industry.setVisibility(View.VISIBLE);
                                    if (!industryNameArrayList.isEmpty()) {
                                        industryNameArrayList.clear();
                                    }
                                    for (int i = 0; i < response.body().getData().getIndustryList().size(); i++) {
                                        industryNameArrayList.add(response.body().getData().getIndustryList().get(i).getIndustryName());
                                    }
                                    recycler_view.setHasFixedSize(true);
                                    recycler_view.setLayoutManager(new GridLayoutManager(mContext, 2));
                                    recycler_view.setAdapter(new CandidateIndustryAdapter(mContext, industryNameArrayList));
                                } else {
                                    ll_industry.setVisibility(View.GONE);
                                }

                                if (response.body().getData().getSkillsList() != null) {
                                    skillsArray = new String[response.body().getData().getSkillsList().size()];
                                    for (int i = 0; i < response.body().getData().getSkillsList().size(); i++) {
                                        skillsArray[i] = response.body().getData().getSkillsList().get(i).getSkillsName();
                                    }
                                    ArrayAdapter<String> countryAdapter = new ArrayAdapter<>(mContext, R.layout.spinner_item, skillsArray);
                                    searchField.setAdapter(countryAdapter);
                                    searchField.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                            skill = skillsArray[i];
                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> adapterView) {

                                        }
                                    });
                                }
                            }
                        }
                    } else {
                        Toast.makeText(mContext, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    Toast.makeText(getApplicationContext(), "something went wrong", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<DefaultData> call, Throwable t) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                Toast.makeText(getApplicationContext(), "something went wrong", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getStatesListMethod() {
        ProgressDialog progressDialogstate = new ProgressDialog(mContext);
        progressDialogstate.setMessage("Please Wait...");
        progressDialogstate.setCancelable(false);
        progressDialogstate.show();
        ApiInterface apiInterface = ApiClients.getClient().create(ApiInterface.class);
        Call<StateListData> stateListDataCall = apiInterface.getStatesListMethod(countryId);

        stateListDataCall.enqueue(new Callback<StateListData>() {
            @Override
            public void onResponse(Call<StateListData> call, Response<StateListData> response) {
                try {
                    if (progressDialogstate.isShowing()) {
                        progressDialogstate.dismiss();
                    }
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            if (response.body().getStateLists() != null) {
                                statenameArray = new String[response.body().getStateLists().size()];
                                for (int i = 0; i < response.body().getStateLists().size(); i++) {
                                    statenameArray[i] = response.body().getStateLists().get(i).getName();
                                }
                                ArrayAdapter<String> countryAdapter = new ArrayAdapter<>(mContext, R.layout.spinner_item, statenameArray);
                                state_spinner.setAdapter(countryAdapter);
                                state_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        stateName = statenameArray[i];
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                    }
                                });
                            }
                        }
                    } else {
                        Toast.makeText(mContext, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    if (progressDialogstate.isShowing()) {
                        progressDialogstate.dismiss();
                    }
                    Toast.makeText(mContext, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<StateListData> call, Throwable t) {
                if (progressDialogstate.isShowing()) {
                    progressDialogstate.dismiss();
                }
                Toast.makeText(mContext, "Something went wrong", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void postCandidateSearchByRecruiterMethod() {
        ProgressDialog progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        ApiInterface apiInterface = ApiClients.getClient().create(ApiInterface.class);
        Call<ReceivedSavedJobsCandidates> postCandidateSearchByRecruiterMethod = apiInterface.postCandidateSearchByRecruiterMethod("Mogo_api/search_candidates/" + appPreferencesShared.getUserId().toString(), skill, countryName, stateName);
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
                                appPreferencesShared.setPageDirection("Candidate Search By Search Button");
                                appPreferencesShared.setRecentSearchName(skill);
                                appPreferencesShared.setRecentSearchLocation(countryName + ", " + stateName);
                                Intent intent = new Intent(mContext, RecruiterCandidateListActivity.class);
                                startActivity(intent);
                            }
                        }
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
                    Toast.makeText(mContext, "Something went wrong !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ReceivedSavedJobsCandidates> call, Throwable t) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                Toast.makeText(mContext, "Something went wrong !", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
