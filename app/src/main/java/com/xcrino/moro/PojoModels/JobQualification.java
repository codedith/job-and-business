package com.xcrino.moro.PojoModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JobQualification {
    @SerializedName("job_qualification_name")
    @Expose
    private String jobQualificationName;

    public String getJobQualificationName() {
        return jobQualificationName;
    }

    public void setJobQualificationName(String jobQualificationName) {
        this.jobQualificationName = jobQualificationName;
    }
}
