package com.xcrino.moro.JobModule;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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
import com.xcrino.moro.Activity.SubscriptionPlanActivity;
import com.xcrino.moro.Interface.ApiInterface;
import com.xcrino.moro.PojoModels.CommonResponseModel;
import com.xcrino.moro.PojoModels.GetJobDetailModel;
import com.xcrino.moro.R;
import com.xcrino.moro.Utilities.ApiClients;
import com.xcrino.moro.Utilities.AppPreferencesShared;
import com.xcrino.moro.Utilities.NetworkStatus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmployeeJobDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView toolbar_title;
    FloatingActionButton save_txt;
    private ImageView back_arrow, recruiter_image_file;
    private Context context;
    AppPreferencesShared appPreferencesShared;
    Button submit_edit_button;
    private TextView job_title, company_name, job_experience, job_vacancies, job_location, job_salary_package, job_skills;
    private TextView job_description, employment_type, industry_type, job_role, education, about_company, recruiter_name_emp,
            company_name_emp, company_name_info, status, job_post_date;


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
        setContentView(R.layout.activity_employee_job_detail);
        context = this;

        getLayoutUiId();

        toolbar_title.setText("Job Details");
        appPreferencesShared = new AppPreferencesShared(context);

        if (NetworkStatus.isNetworkAvailable(context)) {
            getJobDetailsMethod();
        } else {
            Toast.makeText(context, "Please Check Your Internet Connection !", Toast.LENGTH_SHORT).show();
        }

        switch (appPreferencesShared.getPageDirection()) {
            case "Recommended Jobs":
                savedLayout(false);
                break;

            case "Apply Jobs":
                break;

            case "Hot Jobs":
                break;

            case "Saved Jobs":
                savedLayout(true);
                break;

            case "Applied Job":
                status.setVisibility(View.VISIBLE);
                status.setText("Applied");
                submit_edit_button.setVisibility(View.GONE);
                break;

            case "Job Search By Industry":
                break;

            case "Job Search By Search Button":
                break;

            case "Posted Jobs":
                submit_edit_button.setText("Edit");
                break;
        }

        if (appPreferencesShared.getSavedStatus()) {
            savedLayout(true);
        } else {
            savedLayout(false);
        }

        back_arrow.setOnClickListener(this);
        save_txt.setOnClickListener(this);
        submit_edit_button.setOnClickListener(this);
    }

    private void getJobDetailsMethod() {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        ApiInterface apiInterface = ApiClients.getClient().create(ApiInterface.class);
        Call<GetJobDetailModel> commonResponseModelCall = apiInterface.getJobDetailsMethod("Mogo_api/get_job_details/" + appPreferencesShared.getJobId());
        commonResponseModelCall.enqueue(new Callback<GetJobDetailModel>() {
            @Override
            public void onResponse(Call<GetJobDetailModel> call, Response<GetJobDetailModel> response) {
                try {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            if (response.body().getData().getJobDetails() != null) {
                                job_title.setText(response.body().getData().getJobDetails().getJobTitle() == null ? "Not provided" : response.body().getData().getJobDetails().getJobTitle().toString());
                                company_name.setText(response.body().getData().getJobDetails().getCompanyName() == null ? "Company Name Not Provided" : response.body().getData().getJobDetails().getCompanyName().toString());
                                job_experience.setText("Experience:" + response.body().getData().getJobDetails().getExperience() == null ? "Experience:" : "Experience: " + response.body().getData().getJobDetails().getExperience().toString());
                                job_vacancies.setText("Vacancy:" + response.body().getData().getJobDetails().getJobVacancies() == null ? "Vacancy:" : "Vacancy: " + response.body().getData().getJobDetails().getJobVacancies().toString() + " vacancies");
                                if (response.body().getData().getExtra().getJobLocations() != null) {
                                    job_location.setText("Location: ");
                                    job_location.append(response.body().getData().getExtra().getJobLocations().get(0).getJobCountry().toString() + ",");
                                    for (int i = 0; i < response.body().getData().getExtra().getJobLocations().size(); i++) {
                                        job_location.append(response.body().getData().getExtra().getJobLocations().get(i).getJobState().toString());
                                        if (i != response.body().getData().getExtra().getJobLocations().size() - 1) {
                                            job_location.append(", ");
                                        }
                                    }
                                } else {
                                    job_location.setText("Location:");
                                }
                                education.setText("Education is");
                                for (int i = 0; i < response.body().getData().getExtra().getJobQualification().size(); i++) {
                                    education.append("Education is " + response.body().getData().getExtra().getJobQualification().get(i).getJobQualificationName() == null ? "" : "Education is " + response.body().getData().getExtra().getJobQualification().get(i).getJobQualificationName().toString());
                                    if (i != response.body().getData().getExtra().getJobQualification().size() - 1) {
                                        education.append(", ");
                                    }
                                }
                                job_skills.setText("");
                                for (int i = 0; i < response.body().getData().getExtra().getJobSkills().size(); i++) {
                                    job_skills.append("Skills: " + response.body().getData().getExtra().getJobSkills().get(i).getJobSkills().toString());
                                    if (i != response.body().getData().getExtra().getJobSkills().size() - 1) {
                                        job_skills.append(", ");
                                    }
                                }
                                job_salary_package.setText("Salary:" + response.body().getData().getJobDetails().getSalary() == null ? "Salary: Not Disclosed" : "Salary: " + response.body().getData().getJobDetails().getSalary().toString());
                                job_description.setText(response.body().getData().getJobDetails().getJobDescription() == null ? "Not Provided" : response.body().getData().getJobDetails().getJobDescription().toString());
                                employment_type.setText("Employment Type is " + response.body().getData().getJobDetails().getEmploymentType() == null ? "Employement Type: " : "Employment Type is " + response.body().getData().getJobDetails().getEmploymentType().toString());
                                industry_type.setText("Industry Type is  " + response.body().getData().getJobDetails().getIndustry() == null ? "Industry Type:" : "Industry Type is " + response.body().getData().getJobDetails().getIndustry().toString());
                                job_role.setText("Job Role: " + response.body().getData().getJobDetails().getJobRole() == null ? "Job Role:" : "Job Role is " + response.body().getData().getJobDetails().getJobRole().toString());
                                about_company.setText(response.body().getData().getJobDetails().getAboutCompany() == null ? "Not Provided" : response.body().getData().getJobDetails().getAboutCompany().toString());
                                recruiter_name_emp.setText(response.body().getData().getJobDetails().getRecruiterName() == null ? "Not Provided" : response.body().getData().getJobDetails().getRecruiterName().toString());
                                company_name_emp.setText(response.body().getData().getJobDetails().getCompanyName() == null ? "Not Provided" : response.body().getData().getJobDetails().getCompanyName().toString());
                                if (response.body().getData().getJobDetails().getRecruiterProfileImage() != null) {
                                    Picasso.with(context).load(response.body().getData().getJobDetails().getRecruiterProfileImage().toString()).
                                            resize(175, 175).
                                            transform(new CropCircleTransformation()).
                                            placeholder(R.drawable.user_dummy).into(recruiter_image_file);
                                }

                                if (response.body().getData().getJobDetails().getJobCreated() != null) {
                                    String inputPattern = "yyyy-MM-dd HH:mm:ss";
                                    String outputPattern = "dd-MM-yyyy";
                                    SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
                                    SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

                                    Date date = null;
                                    String str = null;

                                    try {
                                        date = inputFormat.parse(response.body().getData().getJobDetails().getJobCreated().toString());
                                        str = outputFormat.format(date);
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                    job_post_date.setText(str.toString());
                                } else {

                                }

                                company_name_info.setText(response.body().getData().getJobDetails().getCompanyName().toString() == null ? "Not Provided" : response.body().getData().getJobDetails().getCompanyName().toString());
                                company_name_info.append("\n\n" + response.body().getData().getJobDetails().getCompanyAddress() + ", " +
                                        response.body().getData().getJobDetails().getState() + ", " +
                                        response.body().getData().getJobDetails().getCountry());
                                company_name_info.append("\n\nCompany Email: " + response.body().getData().getJobDetails().getCompanyEmail() == null ? "Not Provided" : response.body().getData().getJobDetails().getCompanyEmail() + ".");
                                company_name_info.append("\n\nCompany Phone: " + response.body().getData().getJobDetails().getCompanyPhone() == null ? "Not Provided" : response.body().getData().getJobDetails().getCompanyPhone() + ".");
                                company_name_info.append("\n\nCompany Website: " + response.body().getData().getJobDetails().getCompanyWebsite() == null ? "Not Provided" : response.body().getData().getJobDetails().getCompanyWebsite() + ".");
                            } else {
                                job_title.setText("");

                            }
                        }

                    } else {
                        job_title.setText("");

                    }
                } catch (Exception e) {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    e.getMessage();
                }
            }

            @Override
            public void onFailure(Call<GetJobDetailModel> call, Throwable t) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void getLayoutUiId() {
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        back_arrow = (ImageView) findViewById(R.id.back_arrow);
        save_txt = findViewById(R.id.save_txt);
        submit_edit_button = findViewById(R.id.submit_edit_button);
        job_title = (TextView) findViewById(R.id.job_title);
        company_name = (TextView) findViewById(R.id.company_name);
        job_experience = (TextView) findViewById(R.id.job_experience);
        job_vacancies = (TextView) findViewById(R.id.job_vacancies);
        job_location = (TextView) findViewById(R.id.job_location);
        job_salary_package = (TextView) findViewById(R.id.job_salary_package);
        job_skills = (TextView) findViewById(R.id.job_skills);
        job_description = (TextView) findViewById(R.id.job_description);
        employment_type = (TextView) findViewById(R.id.employment_type);
        industry_type = (TextView) findViewById(R.id.industry_type);
        job_role = (TextView) findViewById(R.id.job_role);
        education = (TextView) findViewById(R.id.education);
        about_company = (TextView) findViewById(R.id.about_company);
        recruiter_name_emp = (TextView) findViewById(R.id.recruiter_name_emp);
        company_name_emp = (TextView) findViewById(R.id.company_name_emp);
        recruiter_image_file = (ImageView) findViewById(R.id.recruiter_image_file);
        company_name_info = findViewById(R.id.company_name_info);
        status = findViewById(R.id.status);

        job_post_date = findViewById(R.id.job_post_date);
    }

    private void savedLayout(boolean saved) {
        if (saved) {
            save_txt.setImageResource(R.drawable.ic_saved_star);
            save_txt.setColorFilter(ContextCompat.getColor(context, R.color.text_purple));
            // save_txt.setText("Saved");
        } else {
            save_txt.setImageResource(R.drawable.ic_star);
            //save_txt.setColorFilter(ContextCompat.getColor(context, R.color.white));
            // save_txt.setText("Save");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_arrow:
                onBackPressed();
                break;

            case R.id.submit_edit_button:
                if (NetworkStatus.isNetworkAvailable(context)) {
                    postApplyJobMethod();
                } else {
                    Toast.makeText(context, "Please Check Your Internet Connection !", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.save_txt:
                if (NetworkStatus.isNetworkAvailable(context)) {
                    saveJobMethod();
                } else {
                    Toast.makeText(context, "Please Check Your Internet Connection !", Toast.LENGTH_SHORT).show();
                }
        }
    }

    private void postApplyJobMethod() {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        ApiInterface apiInterface = ApiClients.getClient().create(ApiInterface.class);
        Call<CommonResponseModel> applyjobmethod = apiInterface.postApplyJobMethod(appPreferencesShared.getUserId(), appPreferencesShared.getJobId());
        applyjobmethod.enqueue(new Callback<CommonResponseModel>() {
            @Override
            public void onResponse(Call<CommonResponseModel> call, Response<CommonResponseModel> response) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                if (response.isSuccessful()) {
                    if (response.body().getResult()) {
                        status.setVisibility(View.VISIBLE);
                        status.setText("Applied");
                        submit_edit_button.setVisibility(View.GONE);
                    } else {
                        if (response.body() != null) {
                            Intent intent = new Intent(context, SubscriptionPlanActivity.class);
                            appPreferencesShared.setSubscriptionDirection("Employee");
                            startActivity(intent);
                        }
                        status.setVisibility(View.VISIBLE);
                        status.setText("Already Applied ");
                        submit_edit_button.setVisibility(View.GONE);
                    }
                } else {
                    status.setVisibility(View.VISIBLE);
                    status.setText("Already Applied ");
                    submit_edit_button.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<CommonResponseModel> call, Throwable t) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                status.setVisibility(View.VISIBLE);
                status.setText("Already Applied ");
                submit_edit_button.setVisibility(View.GONE);

            }
        });

    }

    private void saveJobMethod() {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        ApiInterface apiInterface = ApiClients.getClient().create(ApiInterface.class);
        Call<CommonResponseModel> applyjobmethod = apiInterface.saveJobMethod("Mogo_api/save_job/" + appPreferencesShared.getUserId() + "/" + appPreferencesShared.getJobId());
        applyjobmethod.enqueue(new Callback<CommonResponseModel>() {
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

}
