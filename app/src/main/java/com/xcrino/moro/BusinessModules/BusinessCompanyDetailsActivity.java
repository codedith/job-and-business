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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.xcrino.moro.Activity.SubscriptionPlanActivity;
import com.xcrino.moro.Interface.ApiInterface;
import com.xcrino.moro.PojoModels.BusinessModuleDetailsResponse;
import com.xcrino.moro.PojoModels.ImageUploadModel;
import com.xcrino.moro.PojoModels.UserProfile;
import com.xcrino.moro.R;
import com.xcrino.moro.Utilities.ApiClients;
import com.xcrino.moro.Utilities.AppPreferencesShared;
import com.xcrino.moro.Utilities.NetworkStatus;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BusinessCompanyDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView toolbar_title;
    private ImageView back_arrow, company_img;
    private Context mContext;
    private Toolbar toolbar;
    private AppPreferencesShared appPreferencesShared;
    private Button upgradePlanNow_bt;
    private TextView company_name, Company_designation, Company_location, email_id, phone_number, total_applicable_credits;
    private TextView total_consumed_credits, total_remaining_credits, establishedIn, address, validitydate;
    private BusinessModuleDetailsResponse businessModuleDetailsResponse;

    private String userProfileImage, oldUserProfileImage;
    private String[] appPermissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA};
    private int PERMISSIONS_REQUEST_CODE = 1024, GALLERY = 0, CAMERA = 1;
    private UserProfile userProfile;

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
        setContentView(R.layout.activity_company_details);

        mContext = this;
        getLayoutUiId();
        setSupportActionBar(toolbar);
        toolbar_title.setText("Business Profile");
        back_arrow.setOnClickListener(this);
        upgradePlanNow_bt.setOnClickListener(this);
        company_img.setOnClickListener(this);

        Gson gsonUserProfile = new Gson();
        String json = appPreferencesShared.getUserDetails();
        userProfile = gsonUserProfile.fromJson(json, UserProfile.class);

        checkAndRequestPermissions();

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
                Intent intent = new Intent(mContext, BusinessCreateProfileActivity.class);
                appPreferencesShared.setBusinessCreateCompanyProfile(true);
                startActivity(intent);
                break;
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (NetworkStatus.isNetworkAvailable(mContext)) {
            getBusinessCompanyProfileDetails();
        } else {
            Toast.makeText(mContext, "Please Check Your Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }

    private void getBusinessCompanyProfileDetails() {
        ProgressDialog progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        ApiInterface apiInterface = ApiClients.getClient().create(ApiInterface.class);
        Call<BusinessModuleDetailsResponse> businessModuleDetailsResponseCall = apiInterface.
                getBusinessCompanyProfileDetails("Business-module-api/get-business-profile/" + appPreferencesShared.getUserId());

        businessModuleDetailsResponseCall.enqueue(new Callback<BusinessModuleDetailsResponse>() {
            @Override
            public void onResponse(Call<BusinessModuleDetailsResponse> call, Response<BusinessModuleDetailsResponse> response) {
                try {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            if (response.body().getBusinessModuleData().getCompanyDetails() != null) {
                                company_name.setText(response.body().getBusinessModuleData().getCompanyDetails().getUbsName() == null ? "" : response.body().getBusinessModuleData().getCompanyDetails().getUbsName().toString());
                                String data = response.body().getBusinessModuleData().getCompanyDetails().getUbsCategory() == null ? "" : response.body().getBusinessModuleData().getCompanyDetails().getUbsCategory().toString();
                                Company_designation.setText(data);
                                Company_location.setText(response.body().getBusinessModuleData().getCompanyDetails().getUbsState().toString()
                                        + ", " + response.body().getBusinessModuleData().getCompanyDetails().getUbsCountry().toString());
                                email_id.setText(response.body().getBusinessModuleData().getCompanyDetails().getUbsEmail() == null ? "" : response.body().getBusinessModuleData().getCompanyDetails().getUbsEmail().toString());
                                phone_number.setText(response.body().getBusinessModuleData().getCompanyDetails().getUbsContactNo() == null ? "" : response.body().getBusinessModuleData().getCompanyDetails().getUbsContactNo().toString());
                                establishedIn.setText(response.body().getBusinessModuleData().getCompanyDetails().getUbsEstYear() == null ? "" : response.body().getBusinessModuleData().getCompanyDetails().getUbsEstYear().toString());
                                address.setText(response.body().getBusinessModuleData().getCompanyDetails().getUbsAddress().toString() + ", " + response.body().getBusinessModuleData().getCompanyDetails().getUbsState().toString()
                                        + ", " + response.body().getBusinessModuleData().getCompanyDetails().getUbsCountry().toString());

                                if (userProfile.getUserDatum().getUserProfileData().getUserProfileImage() != null) {
                                    Picasso.with(getApplicationContext()).load(userProfile.getUserDatum().getUserProfileData().getUserProfileImage().toString())
                                            .resize(185, 185).transform(new CropCircleTransformation())
                                            .placeholder(R.drawable.user_dummy).into(company_img);
                                }
                            }
                            if (response.body().getBusinessModuleData().getSubscription() == null) {
                                total_applicable_credits.setText("--");
                                total_consumed_credits.setText("--");
                                total_remaining_credits.setText("--");
                                validitydate.setText("--");

                            } else {
                                total_applicable_credits.setText(response.body().getBusinessModuleData().getSubscription().getTotalApplicable() == null ? "" : response.body().getBusinessModuleData().getSubscription().getTotalApplicable().toString());
                                total_consumed_credits.setText(response.body().getBusinessModuleData().getSubscription().getTotalConsumed() == null ? "" : response.body().getBusinessModuleData().getSubscription().getTotalConsumed().toString());
                                total_remaining_credits.setText(response.body().getBusinessModuleData().getSubscription().getTotalRemaining() == null ? "" : Integer.toString(response.body().getBusinessModuleData().getSubscription().getTotalRemaining()));

                                if (response.body().getBusinessModuleData().getSubscription().getValidUpto() != null) {
                                    String inputPattern = "yyyy-MM-dd HH:mm:ss";
                                    String outputPattern = "dd-MM-yyyy";
                                    SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
                                    SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

                                    Date date = null;
                                    String str = null;

                                    try {
                                        date = inputFormat.parse(response.body().getBusinessModuleData().getSubscription().getValidUpto().toString());
                                        str = outputFormat.format(date);
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                    validitydate.setText(str.toString());
                                } else {
                                    validitydate.setText("--");
                                }
                            }

                            businessModuleDetailsResponse = response.body();
                            Gson gson = new Gson();
                            String json = gson.toJson(businessModuleDetailsResponse);
                            appPreferencesShared.setBusinessModuleDetails(json);

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
            public void onFailure(Call<BusinessModuleDetailsResponse> call, Throwable t) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                Toast.makeText(mContext, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getLayoutUiId() {
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        back_arrow = (ImageView) findViewById(R.id.back_arrow);
        company_img = (ImageView) findViewById(R.id.company_img);
        upgradePlanNow_bt = (Button) findViewById(R.id.upgradePlanNow_bt);
        company_name = (TextView) findViewById(R.id.company_name);
        Company_designation = (TextView) findViewById(R.id.Company_designation);
        Company_location = (TextView) findViewById(R.id.Company_location);
        email_id = (TextView) findViewById(R.id.email_id);
        phone_number = (TextView) findViewById(R.id.phone_number);
        total_applicable_credits = (TextView) findViewById(R.id.total_applicable_credits);
        total_consumed_credits = (TextView) findViewById(R.id.total_consumed_credits);
        total_remaining_credits = (TextView) findViewById(R.id.total_remaining_credits);
        establishedIn = (TextView) findViewById(R.id.establishedIn);
        address = (TextView) findViewById(R.id.address);
        validitydate = (TextView) findViewById(R.id.validitydate);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_arrow:
                onBackPressed();
                break;

            case R.id.upgradePlanNow_bt:
                startActivity(new Intent(mContext, SubscriptionPlanActivity.class));
                appPreferencesShared.setSubscriptionDirection("Business");
                break;

            case R.id.company_img:
//                showCompanyImageUploadDialogue();
                break;

        }
    }

    private void showCompanyImageUploadDialogue() {
        String[] items = {"Select photo from gallery", "Capture photo from camera"};
        AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
        alert.setTitle("Select Action").setItems(items, new DialogInterface.OnClickListener() {
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
                                Picasso.with(getApplicationContext()).load(file).resize(200, 200)
                                        .transform(new CropCircleTransformation()).placeholder(R.drawable.user_dummy).into(company_img);
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