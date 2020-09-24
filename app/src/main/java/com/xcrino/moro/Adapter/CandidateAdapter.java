package com.xcrino.moro.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.xcrino.moro.Interface.ApiInterface;
import com.xcrino.moro.JobModule.RecruiterCandidateDetailActivity;
import com.xcrino.moro.PojoModels.CommonResponseModel;
import com.xcrino.moro.PojoModels.ReceivedSavedJobsCandidatesList;
import com.xcrino.moro.R;
import com.xcrino.moro.Utilities.ApiClients;
import com.xcrino.moro.Utilities.AppPreferencesShared;
import com.xcrino.moro.Utilities.NetworkStatus;
import com.xcrino.moro.ViewHolder.CandidateViewHolder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CandidateAdapter extends RecyclerView.Adapter<CandidateViewHolder> {

    private Context context;
    private List<ReceivedSavedJobsCandidatesList> receivedSavedJobsCandidatesLists;
    AppPreferencesShared appPreferencesShared;

    public CandidateAdapter(Context context, List<ReceivedSavedJobsCandidatesList> receivedSavedJobsCandidatesLists) {
        this.context = context;
        this.receivedSavedJobsCandidatesLists = receivedSavedJobsCandidatesLists;
        appPreferencesShared = new AppPreferencesShared(context);
    }

    @NonNull
    @Override
    public CandidateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_candidate, parent, false);
        CandidateViewHolder candidateViewHolder = new CandidateViewHolder(view);
        return candidateViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CandidateViewHolder holder, int position) {
        holder.candidate_name.setText(receivedSavedJobsCandidatesLists.get(position).getEmployeeName().toString() +
                "(" + receivedSavedJobsCandidatesLists.get(position).getEmployeeDesignation().toString() + ")");
        holder.candidate_company_name.setText(receivedSavedJobsCandidatesLists.get(position).getEmployeeLastCompanyName() == null ? "Not Provided" : receivedSavedJobsCandidatesLists.get(position).getEmployeeLastCompanyName());
        holder.job_experience_package.setText(receivedSavedJobsCandidatesLists.get(position).getEmployeeJobExp().toString() +
                ", " + receivedSavedJobsCandidatesLists.get(position).getEmployeeSalaryExpectation().toString());
        holder.job_location_dob.setText("DOB : " + receivedSavedJobsCandidatesLists.get(position).getEmployeeDob().toString() +
                ", " + receivedSavedJobsCandidatesLists.get(position).getEmployeeAddress().toString());

        holder.job_title.setVisibility(View.GONE);

        if (receivedSavedJobsCandidatesLists.get(position).getUserProfileImage() != null) {
            Picasso.with(context).load(receivedSavedJobsCandidatesLists.get(position).getUserProfileImage().toString()).
                    resize(100, 100).transform(new CropCircleTransformation())
                    .placeholder(R.drawable.user_dummy).into(holder.candidate_imgImage);
        }

        if (receivedSavedJobsCandidatesLists.get(position).getCreatedDate() != null) {
            String inputPattern = "yyyy-MM-dd";
            String outputPattern = "dd-MM-yyyy";
            SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
            SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

            Date date = null;
            String str = null;

            try {
                date = inputFormat.parse(receivedSavedJobsCandidatesLists.get(position).getCreatedDate().toString());
                str = outputFormat.format(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            holder.created_on.setText(str.toString());
        } else {
            holder.created_on.setText("Created on:");
        }

        if (receivedSavedJobsCandidatesLists.get(position).getSave_status()) {
            savedLayoutMethod(true, holder, position);
        } else {
            savedLayoutMethod(false, holder, position);
        }

        holder.save_job_star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appPreferencesShared.setCandidateId(receivedSavedJobsCandidatesLists.get(position).getUserId());
                if (NetworkStatus.isNetworkAvailable(context)) {
                    savedCandidateMethod(holder, position);
                } else {
                    Toast.makeText(context, "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, RecruiterCandidateDetailActivity.class);
                if (receivedSavedJobsCandidatesLists.get(position).getSave_status()) {
                    appPreferencesShared.setSavedStatus(true);
                } else {
                    appPreferencesShared.setSavedStatus(false);

                }
                appPreferencesShared.setCandidateId(receivedSavedJobsCandidatesLists.get(position).getUserId());
                appPreferencesShared.setSavedStatus(receivedSavedJobsCandidatesLists.get(position).getSave_status());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return receivedSavedJobsCandidatesLists.size();
    }

    private void savedCandidateMethod(CandidateViewHolder holder, int position) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        ApiInterface apiInterface = ApiClients.getClient().create(ApiInterface.class);
        Call<CommonResponseModel> savecandidatecall = apiInterface.saveCandidateMethod("Mogo_api/save_candidate/" + appPreferencesShared.getUserId() + "/" + appPreferencesShared.getCandidateId(), "1");
        savecandidatecall.enqueue(new Callback<CommonResponseModel>() {
            @Override
            public void onResponse(Call<CommonResponseModel> call, Response<CommonResponseModel> response) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                if (response.isSuccessful()) {
                    if (response.body().getResult()) {
                        Intent intent = new Intent("custom-message");
                        intent.putExtra("response", true);
                        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                    } else {
                        Intent intent = new Intent("custom-message");
                        intent.putExtra("response", true);
                        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                    }
                } else {
                    Toast.makeText(context, "Could not save it due to network issue", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CommonResponseModel> call, Throwable t) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                Toast.makeText(context, "Could not save it due to network issue", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void savedLayoutMethod(boolean saved, CandidateViewHolder holder, int position) {
        if (saved) {
            holder.save_job_star.setImageResource(R.drawable.ic_saved_star);
            holder.save_job_star.setColorFilter(ContextCompat.getColor(context, R.color.light_purple));
        } else {
            holder.save_job_star.setImageResource(R.drawable.ic_star);
            holder.save_job_star.setColorFilter(ContextCompat.getColor(context, R.color.light_purple));
        }

    }

}
