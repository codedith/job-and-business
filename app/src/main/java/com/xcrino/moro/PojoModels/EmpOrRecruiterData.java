package com.xcrino.moro.PojoModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EmpOrRecruiterData {

    @SerializedName("user_exist")
    @Expose
    private Integer userExist;

    public Integer getUserExist() {
        return userExist;
    }

    public void setUserExist(Integer userExist) {
        this.userExist = userExist;
    }
}
