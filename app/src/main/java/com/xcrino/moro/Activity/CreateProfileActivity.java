package com.xcrino.moro.Activity;

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
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.xcrino.moro.Interface.ApiInterface;
import com.xcrino.moro.PojoModels.BusinessCategory;
import com.xcrino.moro.PojoModels.BusinessCategoryData;
import com.xcrino.moro.PojoModels.CommonResponseModel;
import com.xcrino.moro.PojoModels.CountryListData;
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

public class CreateProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private Button submit_button;
    private ImageView back_arrow;
    private Context mContext;
    private View activity_create_profile;
    private Spinner acc_type_spinner;
    String[] accType = {"Individual", "Business"};
    private LinearLayout business_layout;
    String type = "Select";
    Validations validations;
    EditText firstNameField, lastNameField, emaiIdField, businessNameField;
    String[] businessCategory, blankArr = {};
    private TextView toolbar_title, dobField, businessCategoryField, mobileNumberField;
    String business = "Select";
    private boolean[] checkedItems;
    ArrayList<Integer> selectedBusinessCategoryName = new ArrayList<>();
    private AppPreferencesShared appPreferencesShared;

    ArrayList<String> businessCategoryNameFromJson = new ArrayList<>();
    ArrayList<String> businessCategoryIdFromJson = new ArrayList<>();
    String[] businessCategoryId;
    private BusinessCategoryData businessCategoryData;
    private List<BusinessCategoryData> businessCategoryDataList = null;

    private static final String[] PERMISSIONS_READ_STORAGE = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};
    private Uri contentUris;
    private ImageView user_ProfileImage;
    private String userId, userProfileImage, oldUserProfileImage;
    private String[] appPermissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA};
    private int PERMISSIONS_REQUEST_CODE = 1024, GALLERY = 0, CAMERA = 1;

    private Spinner user_country_spinner, user_state_spinner;
    private String[] userCountryNameArray;
    private String[] statenameArray;
    private String[] userBusinessCategory;
    private List<String> CountryIdList = new ArrayList<String>();
    private List<String> StateIdList = new ArrayList<String>();
    private String countryId, stateId, countryName, stateName;
    //    private String userFullName, userEmail, userDob, userAccountType, userBusinessName;
    private TextView acc_type_spinner_temp, businessCategoryField_temporary, user_country_tv, user_state_tv;
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
        setContentView(R.layout.activity_create_profile);

        getLayoutInit();
        mContext = this;

        checkAndRequestPermissions();

        validations = new Validations();

        Gson gsonUserProfile = new Gson();
        String json = appPreferencesShared.getUserDetails();
        userProfile = gsonUserProfile.fromJson(json, UserProfile.class);

        submit_button.setOnClickListener(this);
        back_arrow.setOnClickListener(this);
        dobField.setOnClickListener(this);
        businessCategoryField.setOnClickListener(this);
        user_ProfileImage.setOnClickListener(this);
        mobileNumberField.setText(appPreferencesShared.getMobileNumber());

        if (appPreferencesShared.getIsEdituserProfile()) {
            toolbar_title.setText("Edit User Profile");
            acc_type_spinner.setVisibility(View.GONE);
            acc_type_spinner_temp.setVisibility(View.VISIBLE);
            acc_type_spinner_temp.setTextColor(getResources().getColor(R.color.grey));
            businessCategoryField.setVisibility(View.GONE);
            businessCategoryField_temporary.setVisibility(View.VISIBLE);
            acc_type_spinner_temp.setText(userProfile.getUserDatum().getUserProfileData().getUserAccount().toString());
            String[] fullName = userProfile.getUserDatum().getUserProfileData().getUserFullName().split("\\s+");
            firstNameField.setText(fullName[0].toString());
            firstNameField.setSelection(firstNameField.getText().length());
            lastNameField.setText(fullName[1].toString());
            mobileNumberField.setText(appPreferencesShared.getMobileNumber().toString());
            mobileNumberField.setTextColor(getResources().getColor(R.color.grey));
            emaiIdField.setText(userProfile.getUserDatum().getUserProfileData().getUserEmail().toString());
            dobField.setText(userProfile.getUserDatum().getUserProfileData().getUserDob().toString());
            type = userProfile.getUserDatum().getUserProfileData().getUserAccount().toString();
            if (type.equals("Individual")) {
                business_layout.setVisibility(View.GONE);
            } else {
                business_layout.setVisibility(View.VISIBLE);
                businessNameField.setText(userProfile.getUserDatum().getUserProfileData().getUserBusinessName().toString());
                businessCategoryField_temporary.setText("");
                for (int i = 0; i < userProfile.getUserDatum().getUserBc().size(); i++) {
                    businessCategoryField_temporary.append(userProfile.getUserDatum().getUserBc().get(i).getUserBusinessCategory().toString());
                    if (i != userProfile.getUserDatum().getUserBc().size() - 1) {
                        businessCategoryField_temporary.append(", ");
                    }
                }
                businessCategoryField_temporary.setTextColor(getResources().getColor(R.color.grey));
            }
            user_country_spinner.setVisibility(View.GONE);
            user_state_spinner.setVisibility(View.GONE);

            user_country_tv.setVisibility(View.VISIBLE);
            user_country_tv.setText(userProfile.getUserDatum().getUserProfileData().getUserCountryId());
            user_country_tv.setTextColor(getResources().getColor(R.color.grey));
            countryName = userProfile.getUserDatum().getUserProfileData().getUserCountryId();
            user_state_tv.setVisibility(View.VISIBLE);
            user_state_tv.setText(userProfile.getUserDatum().getUserProfileData().getUserStateId());
            user_state_tv.setTextColor(getResources().getColor(R.color.grey));
            stateName = userProfile.getUserDatum().getUserProfileData().getUserStateId();
        } else {
            toolbar_title.setText("Create User Profile");
            acc_type_spinner.setVisibility(View.VISIBLE);
            acc_type_spinner_temp.setVisibility(View.GONE);
            businessCategoryField.setVisibility(View.VISIBLE);
            businessCategoryField_temporary.setVisibility(View.GONE);
            mobileNumberField.setTextColor(getResources().getColor(R.color.grey));
            if (NetworkStatus.isNetworkAvailable(mContext)) {
                getCountryList();
            } else {
                Toast.makeText(mContext, "Please Check Your Internet Connection !", Toast.LENGTH_SHORT).show();
            }
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(mContext, R.layout.spinner_item, accType);
        acc_type_spinner.setAdapter(dataAdapter);
        acc_type_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                type = parent.getItemAtPosition(position).toString();
                if (type.equals("Business")) {
                    getBusinessCategoriesMethod();
                    business_layout.setVisibility(View.VISIBLE);
                } else {
//                    getBusinessCategoryNameList();
                    business_layout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ButtonVisibility buttonVisibility = new ButtonVisibility();
        buttonVisibility.hideButton(mContext, activity_create_profile, submit_button);
    }

    private void getLayoutInit() {
        user_ProfileImage = (ImageView) findViewById(R.id.user_ProfileImage);
        submit_button = (Button) findViewById(R.id.submit_button);
        back_arrow = (ImageView) findViewById(R.id.back_arrow);
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        activity_create_profile = (View) findViewById(R.id.activity_create_profile);
        acc_type_spinner = (Spinner) findViewById(R.id.acc_type_spinner);
        business_layout = (LinearLayout) findViewById(R.id.business_layout);
        dobField = (TextView) findViewById(R.id.dobField);
        firstNameField = (EditText) findViewById(R.id.firstNameField);
        lastNameField = (EditText) findViewById(R.id.lastNameField);
        mobileNumberField = findViewById(R.id.mobileNumberField);
        emaiIdField = (EditText) findViewById(R.id.emaiIdField);
        businessNameField = (EditText) findViewById(R.id.businessNameField);
        businessCategoryField = (TextView) findViewById(R.id.businessCategoryField);
        user_country_spinner = (Spinner) findViewById(R.id.user_country_spinner);
        user_state_spinner = (Spinner) findViewById(R.id.user_state_spinner);
        acc_type_spinner_temp = findViewById(R.id.acc_type_spinner_temp);
        businessCategoryField_temporary = findViewById(R.id.businessCategoryField_temporary);
        user_country_tv = findViewById(R.id.user_country_tv);
        user_state_tv = findViewById(R.id.user_state_tv);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit_button:
                if (validations.isSpinnerValid(type, mContext, "Select Account Type") &&
                        validations.isFirstNameValid(firstNameField.getText().toString(), firstNameField) &&
                        validations.isLastNameValid(lastNameField.getText().toString(), lastNameField) &&
                        validations.isEmailValid(emaiIdField.getText().toString(), emaiIdField) &&
                        validations.isDobValid(dobField.getText().toString(), dobField)) {
                    if (type.equals("Business") &&
                            validations.isMandatory(businessNameField.getText().toString(), businessNameField) &&
                            validations.isTvMandatory(businessCategoryField.getText().toString(), businessCategoryField)) {
                        if (NetworkStatus.isNetworkAvailable(mContext)) {
                            postUpdateUserProfileMethod(firstNameField.getText().toString() + " " + lastNameField.getText().toString(),
                                    emaiIdField.getText().toString(), dobField.getText().toString(), type, businessCategoryId,
                                    businessNameField.getText().toString(), countryName, stateName);
                        } else {
                            Toast.makeText(mContext, "Please Check Your Internet Connection !", Toast.LENGTH_SHORT).show();
                        }

                    } else if (type.equals("Individual")) {
                        if (NetworkStatus.isNetworkAvailable(mContext)) {
                            postUpdateUserProfileMethod(firstNameField.getText().toString() + " " + lastNameField.getText().toString(),
                                    emaiIdField.getText().toString(), dobField.getText().toString(), type, blankArr,
                                    "", countryName, stateName);
                        } else {
                            Toast.makeText(mContext, "Please Check Your Internet Connection !", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                break;

            case R.id.back_arrow:
                onBackPressed();
                break;

            case R.id.user_ProfileImage:
                showUploadDialogue();
                break;

            case R.id.dobField:
                new DatePicker(mContext, dobField);
                break;

            case R.id.businessCategoryField:
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
                        businessCategoryField.setText(business);
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
                            businessCategoryField.setText("Select");
                        }
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
                break;
        }
    }

    private void postUpdateUserProfileMethod(String fullName, String email, String dob, String type, String[] categoryId,
                                             String businessName, String mCountryId, String mStateId) {
        ProgressDialog progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        JSONArray categoryIdArray = new JSONArray(Arrays.asList(categoryId));

        ApiInterface apiInterface = ApiClients.getClient().create(ApiInterface.class);
        Call<CommonResponseModel> updateUserProfileCall = apiInterface.postUpdateUserProfileMethod(appPreferencesShared.getUserId(),
                fullName, email, dob, type, categoryIdArray, businessName, mCountryId, mStateId);

        updateUserProfileCall.enqueue(new Callback<CommonResponseModel>() {
            @Override
            public void onResponse(Call<CommonResponseModel> call, Response<CommonResponseModel> response) {
                try {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    if (response.isSuccessful()) {
                        if (response.body().getResult()) {
                            if (appPreferencesShared.getIsEdituserProfile()) {
                                finish();
                            } else {
                                Intent intent = new Intent(mContext, DashboardActivity.class);
                                appPreferencesShared.setIsCreateProfile(2);
                                appPreferencesShared.setIsFirstTimeLaunch(false);
                                finish();
                                startActivity(intent);
                            }
                            Toast.makeText(mContext, "Update Profile Successful !", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(mContext, "Something went wrong !", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(mContext, "Something went wrong !", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    e.getMessage();
                }
            }

            @Override
            public void onFailure(Call<CommonResponseModel> call, Throwable t) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_SHORT).show();

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
                                Picasso.with(getApplicationContext()).load(file).resize(200, 200).transform(new CropCircleTransformation()).placeholder(R.drawable.user_dummy).into(user_ProfileImage);
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

    private void getBusinessCategoriesMethod() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        ApiInterface apiInterface = ApiClients.getClient().create(ApiInterface.class);
        Call<BusinessCategory> stringCall = apiInterface.getBusinessCategoriesMethod();
        stringCall.enqueue(new Callback<BusinessCategory>() {
            @Override
            public void onResponse(Call<BusinessCategory> call, Response<BusinessCategory> response) {
                if (response.isSuccessful()) {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    try {
                        if (response.body() != null) {
                            businessCategory = new String[response.body().getData().size()];
                            for (int i = 0; i < response.body().getData().size(); i++) {
                                businessCategoryIdFromJson.add(response.body().getData().get(i).getBcSno());
                                businessCategory[i] = response.body().getData().get(i).getBcName();
                            }
                            checkedItems = new boolean[businessCategory.length];

                        } else {
                            Toast.makeText(mContext, "Data not found !", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    Toast.makeText(mContext, "Something went wrong !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BusinessCategory> call, Throwable t) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
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
                                userCountryNameArray = new String[response.body().getCountryLists().size()];
                                for (int i = 0; i < response.body().getCountryLists().size(); i++) {
                                    CountryIdList.add(response.body().getCountryLists().get(i).getId());
                                    userCountryNameArray[i] = response.body().getCountryLists().get(i).getName();
                                }

                                ArrayAdapter<String> countryAdapter = new ArrayAdapter<>(mContext, R.layout.spinner_item, userCountryNameArray);
                                user_country_spinner.setAdapter(countryAdapter);
                                user_country_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        countryId = CountryIdList.get(i);
                                        countryName = userCountryNameArray[i];
                                        getStatesListMethod();
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                    }
                                });
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
                                user_state_spinner.setAdapter(stateAdapter);
                                user_state_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        stateId = StateIdList.get(i);
                                        stateName = statenameArray[i];
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                    }
                                });
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
