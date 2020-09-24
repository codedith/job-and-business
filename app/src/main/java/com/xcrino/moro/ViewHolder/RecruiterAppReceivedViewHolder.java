package com.xcrino.moro.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xcrino.moro.R;

public class RecruiterAppReceivedViewHolder extends RecyclerView.ViewHolder {

    public ImageView candidate_img;
    public TextView candidate_name_designation, candidate_company_name, candidate_experience_package, candidate_dob_location,
            job_title, date_and_time;

    public RecruiterAppReceivedViewHolder(@NonNull View itemView) {
        super(itemView);

        candidate_img = (ImageView) itemView.findViewById(R.id.candidate_img);
        candidate_name_designation = (TextView) itemView.findViewById(R.id.candidate_name_designation);
        candidate_company_name = (TextView) itemView.findViewById(R.id.candidate_company_name);
        candidate_experience_package = (TextView) itemView.findViewById(R.id.candidate_experience_package);
        candidate_dob_location = (TextView) itemView.findViewById(R.id.candidate_dob_location);
        job_title = (TextView) itemView.findViewById(R.id.job_title);
        date_and_time = (TextView) itemView.findViewById(R.id.date_and_time);

    }
}
