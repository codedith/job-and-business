package com.xcrino.moro.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.xcrino.moro.R;

public class JobViewHolder extends RecyclerView.ViewHolder {

    public TextView mJob_title, mJob_location, mJob_experience, mJob_package, company_name, skills, posted_date;
    public CardView card_view;
    public ImageView save_job_star, company_img;

    public JobViewHolder(@NonNull View itemView) {
        super(itemView);

        mJob_title = itemView.findViewById(R.id.job_title);
        mJob_location = itemView.findViewById(R.id.job_location);
        mJob_experience = itemView.findViewById(R.id.job_experience);
        mJob_package = itemView.findViewById(R.id.salary_package);
        card_view = itemView.findViewById(R.id.card_view);
        save_job_star = itemView.findViewById(R.id.save_job_star);
        company_name = itemView.findViewById(R.id.company_name);
        skills = itemView.findViewById(R.id.job_skills);
        company_img = itemView.findViewById(R.id.company_img);
        posted_date = itemView.findViewById(R.id.posted_date);

    }
}
