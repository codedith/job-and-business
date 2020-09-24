package com.xcrino.moro.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xcrino.moro.R;

public class EmployeeRecruiterInterestViewHolder extends RecyclerView.ViewHolder {
    public TextView recruiter_name,recruiter_company_name,job_title,job_location,job_experience_salary,date_and_time;
    public ImageView recruiter_img;

    public EmployeeRecruiterInterestViewHolder(@NonNull View itemView) {
        super(itemView);
        recruiter_name = itemView.findViewById(R.id.recruiter_name);
        recruiter_company_name = itemView.findViewById(R.id.recruiter_company_name);
        job_title = itemView.findViewById(R.id.job_title);
        job_location = itemView.findViewById(R.id.job_location);
        job_experience_salary = itemView.findViewById(R.id.job_experience_salary);
        date_and_time = itemView.findViewById(R.id.date_and_time);
        recruiter_img = itemView.findViewById(R.id.recruiter_img);
    }
}
