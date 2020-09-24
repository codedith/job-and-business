package com.xcrino.moro.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.xcrino.moro.JobModule.RecruiterCandidateDetailActivity;
import com.xcrino.moro.PojoModels.ReceivedSavedJobsCandidatesList;
import com.xcrino.moro.R;
import com.xcrino.moro.Utilities.AppPreferencesShared;
import com.xcrino.moro.ViewHolder.RecruiterAppReceivedViewHolder;

import java.util.List;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class RecruiterAppReceivedAdapter extends RecyclerView.Adapter<RecruiterAppReceivedViewHolder> {

    private Context context;
    private List<ReceivedSavedJobsCandidatesList> receivedSavedJobsCandidatesLists;
    private AppPreferencesShared appPreferencesShared;

    public RecruiterAppReceivedAdapter(Context context, List<ReceivedSavedJobsCandidatesList> receivedSavedJobsCandidatesLists) {
        this.context = context;
        this.receivedSavedJobsCandidatesLists = receivedSavedJobsCandidatesLists;
        appPreferencesShared = new AppPreferencesShared(context);
    }

    @NonNull
    @Override
    public RecruiterAppReceivedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_application_received, parent, false);
        RecruiterAppReceivedViewHolder recruiterAppReceivedViewHolder = new RecruiterAppReceivedViewHolder(view);
        return recruiterAppReceivedViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecruiterAppReceivedViewHolder holder, int position) {

        holder.candidate_name_designation.setText(receivedSavedJobsCandidatesLists.get(position).getEmployeeName().toString() +
                "(" + receivedSavedJobsCandidatesLists.get(position).getEmployeeDesignation().toString() + ")");
        holder.candidate_company_name.setText(receivedSavedJobsCandidatesLists.get(position).getEmployeeLastCompanyName().toString());
        holder.candidate_experience_package.setText(receivedSavedJobsCandidatesLists.get(position).getEmployeeJobExp().toString() +
                ", " + receivedSavedJobsCandidatesLists.get(position).getEmployeeSalaryExpectation().toString());
        holder.candidate_dob_location.setText("DOB :" + receivedSavedJobsCandidatesLists.get(position).getEmployeeDob().toString() +
                ", " + receivedSavedJobsCandidatesLists.get(position).getEmployeeAddress().toString());
        holder.job_title.setText(receivedSavedJobsCandidatesLists.get(position).getJobTitle().toString());
        holder.date_and_time.setText(receivedSavedJobsCandidatesLists.get(position).getCreatedDate().toString());

        if (receivedSavedJobsCandidatesLists.get(position).getUserProfileImage() != null) {
            Picasso.with(context).load(receivedSavedJobsCandidatesLists.get(position).getUserProfileImage().toString()).
                    resize(100, 100).transform(new CropCircleTransformation())
                    .placeholder(R.drawable.user_dummy).into(holder.candidate_img);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, RecruiterCandidateDetailActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return receivedSavedJobsCandidatesLists.size();
    }
}
