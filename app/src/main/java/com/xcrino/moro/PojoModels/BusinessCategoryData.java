package com.xcrino.moro.PojoModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BusinessCategoryData {
    @SerializedName("bc_sno")
    @Expose
    private String bcSno;
    @SerializedName("bc_name")
    @Expose
    private String bcName;
    @SerializedName("bc_created_time")
    @Expose
    private String bcCreatedTime;

    public String getBcSno() {
        return bcSno;
    }

    public void setBcSno(String bcSno) {
        this.bcSno = bcSno;
    }

    public String getBcName() {
        return bcName;
    }

    public void setBcName(String bcName) {
        this.bcName = bcName;
    }

    public String getBcCreatedTime() {
        return bcCreatedTime;
    }

    public void setBcCreatedTime(String bcCreatedTime) {
        this.bcCreatedTime = bcCreatedTime;
    }
}
