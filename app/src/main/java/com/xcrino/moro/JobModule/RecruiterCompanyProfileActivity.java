package com.xcrino.moro.JobModule;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.xcrino.moro.Activity.DashboardActivity;
import com.xcrino.moro.Interface.ApiInterface;
import com.xcrino.moro.PojoModels.CommonResponseModel;
import com.xcrino.moro.PojoModels.CountryList;
import com.xcrino.moro.PojoModels.CountryListData;
import com.xcrino.moro.PojoModels.GetCompanyDetailsModel;
import com.xcrino.moro.PojoModels.ImageUploadModel;
import com.xcrino.moro.PojoModels.StateList;
import com.xcrino.moro.PojoModels.StateListData;
import com.xcrino.moro.PojoModels.UserProfile;
import com.xcrino.moro.R;
import com.xcrino.moro.Utilities.ApiClients;
import com.xcrino.moro.Utilities.AppPreferencesShared;
import com.xcrino.moro.Utilities.ButtonVisibility;
import com.xcrino.moro.Utilities.NetworkStatus;
import com.xcrino.moro.Utilities.Validations;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecruiterCompanyProfileActivity extends AppCompatActivity implements View.OnClickListener {
    String countryId;
    String stateId;
    Context mContext;
    ImageView back_arrow, company_logo_imageView;
    TextView toolbar_title;
    View activity_recruiter_edit_profile;
    Button submit_button;
    private AppPreferencesShared appPreferencesShared;

    private Spinner country_spinner, state_spinner;
    private String countrieName, statesName, companyProfileImage;
    private List<CountryList> data = null;
    private List<String> CountrynameList = new ArrayList<String>();
    private String[] companyNameArray;
    private String[] statenameArray;
    private ArrayList<String> playerNames = new ArrayList<String>();
    private List<StateList> data1 = null;
    private List<String> StateList = new ArrayList<String>();
    private ArrayList<String> playerNames1 = new ArrayList<String>();
    private List<String> CountryIdList = new ArrayList<String>();
    private List<String> StateIdList = new ArrayList<String>();
    EditText emaiIdField, company_name, company_website, mobileNumberField, addressField, about_company;
    Validations validations;
    private String[] appPermissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA};
    private int PERMISSIONS_REQUEST_CODE = 1024, GALLERY = 0, CAMERA = 1;
    GetCompanyDetailsModel companyDetailsModel;
    UserProfile userProfile;

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
        setContentView(R.layout.activity_recruiter_company_profile);
        mContext = this;

        getLayoutInitUI();

        validations = new Validations();

        checkAndRequestPermissions();

        if (NetworkStatus.isNetworkAvailable(mContext)) {
            getCountryList();
        } else {
            Toast.makeText(mContext, "Please Check Internet Connection", Toast.LENGTH_SHORT).show();
        }

        Gson gson = new Gson();
        String json = appPreferencesShared.getCompanyDetails();
        companyDetailsModel = gson.fromJson(json, GetCompanyDetailsModel.class);

        Gson gSonUserProfile = new Gson();
        String jsonUserProfile = appPreferencesShared.getUserDetails();
        userProfile = gSonUserProfile.fromJson(jsonUserProfile, UserProfile.class);

        if (appPreferencesShared.getCreateCompanyProfile()) {
            toolbar_title.setText("Edit Company Profile");
            emaiIdField.setText(companyDetailsModel.getData().getCompanyDetails().getCompanyEmail().toString());
            emaiIdField.setSelection(emaiIdField.getText().length());
            company_name.setText(companyDetailsModel.getData().getCompanyDetails().getCompanyName().toString());
            company_website.setText(companyDetailsModel.getData().getCompanyDetails().getCompanyWebsite().toString());
            mobileNumberField.setText(companyDetailsModel.getData().getCompanyDetails().getCompanyPhone().toString());
            addressField.setText(companyDetailsModel.getData().getCompanyDetails().getCompanyAddress().toString());
            about_company.setText(companyDetailsModel.getData().getCompanyDetails().getAboutCompany().toString());
            if (companyDetailsModel.getData().getCompanyDetails().getCompanyLogo() != null) {
                Picasso.with(mContext).load(companyDetailsModel.getData().getCompanyDetails().getCompanyLogo()).
                        resize(175, 175).
                        transform(new CropCircleTransformation()).
                        placeholder(R.drawable.user_dummy).into(company_logo_imageView);
            }

        } else {
            toolbar_title.setText("Create Company Profile");
        }

        ButtonVisibility buttonVisibility = new ButtonVisibility();
        buttonVisibility.hideButton(mContext, activity_recruiter_edit_profile, submit_button);

        back_arrow.setOnClickListener(this);
        submit_button.setOnClickListener(this);
        company_logo_imageView.setOnClickListener(this);
    }

    private void getLayoutInitUI() {
        back_arrow = findViewById(R.id.back_arrow);
        toolbar_title = findViewById(R.id.toolbar_title);
        activity_recruiter_edit_profile = findViewById(R.id.activity_recruiter_edit_profile);
        submit_button = findViewById(R.id.submit_button);
        country_spinner = (Spinner) findViewById(R.id.country_spinner);
        state_spinner = (Spinner) findViewById(R.id.state_spinner);
        emaiIdField = findViewById(R.id.emaiIdField);
        company_name = findViewById(R.id.company_name);
        company_website = findViewById(R.id.company_website);
        mobileNumberField = findViewById(R.id.mobileNumberField);
        addressField = findViewById(R.id.addressField);
        about_company = findViewById(R.id.about_company);
        company_logo_imageView = findViewById(R.id.company_logo_imageView);
    }

    @Override
    public void onBackPressed() {

        if (appPreferencesShared.getCreateCompanyProfile()) {
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
                if (appPreferencesShared.getCreateCompanyProfile()) {
                    onBackPressed();
                } else {
                    Intent intent = new Intent(mContext, DashboardActivity.class);
                    startActivity(intent);
                    finish();
                }
                break;

            case R.id.company_logo_imageView:
                showUploadDialogue();
                break;

            case R.id.submit_button:
                if (validations.isEmailValid(emaiIdField.getText().toString(), emaiIdField) &&
                        validations.isMandatory(company_name.getText().toString(), company_name) &&
                        validations.isMandatory(addressField.getText().toString(), addressField) &&
                        validations.isMandatory(about_company.getText().toString(), addressField) &&
                        validations.isMobileValid(mobileNumberField.getText().toString(), mobileNumberField)) {
                    if (appPreferencesShared.getCreateCompanyProfile()) {
                        if (NetworkStatus.isNetworkAvailable(mContext)) {
                            updateCompanyProfile();
                        } else {
                            Toast.makeText(mContext, "Please Check Internet Connection", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        if (NetworkStatus.isNetworkAvailable(mContext)) {
                            createCompanyProfile();
                        } else {
                            Toast.makeText(mContext, "Please Check Internet Connection", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                break;
        }
    }

    private void createCompanyProfile() {
        ProgressDialog progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        ApiInterface apiInterface = ApiClients.getClient().create(ApiInterface.class);
        Call<CommonResponseModel> call = apiInterface.postCreateCompanyProfileMethod(appPreferencesShared.getUserId(), company_name.getText().toString(),
                emaiIdField.getText().toString(), company_website.getText().toString(), addressField.getText().toString(),
                about_company.getText().toString(), countrieName, statesName, mobileNumberField.getText().toString());
        call.enqueue(new Callback<CommonResponseModel>() {
            @Override
            public void onResponse(Call<CommonResponseModel> call, Response<CommonResponseModel> response) {
                try {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    if (response.isSuccessful()) {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
//                        appPreferencesShared.setIsCandidateProfileEdit(true);
                        appPreferencesShared.setCreateCompanyProfile(true);
                        Intent intent = new Intent(mContext, DashboardActivity.class);
                        appPreferencesShared.setFragmentDirection("Job");
                        startActivity(intent);
                        finish();
                        Toast.makeText(mContext, "Your profile is created successfully", Toast.LENGTH_SHORT).show();

//                        appPreferencesShared.setCreateCompanyProfile(true);
//                        finish();
                    } else {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
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
            public void onFailure(Call<CommonResponseModel> call, Throwable t) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                Toast.makeText(mContext, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateCompanyProfile() {
        ProgressDialog progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        ApiInterface apiInterface = ApiClients.getClient().create(ApiInterface.class);
        Call<CommonResponseModel> call = apiInterface.updateCreateCompanyProfileMethod(appPreferencesShared.getUserId(), company_name.getText().toString(),
                emaiIdField.getText().toString(), company_website.getText().toString(), addressField.getText().toString(),
                about_company.getText().toString(), countrieName, statesName, mobileNumberField.getText().toString());
        call.enqueue(new Callback<CommonResponseModel>() {
            @Override
            public void onResponse(Call<CommonResponseModel> call, Response<CommonResponseModel> response) {
                try {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    if (response.isSuccessful()) {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        appPreferencesShared.setCreateCompanyProfile(true);
                        finish();
                        Toast.makeText(mContext, "Your profile is updated successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
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
            public void onFailure(Call<CommonResponseModel> call, Throwable t) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                Toast.makeText(mContext, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
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
                        companyProfileImage = saveImage(bitmap);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                uploadImageMethod();
            } else if (requestCode == CAMERA) {
                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                companyProfileImage = saveImage(thumbnail);
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
        if (companyProfileImage == null || companyProfileImage.isEmpty()) {
            Toast.makeText(mContext, "Please Upload an Image", Toast.LENGTH_SHORT).show();
            return;
        } else {
            ProgressDialog progressDialog = new ProgressDialog(mContext);
            progressDialog.setMessage("Please Wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();

            File file = new File(companyProfileImage);
            RequestBody userId = RequestBody.create(MediaType.parse("text/plain"), appPreferencesShared.getUserId());
            RequestBody requestFile = RequestBody.create(MediaType.parse("*/*"), file);
            MultipartBody.Part userProfileImage = MultipartBody.Part.createFormData("image", file.getName(), requestFile);
            RequestBody imageFlagKey = RequestBody.create(MediaType.parse("text/plain"), "2");
            RequestBody old_image = RequestBody.create(MediaType.parse("text/plain"), /*jobBaseInstance.getOldLogoUrl()*/"");

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
                                Picasso.with(getApplicationContext()).load(file).resize(175, 175).
                                        transform(new CropCircleTransformation()).placeholder(R.drawable.user_dummy).into(company_logo_imageView);
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
                                companyNameArray = new String[response.body().getCountryLists().size()];
                                for (int i = 0; i < response.body().getCountryLists().size(); i++) {
                                    CountryIdList.add(response.body().getCountryLists().get(i).getId());
                                    companyNameArray[i] = response.body().getCountryLists().get(i).getName();
                                }

                                ArrayAdapter<String> countryAdapter = new ArrayAdapter<>(mContext, R.layout.spinner_item, companyNameArray);
                                country_spinner.setAdapter(countryAdapter);
                                country_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        countryId = CountryIdList.get(i);
                                        countrieName = companyNameArray[i];
                                        getStatesListMethod();
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                    }
                                });
                                if (companyDetailsModel != null) {
                                    for (int i = 0; i < companyNameArray.length; i++) {
                                        if (companyNameArray[i].equals(companyDetailsModel.getData().getCompanyDetails().getCountry())) {
                                            country_spinner.setSelection(i);
                                        }
                                    }
                                }

                            }
//                        String countryList = response.body().toString();
//                        CountryJSON(countryList);
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
                                ArrayAdapter<String> stateAdapter = new ArrayAdapter<>(mContext, R.layout.spinner_item, statenameArray);
                                state_spinner.setAdapter(stateAdapter);
                                state_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        stateId = StateIdList.get(i);
                                        statesName = statenameArray[i];
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                    }
                                });

                                if (companyDetailsModel != null) {
                                    for (int i = 0; i < statenameArray.length; i++) {
                                        if (statenameArray[i].equals(companyDetailsModel.getData().getCompanyDetails().getState())) {
                                            state_spinner.setSelection(i);
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

}
