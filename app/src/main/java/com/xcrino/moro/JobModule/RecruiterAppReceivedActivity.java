package com.xcrino.moro.JobModule;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xcrino.moro.Adapter.RecruiterAppReceivedAdapter;
import com.xcrino.moro.Interface.ApiInterface;
import com.xcrino.moro.PojoModels.ReceivedSavedJobsCandidates;
import com.xcrino.moro.PojoModels.ReceivedSavedJobsCandidatesList;
import com.xcrino.moro.R;
import com.xcrino.moro.Utilities.ApiClients;
import com.xcrino.moro.Utilities.AppPreferencesShared;
import com.xcrino.moro.Utilities.NetworkStatus;

import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecruiterAppReceivedActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView toolbar_title;
    private ImageView back_arrow;
    private Context context;
    RecyclerView recycler_view;
    private AppPreferencesShared appPreferencesShared;
    private List<ReceivedSavedJobsCandidatesList> receivedSavedJobsCandidatesLists;
    private RecruiterAppReceivedAdapter recruiterAppReceivedAdapter;
    private RelativeLayout recruiterapprecieved_available;
    private TextView recruiterapprecieved_notavailable;

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
        setContentView(R.layout.activity_recruiter_app_received);
        context = this;

        getLayoutUiId();

        toolbar_title.setText("Applications Received");
        if (NetworkStatus.isNetworkAvailable(context)) {
            getApplicationsReceivedListMethod();
        } else {
            Toast.makeText(context, "Please Check Your Internet Connection !", Toast.LENGTH_SHORT).show();
            recruiterapprecieved_available.setVisibility(View.GONE);
            recruiterapprecieved_notavailable.setVisibility(View.VISIBLE);
            recruiterapprecieved_notavailable.setText("Check Your Internet Connection");
        }

       /* recycler_view.setHasFixedSize(true);
        recycler_view.setLayoutManager(new LinearLayoutManager(context));
        recycler_view.setAdapter(new RecruiterAppReceivedAdapter(context));*/

        back_arrow.setOnClickListener(this);
    }

    private void getApplicationsReceivedListMethod() {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        ApiInterface apiInterface = ApiClients.getClient().create(ApiInterface.class);
        Call<ReceivedSavedJobsCandidates> receivedSavedJobsCandidatesCall = apiInterface.getApplicationsReceivedListMethod(
                "Mogo_api/get_applications_rec/" + appPreferencesShared.getUserId());
        receivedSavedJobsCandidatesCall.enqueue(new Callback<ReceivedSavedJobsCandidates>() {
            @Override
            public void onResponse(Call<ReceivedSavedJobsCandidates> call, Response<ReceivedSavedJobsCandidates> response) {
                try {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    if (response.isSuccessful()) {
                        if (response.body().getReceivedSavedJobsCandidatesLists() != null) {
                            receivedSavedJobsCandidatesLists = response.body().getReceivedSavedJobsCandidatesLists();
                            recruiterapprecieved_available.setVisibility(View.VISIBLE);
                            recruiterapprecieved_notavailable.setVisibility(View.GONE);

                            setApplicationsReceivedListMethodLayout();
                        } else {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
//                            Toast.makeText(context, "Something went wrong!", Toast.LENGTH_SHORT).show();
                            recruiterapprecieved_available.setVisibility(View.GONE);
                            recruiterapprecieved_notavailable.setVisibility(View.VISIBLE);
                            recruiterapprecieved_notavailable.setText("No Applications Received Yet");
                        }
                    }
                    else {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        Toast.makeText(context, "Something went wrong !", Toast.LENGTH_SHORT).show();
                        recruiterapprecieved_available.setVisibility(View.GONE);
                        recruiterapprecieved_notavailable.setVisibility(View.VISIBLE);
                        recruiterapprecieved_notavailable.setText("No Applications Received Yet");
                    }

                } catch (Exception e) {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    e.getMessage();
                    recruiterapprecieved_available.setVisibility(View.GONE);
                    recruiterapprecieved_notavailable.setVisibility(View.VISIBLE);
                    recruiterapprecieved_notavailable.setText("No Applications Received Yet");
                }
            }

            @Override
            public void onFailure(Call<ReceivedSavedJobsCandidates> call, Throwable t) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                recruiterapprecieved_available.setVisibility(View.GONE);
                recruiterapprecieved_notavailable.setVisibility(View.VISIBLE);
                recruiterapprecieved_notavailable.setText("No Applications Recieved Yet");

            }
        });
    }

    private void setApplicationsReceivedListMethodLayout() {
        recruiterAppReceivedAdapter = new RecruiterAppReceivedAdapter(context, receivedSavedJobsCandidatesLists);
        LinearLayoutManager linearHorizontal = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recycler_view.setLayoutManager(linearHorizontal);
        recycler_view.setAdapter(recruiterAppReceivedAdapter);
        recycler_view.setHasFixedSize(true);

    }

    private void getLayoutUiId() {
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        back_arrow = (ImageView) findViewById(R.id.back_arrow);
        recycler_view = findViewById(R.id.recycler_view);
        recruiterapprecieved_available = (RelativeLayout) findViewById(R.id.recruiterapprecieved_available);
        recruiterapprecieved_notavailable = (TextView) findViewById(R.id.recruiterapprecieved_notavailable);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_arrow:
                onBackPressed();
                break;
        }
    }
}
