package com.xcrino.moro.BusinessModules;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.xcrino.moro.Activity.SubscriptionPlanActivity;
import com.xcrino.moro.Interface.ApiInterface;
import com.xcrino.moro.PojoModels.AddCompaignFileResponse;
import com.xcrino.moro.PojoModels.CountryListData;
import com.xcrino.moro.R;
import com.xcrino.moro.Utilities.ApiClients;
import com.xcrino.moro.Utilities.AppPreferencesShared;
import com.xcrino.moro.Utilities.NetworkStatus;
import com.xcrino.moro.Utilities.Validations;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CampaignTextActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView back_arrow;
    TextView title, country_spinner;
    EditText uploaded_text, user_count_tv;
    private AppPreferencesShared appPreferencesShared;
    private Context mContext;
    LinearLayout user_count_ll;
    String[] localUsersArray = {"9540040448", "8130973061"};
    private String database_type_selected = "local", timeStamp = "Select", countries = "Select", selected_countries = "Select",
            localUsers = "Select", userCountMoro = "Select";
    static TextView timeStampField;
    private boolean[] checkedItems;
    ArrayList<Integer> selectedCountriesName = new ArrayList<>();
    private String[] targetCountryArray, selectCountriesArray;
    Button boost_now_button;
    Validations validations;

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
        setContentView(R.layout.activity_campaign_text);

        mContext = this;
        getInitUiId();

        title.setText("Text Campaign Mode");
        validations = new Validations();
        back_arrow.setOnClickListener(this);
        timeStampField.setOnClickListener(this);
        country_spinner.setOnClickListener(this);
        boost_now_button.setOnClickListener(this);
    }

    private void getInitUiId() {
        back_arrow = (ImageView) findViewById(R.id.back_arrow);
        title = findViewById(R.id.toolbar_title);
        uploaded_text = findViewById(R.id.uploaded_text);
        timeStampField = findViewById(R.id.timeStampField);
        country_spinner = findViewById(R.id.country_spinner);
        user_count_ll = findViewById(R.id.user_count_ll);
        user_count_tv = findViewById(R.id.user_count_tv);
        boost_now_button = findViewById(R.id.boost_now_button);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_arrow:
                onBackPressed();
                break;

            case R.id.timeStampField:
                showTimePickerDialog(view);
                showDatePickerDialog(view);
                break;

            case R.id.country_spinner:
                if (NetworkStatus.isNetworkAvailable(mContext)) {
                    getCountryList();
                } else {
                    Toast.makeText(mContext, "Please Check Internet Connection", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.boost_now_button:
                if (validations.isMandatory(uploaded_text.getText().toString(), uploaded_text) &&
                        validations.isTvMandatory(timeStampField.getText().toString(), timeStampField) &&
                        validations.isSpinnerValid(selected_countries, mContext, "Select your target country")) {
                    timeStamp = timeStampField.getText().toString();
                    JSONArray countryArray = new JSONArray(Arrays.asList(selectCountriesArray));
                    countries = countryArray.toString();
                    if (uploaded_text.getText().length() <= 4000) {
                        if (database_type_selected.equals("local")) {
                            if (localUsersArray.length != 0) {
                                JSONArray local_users = new JSONArray(Arrays.asList(localUsersArray));
                                localUsers = local_users.toString();
                                if (NetworkStatus.isNetworkAvailable(mContext)) {
                                    uploadTextCampaignMethod();
                                } else {
                                    Toast.makeText(mContext, "Please Check Internet Connection", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(mContext, "Select the users from your contact list", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            if (validations.isMandatory(user_count_tv.getText().toString(), user_count_tv)) {
                                userCountMoro = user_count_tv.getText().toString();
                                localUsers = "[]";
                                if (NetworkStatus.isNetworkAvailable(mContext)) {
                                    uploadTextCampaignMethod();
                                } else {
                                    Toast.makeText(mContext, "Please Check Internet Connection", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Maximum Limit of the campaign text is 4000 Characters", Toast.LENGTH_SHORT).show();
                    }

                }
                break;
        }
    }

    private void uploadTextCampaignMethod() {
        ProgressDialog progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

//            File file = new File(uploaded_video);
        RequestBody requestFile = RequestBody.create(MediaType.parse("*/*"), "");
        MultipartBody.Part userProfileImage = MultipartBody.Part.createFormData("file", "", requestFile);

        RequestBody userId = RequestBody.create(MediaType.parse("text/plain"), appPreferencesShared.getUserId());
        RequestBody campaignMode = RequestBody.create(MediaType.parse("text/plain"), "text");
        RequestBody text = RequestBody.create(MediaType.parse("text/plain"), uploaded_text.getText().toString());
        RequestBody timestamp = RequestBody.create(MediaType.parse("text/plain"), timeStamp);
        RequestBody countriesFinal = RequestBody.create(MediaType.parse("text/plain"), countries);
        RequestBody database_type = RequestBody.create(MediaType.parse("text/plain"), database_type_selected);
        RequestBody local_users = RequestBody.create(MediaType.parse("text/plain"), localUsers);
        RequestBody link = RequestBody.create(MediaType.parse("text/plain"), "");
        RequestBody user_count = RequestBody.create(MediaType.parse("text/plain"), userCountMoro);

        ApiInterface apiInterface = ApiClients.getClient().create(ApiInterface.class);
        Call<AddCompaignFileResponse> call = apiInterface.boostCampaignMethod(userProfileImage, userId, campaignMode,
                text, timestamp, countriesFinal, database_type, local_users, link, user_count);
        call.enqueue(new Callback<AddCompaignFileResponse>() {
            @Override
            public void onResponse(Call<AddCompaignFileResponse> call, Response<AddCompaignFileResponse> response) {
                try {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    if (response.isSuccessful()) {
                        if (response.body().getResult()) {
                            Toast.makeText(mContext, "Campaign Uploaded Successful", Toast.LENGTH_SHORT).show();
                            AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
                            alert.setTitle("MoRo");
                            alert.setMessage("Your campaign will be active once it has been verified by our team.");
                            // disallow cancel of AlertDialog on click of back button and outside touch
                            alert.setCancelable(false);

                            alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            });

                            AlertDialog dialog = alert.create();
                            dialog.show();
                        } else if (response.body().getData() != null) {
                            Intent intent = new Intent(mContext, SubscriptionPlanActivity.class);
                            appPreferencesShared.setSubscriptionDirection("Business");
                            startActivity(intent);
                        } else {
                            Toast.makeText(mContext, response.body().getMessage().toString(), Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    } else {
                        Toast.makeText(mContext, "Something went wrong", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                } catch (Exception e) {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    Toast.makeText(mContext, "Something went wrong", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<AddCompaignFileResponse> call, Throwable t) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                Toast.makeText(mContext, "Something went wrong", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    public void databaseType(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.LocalDatabase_radio:
                if (checked) {
                    database_type_selected = "local";
                    user_count_ll.setVisibility(View.GONE);
                }
                break;
            case R.id.MoRoDatabase_radio:
                if (checked) {
                    database_type_selected = "mogo";
                    user_count_ll.setVisibility(View.VISIBLE);
                }

                break;
        }
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public static class DatePickerFragment extends DialogFragment implements
            DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        @Override
        public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
            timeStampField.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
        }
    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    public static class TimePickerFragment extends DialogFragment implements
            TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // Do something with the time chosen by the user
            timeStampField.setText(timeStampField.getText() + " -" + hourOfDay + ":" + minute);
        }
    }

    private void getCountryList() {
        ProgressDialog progressDialogcountry = new ProgressDialog(mContext);
        progressDialogcountry.setMessage("Please Wait...");
        progressDialogcountry.setCancelable(false);
        progressDialogcountry.show();

        ApiInterface apiInterface = ApiClients.getClient().create(ApiInterface.class);
        Call<CountryListData> countryListDataCall = apiInterface.getCountryListMethod();

        countryListDataCall.enqueue(new Callback<CountryListData>() {
            @Override
            public void onResponse(Call<CountryListData> call, Response<CountryListData> response) {
                try {
                    if (progressDialogcountry.isShowing()) {
                        progressDialogcountry.dismiss();
                    }
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            if (response.body().getCountryLists() != null) {
                                targetCountryArray = new String[response.body().getCountryLists().size()];
                                for (int i = 0; i < response.body().getCountryLists().size(); i++) {
                                    targetCountryArray[i] = response.body().getCountryLists().get(i).getName();
                                }

                                checkedItems = new boolean[targetCountryArray.length];

                                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                                builder.setTitle("Target Countries");
                                builder.setMultiChoiceItems(targetCountryArray, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                        if (isChecked) {
                                            if (!selectedCountriesName.contains(which)) {
                                                selectedCountriesName.add(which);
                                            }
                                        } else if (selectedCountriesName.contains(which)) {
                                            selectedCountriesName.remove(which);
                                        }
                                    }
                                });

                                builder.setCancelable(false);
                                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if (selectedCountriesName.isEmpty()) {
                                            country_spinner.setText("Select");
                                        } else {
                                            selected_countries = "";
                                            selectCountriesArray = new String[selectedCountriesName.size()];
                                            for (int i = 0; i < selectedCountriesName.size(); i++) {
                                                selected_countries = selected_countries + targetCountryArray[selectedCountriesName.get(i)];
                                                selectCountriesArray[i] = targetCountryArray[selectedCountriesName.get(i)];
                                                if (i != selectedCountriesName.size() - 1) {
                                                    selected_countries = selected_countries + ", ";
                                                }
                                            }
                                            country_spinner.setText(selected_countries);
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
                                        for (int i = 0; i < checkedItems.length; i++) {
                                            checkedItems[i] = false;
                                            selectedCountriesName.clear();
                                            country_spinner.setText("Select");
                                        }
                                    }
                                });

                                AlertDialog dialog = builder.create();
                                dialog.show();
                            }
                        } else {
                            Toast.makeText(mContext, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(mContext, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    if (progressDialogcountry.isShowing()) {
                        progressDialogcountry.dismiss();
                    }
                    Toast.makeText(mContext, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CountryListData> call, Throwable t) {
                if (progressDialogcountry.isShowing()) {
                    progressDialogcountry.dismiss();
                }
                Toast.makeText(mContext, "Something went wrong", Toast.LENGTH_SHORT).show();

            }
        });

    }
}
