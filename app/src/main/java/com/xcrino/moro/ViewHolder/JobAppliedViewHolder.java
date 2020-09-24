package com.xcrino.moro.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xcrino.moro.R;

public class JobAppliedViewHolder extends RecyclerView.ViewHolder {

    public ImageView more_option, recruiter_img;
    public TextView job_title, required_exp_package, company_name_location, recruiter_name, recruiter_company_name;

    public JobAppliedViewHolder(@NonNull View itemView) {
        super(itemView);

        more_option = itemView.findViewById(R.id.more_option);
        recruiter_img = itemView.findViewById(R.id.recruiter_img);
        job_title = itemView.findViewById(R.id.job_title);
        required_exp_package = itemView.findViewById(R.id.required_exp_package);
        company_name_location = itemView.findViewById(R.id.company_name_location);
        recruiter_name = itemView.findViewById(R.id.recruiter_name);
        recruiter_company_name = itemView.findViewById(R.id.recruiter_company_name);

    }
}
