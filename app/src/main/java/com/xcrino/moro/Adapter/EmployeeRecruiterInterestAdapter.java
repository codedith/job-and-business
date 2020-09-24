package com.xcrino.moro.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.xcrino.moro.JobModule.EmployeeJobDetailActivity;
import com.xcrino.moro.PojoModels.GetSavedJobListModel;
import com.xcrino.moro.R;
import com.xcrino.moro.ViewHolder.EmployeeRecruiterInterestViewHolder;

import java.util.List;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class EmployeeRecruiterInterestAdapter extends RecyclerView.Adapter<EmployeeRecruiterInterestViewHolder> {

    Context context;
    List<GetSavedJobListModel> recruiterinterest;

    public EmployeeRecruiterInterestAdapter(Context context, List<GetSavedJobListModel> recruiterinterest) {
        this.context = context;
        this.recruiterinterest = recruiterinterest;
    }

    @NonNull
    @Override
    public EmployeeRecruiterInterestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_recuiter_interest, parent, false);
        EmployeeRecruiterInterestViewHolder employeeRecruiterInterestViewHolder = new EmployeeRecruiterInterestViewHolder(view);
        return employeeRecruiterInterestViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeRecruiterInterestViewHolder holder, int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EmployeeJobDetailActivity.class);
                context.startActivity(intent);
            }
        });
        holder.recruiter_name.setText(recruiterinterest.get(position).getRecruiterName() == null ? "Not Provided" : recruiterinterest.get(position).getRecruiterName().toString());
        holder.recruiter_company_name.setText(recruiterinterest.get(position).getRecruiterCompanyName() == null ? "Not Provided" : recruiterinterest.get(position).getRecruiterCompanyName().toString());
//        holder.job_title.setText(recruiterinterest.get(position).getJobTitle().toString());
        holder.job_location.setText(recruiterinterest.get(position).getCompanyAddress() == null ? "Not Provided" : recruiterinterest.get(position).getCompanyAddress().toString());
        holder.date_and_time.setText(recruiterinterest.get(position).getCreatedDate().toString());
        if (recruiterinterest.get(position).getUserProfileImage() != null) {
            Picasso.with(context).load(recruiterinterest.get(position).getUserProfileImage().toString())
                    .resize(100, 100).transform(new CropCircleTransformation())
                    .placeholder(R.drawable.user_dummy).into(holder.recruiter_img);
        }
    }

    @Override
    public int getItemCount() {
        return recruiterinterest.size();
    }
}
