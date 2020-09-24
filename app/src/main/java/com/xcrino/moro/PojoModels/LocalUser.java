package com.xcrino.moro.PojoModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LocalUser {

    @SerializedName("bc_local_db_id")
    @Expose
    private String bcLocalDbId;
    @SerializedName("bc_local_db_campaign")
    @Expose
    private String bcLocalDbCampaign;
    @SerializedName("bc_local_db_phone_no")
    @Expose
    private String bcLocalDbPhoneNo;

    public String getBcLocalDbId() {
        return bcLocalDbId;
    }

    public void setBcLocalDbId(String bcLocalDbId) {
        this.bcLocalDbId = bcLocalDbId;
    }

    public String getBcLocalDbCampaign() {
        return bcLocalDbCampaign;
    }

    public void setBcLocalDbCampaign(String bcLocalDbCampaign) {
        this.bcLocalDbCampaign = bcLocalDbCampaign;
    }

    public String getBcLocalDbPhoneNo() {
        return bcLocalDbPhoneNo;
    }

    public void setBcLocalDbPhoneNo(String bcLocalDbPhoneNo) {
        this.bcLocalDbPhoneNo = bcLocalDbPhoneNo;
    }
}
