package com.xcrino.moro.JobModule;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.xcrino.moro.Activity.DashboardActivity;
import com.xcrino.moro.Interface.ApiInterface;
import com.xcrino.moro.PojoModels.CommonResponseModel;
import com.xcrino.moro.PojoModels.DefaultData;
import com.xcrino.moro.PojoModels.GetCandidateProfile;
import com.xcrino.moro.PojoModels.ImageUploadModel;
import com.xcrino.moro.PojoModels.StateListData;
import com.xcrino.moro.PojoModels.UserProfile;
import com.xcrino.moro.R;
import com.xcrino.moro.Utilities.ApiClients;
import com.xcrino.moro.Utilities.AppPreferencesShared;
import com.xcrino.moro.Utilities.ButtonVisibility;
import com.xcrino.moro.Utilities.DatePicker;
import com.xcrino.moro.Utilities.NetworkStatus;
import com.xcrino.moro.Utilities.Validations;

import org.json.JSONArray;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.Locale;

public class EmployeeEditProfileActivity extends AppCompatActivity implements View.OnClickListener {

    String countryId, countryName, statesName;
    Context mContext;
    ImageView back_arrow;
    TextView toolbar_title, skills_spinner, dobField, mobileNumberField, state_spinner, country_spinner;
    EditText emailIdField, firstNameField, lastNameField, fullNameField, addressField, prevCompanyNameField, designationField, profile_headline;
    Spinner job_experience_spinner, qualification_spinner, salary_expectation_spinner, employment_type_spinner, industry_spinner, workExperienceCompanyField;
    Button submit_button;
    View activity_employee_profile;
    private boolean[] checkedStates;
    private String[] industrynameArray;
    String[] jobExperienceArray = {"Select", "0 Year", "1 Years", "2 Years", "3 Years", "4 Years", "5 Years", "6 Years",
            "7 Years", "8 Years", "9 Years", "10 Years", "11 Years", "12 Years", "13 Years", "14 Years", "15 Years",
            "16 Years", "17 Years", "18 Years", "19 Years", "20 Years", "More than 20 Years"};
    String jobExperience = "Select", workExperience = "Select";
    String[] qualificationArray, skillsIdArray;
    String qualification = "Select";
    String[] salaryArray = {"Select", "0 lpa - 1 lpa", "1 lpa - 2 lpa", "2 lpa - 3 lpa", "3 lpa - 4 lpa", "4 lpa - 5 lpa",
            "5 lpa - 6 lpa", "6 lpa - 7 lpa", "7 lpa - 8 lpa", "8 lpa - 9 lpa", "More than 9 lpa"};
    String salaryExpectation = "Select";
    String[] employmentArray = {"Select", "Full-Time", "Part-Time", "Internship", "Contract", "Freelancer"};
    String employment = "Select";
    String[] skillsArray;
    String skills = "Select";
    boolean[] checkedItems, checkedSkills;
    ArrayList<Integer> mUserItems = new ArrayList<>();
    private List<String> SkillIdList = new ArrayList<String>();
    private AppPreferencesShared appPreferencesShared;
    private List<String> IndustryIdList = new ArrayList<String>();
    String industryId, industryName;
    private List<String> QualificationIdList = new ArrayList<String>();

    private Spinner job_country_spinner, job_state_spinner;
    private String[] countryNameArray, statenameArray;
    private List<String> CountryIdList = new ArrayList<String>();
    private ArrayList<Integer> SkillIdListTwo = new ArrayList<>();

