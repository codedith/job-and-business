package com.xcrino.moro.BusinessModules;

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
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.xcrino.moro.Interface.ApiInterface;
import com.xcrino.moro.PojoModels.BusinessModuleDetailsResponse;
import com.xcrino.moro.PojoModels.CommonResponseModel;
import com.xcrino.moro.PojoModels.DefaultData;
import com.xcrino.moro.PojoModels.ImageUploadModel;
import com.xcrino.moro.PojoModels.StateListData;
import com.xcrino.moro.PojoModels.UserProfile;
import com.xcrino.moro.R;
import com.xcrino.moro.Utilities.ApiClients;
import com.xcrino.moro.Utilities.AppPreferencesShared;
import com.xcrino.moro.Utilities.ButtonVisibility;
import com.xcrino.moro.Utilities.NetworkStatus;
import com.xcrino.moro.Utilities.Validations;

import org.json.JSONArray;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
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

public class BusinessCreateProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private TextView toolbar_title, category_spinner, CompanyContactNo;
    private ImageView back_arrow, candidate_image;
    private Button submit_button;
    private Context mContext;
    private AppPreferencesShared appPreferencesShared;
    private RelativeLayout activity_BusienssDetails;
    private EditText companyBusinessName, addressOfCompany, emailOfCompany, websiteOfCompany, EstablishedInDate, CompanyAbout;
    private Spinner country_spinner, companyStateSpinner;
    private String[] statenameArray;
    private List<String> CountryIdList = new ArrayList<String>();
    private List<String> StateIdList = new ArrayList<String>();
    private String countryId, stateId, countryName, stateName, userProfileImage;
    private Validations validations;
    String business = "Select";
    private boolean[] checkedItems;
    private UserProfile userProfile;
    private BusinessModuleDetailsResponse mBusinessModuleName;
    private String[] countryNameArray, businessCategory, businessCategoryId;
    ArrayList<String> businessCategoryIdFromJson = new ArrayList<>();
    ArrayList<Integer> selectedBusinessCategoryName = new ArrayList<>();

    private String[] appPermissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
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

        appPreferencesShared = new AppPreferencesShared(this);
        if (appPreferencesShared.getmDayNightMode()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        setContentView(R.layout.activity_business_details);

        mContext = this;
        getLayoutUiId();

        validations = new Validations();

        ButtonVisibility buttonVisibility = new ButtonVisibility();
        buttonVisibility.hideButton(mContext, activity_BusienssDetails, submit_button);

        back_arrow.setOnClickListener(this);
        submit_button.setOnClickListener(this);
        category_spinner.setOnClickListener(this);
        candidate_image.setOnClickListener(this);

        Gson gSonUserProfile = new Gson();
        String jsonUserProfile = appPreferencesShared.getUserDetails();
        userProfile = gSonUserProfile.fromJson(jsonUserProfile, UserProfile.class);

        Gson gsonBusinessModuleDetailsResponse = new Gson();
        String json = appPreferencesShared.getBusinessModuleDetails();
        mBusinessModuleName = gsonBusinessModuleDetailsResponse.fromJson(json, BusinessModuleDetailsResponse.class);

        if (appPreferencesShared.getBusinessCreateCompanyProfile()) {
            toolbar_title.setText("Edit Buisness Profile");
            companyBusinessName.setText(mBusinessModuleName.getBusinessModuleData().getCompanyDetails().getUbsName() == null ? "" : mBusinessModuleName.getBusinessModuleData().getCompanyDetails().getUbsName());
            companyBusinessName.setSelection(companyBusinessName.getText().length());
            String data = mBusinessModuleName.getBusinessModuleData().getCompanyDetails().getUbsCategory() == null ? "" : mBusinessModuleName.getBusinessModuleData().getCompanyDetails().getUbsCategory();
            category_spinner.setText(data);
            String[] arr = mBusinessModuleName.getBusinessModuleData().getCompanyDetails().getUbsCategory().split(",");
            businessCategoryId = new String[arr.length];
            for (int i = 0; i < arr.length; i++) {
                businessCategoryId[i] = arr[i].trim();
            }
            addressOfCompany.setText(mBusinessModuleName.getBusinessModuleData().getCompanyDetails().getUbsAddress() + ", " + mBusinessModuleName.getBusinessModuleData().getCompanyDetails().getUbsState()
                    + " " + mBusinessModuleName.getBusinessModuleData().getCompanyDetails().getUbsCountry());
            emailOfCompany.setText(mBusinessModuleName.getBusinessModuleData().getCompanyDetails().getUbsEmail() == null ? "" : mBusinessModuleName.getBusinessModuleData().getCompanyDetails().getUbsEmail());
            websiteOfCompany.setText(mBusinessModuleName.getBusinessModuleData().getCompanyDetails().getUbsWebsite() == null ? "" : mBusinessModuleName.getBusinessModuleData().getCompanyDetails().getUbsWebsite());
            EstablishedInDate.setText(mBusinessModuleName.getBusinessModuleData().getCompanyDetails().getUbsEstYear() == null ? "" : mBusinessModuleName.getBusinessModuleData().getCompanyDetails().getUbsEstYear());
            CompanyContactNo.setText(mBusinessModuleName.getBusinessModuleData().getCompanyDetails().getUbsContactNo() == null ? "" : mBusinessModuleName.getBusinessModuleData().getCompanyDetails().getUbsContactNo());
            CompanyAbout.setText(mBusinessModuleName.getBusinessModuleData().getCompanyDetails().getUbsAbout() == null ? "" : mBusinessModuleName.getBusinessModuleData().getCompanyDetails().getUbsAbout());
            if (userProfile.getUserDatum().getUserProfileData().getUserProfileImage() != null) {
                Picasso.with(getApplicationContext()).load(userProfile.getUserDatum().getUserProfileData().getUserProfileImage()).
                        resize(200, 200).transform(new CropCircleTransformation()).placeholder(R.drawable.user_dummy).into(candidate_image);
            }
        } else {
            toolbar_title.setText("Create Business Profile");
            companyBusinessName.setText(userProfile.getUserDatum().getUserProfileData().getUserBusinessName().toString());
            if (userProfile.getUserDatum().getUserBc().isEmpty()) {
                category_spinner.setText("Select");
            } else {
                category_spinner.setText("");
                for (int i = 0; i < userProfile.getUserDatum().getUserBc().size(); i++) {
                    category_spinner.append(userProfile.getUserDatum().getUserBc().get(i).getUserBusinessCategory());
                    if (i != userProfile.getUserDatum().getUserBc().size() - 1) {
                        category_spinner.append(", ");
                    }
                }
                category_spinner.setTextColor(getResources().getColor(R.color.grey));
            }

            if (userProfile.getUserDatum().getUserProfileData().getUserProfileImage() != null) {
                Picasso.with(getApplicationContext()).load(userProfile.getUserDatum().getUserProfileData().getUserProfileImage()).
                        resize(200, 200).transform(new CropCircleTransformation()).placeholder(R.drawable.user_dummy).into(candidate_image);
            }
        }

        CompanyContactNo.setText(userProfile.getUserDatum().getUserProfileData().getUserMobile().toString());

        if (NetworkStatus.isNetworkAvailable(mContext)) {
            getDefaultData();
        } else {
            Toast.makeText(mContext, "Please Check Your Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }

    private void getLayoutUiId() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        activity_BusienssDetails = (RelativeLayout) findViewById(R.id.activity_BusienssDetails);
        submit_button = (Button) findViewById(R.id.submit_button);
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        back_arrow = (ImageView) findViewById(R.id.back_arrow);
        candidate_image = (ImageView) findViewById(R.id.candidate_image);
        country_spinner = (Spinner) findViewById(R.id.country_spinner);
        companyStateSpinner = (Spinner) findViewById(R.id.companyStateSpinner);
        companyBusinessName = (EditText) findViewById(R.id.companyBusinessName);
        addressOfCompany = (EditText) findViewById(R.id.addressOfCompany);
        emailOfCompany = (EditText) findViewById(R.id.emailOfCompany);
        websiteOfCompany = (EditText) findViewById(R.id.websiteOfCompany);
        EstablishedInDate = (EditText) findViewById(R.id.EstablishedInDate);
        CompanyContactNo = (TextView) findViewById(R.id.CompanyContactNo);
        CompanyAbout = (EditText) findViewById(R.id.CompanyAbout);
        category_spinner = (TextView) findViewById(R.id.category_spinner);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_arrow:
                onBackPressed();
                break;

            case R.id.submit_button:
                if (appPreferencesShared.getBusinessCreateCompanyProfile()) {
                    if (validations.isMandatory(companyBusinessName.getText().toString(), companyBusinessName) &&
                            validations.isTvMandatory(category_spinner.getText().toString(), category_spinner) &&
                            validations.isMandatory(addressOfCompany.getText().toString(), addressOfCompany) &&
                            validations.isMandatory(emailOfCompany.getText().toString(), emailOfCompany) &&
                            validations.isMandatory(EstablishedInDate.getText().toString(), EstablishedInDate) &&
                            validations.isMandatory(CompanyAbout.getText().toString(), CompanyAbout)) {

                        if (NetworkStatus.isNetworkAvailable(mContext)) {
                            postEditBusinessProfileMethod(companyBusinessName.getText().toString(), businessCategoryId,
                                    addressOfCompany.getText().toString(), countryName, stateName, emailOfCompany.getText().toString(),
                                    websiteOfCompany.getText().toString(), EstablishedInDate.getText().toString(),
                                    CompanyContactNo.getText().toString(), CompanyAbout.getText().toString());
                        } else {
                            Toast.makeText(mContext, "Please Check Your Internet Connection !", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    if (validations.isMandatory(companyBusinessName.getText().toString(), companyBusinessName) &&
                            validations.isTvMandatory(category_spinner.getText().toString(), category_spinner) &&
                            validations.isMandatory(addressOfCompany.getText().toString(), addressOfCompany) &&
                            validations.isMandatory(emailOfCompany.getText().toString(), emailOfCompany) &&
                            validations.isMandatory(EstablishedInDate.getText().toString(), EstablishedInDate) &&
                            validations.isMandatory(CompanyAbout.getText().toString(), CompanyAbout)) {

                        if (NetworkStatus.isNetworkAvailable(mContext)) {
                            postCreateBusinessProfileMethod(companyBusinessName.getText().toString(), businessCategoryId,
                                    addressOfCompany.getText().toString(), countryName, stateName, emailOfCompany.getText().toString(),
                                    websiteOfCompany.getText().toString(), EstablishedInDate.getText().toString(),
                                    CompanyContactNo.getText().toString(), CompanyAbout.getText().toString());
                        } else {
                            Toast.makeText(mContext, "Please Check Your Internet Connection !", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                break;

            case R.id.category_spinner:
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("");
                builder.setMultiChoiceItems(businessCategory, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if (isChecked) {
                            if (!selectedBusinessCategoryName.contains(which)) {
                                selectedBusinessCategoryName.add(which);
                            }
                        } else if (selectedBusinessCategoryName.contains(which)) {
                            selectedBusinessCategoryName.remove(which);
                        }
                    }
                });

                builder.setCancelable(false);
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (selectedBusinessCategoryName.isEmpty()) {
                            category_spinner.setText("Select");
                        } else {
                            business = "";
                            businessCategoryId = new String[selectedBusinessCategoryName.size()];
                            for (int i = 0; i < selectedBusinessCategoryName.size(); i++) {
                                business = business + businessCategory[selectedBusinessCategoryName.get(i)];
//                            businessCategoryId[i] = businessCategoryIdFromJson.get(selectedBusinessCategoryName.get(i));
                                businessCategoryId[i] = businessCategory[selectedBusinessCategoryName.get(i)];
                                if (i != selectedBusinessCategoryName.size() - 1) {
                                    business = business + ", ";
                                }
                            }
                            category_spinner.setText(business);
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
                            selectedBusinessCategoryName.clear();
                            category_spinner.setText("Select");
                        }
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
                break;

            case R.id.candidate_image:
                showUploadDialogue();
                break;

        }
    }

    private void postCreateBusinessProfileMethod(String businessNameOfCompany, String[] categoryTypeSpinner,
                                                 String companyaddress, String mCountryName, String mStateName,
                                                 String companyOfEmail, String companWebsitey, String establishedDate,
                                                 String companyContact, String aboutCompany) {

        ProgressDialog progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        JSONArray category = new JSONArray(Arrays.asList(categoryTypeSpinner));

        ApiInterface apiInterface = ApiClients.getClient().create(ApiInterface.class);
        Call<CommonResponseModel> commonResponseModelCall = apiInterface.postCreateBusinessProfileMethod(appPreferencesShared.getUserId(),
                businessNameOfCompany, category, companyaddress, mCountryName, mStateName, companyOfEmail,
                companWebsitey, establishedDate, companyContact, aboutCompany);

        commonResponseModelCall.enqueue(new Callback<CommonResponseModel>() {
            @Override
            public void onResponse(Call<CommonResponseModel> call, Response<CommonResponseModel> response) {
                try {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    if (response.isSuccessful()) {
                        if (response.body().getResult()) {
                            appPreferencesShared.setBusinessCreateCompanyProfile(true);
//                            Intent intent = new Intent(mContext, DashboardActivity.class);
//                            appPreferencesShared.setFragmentDirection("Business");
//                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(mContext, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(mContext, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.getMessage();
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
                                    country_spinner.setAdapter(countryAdapter);
                                    country_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                                    if (userProfile != null) {
                                        if (userProfile.getUserDatum().getUserProfileData().getUserCountryId() != null && !userProfile.getUserDatum().getUserProfileData().getUserCountryId().isEmpty()) {
                                            for (int i = 0; i < countryNameArray.length; i++) {
                                                if (countryNameArray[i].equals(userProfile.getUserDatum().getUserProfileData().getUserCountryId())) {
                                                    country_spinner.setSelection(i);
                                                }
                                            }
                                        }
                                    }
                                }

                                if (response.body().getData().getBusinessCategoryData() != null) {
                                    businessCategory = new String[response.body().getData().getBusinessCategoryData().size()];
                                    for (int i = 0; i < response.body().getData().getBusinessCategoryData().size(); i++) {
                                        businessCategoryIdFromJson.add(response.body().getData().getBusinessCategoryData().get(i).getBcSno());
                                        businessCategory[i] = response.body().getData().getBusinessCategoryData().get(i).getBcName();
                                    }
                                    checkedItems = new boolean[businessCategory.length];

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
                                ArrayAdapter<String> stateAdapter = new ArrayAdapter<>(mContext, R.layout.spinner_item, statenameArray);
                                companyStateSpinner.setAdapter(stateAdapter);
                                companyStateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        stateId = StateIdList.get(i);
                                        stateName = statenameArray[i];
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                    }
                                });

                                if (userProfile.getUserDatum().getUserProfileData().getUserStateId().toString() != null && !userProfile.getUserDatum().getUserProfileData().getUserStateId().isEmpty()) {
                                    for (int i = 0; i < statenameArray.length; i++) {
                                        if (statenameArray[i].equals(userProfile.getUserDatum().getUserProfileData().getUserStateId().toString())) {
                                            companyStateSpinner.setSelection(i);
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

    private void postEditBusinessProfileMethod(String businessNameOfCompany, String[] categoryTypeSpinner,
                                               String companyaddress, String mCountryName, String mStateName,
                                               String companyOfEmail, String companWebsitey, String establishedDate,
                                               String companyContact, String aboutCompany) {

        ProgressDialog progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        JSONArray category = new JSONArray(Arrays.asList(categoryTypeSpinner));

        ApiInterface apiInterface = ApiClients.getClient().create(ApiInterface.class);
        Call<CommonResponseModel> commonResponseModelCall = apiInterface.postEditBusinessProfileMethod(appPreferencesShared.getUserId(),
                businessNameOfCompany, category, companyaddress, mCountryName, mStateName, companyOfEmail,
                companWebsitey, establishedDate, companyContact, aboutCompany);

        commonResponseModelCall.enqueue(new Callback<CommonResponseModel>() {
            @Override
            public void onResponse(Call<CommonResponseModel> call, Response<CommonResponseModel> response) {
                try {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    if (response.isSuccessful()) {
                        if (response.body().getResult() == true) {
                            appPreferencesShared.setBusinessCreateCompanyProfile(true);
                            appPreferencesShared.setFragmentDirection("Business");
//                            Toast.makeText(mContext, response.body().getMessage(), Toast.LENGTH_SHORT).show();
//                            startActivity(new Intent(mContext, CompaignModeActivity.class));
                            finish();
                        } else {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                            Toast.makeText(mContext, "Something went wrong", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    } else {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        Toast.makeText(mContext, "Something went wrong", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                } catch (Exception e) {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    e.getMessage();
                    Toast.makeText(mContext, "Something went wrong", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<CommonResponseModel> call, Throwable t) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                Toast.makeText(mContext, "Something went wrong", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
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