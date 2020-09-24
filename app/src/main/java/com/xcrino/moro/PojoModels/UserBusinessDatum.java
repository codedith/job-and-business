package com.xcrino.moro.PojoModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserBusinessDatum {

    @SerializedName("user_bc_no")
    @Expose
    private String userBcNo;
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("user_business_category")
    @Expose
    private String userBusinessCategory;

    public String getUserBcNo() {
        return userBcNo;
    }

    public void setUserBcNo(String userBcNo) {
        this.userBcNo = userBcNo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserBusinessCategory() {
        return userBusinessCategory;
    }

    public void setUserBusinessCategory(String userBusinessCategory) {
        this.userBusinessCategory = userBusinessCategory;
    }
}
