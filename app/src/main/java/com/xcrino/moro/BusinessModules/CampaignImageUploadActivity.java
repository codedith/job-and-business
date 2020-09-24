package com.xcrino.moro.BusinessModules;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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

public class CampaignImageUploadActivity extends AppCompatActivity implements View.OnClickListener {

    private Context mContext;
    private TextView toolbar_title;
    private ImageView back_arrow, image_uploaded;
    private Button boostNow_Btn;
    private RelativeLayout compaignIMageUpload;
    private AppPreferencesShared appPreferencesShared;
    private String[] appPermissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA}, targetCountryArray, selectCountriesArray;
    String[] localUsersArray = {"9540040448", "8130973061"};
    private int PERMISSIONS_REQUEST_CODE = 1024, GALLERY = 0, CAMERA = 1;
    private String uploaded_image, database_type_selected = "local", timeStamp = "Select", countries = "Select", selected_countries = "Select",
            localUsers = "Select", userCountMoro = "Select";
    CardView upload_image_card;
    LinearLayout user_count_ll;
    static TextView timeStampField;
    private boolean[] checkedItems;
    ArrayList<Integer> selectedCountriesName = new ArrayList<>();
    TextView country_spinner;
    RadioButton LocalDatabase_radio;
    Validations validations;
    private static final String IMAGE_DIRECTORY = "/moro";

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
        setContentView(R.layout.activity_campaign_image_upload);

        mContext = this;
        checkAndRequestPermissions();
        getInitUiId();

        toolbar_title.setText("Image Campaign");
        validations = new Validations();
        boostNow_Btn.setOnClickListener(this);
        back_arrow.setOnClickListener(this);
        upload_image_card.setOnClickListener(this);
    }

    private void getInitUiId() {
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        back_arrow = (ImageView) findViewById(R.id.back_arrow);
        image_uploaded = (ImageView) findViewById(R.id.image_uploaded);
        boostNow_Btn = (Button) findViewById(R.id.boostNow_Btn);
        compaignIMageUpload = (RelativeLayout) findViewById(R.id.compaignIMageUpload);
        upload_image_card = findViewById(R.id.upload_image_card);
        LocalDatabase_radio = findViewById(R.id.LocalDatabase_radio);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_arrow:
                onBackPressed();
                break;

            case R.id.upload_image_card:
                showUploadDialogue();
                break;

            case R.id.boostNow_Btn:
                if (uploaded_image == null || uploaded_image.isEmpty()) {
                    Toast.makeText(mContext, "Please Upload an Image", Toast.LENGTH_SHORT).show();
                } else {
                    boostDialogue();
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
                        image_uploaded.setImageBitmap(bitmap);
                        uploaded_image = saveImage(bitmap);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else if (requestCode == CAMERA) {
                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                image_uploaded.setImageBitmap(thumbnail);
                uploaded_image = saveImage(thumbnail);
            }
        }
    }

    private String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath());

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }

    private void uploadImageCampaignMethod() {
        if (uploaded_image == null || uploaded_image.isEmpty()) {
            Toast.makeText(mContext, "Please Upload an Image", Toast.LENGTH_SHORT).show();
            return;
        } else {
            ProgressDialog progressDialog = new ProgressDialog(mContext);
            progressDialog.setMessage("Please Wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();

            File file = new File(uploaded_image);
            RequestBody requestFile = RequestBody.create(MediaType.parse("*/*"), file);
            MultipartBody.Part userProfileImage = MultipartBody.Part.createFormData("file", file.getName(), requestFile);

            RequestBody userId = RequestBody.create(MediaType.parse("text/plain"), appPreferencesShared.getUserId());
            RequestBody campaignMode = RequestBody.create(MediaType.parse("text/plain"), "image");
            RequestBody text = RequestBody.create(MediaType.parse("text/plain"), "");
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

    private void boostDialogue() {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.campaign_more_details, null);
        timeStampField = alertLayout.findViewById(R.id.timeStampField);
        country_spinner = alertLayout.findViewById(R.id.country_spinner);
        final EditText user_count_tv = alertLayout.findViewById(R.id.user_count_tv);
        Button submitAllField_button = alertLayout.findViewById(R.id.submitAllField_button);
        user_count_ll = alertLayout.findViewById(R.id.user_count_ll);

        timeStampField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(v);
                showDatePickerDialog(v);
            }
        });

        country_spinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NetworkStatus.isNetworkAvailable(mContext)) {
                    getCountryList();
                } else {
                    Toast.makeText(mContext, "Please Check Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        // this is set the view from XML inside AlertDialog
        alert.setView(alertLayout);
        // disallow cancel of AlertDialog on click of back button and outside touch
        alert.setCancelable(false);

        AlertDialog dialog = alert.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        submitAllField_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validations.isTvMandatory(timeStampField.getText().toString(), timeStampField) &&
                        validations.isSpinnerValid(selected_countries, mContext, "Select your target country")) {
                    timeStamp = timeStampField.getText().toString();
                    JSONArray countryArray = new JSONArray(Arrays.asList(selectCountriesArray));
                    countries = countryArray.toString();
                    if (database_type_selected.equals("local")) {
                        if (localUsersArray.length != 0) {
                            JSONArray local_users = new JSONArray(Arrays.asList(localUsersArray));
                            localUsers = local_users.toString();
                            if (NetworkStatus.isNetworkAvailable(mContext)) {
                                uploadImageCampaignMethod();
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
                                uploadImageCampaignMethod();
                            } else {
                                Toast.makeText(mContext, "Please Check Internet Connection", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
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