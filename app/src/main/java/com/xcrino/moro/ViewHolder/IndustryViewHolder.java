package com.xcrino.moro.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xcrino.moro.R;

public class IndustryViewHolder extends RecyclerView.ViewHolder {

    public ImageView img_industry;
    public TextView text_industry;

    public IndustryViewHolder(@NonNull View itemView) {
        super(itemView);

        img_industry = itemView.findViewById(R.id.img_industry);
        text_industry = itemView.findViewById(R.id.text_industry);
    }
}
