package com.xcrino.moro.PojoModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PostedJobsList {
    @SerializedName("result")
    @Expose
    private Boolean result;
    @SerializedName("data")
    @Expose
//    private List<CommonPostedJobsListData> postedJobsListData = null;
    private List<GetSavedJobListModel> data = null;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("jobLocations")
    @Expose
    private List<JobLocation> jobLocations = null;
    @SerializedName("jobQualification")
    @Expose
    private List<JobQualification> jobQualification = null;
    @SerializedName("jobSkills")
    @Expose
    private List<JobSkill> jobSkills = null;

    public List<JobLocation> getJobLocations() {
        return jobLocations;
    }

    public void setJobLocations(List<JobLocation> jobLocations) {
        this.jobLocations = jobLocations;
    }

    public List<JobQualification> getJobQualification() {
        return jobQualification;
    }

    public void setJobQualification(List<JobQualification> jobQualification) {
        this.jobQualification = jobQualification;
    }

    public List<JobSkill> getJobSkills() {
        return jobSkills;
    }

    public void setJobSkills(List<JobSkill> jobSkills) {
        this.jobSkills = jobSkills;
    }

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    /* public List<CommonPostedJobsListData> getPostedJobsListData() {
         return postedJobsListData;
     }

     public void setData(List<CommonPostedJobsListData> postedJobsListData) {
         this.postedJobsListData = postedJobsListData;
     }
 */

    public List<GetSavedJobListModel> getData() {
        return data;
    }

    public void setData(List<GetSavedJobListModel> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
