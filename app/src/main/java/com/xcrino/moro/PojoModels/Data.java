package com.xcrino.moro.PojoModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {
    @SerializedName("job_details")
    @Expose
    private JobDetails jobDetails;
    @SerializedName("extra")
    @Expose
    private GetSavedJobListModel commonPostedJobsListData;
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("user_full_name")
    @Expose
    private String userFullName;
    @SerializedName("country_code")
    @Expose
    private String countryCode;
    @SerializedName("user_mobile")
    @Expose
    private String userMobile;
    @SerializedName("user_email")
    @Expose
    private String userEmail;
    @SerializedName("user_dob")
    @Expose
    private String userDob;
    @SerializedName("user_profile_image")
    @Expose
    private String userProfileImage;
    @SerializedName("user_business_name")
    @Expose
    private String userBusinessName;
    @SerializedName("user_account")
    @Expose
    private String userAccount;
    @SerializedName("user_created_time")
    @Expose
    private String userCreatedTime;
    @SerializedName("company_logo")
    @Expose
    private String companyLogo;
    @SerializedName("user_country_id")
    @Expose
    private String userCountryId;
    @SerializedName("user_state_id")
    @Expose
    private String userStateId;
    @SerializedName("candidate_id")
    @Expose
    private String candidateId;
    @SerializedName("owner_user_id")
    @Expose
    private String ownerUserId;
    @SerializedName("created_date")
    @Expose
    private String createdDate;
    @SerializedName("employee_name")
    @Expose
    private String employeeName;
    @SerializedName("employee_dob")
    @Expose
    private String employeeDob;
    @SerializedName("employee_gender")
    @Expose
    private String employeeGender;
    @SerializedName("employee_address")
    @Expose
    private String employeeAddress;
    @SerializedName("employee_country")
    @Expose
    private String employeeCountry;
    @SerializedName("employee_state")
    @Expose
    private String employeeState;
    @SerializedName("employee_last_company_name")
    @Expose
    private String employeeLastCompanyName;
    @SerializedName("employee_designation")
    @Expose
    private String employeeDesignation;
    @SerializedName("employee_work_exp_in_last_company")
    @Expose
    private String employeeWorkExpInLastCompany;
    @SerializedName("employee_work_country")
    @Expose
    private String employeeWorkCountry;
    @SerializedName("employee_work_state")
    @Expose
    private String employeeWorkState;
    @SerializedName("employee_profile_headline")
    @Expose
    private String employeeProfileHeadline;
    @SerializedName("employee_skills")
    @Expose
    private String employeeSkills;
    @SerializedName("employee_job_exp")
    @Expose
    private String employeeJobExp;
    @SerializedName("employee_employment_type")
    @Expose
    private String employeeEmploymentType;
    @SerializedName("employee_qualification")
    @Expose
    private String employeeQualification;
    @SerializedName("employee_salary_expectation")
    @Expose
    private String employeeSalaryExpectation;
    @SerializedName("employee_have_passport")
    @Expose
    private String employeeHavePassport;
    @SerializedName("employee_industry")
    @Expose
    private String employeeIndustry;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserDob() {
        return userDob;
    }

    public void setUserDob(String userDob) {
        this.userDob = userDob;
    }

    public String getUserProfileImage() {
        return userProfileImage;
    }

    public void setUserProfileImage(String userProfileImage) {
        this.userProfileImage = userProfileImage;
    }

    public String getUserBusinessName() {
        return userBusinessName;
    }

    public void setUserBusinessName(String userBusinessName) {
        this.userBusinessName = userBusinessName;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getUserCreatedTime() {
        return userCreatedTime;
    }

    public void setUserCreatedTime(String userCreatedTime) {
        this.userCreatedTime = userCreatedTime;
    }

    public String getCompanyLogo() {
        return companyLogo;
    }

    public void setCompanyLogo(String companyLogo) {
        this.companyLogo = companyLogo;
    }

    public String getUserCountryId() {
        return userCountryId;
    }

    public void setUserCountryId(String userCountryId) {
        this.userCountryId = userCountryId;
    }

    public String getUserStateId() {
        return userStateId;
    }

    public void setUserStateId(String userStateId) {
        this.userStateId = userStateId;
    }

    public String getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(String candidateId) {
        this.candidateId = candidateId;
    }

    public String getOwnerUserId() {
        return ownerUserId;
    }

    public void setOwnerUserId(String ownerUserId) {
        this.ownerUserId = ownerUserId;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeDob() {
        return employeeDob;
    }

    public void setEmployeeDob(String employeeDob) {
        this.employeeDob = employeeDob;
    }

    public String getEmployeeGender() {
        return employeeGender;
    }

    public void setEmployeeGender(String employeeGender) {
        this.employeeGender = employeeGender;
    }

    public String getEmployeeAddress() {
        return employeeAddress;
    }

    public void setEmployeeAddress(String employeeAddress) {
        this.employeeAddress = employeeAddress;
    }

    public String getEmployeeCountry() {
        return employeeCountry;
    }

    public void setEmployeeCountry(String employeeCountry) {
        this.employeeCountry = employeeCountry;
    }

    public String getEmployeeState() {
        return employeeState;
    }

    public void setEmployeeState(String employeeState) {
        this.employeeState = employeeState;
    }

    public String getEmployeeLastCompanyName() {
        return employeeLastCompanyName;
    }

    public void setEmployeeLastCompanyName(String employeeLastCompanyName) {
        this.employeeLastCompanyName = employeeLastCompanyName;
    }

    public String getEmployeeDesignation() {
        return employeeDesignation;
    }

    public void setEmployeeDesignation(String employeeDesignation) {
        this.employeeDesignation = employeeDesignation;
    }

    public String getEmployeeWorkExpInLastCompany() {
        return employeeWorkExpInLastCompany;
    }

    public void setEmployeeWorkExpInLastCompany(String employeeWorkExpInLastCompany) {
        this.employeeWorkExpInLastCompany = employeeWorkExpInLastCompany;
    }

    public String getEmployeeWorkCountry() {
        return employeeWorkCountry;
    }

    public void setEmployeeWorkCountry(String employeeWorkCountry) {
        this.employeeWorkCountry = employeeWorkCountry;
    }

    public String getEmployeeWorkState() {
        return employeeWorkState;
    }

    public void setEmployeeWorkState(String employeeWorkState) {
        this.employeeWorkState = employeeWorkState;
    }

    public String getEmployeeProfileHeadline() {
        return employeeProfileHeadline;
    }

    public void setEmployeeProfileHeadline(String employeeProfileHeadline) {
        this.employeeProfileHeadline = employeeProfileHeadline;
    }

    public String getEmployeeSkills() {
        return employeeSkills;
    }

    public void setEmployeeSkills(String employeeSkills) {
        this.employeeSkills = employeeSkills;
    }

    public String getEmployeeJobExp() {
        return employeeJobExp;
    }

    public void setEmployeeJobExp(String employeeJobExp) {
        this.employeeJobExp = employeeJobExp;
    }

    public String getEmployeeEmploymentType() {
        return employeeEmploymentType;
    }

    public void setEmployeeEmploymentType(String employeeEmploymentType) {
        this.employeeEmploymentType = employeeEmploymentType;
    }

    public String getEmployeeQualification() {
        return employeeQualification;
    }

    public void setEmployeeQualification(String employeeQualification) {
        this.employeeQualification = employeeQualification;
    }

    public String getEmployeeSalaryExpectation() {
        return employeeSalaryExpectation;
    }

    public void setEmployeeSalaryExpectation(String employeeSalaryExpectation) {
        this.employeeSalaryExpectation = employeeSalaryExpectation;
    }

    public String getEmployeeHavePassport() {
        return employeeHavePassport;
    }

    public void setEmployeeHavePassport(String employeeHavePassport) {
        this.employeeHavePassport = employeeHavePassport;
    }

    public String getEmployeeIndustry() {
        return employeeIndustry;
    }

    public void setEmployeeIndustry(String employeeIndustry) {
        this.employeeIndustry = employeeIndustry;
    }

    public JobDetails getJobDetails() {
        return jobDetails;
    }

    public void setJobDetails(JobDetails jobDetails) {
        this.jobDetails = jobDetails;
    }

    public GetSavedJobListModel getCommonPostedJobsListData() {
        return commonPostedJobsListData;
    }

    public void setCommonPostedJobsListData(GetSavedJobListModel commonPostedJobsListData) {
        this.commonPostedJobsListData = commonPostedJobsListData;
    }
}
