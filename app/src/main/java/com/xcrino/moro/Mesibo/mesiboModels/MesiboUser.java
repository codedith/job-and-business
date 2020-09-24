package com.xcrino.moro.Mesibo.mesiboModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MesiboUser {

    @SerializedName("uid")
    @Expose
    private String uid;
    @SerializedName("token")
    @Expose
    private String token;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
