package com.xcrino.moro.PojoModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IndustryList {

    @SerializedName("industry_no")
    @Expose
    private String industryNo;
    @SerializedName("industry_name")
    @Expose
    private String industryName;
    @SerializedName("industry_created")
    @Expose
    private String industryCreated;

    public String getIndustryNo() {
        return industryNo;
    }

    public void setIndustryNo(String industryNo) {
        this.industryNo = industryNo;
    }

    public String getIndustryName() {
        return industryName;
    }

    public void setIndustryName(String industryName) {
        this.industryName = industryName;
    }

    public String getIndustryCreated() {
        return industryCreated;
    }

    public void setIndustryCreated(String industryCreated) {
        this.industryCreated = industryCreated;
    }
}
