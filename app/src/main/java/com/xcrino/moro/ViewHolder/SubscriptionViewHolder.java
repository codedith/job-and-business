package com.xcrino.moro.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.xcrino.moro.R;

public class SubscriptionViewHolder extends RecyclerView.ViewHolder {

    public TextView plan_type, valid_from, valid_thru, plan_amount_card, total_credits;
    public CardView card_layout;

    public SubscriptionViewHolder(@NonNull View itemView) {
        super(itemView);
        plan_type = itemView.findViewById(R.id.plan_type);
        valid_from = itemView.findViewById(R.id.valid_from);
        valid_thru = itemView.findViewById(R.id.valid_thru);
        plan_amount_card = itemView.findViewById(R.id.plan_amount_card);
        total_credits = itemView.findViewById(R.id.total_credits);
        card_layout = itemView.findViewById(R.id.card_layout);

    }
}