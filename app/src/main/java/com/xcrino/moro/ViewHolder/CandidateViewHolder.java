package com.xcrino.moro.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xcrino.moro.R;

public class CandidateViewHolder extends RecyclerView.ViewHolder {

    public ImageView candidate_imgImage, save_job_star;
    public TextView candidate_name, candidate_company_name, job_experience_package, job_location_dob, job_title, created_on;

    public CandidateViewHolder(@NonNull View itemView) {
        super(itemView);

        candidate_imgImage = (ImageView) itemView.findViewById(R.id.candidate_imgImage);
        candidate_name = (TextView) itemView.findViewById(R.id.candidate_name);
        candidate_company_name = (TextView) itemView.findViewById(R.id.candidate_company_name);
        job_experience_package = (TextView) itemView.findViewById(R.id.job_experience_package);
        job_location_dob = (TextView) itemView.findViewById(R.id.job_location_dob);
        job_title = (TextView) itemView.findViewById(R.id.job_title);
        created_on = itemView.findViewById(R.id.created_on);
        save_job_star = itemView.findViewById(R.id.save_job_star);

    }
}
