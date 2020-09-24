package com.xcrino.moro.PojoModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserProfileData {
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
    private Object UserBusinessName;
    @SerializedName("user_account")
    @Expose
    private String userAccount;
    @SerializedName("user_created_time")
    @Expose
    private String userCreatedTime;
    @SerializedName("company_logo")
    @Expose
    private Object companyLogo;
    @SerializedName("user_country_id")
    @Expose
    private String userCountryId;
    @SerializedName("user_state_id")
    @Expose
    private String userStateId;
    @SerializedName("company_id")
    @Expose
    private Object companyId;
    @SerializedName("owner_user_id")
    @Expose
    private Object ownerUserId;
    @SerializedName("company_name")
    @Expose
    private Object companyName;
    @SerializedName("company_email")
    @Expose
    private Object companyEmail;
    @SerializedName("company_phone")
    @Expose
    private Object companyPhone;
    @SerializedName("company_website")
    @Expose
    private Object companyWebsite;
    @SerializedName("company_address")
    @Expose
    private Object companyAddress;
    @SerializedName("about_company")
    @Expose
    private Object aboutCompany;
    @SerializedName("country")
    @Expose
    private Object country;
    @SerializedName("state")
    @Expose
    private Object state;
    @SerializedName("created_date")
    @Expose
    private Object createdDate;
    @SerializedName("candidate_id")
    @Expose
    private Object candidateId;
    @SerializedName("employee_name")
    @Expose
    private Object employeeName;
    @SerializedName("employee_dob")
    @Expose
    private Object employeeDob;
    @SerializedName("employee_gender")
    @Expose
    private Object employeeGender;
    @SerializedName("employee_address")
    @Expose
    private Object employeeAddress;
    @SerializedName("employee_country")
    @Expose
    private Object employeeCountry;
    @SerializedName("employee_state")
    @Expose
    private Object employeeState;
    @SerializedName("employee_last_company_name")
    @Expose
    private Object employeeLastCompanyName;
    @SerializedName("employee_designation")
    @Expose
    private Object employeeDesignation;
    @SerializedName("employee_work_exp_in_last_company")
    @Expose
    private Object employeeWorkExpInLastCompany;
    @SerializedName("employee_work_country")
    @Expose
    private Object employeeWorkCountry;
    @SerializedName("employee_work_state")
    @Expose
    private Object employeeWorkState;
    @SerializedName("employee_profile_headline")
    @Expose
    private Object employeeProfileHeadline;
    @SerializedName("employee_skills")
    @Expose
    private Object employeeSkills;
    @SerializedName("employee_job_exp")
    @Expose
    private Object employeeJobExp;
    @SerializedName("employee_employment_type")
    @Expose
    private Object employeeEmploymentType;
    @SerializedName("employee_qualification")
    @Expose
    private Object employeeQualification;
    @SerializedName("employee_salary_expectation")
    @Expose
    private Object employeeSalaryExpectation;
    @SerializedName("employee_have_passport")
    @Expose
    private Object employeeHavePassport;
    @SerializedName("employee_industry")
    @Expose
    private Object employeeIndustry;

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

    public Object getUserBusinessName() {
        return UserBusinessName;
    }

    public void setUserBusinessName(Object UserBusinessName) {
        this.UserBusinessName = UserBusinessName;
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

    public Object getCompanyLogo() {
        return companyLogo;
    }

    public void setCompanyLogo(Object companyLogo) {
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

    public Object getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Object companyId) {
        this.companyId = companyId;
    }

    public Object getOwnerUserId() {
        return ownerUserId;
    }

    public void setOwnerUserId(Object ownerUserId) {
        this.ownerUserId = ownerUserId;
    }

    public Object getCompanyName() {
        return companyName;
    }

    public void setCompanyName(Object companyName) {
        this.companyName = companyName;
    }

    public Object getCompanyEmail() {
        return companyEmail;
    }

    public void setCompanyEmail(Object companyEmail) {
        this.companyEmail = companyEmail;
    }

    public Object getCompanyPhone() {
        return companyPhone;
    }

    public void setCompanyPhone(Object companyPhone) {
        this.companyPhone = companyPhone;
    }

    public Object getCompanyWebsite() {
        return companyWebsite;
    }

    public void setCompanyWebsite(Object companyWebsite) {
        this.companyWebsite = companyWebsite;
    }

    public Object getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(Object companyAddress) {
        this.companyAddress = companyAddress;
    }

    public Object getAboutCompany() {
        return aboutCompany;
    }

    public void setAboutCompany(Object aboutCompany) {
        this.aboutCompany = aboutCompany;
    }

    public Object getCountry() {
        return country;
    }

    public void setCountry(Object country) {
        this.country = country;
    }

    public Object getState() {
        return state;
    }

    public void setState(Object state) {
        this.state = state;
    }

    public Object getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Object createdDate) {
        this.createdDate = createdDate;
    }

    public Object getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(Object candidateId) {
        this.candidateId = candidateId;
    }

    public Object getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(Object employeeName) {
        this.employeeName = employeeName;
    }

    public Object getEmployeeDob() {
        return employeeDob;
    }

    public void setEmployeeDob(Object employeeDob) {
        this.employeeDob = employeeDob;
    }

    public Object getEmployeeGender() {
        return employeeGender;
    }

    public void setEmployeeGender(Object employeeGender) {
        this.employeeGender = employeeGender;
    }

    public Object getEmployeeAddress() {
        return employeeAddress;
    }

    public void setEmployeeAddress(Object employeeAddress) {
        this.employeeAddress = employeeAddress;
    }

    public Object getEmployeeCountry() {
        return employeeCountry;
    }

    public void setEmployeeCountry(Object employeeCountry) {
        this.employeeCountry = employeeCountry;
    }

    public Object getEmployeeState() {
        return employeeState;
    }

    public void setEmployeeState(Object employeeState) {
        this.employeeState = employeeState;
    }

    public Object getEmployeeLastCompanyName() {
        return employeeLastCompanyName;
    }

    public void setEmployeeLastCompanyName(Object employeeLastCompanyName) {
        this.employeeLastCompanyName = employeeLastCompanyName;
    }

    public Object getEmployeeDesignation() {
        return employeeDesignation;
    }

    public void setEmployeeDesignation(Object employeeDesignation) {
        this.employeeDesignation = employeeDesignation;
    }

    public Object getEmployeeWorkExpInLastCompany() {
        return employeeWorkExpInLastCompany;
    }

    public void setEmployeeWorkExpInLastCompany(Object employeeWorkExpInLastCompany) {
        this.employeeWorkExpInLastCompany = employeeWorkExpInLastCompany;
    }

    public Object getEmployeeWorkCountry() {
        return employeeWorkCountry;
    }

    public void setEmployeeWorkCountry(Object employeeWorkCountry) {
        this.employeeWorkCountry = employeeWorkCountry;
    }

    public Object getEmployeeWorkState() {
        return employeeWorkState;
    }

    public void setEmployeeWorkState(Object employeeWorkState) {
        this.employeeWorkState = employeeWorkState;
    }

    public Object getEmployeeProfileHeadline() {
        return employeeProfileHeadline;
    }

    public void setEmployeeProfileHeadline(Object employeeProfileHeadline) {
        this.employeeProfileHeadline = employeeProfileHeadline;
    }

    public Object getEmployeeSkills() {
        return employeeSkills;
    }

    public void setEmployeeSkills(Object employeeSkills) {
        this.employeeSkills = employeeSkills;
    }

    public Object getEmployeeJobExp() {
        return employeeJobExp;
    }

    public void setEmployeeJobExp(Object employeeJobExp) {
        this.employeeJobExp = employeeJobExp;
    }

    public Object getEmployeeEmploymentType() {
        return employeeEmploymentType;
    }

    public void setEmployeeEmploymentType(Object employeeEmploymentType) {
        this.employeeEmploymentType = employeeEmploymentType;
    }

    public Object getEmployeeQualification() {
        return employeeQualification;
    }

    public void setEmployeeQualification(Object employeeQualification) {
        this.employeeQualification = employeeQualification;
    }

    public Object getEmployeeSalaryExpectation() {
        return employeeSalaryExpectation;
    }

    public void setEmployeeSalaryExpectation(Object employeeSalaryExpectation) {
        this.employeeSalaryExpectation = employeeSalaryExpectation;
    }

    public Object getEmployeeHavePassport() {
        return employeeHavePassport;
    }

    public void setEmployeeHavePassport(Object employeeHavePassport) {
        this.employeeHavePassport = employeeHavePassport;
    }

    public Object getEmployeeIndustry() {
        return employeeIndustry;
    }

    public void setEmployeeIndustry(Object employeeIndustry) {
        this.employeeIndustry = employeeIndustry;
    }
}