package com.xcrino.moro.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xcrino.moro.PojoModels.JobDetails;
import com.xcrino.moro.R;
import com.xcrino.moro.Utilities.AppPreferencesShared;
import com.xcrino.moro.ViewHolder.PostedJobListViewHolder;

import java.util.List;

public class PostedJobListAdapter extends RecyclerView.Adapter<PostedJobListViewHolder> {

    private Context context;
    private AppPreferencesShared appPreferencesShared;
    private List<JobDetails> jobList;
    private JobDetails jobDetails;

    public PostedJobListAdapter(Context context, List<JobDetails> jobList) {
        this.context = context;
        this.jobList = jobList;
        appPreferencesShared = new AppPreferencesShared(context);
    }

    @NonNull
    @Override
    public PostedJobListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_posted_job_list, parent, false);
        PostedJobListViewHolder postedJobListViewHolder = new PostedJobListViewHolder(view);
        return postedJobListViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PostedJobListViewHolder holder, int position) {
//        jobDetails = jobList.get(position);
        holder.posted_job_title.setText(jobList.get(position).getJobTitle().toString());
        holder.posted_job_company_name.setText(jobList.get(position).getCompanyName() == null ? "Company Name: " : jobList.get(position).getCompanyName().toString());
        holder.experience.setText(jobList.get(position).getExperience() == null ? "Experience: " : jobList.get(position).getExperience().toString());
        holder.salary_package.setText(jobList.get(position).getSalary() == null ? "Salary: " : jobList.get(position).getSalary().toString());
        holder.company_name_location.setText(jobList.get(position).getCompanyAddress() == null ? "Location: " : jobList.get(position).getCompanyAddress().toString());
        holder.job_created_date.setText("Posted on: " + jobList.get(position).getCreatedDate() == null ? "Posted on:" : "Posted on: " + jobList.get(position).getCreatedDate().toString());


    }

    @Override
    public int getItemCount() {
        return jobList.size();
    }
}