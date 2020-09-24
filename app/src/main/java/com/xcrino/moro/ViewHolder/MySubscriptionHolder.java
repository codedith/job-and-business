package com.xcrino.moro.ViewHolder;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xcrino.moro.R;

public class MySubscriptionHolder extends RecyclerView.ViewHolder {

    public TextView plan_type, total_applicable_applies, total_consumed_applies, total_remaining_applies;
    public LinearLayout card_background;

    public MySubscriptionHolder(@NonNull View itemView) {
        super(itemView);

        plan_type = itemView.findViewById(R.id.plan_type);
        total_applicable_applies = itemView.findViewById(R.id.total_applicable_applies);
        total_consumed_applies = itemView.findViewById(R.id.total_consumed_applies);
        total_remaining_applies = itemView.findViewById(R.id.total_remaining_applies);
        card_background = itemView.findViewById(R.id.card_background);
    }
}
