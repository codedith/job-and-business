package com.xcrino.moro.PojoModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DefaultDataDatum {

    @SerializedName("country_list")
    @Expose
    private List<CountryList> countryList = null;
    @SerializedName("industry_list")
    @Expose
    private List<IndustryList> industryList = null;
    @SerializedName("qualification_list")
    @Expose
    private List<QualificationDataList> qualificationDataList = null;
    @SerializedName("skills_list")
    @Expose
    private List<SkilleDataList> skillsList = null;
    @SerializedName("categories")
    @Expose
    private List<BusinessCategoryData> businessCategoryData = null;

    public List<CountryList> getCountryList() {
        return countryList;
    }

    public void setCountryList(List<CountryList> countryList) {
        this.countryList = countryList;
    }

    public List<IndustryList> getIndustryList() {
        return industryList;
    }

    public void setIndustryList(List<IndustryList> industryList) {
        this.industryList = industryList;
    }

    public List<QualificationDataList> getQualificationList() {
        return qualificationDataList;
    }

    public void setQualificationList(List<QualificationDataList> qualificationList) {
        this.qualificationDataList = qualificationList;
    }

    public List<SkilleDataList> getSkillsList() {
        return skillsList;
    }

    public void setSkillsList(List<SkilleDataList> skillsList) {
        this.skillsList = skillsList;
    }

    public List<BusinessCategoryData> getBusinessCategoryData() {
        return businessCategoryData;
    }

    public void setBusinessCategoryData(List<BusinessCategoryData> businessCategoryData) {
        this.businessCategoryData = businessCategoryData;
    }
}
