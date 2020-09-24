package com.xcrino.moro.PojoModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OtpData {
    @SerializedName("otp")
    @Expose
    private Integer otp;
    @SerializedName("userid")
    @Expose
    private String userid;
    @SerializedName("user_mobile")
    @Expose
    private String userMobile;
    @SerializedName("user_exist")
    @Expose
    private Integer userExist;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public Integer getUserExist() {
        return userExist;
    }

    public void setUserExist(Integer userExist) {
        this.userExist = userExist;
    }

    private static OtpData otpData = null;

    public static OtpData getOtpData() {
        return otpData;
    }

    public static void setOtpData(OtpData otpData) {
        OtpData.otpData = otpData;
    }

    public Integer getOtp() {
        return otp;
    }

    public void setOtp(Integer otp) {
        this.otp = otp;
    }

}