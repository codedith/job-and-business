package com.xcrino.moro.JobModule;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.xcrino.moro.Activity.DashboardActivity;
import com.xcrino.moro.Activity.SubscriptionPlanActivity;
import com.xcrino.moro.Interface.ApiInterface;
import com.xcrino.moro.PojoModels.CommonResponseModel;
import com.xcrino.moro.R;
import com.xcrino.moro.Utilities.ApiClients;
import com.xcrino.moro.Utilities.AppPreferencesShared;
import com.xcrino.moro.Utilities.ButtonVisibility;
import com.xcrino.moro.Utilities.NetworkStatus;
import com.xcrino.moro.Utilities.Validations;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecruiterEditDetailActivity extends AppCompatActivity implements View.OnClickListener {

    EditText recruiter_name, recruiter_contact_no, recruiter_company_name;
    Button submit_button;
    TextView toolbar_title, recruiter_mogo_contact_no;
    ImageView back_arrow;
    Context mContext;
    View recruiter_details;
    private AppPreferencesShared appPreferencesShared;
    private Validations validations;

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
        setContentView(R.layout.activity_recruiter_edit_detail);

        getLayoutInitUI();

        toolbar_title.setText("Recruiter Details");
        mContext = this;
        validations = new Validations();

        back_arrow.setOnClickListener(this);
        submit_button.setOnClickListener(this);

        recruiter_mogo_contact_no.setText(appPreferencesShared.getMobileNumber());

        ButtonVisibility buttonVisibility = new ButtonVisibility();
        buttonVisibility.hideButton(mContext, recruiter_details, submit_button);

    }

    private void getLayoutInitUI() {
        submit_button = findViewById(R.id.submit_button);
        recruiter_name = findViewById(R.id.recruiter_name);
        recruiter_contact_no = findViewById(R.id.recruiter_contact_no);
        recruiter_company_name = findViewById(R.id.recruiter_company_name);
        recruiter_mogo_contact_no = findViewById(R.id.recruiter_mogo_contact_no);
        back_arrow = findViewById(R.id.back_arrow);
        toolbar_title = findViewById(R.id.toolbar_title);
        recruiter_details = findViewById(R.id.recruiter_details);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.submit_button:
                if (validations.isMandatory(recruiter_name.getText().toString(), recruiter_name) &&
                        validations.isMandatory(recruiter_contact_no.getText().toString(), recruiter_contact_no) &&
                        validations.isMandatory(recruiter_company_name.getText().toString(), recruiter_company_name)) {

                    appPreferencesShared.setRecruiterName(recruiter_name.getText().toString());
                    appPreferencesShared.setRecruiterContactNo(recruiter_contact_no.getText().toString());
                    appPreferencesShared.setRecruiterCompanyName(recruiter_company_name.getText().toString());

                    if (appPreferencesShared.getRecruiterAddUpdateJob()) {
                        if (NetworkStatus.isNetworkAvailable(mContext)) {
                            try {
                                updateJobMethod("1", appPreferencesShared.getJobTitle(),
                                        appPreferencesShared.getJobVacancies(), appPreferencesShared.getJobExperience(),
                                        appPreferencesShared.getSalaryExpectation(), appPreferencesShared.getJobRole(),
                                        appPreferencesShared.getJobDescription(), appPreferencesShared.getEmployment(),
                                        appPreferencesShared.getIndustryId(), recruiter_name.getText().toString(),
                                        recruiter_contact_no.getText().toString(), recruiter_company_name.getText().toString(),
                                        appPreferencesShared.getMobileNumber(), appPreferencesShared.getSkillsSpinner(),
                                        appPreferencesShared.getStateSpinner(), "India"/*appPreferencesShared.getCountryId()*/,
                                        appPreferencesShared.getQualificationSpinner());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(mContext, "Please Check Your Internet Connection !", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        if (NetworkStatus.isNetworkAvailable(mContext)) {
                            try {
                                postJobMethod("1", appPreferencesShared.getJobTitle(),
                                        appPreferencesShared.getJobVacancies(), appPreferencesShared.getJobExperience(),
                                        appPreferencesShared.getSalaryExpectation(), appPreferencesShared.getJobRole(),
                                        appPreferencesShared.getJobDescription(), appPreferencesShared.getEmployment(),
                                        appPreferencesShared.getIndustryId(), recruiter_name.getText().toString(),
                                        recruiter_contact_no.getText().toString(), recruiter_company_name.getText().toString(),
                                        appPreferencesShared.getMobileNumber(), appPreferencesShared.getSkillsSpinner(),
                                        appPreferencesShared.getStateSpinner(), "India"/*appPreferencesShared.getCountryId()*/,
                                        appPreferencesShared.getQualificationSpinner());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(mContext, "Please Check Your Internet Connection !", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                break;

            case R.id.back_arrow:
                onBackPressed();
                break;

        }
    }

    private void updateJobMethod(String userId, String jobTitle, String jobVacancies, String jobExperience,
                                  String salaryExpectation, String jobRole, String jobDescription,
                                  String employment, String industryId, String recruiterName,
                                  String recruiterContact, String recruiterCompany, String mobileNumber,
                                  String skillsSpinner, String stateSpinner, String countryId,
                                  String qualificationSpinner) throws JSONException {

        ProgressDialog progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        JSONArray skillsSpinnerArray = new JSONArray(skillsSpinner);
        JSONArray stateSpinnerArray = new JSONArray(stateSpinner);
        JSONArray qualificationSpinnerArray = new JSONArray(qualificationSpinner);

        ApiInterface apiInterface = ApiClients.getClient().create(ApiInterface.class);
        Call<CommonResponseModel> responseModelCall = apiInterface.updatetJobMethod("Mogo_api/update_job_details/" + appPreferencesShared.getUserId(),appPreferencesShared.getUserId(), jobTitle,
                jobVacancies, jobExperience, salaryExpectation, jobRole, jobDescription, employment, industryId, recruiterName,
                recruiterContact, recruiterCompany, mobileNumber, skillsSpinnerArray, stateSpinnerArray, countryId,
                qualificationSpinnerArray); //review it

        responseModelCall.enqueue(new Callback<CommonResponseModel>() {
            @Override
            public void onResponse(Call<CommonResponseModel> call, Response<CommonResponseModel> response) {
                try {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    if (response.isSuccessful()) {
                        if (response.body().getResult()) {
                            if (response.body().getData().equals("")) {
                                appPreferencesShared.setRecruiterAddUpdateJob(true);
                                Toast.makeText(mContext, "Job Update Successful !", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(mContext, DashboardActivity.class)
                                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));

                            } else {
                                startActivity(new Intent(mContext, SubscriptionPlanActivity.class));
                            }
                        } else {
                            Toast.makeText(mContext, "Something Went Wrong !", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        Toast.makeText(mContext, "Something Went Wrong !", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    e.getMessage();
                }
            }

            @Override
            public void onFailure(Call<CommonResponseModel> call, Throwable t) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void postJobMethod(String userId, String jobTitle, String jobVacancies, String jobExperience,
                               String salaryExpectation, String jobRole, String jobDescription,
                               String employment, String industryId, String recruiterName,
                               String recruiterContact, String recruiterCompany, String mobileNumber,
                               String skillsSpinner, String stateSpinner, String countryId,
                               String qualificationSpinner) throws JSONException {

        ProgressDialog progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        JSONArray skillsSpinnerArray = new JSONArray(skillsSpinner);
        JSONArray stateSpinnerArray = new JSONArray(stateSpinner);
        JSONArray qualificationSpinnerArray = new JSONArray(qualificationSpinner);

        ApiInterface apiInterface = ApiClients.getClient().create(ApiInterface.class);
        Call<CommonResponseModel> responseModelCall = apiInterface.postJobMethod("1", jobTitle,
                jobVacancies, jobExperience, salaryExpectation, jobRole, jobDescription, employment, industryId, recruiterName,
                recruiterContact, recruiterCompany, mobileNumber, skillsSpinnerArray, stateSpinnerArray, countryId,
                qualificationSpinnerArray);

        responseModelCall.enqueue(new Callback<CommonResponseModel>() {
            @Override
            public void onResponse(Call<CommonResponseModel> call, Response<CommonResponseModel> response) {
                try {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    if (response.isSuccessful()) {
                        if (response.body().getResult()) {
                            if (response.body().getData().equals("")) {
                                appPreferencesShared.setRecruiterAddUpdateJob(true);
                                Toast.makeText(mContext, "Job Posted Successful !", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(mContext, DashboardActivity.class)
                                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));

                            } else {
                                startActivity(new Intent(mContext, SubscriptionPlanActivity.class));
                            }
                        } else {
                            Toast.makeText(mContext, "Something Went Wrong !", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        Toast.makeText(mContext, "Something Went Wrong !", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    e.getMessage();
                }
            }

            @Override
            public void onFailure(Call<CommonResponseModel> call, Throwable t) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        /*if (appPreferencesShared.getRecruiterPaymentSuccessful()) {
            recruiter_name.setText(appPreferencesShared.getRecruiterName());
            recruiter_contact_no.setText(appPreferencesShared.getRecruiterContactNo());
            recruiter_company_name.setText(appPreferencesShared.getRecruiterCompanyName());
            recruiter_mogo_contact_no.setText(appPreferencesShared.getMobileNumber());

            if (NetworkStatus.isNetworkAvailable(mContext)) {
                try {
                    postJobMethod("1", appPreferencesShared.getJobTitle(),
                            appPreferencesShared.getJobVacancies(), appPreferencesShared.getJobExperience(),
                            appPreferencesShared.getSalaryExpectation(), appPreferencesShared.getJobRole(),
                            appPreferencesShared.getJobDescription(), appPreferencesShared.getEmployment(),
                            appPreferencesShared.getIndustryId(), recruiter_name.getText().toString(),
                            recruiter_contact_no.getText().toString(), recruiter_company_name.getText().toString(),
                            appPreferencesShared.getMobileNumber(), appPreferencesShared.getSkillsSpinner(),
                            appPreferencesShared.getStateSpinner(), appPreferencesShared.getCountryId(),
                            appPreferencesShared.getQualificationSpinner());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(mContext, "Please Check Your Internet Connection !", Toast.LENGTH_SHORT).show();
            }
        }*/
        if (validations.isMandatory(recruiter_name.getText().toString(), recruiter_name) &&
                validations.isMandatory(recruiter_contact_no.getText().toString(), recruiter_contact_no) &&
                validations.isMandatory(recruiter_company_name.getText().toString(), recruiter_company_name)) {

            appPreferencesShared.setRecruiterName(recruiter_name.getText().toString());
            appPreferencesShared.setRecruiterContactNo(recruiter_contact_no.getText().toString());
            appPreferencesShared.setRecruiterCompanyName(recruiter_company_name.getText().toString());

            if (appPreferencesShared.getRecruiterAddUpdateJob()) {
                if (NetworkStatus.isNetworkAvailable(mContext)) {
                    try {
                        updateJobMethod("1", appPreferencesShared.getJobTitle(),
                                appPreferencesShared.getJobVacancies(), appPreferencesShared.getJobExperience(),
                                appPreferencesShared.getSalaryExpectation(), appPreferencesShared.getJobRole(),
                                appPreferencesShared.getJobDescription(), appPreferencesShared.getEmployment(),
                                appPreferencesShared.getIndustryId(), recruiter_name.getText().toString(),
                                recruiter_contact_no.getText().toString(), recruiter_company_name.getText().toString(),
                                appPreferencesShared.getMobileNumber(), appPreferencesShared.getSkillsSpinner(),
                                appPreferencesShared.getStateSpinner(), "India"/*appPreferencesShared.getCountryId()*/,
                                appPreferencesShared.getQualificationSpinner());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(mContext, "Please Check Your Internet Connection !", Toast.LENGTH_SHORT).show();
                }
            } else {
                if (NetworkStatus.isNetworkAvailable(mContext)) {
                    try {
                        postJobMethod("1", appPreferencesShared.getJobTitle(),
                                appPreferencesShared.getJobVacancies(), appPreferencesShared.getJobExperience(),
                                appPreferencesShared.getSalaryExpectation(), appPreferencesShared.getJobRole(),
                                appPreferencesShared.getJobDescription(), appPreferencesShared.getEmployment(),
                                appPreferencesShared.getIndustryId(), recruiter_name.getText().toString(),
                                recruiter_contact_no.getText().toString(), recruiter_company_name.getText().toString(),
                                appPreferencesShared.getMobileNumber(), appPreferencesShared.getSkillsSpinner(),
                                appPreferencesShared.getStateSpinner(), "India"/*appPreferencesShared.getCountryId()*/,
                                appPreferencesShared.getQualificationSpinner());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(mContext, "Please Check Your Internet Connection !", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}