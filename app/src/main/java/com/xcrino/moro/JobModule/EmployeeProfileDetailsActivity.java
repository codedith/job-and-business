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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.xcrino.moro.Interface.ApiInterface;
import com.xcrino.moro.PojoModels.GetCandidateProfile;
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

public class EmployeeProfileDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView toolbar_title;
    private ImageView back_arrow, candidate_img, premium;
    private Context context;
    Toolbar toolbar;
    private AppPreferencesShared appPreferencesShared;
    private TextView candidate_name, candidate_designation, user_location, job_experience, salary_package, email_id, phone_number;
    private TextView total_applicable_applies, total_consumed_applies, total_remaining_applies, skills, employment_type, industry;
    private TextView qualification, address, employee_dob, employee_gender, employee_passport_availability;
    Button my_subscription;

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

        setContentView(R.layout.activity_employee_profile_details);

        getLayoutUiId();

        context = this;
        setSupportActionBar(toolbar);

        toolbar_title.setText("Your Profile");
        back_arrow.setOnClickListener(this);
        my_subscription.setOnClickListener(this);

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
                Intent intent = new Intent(context, EmployeeEditProfileActivity.class);
                appPreferencesShared.setIsCandidateProfileEdit(true);
                startActivity(intent);
                break;
        }
        return true;
    }

    private void getLayoutUiId() {
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        back_arrow = (ImageView) findViewById(R.id.back_arrow);
        candidate_img = (ImageView) findViewById(R.id.candidate_img);
        candidate_name = (TextView) findViewById(R.id.candidate_name);
        candidate_designation = (TextView) findViewById(R.id.candidate_designation);
        user_location = (TextView) findViewById(R.id.user_location);
        job_experience = (TextView) findViewById(R.id.job_experience);
        salary_package = (TextView) findViewById(R.id.salary_package);
        email_id = (TextView) findViewById(R.id.email_id);
        phone_number = (TextView) findViewById(R.id.phone_number);
        total_applicable_applies = (TextView) findViewById(R.id.total_applicable_applies);
        total_consumed_applies = (TextView) findViewById(R.id.total_consumed_applies);
        total_remaining_applies = (TextView) findViewById(R.id.total_remaining_applies);
        skills = (TextView) findViewById(R.id.skills);
        employment_type = (TextView) findViewById(R.id.employment_type);
        industry = (TextView) findViewById(R.id.industry);
        qualification = (TextView) findViewById(R.id.qualification);
        address = (TextView) findViewById(R.id.address);
        employee_dob = findViewById(R.id.employee_dob);
        employee_gender = findViewById(R.id.employee_gender);
        employee_passport_availability = findViewById(R.id.employee_passport_availability);
        premium = findViewById(R.id.premium);
        my_subscription = findViewById(R.id.my_subscription);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.back_arrow:
                onBackPressed();
                break;

            case R.id.my_subscription:
//                intent = new Intent(context, MySubscriptionActivity.class);
                intent = new Intent(context, EmployeeSubscriptionActivity.class);
                appPreferencesShared.setSubscriptionDirection("Candidate My Subscription");
                startActivity(intent);
                break;
        }
    }

    private void getEmployeeProfileMethod() {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        ApiInterface apiInterface = ApiClients.getClient().create(ApiInterface.class);
        Call<GetCandidateProfile> employeeProfileDataCall = apiInterface.getEmployeeProfileMethod(
                "Mogo_api/get_employee_profile/" + appPreferencesShared.getUserId());
        employeeProfileDataCall.enqueue(new Callback<GetCandidateProfile>() {
            @Override
            public void onResponse(Call<GetCandidateProfile> call, Response<GetCandidateProfile> response) {
                try {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    if (response.isSuccessful()) {
                        if (response.body().getData() != null) {
                            candidate_name.setText(response.body().getData().getDetails().getEmployeeName().toString());
                            candidate_designation.setText(response.body().getData().getDetails().getEmployeeDesignation().toString());
                            user_location.setText(response.body().getData().getDetails().getEmployeeState() + ", "
                                    + response.body().getData().getDetails().getEmployeeCountry());
                            job_experience.setText(response.body().getData().getDetails().getEmployeeJobExp().toString());
                            salary_package.setText(response.body().getData().getDetails().getEmployeeSalaryExpectation().toString());
                            email_id.setText(response.body().getData().getDetails().getUserEmail().toString());
                            phone_number.setText(response.body().getData().getDetails().getUserMobile().toString());
                            skills.setText("");
                            for (int i = 0; i < response.body().getData().getSkills().size(); i++) {
                                skills.append(response.body().getData().getSkills().get(i));
                                if (i != response.body().getData().getSkills().size() - 1) {
                                    skills.append(", ");
                                }
                            }
                            employment_type.setText(response.body().getData().getDetails().getEmployeeEmploymentType().toString());
                            industry.setText(response.body().getData().getDetails().getEmployeeIndustry().toString());
                            qualification.setText(response.body().getData().getDetails().getEmployeeQualification().toString());
                            address.setText(response.body().getData().getDetails().getEmployeeAddress().toString());
                            if (response.body().getData().getDetails().getUserProfileImage() != null)
                                Picasso.with(getApplicationContext()).load(response.body().getData().getDetails().getUserProfileImage().toString())
                                        .resize(185, 185).transform(new CropCircleTransformation())
                                        .placeholder(R.drawable.user_dummy).into(candidate_img);

                            if (response.body().getData().getSubscriptionDomestic().getPremiumActivePlan().getSubscriptionType() != null ||
                                    response.body().getData().getSubscriptionInternational().getPremiumActivePlan().getSubscriptionType() != null) {
                                premium.setVisibility(View.VISIBLE);
                            } else {
                                premium.setVisibility(View.GONE);
                            }
                            if (response.body().getData().getDetails().getEmployeeDob() != null) {
                                String inputPattern = "yyyy-MM-dd";
                                String outputPattern = "dd-MM-yyyy";
                                SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
                                SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

                                Date date = null;
                                String str = null;

                                try {
                                    date = inputFormat.parse(response.body().getData().getDetails().getEmployeeDob().toString());
                                    str = outputFormat.format(date);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                employee_dob.setText(str.toString());
                            }

                            employee_gender.setText(response.body().getData().getDetails().getEmployeeGender());
                            if (response.body().getData().getDetails().getEmployeeHavePassport().equals("0")) {
                                employee_passport_availability.setText("No");
                            } else if (response.body().getData().getDetails().getEmployeeHavePassport().equals("1")) {
                                employee_passport_availability.setText("Yes");
                            } else {
                                employee_passport_availability.setText("--");
                            }

                            GetCandidateProfile candidateProfile = response.body();
                            Gson gson = new Gson();
                            String json = gson.toJson(candidateProfile);
                            appPreferencesShared.setCandidateDetails(json);

                        } else {
                            candidate_name.setText("");
                            candidate_designation.setText("");
                            user_location.setText("");
                            job_experience.setText("");
                            salary_package.setText("");
                            email_id.setText("");
                            phone_number.setText("");
                            total_applicable_applies.setText("0");
                            total_consumed_applies.setText("0");
                            total_remaining_applies.setText("0");
                            skills.setText("");
                            employment_type.setText("");
                            industry.setText("");
                            qualification.setText("");
                            address.setText("");
                        }
                    } else {
                        candidate_name.setText("");
                        candidate_designation.setText("");
                        user_location.setText("");
                        job_experience.setText("");
                        salary_package.setText("");
                        email_id.setText("");
                        phone_number.setText("");
                        total_applicable_applies.setText("0");
                        total_consumed_applies.setText("0");
                        total_remaining_applies.setText("0");
                        skills.setText("");
                        employment_type.setText("");
                        industry.setText("");
                        qualification.setText("");
                        address.setText("");

                    }

                } catch (Exception e) {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    e.getMessage();
                }
            }

            @Override
            public void onFailure(Call<GetCandidateProfile> call, Throwable t) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (NetworkStatus.isNetworkAvailable(context)) {
            getEmployeeProfileMethod();
        } else {
            Toast.makeText(context, "Please Check Your Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }
}