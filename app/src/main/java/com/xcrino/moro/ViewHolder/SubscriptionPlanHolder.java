package com.xcrino.moro.ViewHolder;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xcrino.moro.R;

public class SubscriptionPlanHolder extends RecyclerView.ViewHolder {

    public TextView plan_amount, planName;
    public TextView line_first, line_second;
    public Button start_now_button;
    public LinearLayout card_background;

    public SubscriptionPlanHolder(@NonNull View itemView) {
        super(itemView);

        plan_amount = (TextView) itemView.findViewById(R.id.plan_amount);
        line_first = (TextView) itemView.findViewById(R.id.line_first);
        line_second = (TextView) itemView.findViewById(R.id.line_second);
        planName = (TextView) itemView.findViewById(R.id.planName);
        start_now_button = (Button) itemView.findViewById(R.id.start_now_button);
        card_background = (LinearLayout) itemView.findViewById(R.id.card_background);

    }
}
