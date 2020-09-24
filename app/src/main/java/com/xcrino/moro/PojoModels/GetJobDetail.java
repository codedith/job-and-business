package com.xcrino.moro.PojoModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetJobDetail {
    @SerializedName("job_details")
    @Expose
    private JobDetails jobDetails;
    @SerializedName("extra")
    @Expose
    private PostedJobsList extra;

    public JobDetails getJobDetails() {
        return jobDetails;
    }

    public void setJobDetails(JobDetails jobDetails) {
        this.jobDetails = jobDetails;
    }

    public PostedJobsList getExtra() {
        return extra;
    }

    public void setExtra(PostedJobsList extra) {
        this.extra = extra;
    }
}
