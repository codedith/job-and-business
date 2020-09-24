package com.xcrino.moro.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.xcrino.moro.JobModule.EmployeeJobDetailActivity;
import com.xcrino.moro.PojoModels.GetSavedJobListModel;
import com.xcrino.moro.R;
import com.xcrino.moro.Utilities.AppPreferencesShared;
import com.xcrino.moro.ViewHolder.JobAppliedViewHolder;

import java.util.List;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class JobAppliedAdapter extends RecyclerView.Adapter<JobAppliedViewHolder> {

    Context context;
    AppPreferencesShared appPreferencesShared;
    private List<GetSavedJobListModel> postedJobsListData;
    private GetSavedJobListModel commonPostedJobsListData;
    List<GetSavedJobListModel> appliedjob;

//    public JobAppliedAdapter(Context context, List<CommonPostedJobsListData> postedJobsListData) {
//        this.context = context;
//        this.postedJobsListData = postedJobsListData;
//        appPreferencesShared = new AppPreferencesShared(context);
//
//    }

    public JobAppliedAdapter(Context context, List<GetSavedJobListModel> jobListModelList) {
        this.context = context;
        this.appliedjob = jobListModelList;
        appPreferencesShared = new AppPreferencesShared(context);

    }

    @NonNull
    @Override
    public JobAppliedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_applied_job, parent, false);
        JobAppliedViewHolder jobAppliedViewHolder = new JobAppliedViewHolder(view);
        return jobAppliedViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final JobAppliedViewHolder holder, int position) {
        if (appPreferencesShared.getPageDirection().equals("Applied Job")) {
            holder.more_option.setVisibility(View.VISIBLE);
//            holder.required_exp_package.setVisibility(View.GONE);
            holder.job_title.setText(appliedjob.get(position).getJobTitle() == null ? "Not Provided" : appliedjob.get(position).getJobTitle().toString());
            holder.recruiter_company_name.setText(appliedjob.get(position).getRecruiterCompanyName() == null ? "Not provided" : appliedjob.get(position).getRecruiterCompanyName().toString());
            holder.recruiter_name.setText(appliedjob.get(position).getRecruiterName() == null ? "Not provided" : appliedjob.get(position).getRecruiterName().toString());
            holder.company_name_location.setText(appliedjob.get(position).getCompanyAddress()==null?"Not Provided":appliedjob.get(position).getCompanyAddress().toString());
            holder.required_exp_package.setText(appliedjob.get(position).getSalary()==null?"Not Provided":appliedjob.get(position).getSalary().toString());
            if (appliedjob.get(position).getRecruiterProfileImage() != null) {
                Picasso.with(context).load(postedJobsListData.get(position).getRecruiterProfileImage().toString())
                        .resize(100, 100).transform(new CropCircleTransformation())
                        .placeholder(R.drawable.user_dummy).into(holder.recruiter_img);
            }

        }
        else {
//            holder.more_option.setVisibility(View.GONE);
            holder.required_exp_package.setVisibility(View.VISIBLE);
            holder.job_title.setText(appliedjob.get(position).getJobTitle() == null ? "Not Provided" : appliedjob.get(position).getJobTitle().toString());
            holder.recruiter_company_name.setText(appliedjob.get(position).getRecruiterCompanyName() == null ? "Not provided" : appliedjob.get(position).getRecruiterCompanyName().toString());
            holder.recruiter_name.setText(appliedjob.get(position).getRecruiterName() == null ? "Not provided" : appliedjob.get(position).getRecruiterName().toString());
            holder.company_name_location.setText(appliedjob.get(position).getCompanyAddress()==null?"Not Provided":appliedjob.get(position).getCompanyAddress().toString());
            holder.required_exp_package.setText(appliedjob.get(position).getSalary()==null?"Not Provided":appliedjob.get(position).getSalary().toString());
            if (appliedjob.get(position).getRecruiterProfileImage() != null) {
                Picasso.with(context).load(postedJobsListData.get(position).getRecruiterProfileImage().toString())
                        .resize(100, 100).transform(new CropCircleTransformation())
                        .placeholder(R.drawable.user_dummy).into(holder.recruiter_img);
            }
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EmployeeJobDetailActivity.class);
                appPreferencesShared.setJobId(appliedjob.get(position).getJobId());
                if (appliedjob.get(position).getSaveStatus()) {
                    appPreferencesShared.setSavedStatus(true);
                } else {
                    appPreferencesShared.setSavedStatus(false);
                }
               /* if (appliedjob.get(position).getSaveStatus()) {
                    appPreferencesShared.setSavedStatus(true);
                }
                else {
                    appPreferencesShared.setSavedStatus(false);
                }*/
                context.startActivity(intent);
            }
        });

        holder.more_option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(context, holder.more_option);
                popupMenu.getMenuInflater().inflate(R.menu.recruiter_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_message:
                                break;

                            case R.id.action_follow:
                                break;

                            case R.id.action_mogo_chat:
                                break;
                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return appliedjob.size();
    }
}
