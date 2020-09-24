package com.xcrino.moro.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.xcrino.moro.Activity.PaymentActivity;
import com.xcrino.moro.PojoModels.SubscriptionPlan;
import com.xcrino.moro.PojoModels.UserProfile;
import com.xcrino.moro.R;
import com.xcrino.moro.Utilities.AppPreferencesShared;
import com.xcrino.moro.ViewHolder.SubscriptionPlanHolder;

import java.util.ArrayList;
import java.util.List;

public class SubscriptionPlanAdapter extends RecyclerView.Adapter<SubscriptionPlanHolder> {

    private Context mContext;
    private List<SubscriptionPlan> subscriptionPlanList;
    private AppPreferencesShared appPreferencesShared;
    private int[] background = {R.drawable.sub_plan_bg_green, R.drawable.sub_plan_bg_blue, R.drawable.sub_plan_pink};
    private String amount;
    UserProfile userProfile;

    public SubscriptionPlanAdapter(Context mContext, List<SubscriptionPlan> subscriptionPlanList) {
        this.mContext = mContext;
        this.subscriptionPlanList = subscriptionPlanList;
        appPreferencesShared = new AppPreferencesShared(mContext);
        Gson gsonUserProfile = new Gson();
        String json = appPreferencesShared.getUserDetails();
        userProfile = gsonUserProfile.fromJson(json, UserProfile.class);
    }

    @NonNull
    @Override
    public SubscriptionPlanHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.card_subscription_plan, parent, false);
        SubscriptionPlanHolder subscriptionPlanHolder = new SubscriptionPlanHolder(view);
        return subscriptionPlanHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull SubscriptionPlanHolder holder, int position) {
        switch (appPreferencesShared.getSubscriptionDirection()) {
            case "Employee":
                ArrayList<Integer> arr1 = new ArrayList<>();
                for (int i = 0; i < 7; i++) {
                    for (int k = 0; k < background.length; k++) {
                        if (arr1.size() == subscriptionPlanList.size()) {
                            break;
                        } else {
                            arr1.add(background[k]);
                        }
                    }
                }
                holder.card_background.setBackgroundResource(arr1.get(position));
                holder.planName.setText(subscriptionPlanList.get(position).getEsType() + " Plan");
                holder.plan_amount.setText("Subscription Amount : ");
                if (userProfile.getUserDatum().getUserProfileData().getUserCountryId().equals("india") ||
                        userProfile.getUserDatum().getUserProfileData().getUserCountryId().equals("India")) {
                    holder.plan_amount.append(subscriptionPlanList.get(position).getEsAmount().toString());
                    holder.plan_amount.append(" ");
                    holder.plan_amount.append(subscriptionPlanList.get(position).getEsCurrency().toString());
                } else {
                    holder.plan_amount.append(subscriptionPlanList.get(position).getEsForeignAmt().toString());
                    holder.plan_amount.append(" ");
                    holder.plan_amount.append(subscriptionPlanList.get(position).getEsForeignCur().toString());
                }
                holder.line_first.setText("Valid Upto : -NA-");
                holder.line_second.setText("Total Credits : ");
                holder.line_second.append(subscriptionPlanList.get(position).getEsCredits().toString());

                holder.start_now_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SubscriptionPlan subscriptionPlan = subscriptionPlanList.get(position);
                        Gson gson = new Gson();
                        String json = gson.toJson(subscriptionPlan);
                        appPreferencesShared.setPlanDetails(json);

                        mContext.startActivity(new Intent(mContext, PaymentActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                    }
                });

                break;

            case "Recruiter":
                ArrayList<Integer> arr2 = new ArrayList<>();
                for (int i = 0; i < 7; i++) {
                    for (int k = 0; k < background.length; k++) {
                        if (arr2.size() == subscriptionPlanList.size()) {
                            break;
                        } else {
                            arr2.add(background[k]);
                        }
                    }
                }
                holder.card_background.setBackgroundResource(arr2.get(position));
                holder.planName.setText(subscriptionPlanList.get(position).getPlansName() + " Plan");
                holder.plan_amount.setText("Subscription Amount : ");
                if (userProfile.getUserDatum().getUserProfileData().getUserCountryId().equals("india") ||
                        userProfile.getUserDatum().getUserProfileData().getUserCountryId().equals("India")) {
                    holder.plan_amount.append(subscriptionPlanList.get(position).getAmount().toString());
                    holder.plan_amount.append(" ");
                    holder.plan_amount.append(subscriptionPlanList.get(position).getCurrency().toString());
                } else {
                    holder.plan_amount.append(subscriptionPlanList.get(position).getForeignAmt().toString());
                    holder.plan_amount.append(" ");
                    holder.plan_amount.append(subscriptionPlanList.get(position).getForeignCur().toString());
                }

                holder.line_first.setText("Valid Upto : ");
                holder.line_first.append(subscriptionPlanList.get(position).getDays().toString() + " Days");
                holder.line_second.setText("Total Credits : ");
                holder.line_second.append(subscriptionPlanList.get(position).getPostJob().toString());

                holder.start_now_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SubscriptionPlan subscriptionPlan = subscriptionPlanList.get(position);
                        Gson gson = new Gson();
                        String json = gson.toJson(subscriptionPlan);
                        appPreferencesShared.setPlanDetails(json);
                        mContext.startActivity(new Intent(mContext, PaymentActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                    }
                });

                break;

            case "Business":
                ArrayList<Integer> arr3 = new ArrayList<>();
                for (int i = 0; i < 7; i++) {
                    for (int k = 0; k < background.length; k++) {
                        if (arr3.size() == subscriptionPlanList.size()) {
                            break;
                        } else {
                            arr3.add(background[k]);
                        }
                    }
                }
                holder.card_background.setBackgroundResource(arr3.get(position));
                holder.planName.setText("Business Plan");
                holder.plan_amount.setText("Subscription Amount : ");
                if (userProfile.getUserDatum().getUserProfileData().getUserCountryId().equals("india") ||
                        userProfile.getUserDatum().getUserProfileData().getUserCountryId().equals("India")) {
                    holder.plan_amount.append(subscriptionPlanList.get(position).getBmsAmtInr().toString());
                    holder.plan_amount.append(" ");
                    holder.plan_amount.append(subscriptionPlanList.get(position).getBmsAmtCur().toString());
                } else {
                    holder.plan_amount.append(subscriptionPlanList.get(position).getBmsAmtUsd().toString());
                    holder.plan_amount.append(" ");
                    holder.plan_amount.append(subscriptionPlanList.get(position).getBmsAmtForeignCur().toString());
                }
                holder.line_first.setText("Valid Upto : ");
                holder.line_first.append(subscriptionPlanList.get(position).getBmsValidity());
                holder.line_first.append(" Days");
                holder.line_second.setText("Total Credits : ");
                holder.line_second.append(subscriptionPlanList.get(position).getBmsCredit().toString());

                holder.start_now_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SubscriptionPlan subscriptionPlan = subscriptionPlanList.get(position);
                        Gson gson = new Gson();
                        String json = gson.toJson(subscriptionPlan);
                        appPreferencesShared.setPlanDetails(json);

                        mContext.startActivity(new Intent(mContext, PaymentActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                    }
                });

                break;
        }
    }

    @Override
    public int getItemCount() {
        return subscriptionPlanList.size();

    }
}