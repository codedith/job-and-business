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
import com.xcrino.moro.Utilities.NetworkStatus;
import com.xcrino.moro.Utilities.Validations;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FiltersActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView toolbar_title, skills_spinner, user_state_spinner, qualification_Spinner;
    private Spinner user_country_spinner;
    private AppPreferencesShared appPreferencesShared;
    private Context mContext;
    private ImageView back_arrow;
    private Button submit_button;
    private Validations validations;

    private boolean[] checkedStates;
    boolean[] checkedqaulificationItems;
    boolean[] checkedSkills;
    private String state = "select";

    private String[] stateIdArray;
    private String skills = "Select";
    private String[] skillsArray;
    private String[] skillsIdArray;
    private String qualification = "select";
    private String[] qualificationIdArray;
    private String[] qualificationArray;

    private String[] companyNameArray;
    private String[] statenameArray;

    private String countryId, countryName;

    private List<String> CountryIdList = new ArrayList<String>();
    private List<String> StateIdList = new ArrayList<String>();
    private ArrayList<Integer> StateIdListTwo = new ArrayList<>();

    private List<String> SkillIdList = new ArrayList<String>();
    private List<String> QualificationIdList = new ArrayList<String>();
    private ArrayList<Integer> QualificationIdListTwo = new ArrayList<>();
    private ArrayList<Integer> SkillIdListTwo = new ArrayList<>();
    String filterState, filterCountry, filterSkills, filterQualification;

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
        setContentView(R.layout.activity_filters);

        mContext = this;
        getUiLayoutInit();
        toolbar_title.setText("Filters");
        validations = new Validations();

        skills_spinner.setOnClickListener(this);
        user_state_spinner.setOnClickListener(this);
        qualification_Spinner.setOnClickListener(this);
        back_arrow.setOnClickListener(this);
        submit_button.setOnClickListener(this);

        if (NetworkStatus.isNetworkAvailable(getApplicationContext())) {
            getDefaultData();
        } else {
            Toast.makeText(getApplicationContext(), "Check Internet Connection", Toast.LENGTH_SHORT).show();
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
                                    user_country_spinner.setAdapter(countryAdapter);
                                    user_country_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                            countryId = CountryIdList.get(i);
                                            filterCountry = companyNameArray[i];
                                            getStatesListMethod();
                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> adapterView) {

                                        }
                                    });
                                }
                                if (response.body().getData().getQualificationList() != null) {
                                    qualificationArray = new String[response.body().getData().getQualificationList().size()];
                                    for (int i = 0; i < response.body().getData().getIndustryList().size(); i++) {
                                        QualificationIdList.add(response.body().getData().getQualificationList().get(i).getQualificationSno());
                                        qualificationArray[i] = response.body().getData().getQualificationList().get(i).getQualificationName();

                                    }
                                } else {
                                    Toast.makeText(mContext, "something went wrong", Toast.LENGTH_SHORT).show();
                                }

                                if (response.body().getData().getSkillsList() != null) {
                                    skillsArray = new String[response.body().getData().getSkillsList().size()];
                                    for (int i = 0; i < response.body().getData().getSkillsList().size(); i++) {
                                        SkillIdList.add(response.body().getData().getSkillsList().get(i).getSkillsNo());
                                        skillsArray[i] = response.body().getData().getSkillsList().get(i).getSkillsName();
                                    }
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
                                    StateIdList.add(response.body().getStateLists().get(i).getId());
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

    private void getUiLayoutInit() {
        back_arrow = (ImageView) findViewById(R.id.back_arrow);
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        skills_spinner = (TextView) findViewById(R.id.skills_spinner);
        user_state_spinner = (TextView) findViewById(R.id.user_state_spinner);
        qualification_Spinner = (TextView) findViewById(R.id.qualification);
        user_country_spinner = (Spinner) findViewById(R.id.user_country_spinner);
        submit_button = (Button) findViewById(R.id.submit_button);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_arrow:
                onBackPressed();
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
                        if (!SkillIdListTwo.isEmpty()) {
                            for (int i = 0; i < checkedSkills.length; i++) {
                                checkedSkills[i] = false;
                                SkillIdListTwo.clear();
                                skills_spinner.setText("Select");
                            }
                        }
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
                break;

            case R.id.user_state_spinner:
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
                        state = "";
                        stateIdArray = new String[StateIdListTwo.size()];
                        for (int i1 = 0; i1 < StateIdListTwo.size(); i1++) {

                            state = state + statenameArray[StateIdListTwo.get(i1)];
                            stateIdArray[i1] = statenameArray[StateIdListTwo.get(i1)];
                            if (i1 != StateIdListTwo.size() - 1) {
                                state = state + " ,";
                            }

                        }
                        user_state_spinner.setText(state);
                        StateIdListTwo.clear();
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
                            user_state_spinner.setText("Select");
                        }
                    }
                });
                AlertDialog dialog1 = builder1.create();
                dialog1.show();
                break;

            case R.id.qualification:
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
                        qualification = "";
                        qualificationIdArray = new String[QualificationIdListTwo.size()];
                        for (int iq = 0; iq < QualificationIdListTwo.size(); iq++) {
                            qualification = qualification + qualificationArray[QualificationIdListTwo.get(iq)];
                            qualificationIdArray[iq] = qualificationArray[QualificationIdListTwo.get(iq)];
                            if (iq != QualificationIdListTwo.size() - 1) {
                                qualification = qualification + " ,";
                            }
                            qualification_Spinner.setText(qualification);
                            QualificationIdListTwo.clear();

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
                            qualification_Spinner.setText("Select");
                        }
                    }
                });

                AlertDialog dialogqualification = builderqualification.create();
                dialogqualification.show();
                break;

            case R.id.submit_button:
                if (user_state_spinner.getText().equals("Select")) {
                    filterState = "";
                } else {
                    JSONArray stateIdJsonArray = new JSONArray(Arrays.asList(stateIdArray));
                    filterState = stateIdJsonArray.toString();
                }

                if (skills_spinner.getText().equals("Select")) {
                    filterSkills = "";
                } else {
                    JSONArray skillsIdJsonArray = new JSONArray(Arrays.asList(skillsIdArray));
                    filterSkills = skillsIdJsonArray.toString();
                }

                if (qualification_Spinner.getText().equals("Select")) {
                    filterQualification = "";
                } else {
                    JSONArray qualificationIdJsonArray = new JSONArray(Arrays.asList(qualificationIdArray));
                    filterQualification = qualificationIdJsonArray.toString();
                }

                Log.d("filterState", filterState.toString());
                Log.d("filterSkills", filterSkills.toString());
                Log.d("filterQualification", filterQualification.toString());
                Log.d("filterCountry", filterCountry.toString());

                Intent intent = new Intent(mContext, ApplyJobActivity.class);
                intent.putExtra("filterState", filterState.toString());
                intent.putExtra("filterSkills", filterSkills.toString());
                intent.putExtra("filterQualification", filterQualification.toString());
                intent.putExtra("filterCountry", filterCountry.toString());
                finish();
                startActivity(intent);


//                appPreferencesShared.setCountryId(countryName);
//                appPreferencesShared.setStateSpinner(stateIdJsonArray.toString());
//                appPreferencesShared.setSkillsSpinner(skillsIdJsonArray.toString());
//                appPreferencesShared.setQualificationSpinner(qualificationIdJsonArray.toString());

                break;
        }
    }
}