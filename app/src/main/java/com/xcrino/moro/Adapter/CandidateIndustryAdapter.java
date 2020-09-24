package com.xcrino.moro.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xcrino.moro.JobModule.CandidateSearchByIndustry;
import com.xcrino.moro.R;
import com.xcrino.moro.Utilities.AppPreferencesShared;
import com.xcrino.moro.ViewHolder.IndustryViewHolder;

import java.util.ArrayList;

public class CandidateIndustryAdapter extends RecyclerView.Adapter<IndustryViewHolder> {

    Context context;
    ArrayList<String> industryNameArrayList;
    AppPreferencesShared appPreferencesShared;

    public CandidateIndustryAdapter(Context context, ArrayList<String> industryNameArrayList) {
        this.context = context;
        this.industryNameArrayList = industryNameArrayList;
        appPreferencesShared = new AppPreferencesShared(context);
    }

    @NonNull
    @Override
    public IndustryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.card_industry, parent, false);
        IndustryViewHolder industryViewHolder = new IndustryViewHolder(view);
        return industryViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull IndustryViewHolder holder, int position) {
        holder.text_industry.setText(industryNameArrayList.get(position).toString());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (appPreferencesShared.getPageDirection().equals("Candidate Search"))
                {
                    Intent intent = new Intent(context, CandidateSearchByIndustry.class);
//                    appPreferencesShared.setPageDirection("Candidate Search By Industry");
                    appPreferencesShared.setIndustryName(industryNameArrayList.get(position).toString());
                    context.startActivity(intent);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return industryNameArrayList.size();
    }
}
