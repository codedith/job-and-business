package com.xcrino.moro.JobModule;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.xcrino.moro.Interface.ApiInterface;
import com.xcrino.moro.PojoModels.DefaultData;
import com.xcrino.moro.PojoModels.StateListData;
import com.xcrino.moro.R;
import com.xcrino.moro.Utilities.ApiClients;
import com.xcrino.moro.Utilities.AppPreferencesShared;
import com.xcrino.moro.Utilities.ButtonVisibility;
import com.xcrino.moro.Utilities.NetworkStatus;
import com.xcrino.moro.Utilities.Validations;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.Locale;

public class RecruiterAddJobActivity extends AppCompatActivity implements View.OnClickListener {

    Context mContext;
    ImageView back_arrow;
    TextView toolbar_title, skills_spinner, qualification_spinner, state_spinnerField;
    View activity_recruiter_add_job;
    Button next_button;
    EditText job_title, job_vacancies, job_role, job_description, recruiter_name, recruiter_contact_no, recruiter_company_name,
            recruiter_mogo_contact_no;
    Spinner job_experience_spinner, salary_package_spinner,
            industry_spinner, employment_type_spinner, country_spinner;
    String[] jobExperienceArray = {"Select", "0 Year", "1 Years", "2 Years", "3 Years", "4 Years", "5 Years", "6 Years",
            "7 Years", "8 Years", "9 Years", "10 Years", "11 Years", "12 Years", "13 Years", "14 Years", "15 Years",
            "16 Years", "17 Years", "18 Years", "19 Years", "20 Years", "More than 20 Years"}, companyNameArray, statenameArray,
            industrynameArray, qualificationArray, skillsArray, employmentArray = {"Select", "Full-Time", "Part-Time", "Internship", "Contract", "Freelancer"},
            salaryArray = {"Select", "0 lpa - 1 lpa", "1 lpa - 2 lpa", "2 lpa - 3 lpa", "3 lpa - 4 lpa", "4 lpa - 5 lpa",
                    "5 lpa - 6 lpa", "6 lpa - 7 lpa", "7 lpa - 8 lpa", "8 lpa - 9 lpa", "More than 9 lpa"}, stateIdArray, skillsIdArray, qualificationIdArray;
    String jobExperience = "Select", countryId, countryName, industryId, industryName, salaryExpectation = "Select", employment = "Select", skills = "Select",
            state = "select", qualification = "select";
    boolean[] checkedqaulificationItems, checkedSkills, checkedStates;
    private AppPreferencesShared appPreferencesShared;

    private List<String> CountryIdList = new ArrayList<String>();
    private List<String> IndustryIdList = new ArrayList<String>();
    private ArrayList<Integer> StateIdListTwo = new ArrayList<>();

    private ArrayList<Integer> QualificationIdListTwo = new ArrayList<>();
    private ArrayList<Integer> SkillIdListTwo = new ArrayList<>();
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
        setContentView(R.layout.activity_recruiter_add_job);
        mContext = this;

        getLayoutInitUI();

        toolbar_title.setText("Post a Job");
        validations = new Validations();

        if (NetworkStatus.isNetworkAvailable(getApplicationContext())) {
            getDefaultData();
        } else {
            Toast.makeText(getApplicationContext(), "Check Internet Connection", Toast.LENGTH_SHORT).show();
        }

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

        ArrayAdapter<String> salaryExpectationAdapter = new ArrayAdapter<String>(mContext, R.layout.spinner_item, salaryArray);
        salary_package_spinner.setAdapter(salaryExpectationAdapter);
        salary_package_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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


        ButtonVisibility buttonVisibility = new ButtonVisibility();
        buttonVisibility.hideButton(mContext, activity_recruiter_add_job, next_button);

