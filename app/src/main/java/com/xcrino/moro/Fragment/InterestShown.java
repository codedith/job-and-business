package com.xcrino.moro.Fragment;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.xcrino.moro.Adapter.CandidateAdapter;
import com.xcrino.moro.Interface.ApiInterface;
import com.xcrino.moro.PojoModels.ReceivedSavedJobsCandidates;
import com.xcrino.moro.PojoModels.ReceivedSavedJobsCandidatesList;
import com.xcrino.moro.R;
import com.xcrino.moro.Utilities.ApiClients;
import com.xcrino.moro.Utilities.AppPreferencesShared;
import com.xcrino.moro.Utilities.NetworkStatus;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class InterestShown extends Fragment {

    Context context;
    private List<ReceivedSavedJobsCandidatesList> receivedSavedJobsCandidatesLists = new ArrayList<>();
    private RecyclerView recycler_view;
    TextView not_available;
    private AppPreferencesShared appPreferencesShared;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_interest_shown, container, false);
        not_available = view.findViewById(R.id.not_available);
        recycler_view = view.findViewById(R.id.recycler_view);
        context = getContext();
        appPreferencesShared = new AppPreferencesShared(context);
        appPreferencesShared.setPageDirection("Interested Candidate");

        if (NetworkStatus.isNetworkAvailable(context)) {
            getSavedCandidatesListMethod();
        } else {
            Toast.makeText(context, "Please Check Your Internet Connection", Toast.LENGTH_SHORT).show();
        }

        LocalBroadcastManager.getInstance(context).registerReceiver(mMessageReceiver,
                new IntentFilter("custom-message"));
        return view;
    }

    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String ItemName = intent.getStringExtra("response");
            if (!receivedSavedJobsCandidatesLists.isEmpty()) {
                receivedSavedJobsCandidatesLists.clear();
            }
            if (NetworkStatus.isNetworkAvailable(context)) {
                getSavedCandidatesListMethod();
            } else {
                Toast.makeText(context, "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
                recycler_view.setVisibility(View.VISIBLE);
                not_available.setVisibility(View.GONE);
                not_available.setText("Please Check your Internet Connection");
            }
        }
    };

    private void getSavedCandidatesListMethod() {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        ApiInterface apiInterface = ApiClients.getClient().create(ApiInterface.class);
        Call<ReceivedSavedJobsCandidates> savedJobsCandidatesCall = apiInterface.getSavedCandidatesListMethod("Mogo_api/get_save_candidates/" + appPreferencesShared.getUserId(), "2");
        savedJobsCandidatesCall.enqueue(new Callback<ReceivedSavedJobsCandidates>() {
            @Override
            public void onResponse(Call<ReceivedSavedJobsCandidates> call, Response<ReceivedSavedJobsCandidates> response) {
                try {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            if (response.body().getReceivedSavedJobsCandidatesLists() != null) {
                                receivedSavedJobsCandidatesLists = response.body().getReceivedSavedJobsCandidatesLists();
                                recycler_view.setVisibility(View.VISIBLE);
                                not_available.setVisibility(View.GONE);
                                recycler_view.setLayoutManager(new LinearLayoutManager(context));
                                recycler_view.setAdapter(new CandidateAdapter(context, receivedSavedJobsCandidatesLists));
                                recycler_view.setHasFixedSize(true);
                            } else {
                                if (progressDialog.isShowing()) {
                                    progressDialog.dismiss();
                                }
                                recycler_view.setVisibility(View.GONE);
                                not_available.setVisibility(View.VISIBLE);
                                not_available.setText("No Candidates have been Responded Yet");
                            }
                        } else {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                            recycler_view.setVisibility(View.GONE);
                            not_available.setVisibility(View.VISIBLE);
                            not_available.setText("No Candidates have been Responded Yet");
                        }
                    }

                } catch (Exception e) {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    e.getMessage();
                    recycler_view.setVisibility(View.GONE);
                    not_available.setVisibility(View.VISIBLE);
                    not_available.setText("No Candidates have been Responded Yet");
                }
            }

            @Override
            public void onFailure(Call<ReceivedSavedJobsCandidates> call, Throwable t) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                recycler_view.setVisibility(View.GONE);
                not_available.setVisibility(View.VISIBLE);
                not_available.setText("No Candidates have been Responded Yet");

            }
        });
    }
}
