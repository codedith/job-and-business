package com.xcrino.moro.PojoModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Campaign {
    @SerializedName("business_campaign_id")
    @Expose
    private String businessCampaignId;
    @SerializedName("business_campaign_user_id")
    @Expose
    private String businessCampaignUserId;
    @SerializedName("business_campaign_database_type")
    @Expose
    private String businessCampaignDatabaseType;
    @SerializedName("business_campaign_mode")
    @Expose
    private String businessCampaignMode;
    @SerializedName("business_campaign_file")
    @Expose
    private Object businessCampaignFile;
    @SerializedName("business_campaign_text")
    @Expose
    private Object businessCampaignText;
    @SerializedName("business_campaign_link")
    @Expose
    private String businessCampaignLink;
    @SerializedName("business_campaign_schedule")
    @Expose
    private String businessCampaignSchedule;
    @SerializedName("business_campaign_targets")
    @Expose
    private String businessCampaignTargets;
    @SerializedName("business_campaign_views")
    @Expose
    private String businessCampaignViews;
    @SerializedName("business_campaign_created_on")
    @Expose
    private String businessCampaignCreatedOn;
    @SerializedName("business_campaign_status")
    @Expose
    private String businessCampaignStatus;
    @SerializedName("likes")
    @Expose
    private String likes;
    @SerializedName("dislikes")
    @Expose
    private String dislikes;
    @SerializedName("block_by_users")
    @Expose
    private String blockByUsers;
    @SerializedName("business_campaign_user_count")
    @Expose
    private String businessCampaignUserCount;
    @SerializedName("user_like_or_dislike")
    @Expose
    private String userLikeOrDislike;
    @SerializedName("local_users")
    @Expose
    private List<LocalUser> localUsers = null;

    public String getBusinessCampaignId() {
        return businessCampaignId;
    }

    public void setBusinessCampaignId(String businessCampaignId) {
        this.businessCampaignId = businessCampaignId;
    }

    public String getBusinessCampaignUserId() {
        return businessCampaignUserId;
    }

    public void setBusinessCampaignUserId(String businessCampaignUserId) {
        this.businessCampaignUserId = businessCampaignUserId;
    }

    public String getBusinessCampaignDatabaseType() {
        return businessCampaignDatabaseType;
    }

    public void setBusinessCampaignDatabaseType(String businessCampaignDatabaseType) {
        this.businessCampaignDatabaseType = businessCampaignDatabaseType;
    }

    public String getBusinessCampaignMode() {
        return businessCampaignMode;
    }

    public void setBusinessCampaignMode(String businessCampaignMode) {
        this.businessCampaignMode = businessCampaignMode;
    }

    public Object getBusinessCampaignFile() {
        return businessCampaignFile;
    }

    public void setBusinessCampaignFile(Object businessCampaignFile) {
        this.businessCampaignFile = businessCampaignFile;
    }

    public Object getBusinessCampaignText() {
        return businessCampaignText;
    }

    public void setBusinessCampaignText(Object businessCampaignText) {
        this.businessCampaignText = businessCampaignText;
    }

    public String getBusinessCampaignLink() {
        return businessCampaignLink;
    }

    public void setBusinessCampaignLink(String businessCampaignLink) {
        this.businessCampaignLink = businessCampaignLink;
    }

    public String getBusinessCampaignSchedule() {
        return businessCampaignSchedule;
    }

    public void setBusinessCampaignSchedule(String businessCampaignSchedule) {
        this.businessCampaignSchedule = businessCampaignSchedule;
    }

    public String getBusinessCampaignTargets() {
        return businessCampaignTargets;
    }

    public void setBusinessCampaignTargets(String businessCampaignTargets) {
        this.businessCampaignTargets = businessCampaignTargets;
    }

    public String getBusinessCampaignViews() {
        return businessCampaignViews;
    }

    public void setBusinessCampaignViews(String businessCampaignViews) {
        this.businessCampaignViews = businessCampaignViews;
    }

    public String getBusinessCampaignCreatedOn() {
        return businessCampaignCreatedOn;
    }

    public void setBusinessCampaignCreatedOn(String businessCampaignCreatedOn) {
        this.businessCampaignCreatedOn = businessCampaignCreatedOn;
    }

    public String getBusinessCampaignStatus() {
        return businessCampaignStatus;
    }

    public void setBusinessCampaignStatus(String businessCampaignStatus) {
        this.businessCampaignStatus = businessCampaignStatus;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public String getDislikes() {
        return dislikes;
    }

    public void setDislikes(String dislikes) {
        this.dislikes = dislikes;
    }

    public String getBlockByUsers() {
        return blockByUsers;
    }

    public void setBlockByUsers(String blockByUsers) {
        this.blockByUsers = blockByUsers;
    }

    public String getBusinessCampaignUserCount() {
        return businessCampaignUserCount;
    }

    public void setBusinessCampaignUserCount(String businessCampaignUserCount) {
        this.businessCampaignUserCount = businessCampaignUserCount;
    }

    public String getUserLikeOrDislike() {
        return userLikeOrDislike;
    }

    public void setUserLikeOrDislike(String userLikeOrDislike) {
        this.userLikeOrDislike = userLikeOrDislike;
    }

    public List<LocalUser> getLocalUsers() {
        return localUsers;
    }

    public void setLocalUsers(List<LocalUser> localUsers) {
        this.localUsers = localUsers;
    }
}
