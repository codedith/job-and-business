package com.xcrino.moro.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.xcrino.moro.PojoModels.UpcomingSubscription;
import com.xcrino.moro.PojoModels.UserProfile;
import com.xcrino.moro.R;
import com.xcrino.moro.Utilities.AppPreferencesShared;
import com.xcrino.moro.ViewHolder.SubscriptionViewHolder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class SubsriptionAdapter extends RecyclerView.Adapter<SubscriptionViewHolder> {

    Context mContext;
    private List<UpcomingSubscription> upcomingSubscriptions;
    private AppPreferencesShared appPreferencesShared;
    UserProfile userProfile;

    public SubsriptionAdapter(Context mContext, List<UpcomingSubscription> upcomingSubscriptions) {
        this.mContext = mContext;
        this.upcomingSubscriptions = upcomingSubscriptions;
        appPreferencesShared = new AppPreferencesShared(mContext);
        Gson gsonUserProfile = new Gson();
        String json = appPreferencesShared.getUserDetails();
        userProfile = gsonUserProfile.fromJson(json, UserProfile.class);
    }

    @NonNull
    @Override
    public SubscriptionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.subscription_layout, parent, false);
        return new SubscriptionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubscriptionViewHolder holder, int position) {
        switch (userProfile.getUserDatum().getUserType()) {
            case 1:
                holder.plan_type.setText("Recruiter");
                holder.plan_type.append(upcomingSubscriptions.get(position).getUsType());
                holder.plan_type.append(" Plan");
                if (userProfile.getUserDatum().getUserProfileData().getUserCountryId().equals("India") ||
                        userProfile.getUserDatum().getUserProfileData().getUserCountryId().equals("india")) {
                    holder.plan_amount_card.setText(upcomingSubscriptions.get(position).getUsAmount());
                    holder.plan_amount_card.append(" ");
                    holder.plan_amount_card.append(upcomingSubscriptions.get(position).getUsCurrency());
                } else {
                    holder.plan_amount_card.setText(upcomingSubscriptions.get(position).getUsAmountForeign());
                    holder.plan_amount_card.append(" ");
                    holder.plan_amount_card.append(upcomingSubscriptions.get(position).getUsForCurrencyForeign());
                }
                holder.total_credits.setText(upcomingSubscriptions.get(position).getUsJobsAllowed());

                String inputPattern1 = "yyyy-MM-dd HH:mm:ss";
                String outputPattern1 = "dd-MM-yyyy";
                SimpleDateFormat inputFormat1 = new SimpleDateFormat(inputPattern1);
                SimpleDateFormat outputFormat1 = new SimpleDateFormat(outputPattern1);

                Date date1 = null;
                String str1 = null;

                try {
                    date1 = inputFormat1.parse(upcomingSubscriptions.get(position).getUsActiveDate());
                    str1 = outputFormat1.format(date1);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                holder.valid_from.setText(str1.toString());

                String inputPattern2 = "yyyy-MM-dd HH:mm:ss";
                String outputPattern2 = "dd-MM-yyyy";
                SimpleDateFormat inputFormat2 = new SimpleDateFormat(inputPattern2);
                SimpleDateFormat outputFormat2 = new SimpleDateFormat(outputPattern2);

                Date date2 = null;
                String str2 = null;

                try {
                    date2 = inputFormat2.parse(upcomingSubscriptions.get(position).getUsExpireDate());
                    str2 = outputFormat2.format(date2);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                holder.valid_thru.setText(str2.toString());
                break;

            case 2:
                holder.plan_type.setText("Employee ");
                holder.plan_type.append(upcomingSubscriptions.get(position).getEasType());
                holder.plan_type.append(" Plan");

                if (userProfile.getUserDatum().getUserProfileData().getUserCountryId().equals("India") ||
                        userProfile.getUserDatum().getUserProfileData().getUserCountryId().equals("india")) {
                    holder.plan_amount_card.setText(upcomingSubscriptions.get(position).getEasAmt());
                    holder.plan_amount_card.append(" ");
                    holder.plan_amount_card.append(upcomingSubscriptions.get(position).getEasCurrency());
                } else {
                    holder.plan_amount_card.setText(upcomingSubscriptions.get(position).getEasAmtFor());
                    holder.plan_amount_card.append(" ");
                    holder.plan_amount_card.append(upcomingSubscriptions.get(position).getEasCurrencyFor());
                }
                holder.total_credits.setText(upcomingSubscriptions.get(position).getEasCreditsRec());

                String inputPattern = "yyyy-MM-dd HH:mm:ss";
                String outputPattern = "dd-MM-yyyy";
                SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
                SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

                Date date = null;
                String str = null;

                try {
                    date = inputFormat.parse(upcomingSubscriptions.get(position).getEasDateApplied());
                    str = outputFormat.format(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                holder.valid_from.setText(str.toString());
                holder.valid_thru.setText("--");
                break;
        }
    }

    @Override
    public int getItemCount() {
        return upcomingSubscriptions.size();
    }


}
