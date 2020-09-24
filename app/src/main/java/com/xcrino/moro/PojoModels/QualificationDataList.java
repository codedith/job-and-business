package com.xcrino.moro.PojoModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QualificationDataList {

    @SerializedName("qualification_sno")
    @Expose
    private String qualificationSno;
    @SerializedName("qualification_name")
    @Expose
    private String qualificationName;
    @SerializedName("qualification_created")
    @Expose
    private String qualificationCreated;

    public String getQualificationSno() {
        return qualificationSno;
    }

    public void setQualificationSno(String qualificationSno) {
        this.qualificationSno = qualificationSno;
    }

    public String getQualificationName() {
        return qualificationName;
    }

    public void setQualificationName(String qualificationName) {
        this.qualificationName = qualificationName;
    }

    public String getQualificationCreated() {
        return qualificationCreated;
    }

    public void setQualificationCreated(String qualificationCreated) {
        this.qualificationCreated = qualificationCreated;
    }
}