    String passport = "Select", userProfileImage, gender = "Select";
    UserProfile userProfile;
    GetCandidateProfile candidateProfile;
    Validations validations;
    RadioButton male_radio, female_radio, yes_radio, no_radio;
    ImageView candidate_image;
    private String[] appPermissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA};
    private int PERMISSIONS_REQUEST_CODE = 1024, GALLERY = 0, CAMERA = 1;

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
        setContentView(R.layout.activity_employee_edit_profile);
        mContext = this;

        getLayoutInitUI();

        validations = new Validations();

        checkAndRequestPermissions();

        Gson gSonUserProfile = new Gson();
        String jsonUserProfile = appPreferencesShared.getUserDetails();
        userProfile = gSonUserProfile.fromJson(jsonUserProfile, UserProfile.class);

        Gson gSonCandidateProfile = new Gson();
        String jsonCandidateProfile = appPreferencesShared.getCandidateDetails();
        candidateProfile = gSonCandidateProfile.fromJson(jsonCandidateProfile, GetCandidateProfile.class);

        ArrayAdapter<String> jobExperienceAdapter = new ArrayAdapter<String>(mContext, R.layout.spinner_item, jobExperienceArray);
        job_experience_spinner.setAdapter(jobExperienceAdapter);
        job_experience_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                jobExperience = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<String> workExperienceAdapter = new ArrayAdapter<String>(mContext, R.layout.spinner_item, jobExperienceArray);
        workExperienceCompanyField.setAdapter(workExperienceAdapter);
        workExperienceCompanyField.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                workExperience = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<String> salaryExpectationAdapter = new ArrayAdapter<String>(mContext, R.layout.spinner_item, salaryArray);
        salary_expectation_spinner.setAdapter(salaryExpectationAdapter);
        salary_expectation_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                salaryExpectation = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<String> employmentAdapter = new ArrayAdapter<String>(mContext, R.layout.spinner_item, employmentArray);
        employment_type_spinner.setAdapter(employmentAdapter);
        employment_type_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                employment = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (appPreferencesShared.getIsCandidateProfileEdit()) {
            toolbar_title.setText("Edit Profile");
            fullNameField.setText(candidateProfile.getData().getDetails().getEmployeeName().toString());
            fullNameField.setSelection(fullNameField.getText().length());
            emailIdField.setText(candidateProfile.getData().getDetails().getUserEmail().toString());
            mobileNumberField.setText(candidateProfile.getData().getDetails().getUserMobile().toString());
            mobileNumberField.setTextColor(getResources().getColor(R.color.grey));
            String inputPattern = "yyyy-MM-dd";
            String outputPattern = "dd-MM-yyyy";
            SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
            SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

            Date date = null;
            String str = null;

            try {
                date = inputFormat.parse(candidateProfile.getData().getDetails().getEmployeeDob());
                str = outputFormat.format(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            dobField.setText(str.toString());
            country_spinner.setText(candidateProfile.getData().getDetails().getUserCountryId().toString());
            country_spinner.setTextColor(getResources().getColor(R.color.grey));
            state_spinner.setText(candidateProfile.getData().getDetails().getUserStateId().toString());
            state_spinner.setTextColor(getResources().getColor(R.color.grey));
            prevCompanyNameField.setText(candidateProfile.getData().getDetails().getEmployeeLastCompanyName().toString());
            designationField.setText(candidateProfile.getData().getDetails().getEmployeeDesignation().toString());
            profile_headline.setText(candidateProfile.getData().getDetails().getEmployeeProfileHeadline().toString());
            addressField.setText(candidateProfile.getData().getDetails().getEmployeeAddress().toString());

            if (candidateProfile.getData().getDetails().getUserProfileImage() != null) {
                Picasso.with(getApplicationContext()).load(candidateProfile.getData().getDetails().getUserProfileImage().toString()).
                        resize(200, 200).transform(new CropCircleTransformation()).placeholder(R.drawable.user_dummy).into(candidate_image);
            }

            for (int i = 0; i < jobExperienceArray.length; i++) {
                if (jobExperienceArray[i].equals(candidateProfile.getData().getDetails().getEmployeeWorkExpInLastCompany().toString())) {
                    workExperienceCompanyField.setSelection(i);
                }
                if (jobExperienceArray[i].equals(candidateProfile.getData().getDetails().getEmployeeJobExp().toString())) {
                    job_experience_spinner.setSelection(i);
                }
            }

            for (int i = 0; i < employmentArray.length; i++) {
                if (employmentArray[i].equals(candidateProfile.getData().getDetails().getEmployeeEmploymentType().toString())) {
                    employment_type_spinner.setSelection(i);
                }
            }

            for (int i = 0; i < salaryArray.length; i++) {
                if (salaryArray[i].equals(candidateProfile.getData().getDetails().getEmployeeSalaryExpectation().toString())) {
                    salary_expectation_spinner.setSelection(i);
                }
            }

            skills_spinner.setText("");
            skillsIdArray = new String[candidateProfile.getData().getSkills().size()];
            for (int i = 0; i < candidateProfile.getData().getSkills().size(); i++) {
                skillsIdArray[i] = candidateProfile.getData().getSkills().get(i).toString();
                skills_spinner.append(candidateProfile.getData().getSkills().get(i));
                if (i != candidateProfile.getData().getSkills().size() - 1) {
                    skills_spinner.append(",");
                }
            }

            skills = skills_spinner.getText().toString();

            gender = candidateProfile.getData().getDetails().getEmployeeGender();
            if (gender.equals("male")) {
                male_radio.setChecked(true);
                female_radio.setChecked(false);
            } else {
                male_radio.setChecked(false);
                female_radio.setChecked(true);
            }

            passport = candidateProfile.getData().getDetails().getEmployeeHavePassport();
            if (passport.equals("0")) {
                no_radio.setChecked(true);
                yes_radio.setChecked(false);
            } else {
                no_radio.setChecked(false);
                yes_radio.setChecked(true);
            }

        } else {
            toolbar_title.setText("Create Profile");
            fullNameField.setText(userProfile.getUserDatum().getUserProfileData().getUserFullName().toString());
            fullNameField.setSelection(fullNameField.getText().length());
            emailIdField.setText(userProfile.getUserDatum().getUserProfileData().getUserEmail().toString());
            mobileNumberField.setText(userProfile.getUserDatum().getUserProfileData().getUserMobile().toString());
            mobileNumberField.setTextColor(getResources().getColor(R.color.grey));

            dobField.setText(userProfile.getUserDatum().getUserProfileData().getUserDob());
            country_spinner.setText(userProfile.getUserDatum().getUserProfileData().getUserCountryId().toString());
            country_spinner.setTextColor(getResources().getColor(R.color.grey));
            state_spinner.setText(userProfile.getUserDatum().getUserProfileData().getUserStateId().toString());
            state_spinner.setTextColor(getResources().getColor(R.color.grey));
            if (userProfile.getUserDatum().getUserProfileData().getUserProfileImage() != null) {
                Picasso.with(getApplicationContext()).load(userProfile.getUserDatum().getUserProfileData().getUserProfileImage()).
                        resize(200, 200).transform(new CropCircleTransformation()).placeholder(R.drawable.user_dummy).into(candidate_image);
            }

            if (yes_radio.isChecked()) {
                passport = "1";
            } else {
                passport = "0";
            }

            if (male_radio.isChecked()) {
                gender = "male";
            } else {
                gender = "female";
            }
        }

        ButtonVisibility buttonVisibility = new ButtonVisibility();
        buttonVisibility.hideButton(mContext, activity_employee_profile, submit_button);

        if (NetworkStatus.isNetworkAvailable(mContext)) {
            getDefaultData();
        } else {
            Toast.makeText(mContext, "Please Check Your Internet Connection", Toast.LENGTH_SHORT).show();
        }

        back_arrow.setOnClickListener(this);
        submit_button.setOnClickListener(this);
        skills_spinner.setOnClickListener(this);
        dobField.setOnClickListener(this);
        candidate_image.setOnClickListener(this);
    }

    private void getLayoutInitUI() {
        back_arrow = findViewById(R.id.back_arrow);
        toolbar_title = findViewById(R.id.toolbar_title);
        emailIdField = findViewById(R.id.emailIdField);
        mobileNumberField = findViewById(R.id.mobileNumberField);
        job_experience_spinner = findViewById(R.id.job_experience_spinner);
        qualification_spinner = findViewById(R.id.qualification_spinner);
        salary_expectation_spinner = findViewById(R.id.salary_expectation_spinner);
        submit_button = findViewById(R.id.submit_button);
        activity_employee_profile = findViewById(R.id.activity_employee_profile);
        employment_type_spinner = findViewById(R.id.employment_type_spinner);
        skills_spinner = findViewById(R.id.skills_spinner);
        country_spinner = (TextView) findViewById(R.id.country_spinner);
        state_spinner = (TextView) findViewById(R.id.state_spinner);
        dobField = findViewById(R.id.dobField);
        industry_spinner = findViewById(R.id.industry_spinner);
        fullNameField = findViewById(R.id.fullNameField);
        job_country_spinner = findViewById(R.id.job_country_spinner);
        job_state_spinner = findViewById(R.id.job_state_spinner);
        designationField = findViewById(R.id.designationField);
        workExperienceCompanyField = findViewById(R.id.workExperienceCompanyField);
        profile_headline = findViewById(R.id.profile_headline);
        prevCompanyNameField = findViewById(R.id.prevCompanyNameField);
        addressField = findViewById(R.id.addressField);
        male_radio = findViewById(R.id.male_radio);
        female_radio = findViewById(R.id.female_radio);
        yes_radio = findViewById(R.id.yes_radio);
        no_radio = findViewById(R.id.no_radio);
        workExperienceCompanyField = findViewById(R.id.workExperienceCompanyField);
        candidate_image = findViewById(R.id.candidate_image);
    }

    @Override
    public void onBackPressed() {
        if (appPreferencesShared.getIsCandidateProfileEdit()) {
            onBackPressed();
        } else {
            Intent intent = new Intent(mContext, DashboardActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_arrow:
                if (appPreferencesShared.getIsCandidateProfileEdit()) {
                    onBackPressed();
                } else {
                    Intent intent = new Intent(mContext, DashboardActivity.class);
                    startActivity(intent);
                    finish();
                }
                break;

            case R.id.submit_button:
                if (validations.isMandatory(fullNameField.getText().toString(), fullNameField) &&
                        validations.isEmailValid(emailIdField.getText().toString(), emailIdField) &&
                        validations.isDobValid(dobField.getText().toString(), dobField) &&
                        validations.isSpinnerValid(gender, mContext, "Select your gender") &&
                        validations.isMandatory(addressField.getText().toString(), addressField) &&
                        validations.isMandatory(prevCompanyNameField.getText().toString(), prevCompanyNameField) &&
                        validations.isMandatory(designationField.getText().toString(), designationField) &&
                        validations.isSpinnerValid(workExperience, mContext, "Select your work experience in last company") &&
                        validations.isMandatory(profile_headline.getText().toString(), profile_headline) &&
                        validations.isSpinnerValid(skills, mContext, "Select your skills") &&
                        validations.isSpinnerValid(jobExperience, mContext, "Select your job experience") &&
                        validations.isSpinnerValid(employment, mContext, "Select your employement type") &&
                        validations.isSpinnerValid(salaryExpectation, mContext, "Select your salary expectation") &&
                        validations.isSpinnerValid(passport, mContext, "Select your passport availability")) {

                    if (appPreferencesShared.getIsCandidateProfileEdit()) {
                        if (NetworkStatus.isNetworkAvailable(mContext)) {
                            postUpdateEmployeeProfile();
                        } else {
                            Toast.makeText(mContext, "Please Check Your Internet Connection", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        if (NetworkStatus.isNetworkAvailable(mContext)) {
                            postCreateEmployeeProfile();
                        } else {
                            Toast.makeText(mContext, "Please Check Your Internet Connection", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                break;

            case R.id.skills_spinner:
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("");
                builder.setMultiChoiceItems(skillsArray, checkedSkills, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if (isChecked) {
                            if (!SkillIdListTwo.contains(which)) {
                                SkillIdListTwo.add(which);
                            }
                        } else if (SkillIdListTwo.contains(which)) {
                            SkillIdListTwo.remove(which);
                        }
                    }
                });

                builder.setCancelable(false);
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        skills = "";
                        skillsIdArray = new String[SkillIdListTwo.size()];
                        for (int i = 0; i < SkillIdListTwo.size(); i++) {
                            skills = skills + skillsArray[SkillIdListTwo.get(i)];
                            skillsIdArray[i] = skillsArray[SkillIdListTwo.get(i)];
                            if (i != SkillIdListTwo.size() - 1) {
                                skills = skills + ", ";
                            }
                        }
                        skills_spinner.setText(skills);
                    }
                });

                builder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (checkedSkills.length != 0) {
                            for (int i = 0; i < checkedSkills.length; i++) {
                                checkedSkills[i] = false;
                                SkillIdListTwo.clear();
                                skills_spinner.setText("Select");
                            }
                        } else {
                            dialog.dismiss();
                        }
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
                break;

            case R.id.dobField:
                new DatePicker(mContext, dobField);
                break;

            case R.id.candidate_image:
                showUploadDialogue();
                break;
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
                                    job_country_spinner.setAdapter(countryAdapter);
                                    job_country_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                                    if (candidateProfile != null) {
                                        if (candidateProfile.getData().getDetails().getEmployeeWorkCountry() != null && !candidateProfile.getData().getDetails().getEmployeeWorkCountry().isEmpty()) {
                                            for (int i = 0; i < countryNameArray.length; i++) {
                                                if (countryNameArray[i].equals(candidateProfile.getData().getDetails().getEmployeeWorkCountry())) {
                                                    job_country_spinner.setSelection(i);
                                                }
                                            }
                                        }
                                    }
                                }
                                if (response.body().getData().getIndustryList() != null) {
                                    industrynameArray = new String[response.body().getData().getIndustryList().size()];
                                    for (int i = 0; i < response.body().getData().getIndustryList().size(); i++) {
                                        IndustryIdList.add(response.body().getData().getCountryList().get(i).getId());
                                        industrynameArray[i] = response.body().getData().getIndustryList().get(i).getIndustryName();
                                    }
                                    ArrayAdapter<String> industryAdapter = new ArrayAdapter<>(mContext, R.layout.spinner_item, industrynameArray);
                                    industry_spinner.setAdapter(industryAdapter);
                                    industry_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                            industryId = IndustryIdList.get(i);
                                            industryName = industrynameArray[i];
                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> adapterView) {

                                        }
                                    });
                                }
                                if (response.body().getData().getQualificationList() != null) {
                                    qualificationArray = new String[response.body().getData().getQualificationList().size()];
                                    for (int i = 0; i < response.body().getData().getIndustryList().size(); i++) {
                                        qualificationArray[i] = response.body().getData().getQualificationList().get(i).getQualificationName();
                                    }
                                    ArrayAdapter<String> qualificationAdapter = new ArrayAdapter<String>(mContext, R.layout.spinner_item, qualificationArray);
                                    qualification_spinner.setAdapter(qualificationAdapter);
                                    qualification_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                            qualification = parent.getItemAtPosition(position).toString();
                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> parent) {

                                        }
                                    });
                                }

                                if (response.body().getData().getSkillsList() != null) {
                                    skillsArray = new String[response.body().getData().getSkillsList().size()];
                                    for (int i = 0; i < response.body().getData().getSkillsList().size(); i++) {
                                        skillsArray[i] = response.body().getData().getSkillsList().get(i).getSkillsName();
                                    }
                                    checkedSkills = new boolean[skillsArray.length];
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

                                ArrayAdapter<String> employmentAdapter = new ArrayAdapter<String>(mContext, R.layout.spinner_item, statenameArray);
                                job_state_spinner.setAdapter(employmentAdapter);
                                job_state_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        statesName = parent.getItemAtPosition(position).toString();
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });
                                if (candidateProfile != null) {
                                    if (candidateProfile.getData().getDetails().getEmployeeWorkState() != null && !candidateProfile.getData().getDetails().getEmployeeWorkState().isEmpty()) {
                                        for (int i = 0; i < statenameArray.length; i++) {
                                            if (statenameArray[i].equals(candidateProfile.getData().getDetails().getEmployeeWorkState())) {
                                                job_state_spinner.setSelection(i);
                                            }
                                        }
                                    }
                                }
                            }
                        } else {
                            Toast.makeText(mContext, "Something went wrong", Toast.LENGTH_SHORT).show();
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

    private void postCreateEmployeeProfile() {
        ProgressDialog progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        JSONArray skilljsonarray = new JSONArray(Arrays.asList(skillsIdArray));

        ApiInterface apiInterface = ApiClients.getClient().create(ApiInterface.class);
        Call<CommonResponseModel> createemployeeprofile = apiInterface.postCreateEmployeeProfileMethod(appPreferencesShared.getUserId(),
                fullNameField.getText().toString(), dobField.getText().toString(), gender, addressField.getText().toString(),
                country_spinner.getText().toString(), state_spinner.getText().toString(), prevCompanyNameField.getText().toString(),
                designationField.getText().toString(), workExperience, countryName, statesName,
                profile_headline.getText().toString(), skilljsonarray, jobExperience,
                employment, qualification, salaryExpectation, passport, industryName);

        createemployeeprofile.enqueue(new Callback<CommonResponseModel>() {
            @Override
            public void onResponse(Call<CommonResponseModel> call, Response<CommonResponseModel> response) {
                if (response.isSuccessful()) {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    appPreferencesShared.setIsCandidateProfileEdit(true);
                    Intent intent = new Intent(mContext, DashboardActivity.class);
                    appPreferencesShared.setFragmentDirection("Job");
                    startActivity(intent);
                    finish();
                    Toast.makeText(mContext, "Your profile is created successfully", Toast.LENGTH_SHORT).show();
                } else {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    Toast.makeText(mContext, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onFailure(Call<CommonResponseModel> call, Throwable t) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                Toast.makeText(mContext, "Something Went Wrong", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void postUpdateEmployeeProfile() {
        ProgressDialog progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        JSONArray skilljsonarray = new JSONArray(Arrays.asList(skillsIdArray));

        ApiInterface apiInterface = ApiClients.getClient().create(ApiInterface.class);
        Call<CommonResponseModel> updateemployeeprofile = apiInterface.postUpdateEmployeeProfileMethod(appPreferencesShared.getUserId(), fullNameField.getText().toString(), dobField.getText().toString(),
                gender, addressField.getText().toString(), country_spinner.getText().toString(), state_spinner.getText().toString(),
                prevCompanyNameField.getText().toString(), designationField.getText().toString(),
                workExperience, countryName, statesName, profile_headline.getText().toString(),
                skilljsonarray, jobExperience, employment, qualification, salaryExpectation, passport, industryName);

        updateemployeeprofile.enqueue(new Callback<CommonResponseModel>() {
            @Override
            public void onResponse(Call<CommonResponseModel> call, Response<CommonResponseModel> response) {
                if (response.isSuccessful()) {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    appPreferencesShared.setIsCandidateProfileEdit(true);
                    appPreferencesShared.setFragmentDirection("Job");
                    finish();
                    Toast.makeText(mContext, "Your profile is updated successfully", Toast.LENGTH_SHORT).show();
                } else {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    Toast.makeText(mContext, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CommonResponseModel> call, Throwable t) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                Toast.makeText(mContext, "Something Went Wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onPassportRadioClicked(View view) {

        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.yes_radio:
                if (checked) {
                    passport = "1";
                }
                break;
            case R.id.no_radio:
                if (checked) {
                    passport = "0";
                }

                break;
        }
    }

    public void onGenderRadioClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.male_radio:
                if (checked) {
                    gender = "male";
                }
                break;
            case R.id.female_radio:
                if (checked) {
                    gender = "female";
                }

                break;
        }
    }

    private void showUploadDialogue() {
        String[] items = {"Select photo from gallery", "Capture photo from camera"};
        AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
        alert.setTitle("Select Action")
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                galleryIntent.setType("image/*");
                                startActivityForResult(Intent.createChooser(galleryIntent, "Select Image"), GALLERY);
                                break;

                            case 1:
                                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(cameraIntent, CAMERA);
                                break;
                        }
                    }
                });
        alert.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == GALLERY) {
                if (data != null) {
                    Uri contentURI = data.getData();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(mContext.getContentResolver(), contentURI);
                        userProfileImage = saveImage(bitmap);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                uploadImageMethod();
            } else if (requestCode == CAMERA) {
                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                userProfileImage = saveImage(thumbnail);
                uploadImageMethod();
            }
        }
    }

    private String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File((Environment.getExternalStorageDirectory()).toString());
        if (wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }
        try {
            File f = new File(wallpaperDirectory, (Long.toString(Calendar.getInstance().getTimeInMillis()) + ".jpg"));
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(mContext, new String[]{f.getPath()}, new String[]{"image/jpeg"}, null);
            fo.close();
            return f.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();

        }
        return "";
    }

    private void uploadImageMethod() {
        if (userProfileImage == null || userProfileImage.isEmpty()) {
            Toast.makeText(mContext, "Please Upload an Image", Toast.LENGTH_SHORT).show();
            return;
        } else {
            ProgressDialog progressDialog = new ProgressDialog(mContext);
            progressDialog.setMessage("Please Wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();

            File file = new File(userProfileImage);
            RequestBody userId = RequestBody.create(MediaType.parse("text/plain"), appPreferencesShared.getUserId());
            RequestBody requestFile = RequestBody.create(MediaType.parse("*/*"), file);
            MultipartBody.Part userProfileImage = MultipartBody.Part.createFormData("image", file.getName(), requestFile);
            RequestBody imageFlagKey = RequestBody.create(MediaType.parse("text/plain"), "1");
            RequestBody old_image = RequestBody.create(MediaType.parse("text/plain"),
                    userProfile.getUserDatum().getUserProfileData().getUserProfileImage() == null ? "" : userProfile.getUserDatum().getUserProfileData().getUserProfileImage().toString());

            ApiInterface apiInterface = ApiClients.getClient().create(ApiInterface.class);
            Call<ImageUploadModel> call = apiInterface.uploadImageMethod(userProfileImage, imageFlagKey, userId, old_image);
            call.enqueue(new Callback<ImageUploadModel>() {
                @Override
                public void onResponse(Call<ImageUploadModel> call, Response<ImageUploadModel> response) {
                    try {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                Picasso.with(getApplicationContext()).load(file).resize(200, 200).transform(new CropCircleTransformation()).placeholder(R.drawable.user_dummy).into(candidate_image);
                                Toast.makeText(mContext, "Image Upload Successful", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(mContext, "Something went wrong", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(mContext, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        Toast.makeText(mContext, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ImageUploadModel> call, Throwable t) {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    Toast.makeText(mContext, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private Boolean checkAndRequestPermissions() {
        ArrayList<String> listPermissionsNeeded = new ArrayList<String>();
        String[] permissions = new String[0];
        for (int i = 0; i < appPermissions.length; i++) {
            if (ContextCompat.checkSelfPermission(mContext, appPermissions[i].toString()) != PackageManager.PERMISSION_GRANTED)
                listPermissionsNeeded.add(appPermissions[i].toString());
            permissions = new String[listPermissionsNeeded.size()];
            for (int j = 0; j < listPermissionsNeeded.size(); j++) {
                permissions[j] = listPermissionsNeeded.get(j);
            }
        }

        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(((Activity) mContext), permissions, PERMISSIONS_REQUEST_CODE);
            return false;
        }

        return true;
    }

}