        back_arrow.setOnClickListener(this);
        next_button.setOnClickListener(this);
        skills_spinner.setOnClickListener(this);
        state_spinnerField.setOnClickListener(this);
        qualification_spinner.setOnClickListener(this);
    }

    private void getLayoutInitUI() {
        state_spinnerField = findViewById(R.id.state_spinner);
        back_arrow = findViewById(R.id.back_arrow);
        toolbar_title = findViewById(R.id.toolbar_title);
        activity_recruiter_add_job = findViewById(R.id.activity_recruiter_add_job);
        next_button = findViewById(R.id.next_button);
        job_title = findViewById(R.id.job_title);
        country_spinner = (Spinner) findViewById(R.id.country_spinner);
        job_vacancies = findViewById(R.id.job_vacancies);
        job_experience_spinner = findViewById(R.id.job_experience_spinner);
        salary_package_spinner = findViewById(R.id.salary_package_spinner);
        job_role = findViewById(R.id.job_role);
        skills_spinner = findViewById(R.id.skills_spinner);
        job_description = findViewById(R.id.job_description);
        employment_type_spinner = findViewById(R.id.employment_type_spinner);
        qualification_spinner = findViewById(R.id.qualification_spinner);
        industry_spinner = findViewById(R.id.industry_spinner);
        recruiter_name = findViewById(R.id.recruiter_name);
        recruiter_contact_no = findViewById(R.id.recruiter_contact_no);
        recruiter_company_name = findViewById(R.id.recruiter_company_name);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_arrow:
                onBackPressed();
                break;

            case R.id.qualification_spinner:
                AlertDialog.Builder builderqualification = new AlertDialog.Builder(mContext);
                builderqualification.setTitle("");
                builderqualification.setMultiChoiceItems(qualificationArray, checkedqaulificationItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        if (b) {
                            if (!QualificationIdListTwo.contains(i)) {
                                QualificationIdListTwo.add(i);
                            } else {
                                QualificationIdListTwo.remove(i);
                            }
                        }
                    }
                });
                builderqualification.setCancelable(false);
                builderqualification.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (QualificationIdListTwo.isEmpty()) {
                            qualification_spinner.setText("Select");
                        } else {
                            qualification = "";
                            qualificationIdArray = new String[QualificationIdListTwo.size()];
                            for (int iq = 0; iq < QualificationIdListTwo.size(); iq++) {
                                qualification = qualification + qualificationArray[QualificationIdListTwo.get(iq)];
                                qualificationIdArray[iq] = qualificationArray[QualificationIdListTwo.get(iq)];
                                if (iq != QualificationIdListTwo.size() - 1) {
                                    qualification = qualification + " ,";
                                }
                                qualification_spinner.setText(qualification);
                                QualificationIdListTwo.clear();

                            }
                        }
                    }
                });
                builderqualification.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                builderqualification.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        for (int iq = 0; iq < checkedqaulificationItems.length; iq++) {
                            checkedqaulificationItems[iq] = false;
                            QualificationIdListTwo.clear();
                            qualification_spinner.setText("Select");
                        }
                    }
                });

                AlertDialog dialogqualification = builderqualification.create();
                dialogqualification.show();
                break;

            case R.id.next_button:
                if (validations.isMandatory(job_title.getText().toString(), job_title) &&
                        validations.isMandatory(job_vacancies.getText().toString(), job_vacancies) &&
                        validations.isSpinnerValid(jobExperience, mContext, "Select Job Experience") &&
                        validations.isSpinnerValid(state_spinnerField.getText().toString(), mContext, "Select State") &&
                        validations.isSpinnerValid(salaryExpectation, mContext, "Select Salary") &&
                        validations.isMandatory(job_role.getText().toString(), job_role) &&
                        validations.isSpinnerValid(skills_spinner.getText().toString(), mContext, "Select Skills") &&
                        validations.isMandatory(job_description.getText().toString(), job_description) &&
                        validations.isSpinnerValid(employment, mContext, "Select Employment") &&
                        validations.isSpinnerValid(qualification_spinner.getText().toString(), mContext, "Select Qualification")) {

                    JSONArray stateIdJsonArray = new JSONArray(Arrays.asList(stateIdArray));
                    Log.e("json array", stateIdJsonArray.toString());

                    JSONArray qualificationIdJsonArray = new JSONArray(Arrays.asList(qualificationIdArray));
                    Log.e("json array", qualificationIdJsonArray.toString());

                    JSONArray skillsIdJsonArray = new JSONArray(Arrays.asList(skillsIdArray));
                    Log.e("json array", skillsIdJsonArray.toString());

                    appPreferencesShared.setJobTitle(job_title.getText().toString());
                    appPreferencesShared.setJobVacancies(job_vacancies.getText().toString());
                    appPreferencesShared.setJobExperience(jobExperience);
//                    appPreferencesShared.setCountryId(countryName);
                    appPreferencesShared.setStateSpinner(stateIdJsonArray.toString());
                    appPreferencesShared.setSalaryExpectation(salaryExpectation);
                    appPreferencesShared.setJobRole(job_role.getText().toString());
                    appPreferencesShared.setSkillsSpinner(skillsIdJsonArray.toString());
                    appPreferencesShared.setJobDescription(job_description.getText().toString());
                    appPreferencesShared.setEmployment(employment);
                    appPreferencesShared.setQualificationSpinner(qualificationIdJsonArray.toString());
                    appPreferencesShared.setIndustryId(industryName);

                    Intent intent = new Intent(RecruiterAddJobActivity.this, RecruiterEditDetailActivity.class);
                    startActivity(intent);

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
                        if (SkillIdListTwo.isEmpty()) {
                            skills_spinner.setText("Select");
                        } else {
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
                            SkillIdListTwo.clear();
                        }
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
                        for (int i = 0; i < checkedSkills.length; i++) {
                            checkedSkills[i] = false;
                            SkillIdListTwo.clear();
                            skills_spinner.setText("Select");
                        }
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
                break;

            case R.id.state_spinner:
                AlertDialog.Builder builder1 = new AlertDialog.Builder(mContext);
                builder1.setTitle("");
                builder1.setMultiChoiceItems(statenameArray, checkedStates, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        if (b) {
                            if (!StateIdListTwo.contains(i)) {
                                StateIdListTwo.add(i);
                            } else {
                                StateIdListTwo.remove(i);
                            }
                        }
                    }
                });
                builder1.setCancelable(false);
                builder1.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (StateIdListTwo.isEmpty()) {
                            state_spinnerField.setText("Select");
                        } else {
                            state = "";
                            stateIdArray = new String[StateIdListTwo.size()];
                            for (int i1 = 0; i1 < StateIdListTwo.size(); i1++) {

                                state = state + statenameArray[StateIdListTwo.get(i1)];
                                stateIdArray[i1] = statenameArray[StateIdListTwo.get(i1)];
                                if (i1 != StateIdListTwo.size() - 1) {
                                    state = state + " ,";
                                }

                            }
                            state_spinnerField.setText(state);
                            StateIdListTwo.clear();
                        }
                    }
                });
                builder1.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder1.setNeutralButton("Clear all", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        for (int i1 = 0; i1 < checkedStates.length; i1++) {
                            checkedStates[i1] = false;
                            StateIdListTwo.clear();
                            state_spinnerField.setText("Select");
                        }
                    }
                });
                AlertDialog dialog1 = builder1.create();
                dialog1.show();
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
                                    companyNameArray = new String[response.body().getData().getCountryList().size()];
                                    for (int i = 0; i < response.body().getData().getCountryList().size(); i++) {
                                        CountryIdList.add(response.body().getData().getCountryList().get(i).getId());
                                        companyNameArray[i] = response.body().getData().getCountryList().get(i).getName();
                                    }
                                    ArrayAdapter<String> countryAdapter = new ArrayAdapter<>(mContext, R.layout.spinner_item, companyNameArray);
                                    country_spinner.setAdapter(countryAdapter);
                                    country_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                            countryId = CountryIdList.get(i);
                                            countryName = companyNameArray[i];
                                            getStatesListMethod();
                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> adapterView) {

                                        }
                                    });
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
                                    checkedqaulificationItems = new boolean[qualificationArray.length];
                                } else {
                                    Toast.makeText(mContext, "something went wrong", Toast.LENGTH_SHORT).show();
                                }

                                if (response.body().getData().getSkillsList() != null) {
                                    skillsArray = new String[response.body().getData().getSkillsList().size()];
                                    for (int i = 0; i < response.body().getData().getSkillsList().size(); i++) {
                                        skillsArray[i] = response.body().getData().getSkillsList().get(i).getSkillsName();
                                    }
                                    checkedSkills = new boolean[skillsArray.length];
                                } else {
                                    Toast.makeText(mContext, "something went wrong", Toast.LENGTH_SHORT).show();
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
                                checkedStates = new boolean[statenameArray.length];
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

}