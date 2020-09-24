package com.xcrino.moro.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xcrino.moro.R;

public class PostedJobListViewHolder extends RecyclerView.ViewHolder {

    public TextView posted_job_title, salary_package, company_name_location, job_created_date, posted_job_company_name, experience;

    public PostedJobListViewHolder(@NonNull View itemView) {
        super(itemView);
        posted_job_title = (TextView) itemView.findViewById(R.id.posted_job_title);
        salary_package = itemView.findViewById(R.id.salary_package);
        company_name_location = (TextView) itemView.findViewById(R.id.company_name_location);
        job_created_date = itemView.findViewById(R.id.job_created_date);
        posted_job_company_name = itemView.findViewById(R.id.posted_job_company_name);
        experience = itemView.findViewById(R.id.experience);

    }
}
