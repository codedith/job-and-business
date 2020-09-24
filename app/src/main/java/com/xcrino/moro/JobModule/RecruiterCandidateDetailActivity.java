package com.xcrino.moro.JobModule;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;
import com.xcrino.moro.Interface.ApiInterface;
import com.xcrino.moro.PojoModels.CommonResponseModel;
import com.xcrino.moro.PojoModels.GetCandidateProfile;
import com.xcrino.moro.R;
import com.xcrino.moro.Utilities.ApiClients;
import com.xcrino.moro.Utilities.AppPreferencesShared;
import com.xcrino.moro.Utilities.NetworkStatus;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecruiterCandidateDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView toolbar_title, save_txt, candidate_name, candidate_company, candidate_designation, job_experience, job_location,
            job_salary_package, job_skills, candidate_description, employment_type, industry_type, job_role, education, candidate_address,
            candidate_email, candidate_dob, send_message, status;
    private ImageView back_arrow, candidate_profile_photo;
    private Context context;
    private AppPreferencesShared appPreferencesShared;
    Button interest_button;
    LinearLayout ll_candidatelocation;
    FloatingActionButton save_img;

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
        setContentView(R.layout.activity_recruiter_candidate_detail);
        context = this;

        getLayoutUiId();

        if (NetworkStatus.isNetworkAvailable(this)) {
            getCandidateDetailsMethod();
        } else {
            Toast.makeText(context, "Check your Internet Connection", Toast.LENGTH_SHORT).show();
        }

        toolbar_title.setText("Candidate Details");

        switch (appPreferencesShared.getPageDirection()) {
            case "Saved Candidate":
                savedLayout(true);
                break;

            case "Interested Candidate":
                status.setVisibility(View.VISIBLE);
                status.setText("Shown Interest");
                interest_button.setVisibility(View.GONE);
                break;
        }

        if (appPreferencesShared.getSavedStatus()) {
            savedLayout(true);
        } else {
            savedLayout(false);
        }

        back_arrow.setOnClickListener(this);
        save_img.setOnClickListener(this);
        interest_button.setOnClickListener(this);
    }

    private void getLayoutUiId() {
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        back_arrow = (ImageView) findViewById(R.id.back_arrow);
        save_img = (FloatingActionButton) findViewById(R.id.save_img);
        save_txt = (TextView) findViewById(R.id.save_txt);
        candidate_name = (TextView) findViewById(R.id.candidate_name);
        candidate_company = (TextView) findViewById(R.id.candidate_company);
        candidate_designation = (TextView) findViewById(R.id.candidate_designation);
        candidate_profile_photo = (ImageView) findViewById(R.id.candidate_profile_photo);
        job_experience = (TextView) findViewById(R.id.job_experience);
        job_location = (TextView) findViewById(R.id.job_location);
        job_salary_package = (TextView) findViewById(R.id.job_salary_package);
        job_skills = (TextView) findViewById(R.id.job_skills);
        candidate_description = (TextView) findViewById(R.id.candidate_description);
        employment_type = (TextView) findViewById(R.id.employment_type);
        industry_type = (TextView) findViewById(R.id.industry_type);
        job_role = (TextView) findViewById(R.id.job_role);
        education = (TextView) findViewById(R.id.education);
        candidate_address = (TextView) findViewById(R.id.candidate_address);
        candidate_email = (TextView) findViewById(R.id.candidate_email);
        candidate_dob = (TextView) findViewById(R.id.candidate_dob);
        send_message = (TextView) findViewById(R.id.send_message);
//        submit_button = (TextView) findViewById(R.id.submit_button);
        ll_candidatelocation = (LinearLayout) findViewById(R.id.ll_candidatelocation);
        //ll_save = findViewById(R.id.ll_save);
        interest_button = findViewById(R.id.interest_button);
        status = findViewById(R.id.status);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_arrow:
                onBackPressed();
                break;

            case R.id.save_img:
                if (NetworkStatus.isNetworkAvailable(context)) {
                    saveCandidateMethod();
                } else {
                    Toast.makeText(context, "Please Check Your Internet Connection !", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.interest_button:
                if (NetworkStatus.isNetworkAvailable(context)) {
                    showInterestMethod();
                } else {
                    Toast.makeText(context, "Please Check Your Internet Connection !", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void savedLayout(boolean saved) {
        if (saved) {
            save_img.setImageResource(R.drawable.ic_saved_star);
            save_img.setColorFilter(ContextCompat.getColor(context, R.color.text_purple));

        } else {
            save_img.setImageResource(R.drawable.ic_star);
            //  save_img.setColorFilter(ContextCompat.getColor(context, R.color.white));

        }
    }

    private void getCandidateDetailsMethod() {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        ApiInterface apiInterface = ApiClients.getClient().create(ApiInterface.class);
        Call<GetCandidateProfile> GetCandidateDetailsMethod = apiInterface.GetCandidatesDetailsMethod("Mogo_api/get_employee_profile/" + appPreferencesShared.getCandidateId().toString() + "/" + appPreferencesShared.getUserId().toString());
        GetCandidateDetailsMethod.enqueue(new Callback<GetCandidateProfile>() {
            @Override
            public void onResponse(Call<GetCandidateProfile> call, Response<GetCandidateProfile> response) {
                try {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            if (response.body().getData() != null) {
                                candidate_name.setText(response.body().getData().getDetails().getEmployeeName() == null ? "--" : response.body().getData().getDetails().getEmployeeName().toString());
                                candidate_company.setText(response.body().getData().getDetails().getEmployeeLastCompanyName() == null ? "Not provided by candidate" :
                                        response.body().getData().getDetails().getEmployeeLastCompanyName().toString());
                                candidate_designation.setText(response.body().getData().getDetails().getEmployeeDesignation() == null ? "--" : response.body().getData().getDetails().getEmployeeDesignation().toString());
                                job_experience.setText(response.body().getData().getDetails().getEmployeeJobExp() == null ? "-NA-" : response.body().getData().getDetails().getEmployeeJobExp().toString());
                                if (response.body().getData().getDetails().getEmployeeAddress() == null) {
                                    ll_candidatelocation.setVisibility(View.GONE);
                                } else {
                                    ll_candidatelocation.setVisibility(View.VISIBLE);
                                    job_location.setText(response.body().getData().getDetails().getEmployeeAddress().toString());
                                }
                                job_salary_package.setText(response.body().getData().getDetails().getEmployeeSalaryExpectation() == null ? "Not Disclosed" : response.body().getData().getDetails().getEmployeeSalaryExpectation().toString());
                                candidate_description.setText(response.body().getData().getDetails().getEmployeeProfileHeadline() == null ? "Not Provided " : response.body().getData().getDetails().getEmployeeProfileHeadline().toString());
                                employment_type.setText("Employment type is " + response.body().getData().getDetails().getEmployeeEmploymentType() == null ? "Employment type" : "Employment type is " + response.body().getData().getDetails().getEmployeeEmploymentType().toString());
                                industry_type.setText("Industry type is: " + response.body().getData().getDetails().getEmployeeIndustry() == null ? "Industry type:" : "Industry type is " + response.body().getData().getDetails().getEmployeeIndustry().toString());
                                job_role.setText("Job Role is " + response.body().getData().getDetails().getEmployeeDesignation() == null ? "Job Role:" : "Job Role is: " + response.body().getData().getDetails().getEmployeeDesignation().toString());
                                education.setText("Education is " + response.body().getData().getDetails().getEmployeeQualification() == null ? "Education:" : "Education is " + response.body().getData().getDetails().getEmployeeDesignation().toString());
                                candidate_address.setText(response.body().getData().getDetails().getEmployeeAddress() == null ? "--" : response.body().getData().getDetails().getEmployeeAddress().toString());
                                candidate_email.setText(response.body().getData().getDetails().getUserEmail() == null ? "Not Provided" : response.body().getData().getDetails().getUserEmail().toString());

                                if (response.body().getData().getDetails().getEmployeeDob() != null) {
//                                    String inputPattern = "yyyy-MM-dd";
//                                    String outputPattern = "dd-MM-yyyy";
//                                    SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
//                                    SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
//                                    Date dob = outputFormat.format(response.body().getData().getDetails().getEmployeeDob());


                                    String strCurrentDate = response.body().getData().getDetails().getEmployeeDob();
                                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                                    Date newDate = format.parse(strCurrentDate);

                                    format = new SimpleDateFormat("dd-MMM-yyyy");
                                    String date = format.format(newDate);


                                    candidate_dob.setText(date);
                                } else {
                                    candidate_dob.setText("Not Provided");
                                }

                                if (response.body().getData().getSkills() != null) {
                                    job_skills.setText("");
                                    for (int i = 0; i < response.body().getData().getSkills().size(); i++) {
                                        job_skills.append(response.body().getData().getSkills().get(i).toString());
                                        if (i != response.body().getData().getSkills().size() - 1) {
                                            job_skills.append(", ");
                                        }
                                    }
                                } else {
                                    job_skills.setText("Not Mentioned");
                                }

                                if (response.body().getData().getDetails().getUserProfileImage() != null) {
                                    Picasso.with(context).load(response.body().getData().getDetails().getUserProfileImage().toString()).
                                            resize(175, 175).
                                            transform(new CropCircleTransformation()).
                                            placeholder(R.drawable.user_dummy).into(candidate_profile_photo);
                                }

                                if (response.body().getData().getInterested_candidate()) {
                                    status.setVisibility(View.VISIBLE);
                                    status.setText("Already Shown Interest");
                                    interest_button.setVisibility(View.GONE);
                                } else {
                                    status.setVisibility(View.GONE);
                                    status.setText("");
                                    interest_button.setVisibility(View.VISIBLE);
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetCandidateProfile> call, Throwable t) {

            }
        });


    }

    private void saveCandidateMethod() {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        ApiInterface apiInterface = ApiClients.getClient().create(ApiInterface.class);
        Call<CommonResponseModel> saveCandidateMethod = apiInterface.saveCandidateMethod("Mogo_api/save_candidate/" +
                appPreferencesShared.getUserId() + "/" + appPreferencesShared.getCandidateId(), "1");
        saveCandidateMethod.enqueue(new Callback<CommonResponseModel>() {
            @Override
            public void onResponse(Call<CommonResponseModel> call, Response<CommonResponseModel> response) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                if (response.isSuccessful()) {
//                    savedLayout(true);
                    if (response.body().getResult()) {
                        savedLayout(true);
                    } else {
                        savedLayout(false);
                    }
                } else {
                    savedLayout(false);
                }
            }

            @Override
            public void onFailure(Call<CommonResponseModel> call, Throwable t) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                savedLayout(false);
            }
        });
    }

    private void showInterestMethod() {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        ApiInterface apiInterface = ApiClients.getClient().create(ApiInterface.class);
        Call<CommonResponseModel> saveCandidateMethod = apiInterface.saveCandidateMethod("Mogo_api/save_candidate/" +
                appPreferencesShared.getUserId() + "/" + appPreferencesShared.getCandidateId(), "2");
        saveCandidateMethod.enqueue(new Callback<CommonResponseModel>() {
            @Override
            public void onResponse(Call<CommonResponseModel> call, Response<CommonResponseModel> response) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                if (response.isSuccessful()) {
                    if (response.body().getResult()) {
                        status.setVisibility(View.VISIBLE);
                        status.setText("Shown Interest");
                        interest_button.setVisibility(View.GONE);
                    } else {
                        status.setVisibility(View.VISIBLE);
                        status.setText("Already Shown Interest");
                        interest_button.setVisibility(View.GONE);
                    }
                } else {
                    status.setVisibility(View.VISIBLE);
                    status.setText("Already Shown Interest");
                    interest_button.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<CommonResponseModel> call, Throwable t) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                status.setVisibility(View.VISIBLE);
                status.setText("Already Shown Interest");
                interest_button.setVisibility(View.GONE);
            }
        });
    }
}
