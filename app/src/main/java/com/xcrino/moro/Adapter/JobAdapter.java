package com.xcrino.moro.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.xcrino.moro.Interface.ApiInterface;
import com.xcrino.moro.JobModule.EmployeeJobDetailActivity;
import com.xcrino.moro.PojoModels.CommonResponseModel;
import com.xcrino.moro.PojoModels.GetSavedJobListModel;
import com.xcrino.moro.R;
import com.xcrino.moro.Utilities.ApiClients;
import com.xcrino.moro.Utilities.AppPreferencesShared;
import com.xcrino.moro.Utilities.NetworkStatus;
import com.xcrino.moro.ViewHolder.JobViewHolder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JobAdapter extends RecyclerView.Adapter<JobViewHolder> {

    Context mContext;
    AppPreferencesShared appPreferencesShared;
    List<GetSavedJobListModel> savedJobList;


    public JobAdapter(Context context, List<GetSavedJobListModel> savedJobList) {
        this.mContext = context;
        appPreferencesShared = new AppPreferencesShared(mContext);
        this.savedJobList = savedJobList;
    }

    @NonNull
    @Override
    public JobViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.card_job, parent, false);
        JobViewHolder jobViewHolder = new JobViewHolder(view);
        return jobViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull JobViewHolder holder, int position) {
        //holder.mJob_title.setText(savedJobList.get(position).getJobTitle().toString());
        holder.mJob_title.setText(savedJobList.get(position).getJobTitle() == null ? "--" : savedJobList.get(position).getJobTitle().toString());
        holder.company_name.setText(savedJobList.get(position).getCompanyName() == null ? "--" : savedJobList.get(position).getCompanyName().toString());
        holder.mJob_experience.setText("Experience: " + savedJobList.get(position).getExperience() == null ? "Experience: " : "Experience: " + savedJobList.get(position).getExperience().toString());
        if (savedJobList.get(position).getJobLocations() != null && !savedJobList.get(position).getJobLocations().isEmpty()) {
            holder.mJob_location.setText("Location: ");
            holder.mJob_location.append(savedJobList.get(position).getJobLocations().get(0).getJobCountry().toString() + ", ");
            for (int i = 0; i < savedJobList.get(position).getJobLocations().size(); i++) {
                holder.mJob_location.append(savedJobList.get(position).getJobLocations().get(i).getJobState().toString());
                if (i < savedJobList.get(position).getJobLocations().size() - 1) {
                    holder.mJob_location.append(",");
                }
            }
        } else {
            holder.mJob_location.setText("Location: Not Available");
        }

        if (savedJobList.get(position).getJobSkills() != null && !savedJobList.get(position).getJobSkills().isEmpty()) {
            holder.skills.setText("Skills: ");
            for (int i = 0; i < savedJobList.get(position).getJobSkills().size(); i++) {
                holder.skills.append(savedJobList.get(position).getJobSkills().get(i).getJobSkills().toString());
                if (i < savedJobList.get(position).getJobSkills().size() - 1) {
                    holder.skills.append(",");
                }
            }
        } else {
            holder.skills.setText("Skills: Not Available");
        }

        if (savedJobList.get(position).getCompanyLogo() != null) {
            Picasso.with(mContext).load(savedJobList.get(position).getCompanyLogo().toString())
                    .resize(100, 100).transform(new CropCircleTransformation())
                    .placeholder(R.drawable.user_dummy).into(holder.company_img);
        }

        holder.mJob_package.setText("Salary: " + savedJobList.get(position).getSalary() == null ? "Salary: " : "Salary: " + savedJobList.get(position).getSalary().toString());
        if (savedJobList.get(position).getSaveStatus()) {
            savedLayout(true, holder, position);
        } else {
            savedLayout(false, holder, position);
        }
        if (savedJobList.get(position).getJobCreated() != null) {
            String inputPattern = "yyyy-MM-dd HH:mm:ss";
            String outputPattern = "dd-MM-yyyy";
            SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
            SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

            Date date = null;
            String str = null;

            try {
                date = inputFormat.parse(savedJobList.get(position).getJobCreated());
                str = outputFormat.format(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            holder.posted_date.setText(str);

        } else {
            holder.posted_date.setText("Posted Date: Not Available");
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, EmployeeJobDetailActivity.class);
                if (savedJobList.get(position).getSaveStatus()) {
                    appPreferencesShared.setSavedStatus(true);
                } else {
                    appPreferencesShared.setSavedStatus(false);
                }
                appPreferencesShared.setJobId(savedJobList.get(position).getJobId());
                mContext.startActivity(intent);
            }
        });

        holder.save_job_star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appPreferencesShared.setJobId(savedJobList.get(position).getJobId());
                if (NetworkStatus.isNetworkAvailable(mContext)) {
                    saveJobMethod(holder, position);
                } else {
                    Toast.makeText(mContext, "Please Check Your Internet Connection !", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return savedJobList.size();
    }

    private void saveJobMethod(JobViewHolder holder, int position) {
        ProgressDialog progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        ApiInterface apiInterface = ApiClients.getClient().create(ApiInterface.class);
        Call<CommonResponseModel> applyjobmethod = apiInterface.saveJobMethod("Mogo_api/save_job/" + appPreferencesShared.getUserId() + "/" + appPreferencesShared.getJobId());
        applyjobmethod.enqueue(new Callback<CommonResponseModel>() {
            @Override
            public void onResponse(Call<CommonResponseModel> call, Response<CommonResponseModel> response) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                if (response.isSuccessful()) {
                    if (response.body().getResult()) {
                        Intent intent = new Intent("custom-message");
                        intent.putExtra("response", true);
                        LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
                    } else {
                        Intent intent = new Intent("custom-message");
                        intent.putExtra("response", true);
                        LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
                    }
                } else {
                    Toast.makeText(mContext, "Could not save it due to network issue", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CommonResponseModel> call, Throwable t) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                Toast.makeText(mContext, "Could not save it due to network issue", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void savedLayout(boolean saved, JobViewHolder holder, int position) {
        if (saved) {
            holder.save_job_star.setImageResource(R.drawable.ic_saved_star);
            holder.save_job_star.setColorFilter(ContextCompat.getColor(mContext, R.color.light_purple));
        } else {
            holder.save_job_star.setImageResource(R.drawable.ic_star);
            holder.save_job_star.setColorFilter(ContextCompat.getColor(mContext, R.color.light_purple));
        }
    }
}
