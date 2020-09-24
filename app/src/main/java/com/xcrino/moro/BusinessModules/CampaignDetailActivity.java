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
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.format.DateFormat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Size;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.VideoView;

import com.squareup.picasso.Picasso;
import com.xcrino.moro.Activity.SubscriptionPlanActivity;
import com.xcrino.moro.Interface.ApiInterface;
import com.xcrino.moro.PojoModels.AddCompaignFileResponse;
import com.xcrino.moro.PojoModels.CampaignModel;
import com.xcrino.moro.PojoModels.CampaignResponseModel;
import com.xcrino.moro.PojoModels.CommonResponseModel;
import com.xcrino.moro.PojoModels.CountryListData;
import com.xcrino.moro.R;
import com.xcrino.moro.Utilities.ApiClients;
import com.xcrino.moro.Utilities.AppPreferencesShared;
import com.xcrino.moro.Utilities.NetworkStatus;
import com.xcrino.moro.Utilities.Validations;

import org.json.JSONArray;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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


public class CampaignDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView type_of_campaign, views_count, likes_count, dislikes_count, replies_count, blocks_count, text_view;
    private Context mContext;
    private AppPreferencesShared appPreferencesShared;
    private TextView toolbar_title;
    private ImageView back_arrow;
    private ImageView image_view;
    private VideoView video_view;
    private Button btn_like_dislike, btn_block, btn_reply, re_boost_button;
    String toggle_like_dislike = "1";
    private MediaController mediacontroller;
    LinearLayout feature_layout;
    private String uploadedCampaignMode, uploadedCampaignText, uploadedCampaignLink, uploadedCampaignFile,
            uploadedTimeStamp, uploadedDatabaseType, uploadedPromotionTarget, uploadedCampaignUserCount, uploadedLocalUsers, fileName;
    private static final String IMAGE_DIRECTORY = "/moro";
    private static final int BUFFER_SIZE = 1024 * 2;
    private String database_type_selected = "local", timeStamp = "Select", countries = "Select", selected_countries = "Select",
            localUsers = "Select", userCountMoro = "Select";
    LinearLayout user_count_ll;
    static TextView timeStampField;
    private boolean[] checkedItems;
    ArrayList<Integer> selectedCountriesName = new ArrayList<>();
    TextView country_spinner;
    String[] targetCountryArray, selectCountriesArray;
    Validations validations;
    private String[] arr, targetArr;

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
        setContentView(R.layout.activity_campaign_detail);
        mContext = this;
        validations = new Validations();
        getInitUi();

        if (appPreferencesShared.getPageDirection().equals("Previous")) {
            feature_layout.setVisibility(View.GONE);
            re_boost_button.setVisibility(View.VISIBLE);
        } else {
            feature_layout.setVisibility(View.VISIBLE);
            re_boost_button.setVisibility(View.GONE);
        }

        toolbar_title.setText("Campaign Details");
        back_arrow.setOnClickListener(this);
        btn_like_dislike.setOnClickListener(this);
        btn_block.setOnClickListener(this);
        btn_reply.setOnClickListener(this);
        mediacontroller = new MediaController(this);
        mediacontroller.setAnchorView(video_view);
        re_boost_button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_arrow:
                onBackPressed();
                break;

            case R.id.btn_like_dislike:
                if (NetworkStatus.isNetworkAvailable(mContext)) {
                    getToggleLikeCampaign();
                } else {
                    Toast.makeText(mContext, "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.btn_block:
                if (NetworkStatus.isNetworkAvailable(mContext)) {
                    postToggleBlockCampaign();
                } else {
                    Toast.makeText(mContext, "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.btn_reply:
                break;

            case R.id.re_boost_button:
                boostDialogue();
                break;

        }
    }

    private void getInitUi() {
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        back_arrow = (ImageView) findViewById(R.id.back_arrow);
        type_of_campaign = findViewById(R.id.type_of_campaign);
        views_count = findViewById(R.id.views_count);
        likes_count = findViewById(R.id.likes_count);
        dislikes_count = findViewById(R.id.dislikes_count);
        replies_count = findViewById(R.id.replies_count);
        blocks_count = findViewById(R.id.blocks_count);
        image_view = findViewById(R.id.image_view);
        video_view = findViewById(R.id.video_view);
        text_view = findViewById(R.id.text_view);
        btn_like_dislike = findViewById(R.id.btn_like_dislike);
        btn_block = findViewById(R.id.btn_block);
        btn_reply = findViewById(R.id.btn_reply);
        feature_layout = findViewById(R.id.feature_layout);
        re_boost_button = findViewById(R.id.re_boost_button);
    }

    private void getCampaignDetail() {
        ProgressDialog progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        ApiInterface apiInterface = ApiClients.getClient().create(ApiInterface.class);
        Call<CampaignModel> campaigndetailcall = apiInterface.getCampaignDetail("Business-module-api/get-campaign-details/" +
                appPreferencesShared.getCampaignId() + "/" + appPreferencesShared.getUserId());
        campaigndetailcall.enqueue(new Callback<CampaignModel>() {
            @Override
            public void onResponse(Call<CampaignModel> call, Response<CampaignModel> response) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getData() != null) {
                            views_count.setText(response.body().getData().getBusinessCampaignViews().toString());
                            likes_count.setText(response.body().getData().getLikes().toString());
                            dislikes_count.setText(response.body().getData().getDislikes().toString());
                            blocks_count.setText(response.body().getData().getBlockByUsers().toString());
                            type_of_campaign.setText("Type of Campaign : " + response.body().getData().getBusinessCampaignMode());

                            uploadedCampaignMode = response.body().getData().getBusinessCampaignMode() == null ? "" :
                                    response.body().getData().getBusinessCampaignMode().toString();
                            uploadedCampaignFile = response.body().getData().getBusinessCampaignFile() == null ? "" :
                                    response.body().getData().getBusinessCampaignFile().toString();

                            final File file = new File(Environment.getExternalStorageDirectory(), uploadedCampaignFile);
                            Uri uri = Uri.fromFile(file);
                            File auxFile = new File(uri.toString());
                            fileName = auxFile.getPath();

                            uploadedCampaignLink = response.body().getData().getBusinessCampaignLink() == null ? "" :
                                    response.body().getData().getBusinessCampaignLink();
                            uploadedCampaignText = response.body().getData().getBusinessCampaignText() == null ? "" :
                                    response.body().getData().getBusinessCampaignText().toString();
                            uploadedDatabaseType = response.body().getData().getBusinessCampaignDatabaseType() == null ? "" :
                                    response.body().getData().getBusinessCampaignDatabaseType().toString();
                            uploadedTimeStamp = response.body().getData().getBusinessCampaignSchedule() == null ? "" :
                                    response.body().getData().getBusinessCampaignSchedule().toString();
                            uploadedCampaignUserCount = response.body().getData().getBusinessCampaignUserCount() == null ? "" :
                                    response.body().getData().getBusinessCampaignUserCount().toString();
                            if (response.body().getData().getBusinessCampaignTargets().contains(",")) {
                                arr = response.body().getData().getBusinessCampaignTargets().split(",");
                                targetArr = new String[arr.length];
                                for (int i = 0; i < arr.length; i++) {
                                    targetArr[i] = arr[i].trim();
                                }
                            } else {
                                targetArr = new String[1];
                                targetArr[0] = response.body().getData().getBusinessCampaignTargets();
                                JSONArray jsonArray = new JSONArray(Arrays.asList(targetArr));
                                uploadedPromotionTarget = jsonArray.toString();
                            }
                            String[] localUsers;
                            if (!response.body().getData().getLocalUsers().isEmpty()) {
                                localUsers = new String[response.body().getData().getLocalUsers().size()];
                                for (int i = 0; i < response.body().getData().getLocalUsers().size(); i++) {
                                    localUsers[i] = response.body().getData().getLocalUsers().get(i).getBcLocalDbPhoneNo().toString();
                                }
                            } else {
                                localUsers = new String[0];
                            }
                            JSONArray jsonArray = new JSONArray(Arrays.asList(localUsers));
                            uploadedLocalUsers = jsonArray.toString();

                            switch (response.body().getData().getBusinessCampaignMode()) {
                                case "link":
                                    image_view.setVisibility(View.GONE);
                                    text_view.setVisibility(View.VISIBLE);
                                    video_view.setVisibility(View.GONE);
                                    text_view.setText(response.body().getData().getBusinessCampaignLink());
                                    break;

                                case "text":
                                    text_view.setVisibility(View.VISIBLE);
                                    video_view.setVisibility(View.GONE);
                                    image_view.setVisibility(View.GONE);
                                    text_view.setText(response.body().getData().getBusinessCampaignText().toString());
                                    break;

                                case "image":
                                    image_view.setVisibility(View.VISIBLE);
                                    text_view.setVisibility(View.GONE);
                                    video_view.setVisibility(View.GONE);
                                    Picasso.with(mContext).load(response.body().getData().getBusinessCampaignFile().toString())
                                            .rotate(270).placeholder(R.drawable.notfound).into(image_view);
                                    image_view.invalidate();
                                    BitmapDrawable drawable = (BitmapDrawable) image_view.getDrawable();
                                    Bitmap bitmap = drawable.getBitmap();
                                    fileName = saveImage(bitmap);
                                    break;

                                case "video":
                                    image_view.setVisibility(View.GONE);
                                    text_view.setVisibility(View.GONE);
                                    video_view.setVisibility(View.VISIBLE);
                                    Uri myUri = Uri.parse(response.body().getData().getBusinessCampaignFile().toString());
                                    video_view.setMediaController(mediacontroller);
                                    video_view.setVideoURI(myUri);
                                    video_view.requestFocus();
                                    video_view.start();
                                    video_view.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                        @Override
                                        public void onCompletion(MediaPlayer mp) {
                                            video_view.start();
                                        }
                                    });
                                    fileName = saveVideo(mContext, myUri);
                                    break;

                                case "mix":
                                    image_view.setVisibility(View.VISIBLE);
                                    text_view.setVisibility(View.VISIBLE);
                                    video_view.setVisibility(View.GONE);
                                    text_view.setText(response.body().getData().getBusinessCampaignText().toString());
                                    Picasso.with(mContext).load(response.body().getData().getBusinessCampaignFile().toString())
                                            .rotate(180).placeholder(R.drawable.notfound).into(image_view);
                                    break;
                            }

                            if (response.body().getData().getUserLikeOrDislike().toLowerCase().equals("like") ||
                                    response.body().getData().getUserLikeOrDislike().equals("liked")) {
                                toggle_like_dislike = "2";
                                btn_like_dislike.setText("Dislike");
                            } else {
                                toggle_like_dislike = "1";
                                btn_like_dislike.setText("Like");
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<CampaignModel> call, Throwable t) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                Toast.makeText(mContext, "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getToggleLikeCampaign() {
        ProgressDialog progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        ApiInterface apiInterface = ApiClients.getClient().create(ApiInterface.class);
        Call<CommonResponseModel> togglecampaigncall = apiInterface.getToggleLikeCampaign("Business-module-api/toggle-campaign-like-dislike/" + toggle_like_dislike + "/" + appPreferencesShared.getCampaignId() + "/" + appPreferencesShared.getUserId());
        togglecampaigncall.enqueue(new Callback<CommonResponseModel>() {
            @Override
            public void onResponse(Call<CommonResponseModel> call, Response<CommonResponseModel> response) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                if (response.isSuccessful()) {
                    if (response.body().getResult()) {
                        if (btn_like_dislike.getText().equals("Like")) {
                            toggle_like_dislike = "2";
                            btn_like_dislike.setText("Dislike");
                        } else {
                            toggle_like_dislike = "1";
                            btn_like_dislike.setText("Like");
                        }
                    }
                }
                if (NetworkStatus.isNetworkAvailable(mContext)) {
                    getCampaignDetail();
                } else {
                    Toast.makeText(mContext, "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CommonResponseModel> call, Throwable t) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }
        });
    }

    private void postToggleBlockCampaign() {
        ProgressDialog progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        ApiInterface apiInterface = ApiClients.getClient().create(ApiInterface.class);
        Call<CommonResponseModel> togglecampaigncall = apiInterface.postToggleBlockCampaign(appPreferencesShared.getUserId(), appPreferencesShared.getCampaignId());
        togglecampaigncall.enqueue(new Callback<CommonResponseModel>() {
            @Override
            public void onResponse(Call<CommonResponseModel> call, Response<CommonResponseModel> response) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                if (response.isSuccessful()) {
                    if (response.body().getResult()) {
                        if (btn_block.getText().equals("Block")) {
                            btn_block.setText("Unblock");
                        } else {
                            btn_block.setText("Block");
                        }
                    }
                }
                if (NetworkStatus.isNetworkAvailable(mContext)) {
                    getCampaignDetail();
                } else {
                    Toast.makeText(mContext, "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CommonResponseModel> call, Throwable t) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (NetworkStatus.isNetworkAvailable(mContext)) {
            getCampaignDetail();
        } else {
            Toast.makeText(mContext, "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    private void reBoostCampaignMethod() {
        ProgressDialog progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        MultipartBody.Part userProfileImage = null;
        RequestBody requestFile;
        if (!fileName.isEmpty()) {
            File file = new File(fileName);
            requestFile = RequestBody.create(MediaType.parse("*/*"), file);
            userProfileImage = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        } else {
            requestFile = RequestBody.create(MediaType.parse("*/*"), "");
            userProfileImage = MultipartBody.Part.createFormData("file", "", requestFile);
        }

        RequestBody userId = RequestBody.create(MediaType.parse("text/plain"), appPreferencesShared.getUserId());
        RequestBody campaignMode = RequestBody.create(MediaType.parse("text/plain"), uploadedCampaignMode);
        RequestBody text = RequestBody.create(MediaType.parse("text/plain"), uploadedCampaignText);
        RequestBody timestamp = RequestBody.create(MediaType.parse("text/plain"), uploadedTimeStamp);
        RequestBody countriesFinal = RequestBody.create(MediaType.parse("text/plain"), uploadedPromotionTarget);
        RequestBody database_type = RequestBody.create(MediaType.parse("text/plain"), uploadedDatabaseType);
        RequestBody local_users = RequestBody.create(MediaType.parse("text/plain"), uploadedLocalUsers);
        RequestBody link = RequestBody.create(MediaType.parse("text/plain"), uploadedCampaignLink);
        RequestBody user_count = RequestBody.create(MediaType.parse("text/plain"), uploadedCampaignUserCount);

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

    private String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance().getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this, new String[]{f.getPath()}, new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath());

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }

    private String saveVideo(Context context, Uri contentUri) {
        File wallpaperDirectory = new File(Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        File copyFile = new File(wallpaperDirectory + File.separator + Calendar.getInstance().getTimeInMillis() + ".mp4");
        // create folder if not exists
        copy(context, contentUri, copyFile);
        Log.d("vPath--->", copyFile.getAbsolutePath());

        return copyFile.getAbsolutePath();
    }

    public static void copy(Context context, Uri srcUri, File dstFile) {
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(srcUri);
            if (inputStream == null) return;
            OutputStream outputStream = new FileOutputStream(dstFile);
            copystream(inputStream, outputStream);
            inputStream.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int copystream(InputStream input, OutputStream output) throws Exception, IOException {
        byte[] buffer = new byte[BUFFER_SIZE];

        BufferedInputStream in = new BufferedInputStream(input, BUFFER_SIZE);
        BufferedOutputStream out = new BufferedOutputStream(output, BUFFER_SIZE);
        int count = 0, n = 0;
        try {
            while ((n = in.read(buffer, 0, BUFFER_SIZE)) != -1) {
                out.write(buffer, 0, n);
                count += n;
            }
            out.flush();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                Log.e(e.getMessage(), String.valueOf(e));
            }
            try {
                in.close();
            } catch (IOException e) {
                Log.e(e.getMessage(), String.valueOf(e));
            }
        }
        return count;
    }

    private void boostDialogue() {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.campaign_more_details, null);
        timeStampField = alertLayout.findViewById(R.id.timeStampField);
        country_spinner = alertLayout.findViewById(R.id.country_spinner);
        final EditText user_count_tv = alertLayout.findViewById(R.id.user_count_tv);
        user_count_tv.setVisibility(View.GONE);
        Button submitAllField_button = alertLayout.findViewById(R.id.submitAllField_button);
        user_count_ll = alertLayout.findViewById(R.id.user_count_ll);
        user_count_ll.setVisibility(View.GONE);
        LinearLayout promotion_target_ll = alertLayout.findViewById(R.id.promotion_target_ll);
        promotion_target_ll.setVisibility(View.GONE);
        LinearLayout radioG = alertLayout.findViewById(R.id.radioG);
        radioG.setVisibility(View.GONE);
        timeStampField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(v);
                showDatePickerDialog(v);
            }
        });

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setView(alertLayout);
        alert.setCancelable(false);
        AlertDialog dialog = alert.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        submitAllField_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validations.isTvMandatory(timeStampField.getText().toString(), timeStampField)) {
                    timeStamp = timeStampField.getText().toString();
                    if (NetworkStatus.isNetworkAvailable(mContext)) {
                        reBoostCampaignMethod();
                    } else {
                        Toast.makeText(mContext, "Please Check Internet Connection", Toast.LENGTH_SHORT).show();
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
}