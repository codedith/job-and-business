package com.xcrino.moro.PojoModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SkilleDataList {
    @SerializedName("skills_no")
    @Expose
    private String skillsNo;
    @SerializedName("skills_name")
    @Expose
    private String skillsName;
    @SerializedName("skills_created")
    @Expose
    private String skillsCreated;

    public String getSkillsNo() {
        return skillsNo;
    }

    public void setSkillsNo(String skillsNo) {
        this.skillsNo = skillsNo;
    }

    public String getSkillsName() {
        return skillsName;
    }

    public void setSkillsName(String skillsName) {
        this.skillsName = skillsName;
    }

    public String getSkillsCreated() {
        return skillsCreated;
    }

    public void setSkillsCreated(String skillsCreated) {
        this.skillsCreated = skillsCreated;
    }
}
