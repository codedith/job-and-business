package com.xcrino.moro.PojoModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetSavedJobListModel {

    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("user_full_name")
    @Expose
    private Object userFullName;
    @SerializedName("country_code")
    @Expose
    private String countryCode;
    @SerializedName("user_mobile")
    @Expose
    private String userMobile;
    @SerializedName("user_email")
    @Expose
    private Object userEmail;
    @SerializedName("user_dob")
    @Expose
    private Object userDob;
    @SerializedName("user_profile_image")
    @Expose
    private Object userProfileImage;
    @SerializedName("user_business_name")
    @Expose
    private Object userBusinessName;
    @SerializedName("user_account")
    @Expose
    private Object userAccount;
    @SerializedName("user_created_time")
    @Expose
    private String userCreatedTime;
    @SerializedName("company_logo")
    @Expose
    private Object companyLogo;
    @SerializedName("user_country_id")
    @Expose
    private Object userCountryId;
    @SerializedName("user_state_id")
    @Expose
    private Object userStateId;
    @SerializedName("usj_id")
    @Expose
    private String usjId;
    @SerializedName("usj_user_id")
    @Expose
    private String usjUserId;
    @SerializedName("usj_job_id")
    @Expose
    private String usjJobId;
    @SerializedName("job_id")
    @Expose
    private String jobId;
    @SerializedName("owner_user_id")
    @Expose
    private String ownerUserId;
    @SerializedName("job_title")
    @Expose
    private String jobTitle;
    @SerializedName("job_vacancies")
    @Expose
    private String jobVacancies;
    @SerializedName("experience")
    @Expose
    private String experience;
    @SerializedName("salary")
    @Expose
    private String salary;
    @SerializedName("job_role")
    @Expose
    private String jobRole;
    @SerializedName("job_description")
    @Expose
    private String jobDescription;
    @SerializedName("employment_type")
    @Expose
    private String employmentType;
    @SerializedName("industry")
    @Expose
    private String industry;
    @SerializedName("recruiter_name")
    @Expose
    private String recruiterName;
    @SerializedName("recruiter_contact_number")
    @Expose
    private String recruiterContactNumber;
    @SerializedName("recruiter_company_name")
    @Expose
    private String recruiterCompanyName;
    @SerializedName("company_name")
    @Expose
    private String companyName;
    @SerializedName("recruiter_mogo_contact_number")
    @Expose
    private String recruiterMogoContactNumber;
    @SerializedName("recruiter_profile_image")
    @Expose
    private Object recruiterProfileImage;
    @SerializedName("job_created")
    @Expose
    private String jobCreated;
    @SerializedName("postjob_status")
    @Expose
    private String postjobStatus;
    @SerializedName("jobskills_id")
    @Expose
    private String jobskillsId;
    @SerializedName("job_skills")
    @Expose
    private String job_skills;
    @SerializedName("sc_candidate_id")
    @Expose
    private String scCandidateId;
    @SerializedName("sc_id")
    @Expose
    private String scId;
    @SerializedName("userid")
    @Expose
    private String userid;
    @SerializedName("sc_user_id")
    @Expose
    private String scUserId;
    @SerializedName("company_id")
    @Expose
    private String companyId;
    @SerializedName("company_email")
    @Expose
    private String companyEmail;
    @SerializedName("company_phone")
    @Expose
    private String companyPhone;
    @SerializedName("company_website")
    @Expose
    private String companyWebsite;
    @SerializedName("company_address")
    @Expose
    private String companyAddress;
    @SerializedName("about_company")
    @Expose
    private String aboutCompany;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("created_date")
    @Expose
    private String createdDate;
    @SerializedName("jobLocations")
    @Expose
    private List<JobLocation> jobLocations = null;
    @SerializedName("jobQualification")
    @Expose
    private List<JobQualification> jobQualification = null;
    @SerializedName("jobSkills")
    @Expose
    private List<JobSkill> jobSkills = null;
    @SerializedName("job_country")
    @Expose
    private String job_country;
    @SerializedName("job_state")
    @Expose
    private String job_state;
    @SerializedName("save_status")
    @Expose
    private Boolean saveStatus;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Object getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(Object userFullName) {
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

    public Object getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(Object userEmail) {
        this.userEmail = userEmail;
    }

    public Object getUserDob() {
        return userDob;
    }

    public void setUserDob(Object userDob) {
        this.userDob = userDob;
    }

    public Object getUserProfileImage() {
        return userProfileImage;
    }

    public void setUserProfileImage(Object userProfileImage) {
        this.userProfileImage = userProfileImage;
    }

    public String getScId() {
        return scId;
    }

    public void setScId(String scId) {
        this.scId = scId;
    }

    public String getScUserId() {
        return scUserId;
    }

    public String getScCandidateId() {
        return scCandidateId;
    }

    public void setScCandidateId(String scCandidateId) {
        this.scCandidateId = scCandidateId;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public void setScUserId(String scUserId) {
        this.scUserId = scUserId;
    }

    public Object getUserBusinessName() {
        return userBusinessName;
    }

    public void setUserBusinessName(Object userBusinessName) {
        this.userBusinessName = userBusinessName;
    }

    public Object getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(Object userAccount) {
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

    public Object getUserCountryId() {
        return userCountryId;
    }

    public void setUserCountryId(Object userCountryId) {
        this.userCountryId = userCountryId;
    }

    public Object getUserStateId() {
        return userStateId;
    }

    public void setUserStateId(Object userStateId) {
        this.userStateId = userStateId;
    }

    public String getUsjId() {
        return usjId;
    }

    public void setUsjId(String usjId) {
        this.usjId = usjId;
    }

    public String getUsjUserId() {
        return usjUserId;
    }

    public void setUsjUserId(String usjUserId) {
        this.usjUserId = usjUserId;
    }

    public String getUsjJobId() {
        return usjJobId;
    }

    public void setUsjJobId(String usjJobId) {
        this.usjJobId = usjJobId;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getOwnerUserId() {
        return ownerUserId;
    }

    public void setOwnerUserId(String ownerUserId) {
        this.ownerUserId = ownerUserId;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getJobVacancies() {
        return jobVacancies;
    }

    public void setJobVacancies(String jobVacancies) {
        this.jobVacancies = jobVacancies;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getJobRole() {
        return jobRole;
    }

    public void setJobRole(String jobRole) {
        this.jobRole = jobRole;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public String getEmploymentType() {
        return employmentType;
    }

    public void setEmploymentType(String employmentType) {
        this.employmentType = employmentType;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getRecruiterName() {
        return recruiterName;
    }

    public void setRecruiterName(String recruiterName) {
        this.recruiterName = recruiterName;
    }

    public String getRecruiterContactNumber() {
        return recruiterContactNumber;
    }

    public void setRecruiterContactNumber(String recruiterContactNumber) {
        this.recruiterContactNumber = recruiterContactNumber;
    }

    public String getRecruiterCompanyName() {
        return recruiterCompanyName;
    }

    public void setRecruiterCompanyName(String recruiterCompanyName) {
        this.recruiterCompanyName = recruiterCompanyName;
    }

    public String getRecruiterMogoContactNumber() {
        return recruiterMogoContactNumber;
    }

    public void setRecruiterMogoContactNumber(String recruiterMogoContactNumber) {
        this.recruiterMogoContactNumber = recruiterMogoContactNumber;
    }

    public Object getRecruiterProfileImage() {
        return recruiterProfileImage;
    }

    public void setRecruiterProfileImage(Object recruiterProfileImage) {
        this.recruiterProfileImage = recruiterProfileImage;
    }

    public String getJobCreated() {
        return jobCreated;
    }

    public void setJobCreated(String jobCreated) {
        this.jobCreated = jobCreated;
    }

    public String getPostjobStatus() {
        return postjobStatus;
    }

    public void setPostjobStatus(String postjobStatus) {
        this.postjobStatus = postjobStatus;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyEmail() {
        return companyEmail;
    }

    public void setCompanyEmail(String companyEmail) {
        this.companyEmail = companyEmail;
    }

    public String getCompanyPhone() {
        return companyPhone;
    }

    public void setCompanyPhone(String companyPhone) {
        this.companyPhone = companyPhone;
    }

    public String getCompanyWebsite() {
        return companyWebsite;
    }

    public void setCompanyWebsite(String companyWebsite) {
        this.companyWebsite = companyWebsite;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getAboutCompany() {
        return aboutCompany;
    }

    public void setAboutCompany(String aboutCompany) {
        this.aboutCompany = aboutCompany;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public List<JobLocation> getJobLocations() {
        return jobLocations;
    }

    public void setJobLocations(List<JobLocation> jobLocations) {
        this.jobLocations = jobLocations;
    }

    public List<JobQualification> getJobQualification() {
        return jobQualification;
    }

    public void setJobQualification(List<JobQualification> jobQualification) {
        this.jobQualification = jobQualification;
    }

    public List<JobSkill> getJobSkills() {
        return jobSkills;
    }

    public void setJobSkills(List<JobSkill> jobSkills) {
        this.jobSkills = jobSkills;
    }

    public String getJob_skills() {
        return job_skills;
    }

    public void setJob_skills(String job_skills) {
        this.job_skills = job_skills;
    }

    public String getJob_country() {
        return job_country;
    }

    public void setJob_country(String job_country) {
        this.job_country = job_country;
    }

    public String getJob_state() {
        return job_state;
    }

    public void setJob_state(String job_state) {
        this.job_state = job_state;
    }

    public Boolean getSaveStatus() {
        return saveStatus;
    }

    public void setSaveStatus(Boolean saveStatus) {
        this.saveStatus = saveStatus;
    }
}
