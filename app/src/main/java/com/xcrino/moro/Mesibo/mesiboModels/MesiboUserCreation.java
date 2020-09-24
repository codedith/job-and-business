package com.xcrino.moro.Mesibo.mesiboModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MesiboUserCreation {

    @SerializedName("MesiboApp")
    @Expose
    private MesiboApp MesiboApp;
    @SerializedName("uts")
    @Expose
    private String uts;
    @SerializedName("disabled")
    @Expose
    private Integer disabled;
    @SerializedName("MesiboUser")
    @Expose
    private MesiboUser MesiboUser;
    @SerializedName("op")
    @Expose
    private String op;
    @SerializedName("result")
    @Expose
    private Boolean result;

    public MesiboApp getMesiboApp() {
        return MesiboApp;
    }

    public void setMesiboApp(MesiboApp MesiboApp) {
        this.MesiboApp = MesiboApp;
    }

    public String getUts() {
        return uts;
    }

    public void setUts(String uts) {
        this.uts = uts;
    }

    public Integer getDisabled() {
        return disabled;
    }

    public void setDisabled(Integer disabled) {
        this.disabled = disabled;
    }

    public MesiboUser getMesiboUser() {
        return MesiboUser;
    }

    public void setMesiboUser(MesiboUser MesiboUser) {
        this.MesiboUser = MesiboUser;
    }

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

}
