package com.xcrino.moro.Interface;

import com.xcrino.moro.PojoModels.AddCompaignFileResponse;
import com.xcrino.moro.PojoModels.BankDetailsResponse;
import com.xcrino.moro.PojoModels.BusinessCategory;
import com.xcrino.moro.PojoModels.BusinessModuleDetailsResponse;
import com.xcrino.moro.PojoModels.CampaignModel;
import com.xcrino.moro.PojoModels.CampaignResponseModel;
import com.xcrino.moro.PojoModels.CheckEmpOrRecruiter;
import com.xcrino.moro.PojoModels.CommonResponseModel;
import com.xcrino.moro.PojoModels.CompaignBlockedResponse;
import com.xcrino.moro.PojoModels.CountryListData;
import com.xcrino.moro.PojoModels.DefaultData;
import com.xcrino.moro.PojoModels.EmployeeSubscriptionWallet;
import com.xcrino.moro.PojoModels.GetCandidateProfile;
import com.xcrino.moro.PojoModels.GetCompanyDetailsModel;
import com.xcrino.moro.PojoModels.GetJobDetailModel;
import com.xcrino.moro.PojoModels.ImageUploadModel;
import com.xcrino.moro.PojoModels.IndustryList;
import com.xcrino.moro.PojoModels.OtpLogin;
import com.xcrino.moro.PojoModels.PostedJobsList;
import com.xcrino.moro.PojoModels.QualificationData;
import com.xcrino.moro.PojoModels.ReceivedSavedJobsCandidates;
import com.xcrino.moro.PojoModels.RecruiterSubscriptionWallet;
import com.xcrino.moro.PojoModels.SkillsData;
import com.xcrino.moro.PojoModels.StateListData;
import com.xcrino.moro.PojoModels.SubscriptionPlanResponse;
import com.xcrino.moro.PojoModels.UploadUserOrCompanyImage;
import com.xcrino.moro.PojoModels.UserProfile;
import com.xcrino.moro.PojoModels.UserRegistration;

import org.json.JSONArray;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Url;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("Mogo_api/sendOTPRegisterMobile")
    Call<OtpLogin> getOTPMethod(@Field("user_mobile") String mobileNumber, @Field("country_code") Integer countryCode);

    @FormUrlEncoded
    @POST("Mogo_api/user_register")
    Call<UserRegistration> postUserRegistrationMethod(@Field("user_mobile") String mobileNumber,
                                                      @Field("country_code") Integer countryCode, @Field("token") String token);

    @GET("Mogo_api/business_category")
    Call<BusinessCategory> getBusinessCategoriesMethod();

    @FormUrlEncoded
    @POST("Mogo_api/getUserProfile")
    Call<UserProfile> getUserProfileDetailsMethod(@Field("userid") String userid);

    @FormUrlEncoded
    @POST("Mogo_api/user_profile_update")
    Call<CommonResponseModel> postUpdateUserProfileMethod(@Field("userid") String userId, @Field("user_full_name") String userFullName,
                                                          @Field("user_email") String userEmail, @Field("user_dob") String userDob,
                                                          @Field("user_account") String userAccountType,
                                                          @Field("user_business_category") JSONArray userBusinessCategory,
                                                          @Field("user_business_name") String userBusinessName,
                                                          @Field("user_country_id") String userCountryId,
                                                          @Field("user_state_id") String userStateId);

    @Multipart
    @POST("Mogo_api/UploadUserOrCompanyImage")
    Call<UploadUserOrCompanyImage> postUploadUserOrCompanyImage(@Part("userid") RequestBody userId, @Part RequestBody imageflagkey,
                                                                @Part MultipartBody.Part userOrCompanyImage);

    @GET("Mogo_api/getCountryList")
    Call<CountryListData> getCountryListMethod();

    @FormUrlEncoded
    @POST("Mogo_api/getStateList")
    Call<StateListData> getStatesListMethod(@Field("country_code") String countryCode);

    @GET("Mogo_api/skills")
    Call<SkillsData> getSkillsDataList();

    @GET("Mogo_api/qualification")
    Call<QualificationData> getQualificationDataList();

    @GET("Mogo_api/industry")
    Call<IndustryList> getIndustryDataList();

    @FormUrlEncoded
    @POST("Mogo_api/checkEmpOrRecruiter")
    Call<CheckEmpOrRecruiter> CheckEmpOrRecruiter(@Field("userid") String userid);

    @GET("Mogo_api/get_default_data")
    Call<DefaultData> getDefaultDataMethod();

    @Multipart
    @POST("Mogo_api/uploadImage")
    Call<ImageUploadModel> uploadImageMethod(@Part MultipartBody.Part image, @Part("imageflagkey") RequestBody imageFlagKey,
                                             @Part("userid") RequestBody userId, @Part("old_image") RequestBody old_image);

    @FormUrlEncoded
    @POST("Mogo_api/create_company_profile")
    Call<CommonResponseModel> postCreateCompanyProfileMethod(@Field("userid") String userid,
                                                             @Field("company_name") String company_name,
                                                             @Field("company_email") String company_email,
                                                             @Field("company_website") String company_website,
                                                             @Field("company_address") String company_address,
                                                             @Field("about_company") String about_company,
                                                             @Field("country") String country, @Field("state") String state,
                                                             @Field("company_phone") String company_phone);

    @GET
    Call<GetCompanyDetailsModel> getCompanydetails(@Url String id);

    @FormUrlEncoded
    @POST("Mogo_api/update_company_profile")
    Call<CommonResponseModel> updateCreateCompanyProfileMethod(@Field("userid") String userid,
                                                               @Field("company_name") String company_name,
                                                               @Field("company_email") String company_email,
                                                               @Field("company_website") String company_website,
                                                               @Field("company_address") String company_address,
                                                               @Field("about_company") String about_company,
                                                               @Field("country") String country, @Field("state") String state,
                                                               @Field("company_phone") String company_phone);

    @FormUrlEncoded
    @POST("Mogo_api/postJobs")
    Call<CommonResponseModel> postJobMethod(@Field("userid") String userId, @Field("job_title") String jobTitle,
                                            @Field("job_vacancies") String jobVacancies, @Field("experience") String experience,
                                            @Field("salary") String salary, @Field("job_role") String jobRole,
                                            @Field("job_description") String jobDescription,
                                            @Field("employment_type") String employmentType,
                                            @Field("industry") String industry, @Field("recruiter_name") String recruiterName,
                                            @Field("recruiter_contact_number") String recruiterContactNumber,
                                            @Field("recruiter_company_name") String recruiterCompanyName,
                                            @Field("recruiter_mogo_contact_number") String recruiterMogoContactNumber,
                                            @Field("skills") JSONArray skills, @Field("state") JSONArray state,
                                            @Field("country") String country, @Field("qualification") JSONArray qualification);

    @GET
    Call<SubscriptionPlanResponse> getSubscriptionPlanMethod(@Url String id);

    @GET
    Call<PostedJobsList> getPostedJobsListMethod(@Url String id);

    @GET
    Call<GetJobDetailModel> getJobDetailsMethod(@Url String id);

    @GET
    Call<GetCandidateProfile> getEmployeeProfileMethod(@Url String id);

    @FormUrlEncoded
    @POST
    Call<ReceivedSavedJobsCandidates> getSavedCandidatesListMethod(@Url String id, @Field("type") String type);

    @GET
    Call<ReceivedSavedJobsCandidates> getApplicationsReceivedListMethod(@Url String id);

    @GET
    Call<PostedJobsList> getSavedJoblist(@Url String id);

    @FormUrlEncoded
    @POST("Mogo_api/add_subscription")
    Call<CommonResponseModel> postAddSubscriptionMethod(@Field("userid") String userId, @Field("subscription_id") String subscriptionId,
                                                        @Field("razorpayPaymentID") String razorpayPaymentID);


    @GET
    Call<PostedJobsList> GetAppliedJobs(@Url String id);

    @FormUrlEncoded
    @POST
    Call<CommonResponseModel> updatetJobMethod(@Url String id,
                                               @Field("userid") String userId, @Field("job_title") String jobTitle,
                                               @Field("job_vacancies") String jobVacancies, @Field("experience") String experience,
                                               @Field("salary") String salary, @Field("job_role") String jobRole,
                                               @Field("job_description") String jobDescription,
                                               @Field("employment_type") String employmentType,
                                               @Field("industry") String industry, @Field("recruiter_name") String recruiterName,
                                               @Field("recruiter_contact_number") String recruiterContactNumber,
                                               @Field("recruiter_company_name") String recruiterCompanyName,
                                               @Field("recruiter_mogo_contact_number") String recruiterMogoContactNumber,
                                               @Field("skills") JSONArray skills, @Field("state") JSONArray state,
                                               @Field("country") String country, @Field("qualification") JSONArray qualification);

    @GET
    Call<GetCandidateProfile> GetCandidatesDetailsMethod(@Url String id);

    @GET
    Call<PostedJobsList> getAllJobsListMethod(@Url String url);

    @GET
    Call<PostedJobsList> getInterestRecruiters(@Url String id);

    @GET
    Call<PostedJobsList> getJobsListByCandidateSkillsMethod(@Url String id);

    @GET
    Call<PostedJobsList> getJobsListByCandidateLocationMethod(@Url String id);

    @FormUrlEncoded
    @POST
    Call<PostedJobsList> getJobListByIndustry(@Url String id, @Field("industry") String industry);

    @FormUrlEncoded
    @POST
    Call<PostedJobsList> postJobSearchByCandidateMethod(@Url String id, @Field("skills") String skills,
                                                        @Field("country") String country, @Field("state") String state);

    @FormUrlEncoded
    @POST
    Call<ReceivedSavedJobsCandidates> postCandidateSearchByIndustry(@Url String id, @Field("industry") String industry);

    @FormUrlEncoded
    @POST
    Call<ReceivedSavedJobsCandidates> postCandidateSearchByRecruiterMethod(@Url String id, @Field("skills") String skills, @Field("country") String country,
                                                                           @Field("state") String state);

    @FormUrlEncoded
    @POST("Mogo_api/apply_job")
    Call<CommonResponseModel> postApplyJobMethod(@Field("user_id") String user_id, @Field("job_id") String job_id);

    @GET
    Call<CommonResponseModel> saveJobMethod(@Url String id);

    @FormUrlEncoded
    @POST
    Call<CommonResponseModel> saveCandidateMethod(@Url String id, @Field("type") String type);

    @FormUrlEncoded
    @POST("Mogo_api/create_employee_profile")
    Call<CommonResponseModel> postCreateEmployeeProfileMethod(@Field("user_id") String user_id, @Field("employee_name") String employee_name,
                                                              @Field("employee_dob") String employee_dob,
                                                              @Field("employee_gender") String employee_gender,
                                                              @Field("employee_address") String employee_address,
                                                              @Field("employee_country") String employee_country,
                                                              @Field("employee_state") String employee_state,
                                                              @Field("employee_last_company_name") String employee_last_company_name,
                                                              @Field("employee_designation") String employee_designation,
                                                              @Field("employee_work_exp_in_last_company") String employee_work_exp_in_last_company,
                                                              @Field("employee_work_country") String employee_work_country,
                                                              @Field("employee_work_state") String employee_work_state,
                                                              @Field("employee_profile_headline") String employee_profile_headline,
                                                              @Field("employee_skills") JSONArray employee_skills,
                                                              @Field("employee_job_exp") String employee_job_exp,
                                                              @Field("employee_employment_type") String employee_employment_type,
                                                              @Field("employee_qualification") String employee_qualification,
                                                              @Field("employee_salary_expectation") String employee_salary_expectation,
                                                              @Field("employee_have_passport") String employee_have_passport,
                                                              @Field("employee_industry") String employee_industry);

    @FormUrlEncoded
    @POST("Mogo_api/update_employee_profile")
    Call<CommonResponseModel> postUpdateEmployeeProfileMethod(@Field("user_id") String user_id,
                                                              @Field("employee_name") String employee_name,
                                                              @Field("employee_dob") String employee_dob,
                                                              @Field("employee_gender") String employee_gender,
                                                              @Field("employee_address") String employee_address,
                                                              @Field("employee_country") String employee_country,
                                                              @Field("employee_state") String employee_state,
                                                              @Field("employee_last_company_name") String employee_last_company_name,
                                                              @Field("employee_designation") String employee_designation,
                                                              @Field("employee_work_exp_in_last_company") String employee_work_exp_in_last_company,
                                                              @Field("employee_work_country") String employee_work_country,
                                                              @Field("employee_work_state") String employee_work_state,
                                                              @Field("employee_profile_headline") String employee_profile_headline,
                                                              @Field("employee_skills") JSONArray employee_skills,
                                                              @Field("employee_job_exp") String employee_job_exp,
                                                              @Field("employee_employment_type") String employee_employment_type,
                                                              @Field("employee_qualification") String employee_qualification,
                                                              @Field("employee_salary_expectation") String employee_salary_expectation,
                                                              @Field("employee_have_passport") String employee_have_passport,
                                                              @Field("employee_industry") String employee_industry);

    @FormUrlEncoded
    @POST("Business-module-api/create-business-profile")
    Call<CommonResponseModel> postCreateBusinessProfileMethod(@Field("user_id") String userId, @Field("name") String name,
                                                              @Field("category") JSONArray category, @Field("address") String address,
                                                              @Field("country") String country, @Field("state") String state,
                                                              @Field("email") String email, @Field("website") String website,
                                                              @Field("est_year") String estYear, @Field("contact_no") String contactNo,
                                                              @Field("about") String about);

    @FormUrlEncoded
    @POST("Business_module_api/edit-business-profile")
    Call<CommonResponseModel> postEditBusinessProfileMethod(@Field("user_id") String userId, @Field("name") String name,
                                                            @Field("category") JSONArray category, @Field("address") String address,
                                                            @Field("country") String country, @Field("state") String state,
                                                            @Field("email") String email, @Field("website") String website,
                                                            @Field("est_year") String estYear, @Field("contact_no") String contactNo,
                                                            @Field("about") String about);

    @GET
    Call<BusinessModuleDetailsResponse> getBusinessCompanyProfileDetails(@Url String id);

    @GET("Business-module-api/get-business-module-plans")
    Call<SubscriptionPlanResponse> getBusinessModulePlans();

    @FormUrlEncoded
    @POST
    Call<PostedJobsList> postFilterJobList(@Url String id, @Field("skills") String skills, @Field("country") String country,
                                           @Field("state") String state, @Field("qualification") String qualification);

    @GET
    Call<SubscriptionPlanResponse> getEmployeeSubscriptionPlans(@Url String id);

    @GET
    Call<RecruiterSubscriptionWallet> getRecruiterSubscriptionWalletMethod(@Url String id);

    @FormUrlEncoded
    @POST("Mogo_api/apply_plan_employee")
    Call<CommonResponseModel> applyEmployeePlanMethod(@Field("user_id") String userId, @Field("plan_id") String subscriptionId,
                                                      @Field("transaction_id") String razorpayPaymentID);

    @GET
    Call<EmployeeSubscriptionWallet> getEmployeeSubscriptionWalletMethod(@Url String id);

    @FormUrlEncoded
    @POST("Business-module-api/apply-business-module-plan")
    Call<CommonResponseModel> applyBusinessModulePlanMethod(@Field("user_id") String userId, @Field("plan_id") String subscriptionId,
                                                            @Field("transaction_id") String razorpayPaymentID);

    @Multipart
    @POST("Business-module-api/add-campaign")
    Call<AddCompaignFileResponse> boostCampaignMethod(@Part MultipartBody.Part file,
                                                      @Part("user_id") RequestBody user_id,
                                                      @Part("campaign_mode") RequestBody campaign_mode,
                                                      @Part("text") RequestBody text,
                                                      @Part("timestamp") RequestBody timestamp,
                                                      @Part("countries") RequestBody countries,
                                                      @Part("database_type") RequestBody database_type,
                                                      @Part("local_users") RequestBody local_users,
                                                      @Part("link") RequestBody link,
                                                      @Part("user_count") RequestBody user_count);

    @GET
    Call<CampaignResponseModel> getCampaignByUser(@Url String id);

    @GET
    Call<CampaignResponseModel> getBlockedCampaign(@Url String id);

    @GET
    Call<CampaignResponseModel> getAllCampaign(@Url String id);

    @GET
    Call<CampaignModel> getCampaignDetail(@Url String id);

    @GET
    Call<CommonResponseModel> getToggleLikeCampaign(@Url String id);

    @FormUrlEncoded
    @POST("Business-module-api/toggle-block-campaign")
    Call<CommonResponseModel> postToggleBlockCampaign(@Field("user_id") String userId, @Field("campaign_id") String campaignId);

    @GET
    Call<BankDetailsResponse> getBankDetails(@Url String id);

    @FormUrlEncoded
    @POST("Mogo_api/withdraw_wallet_amount")
    Call<BankDetailsResponse> postWithdrawWalletAmount(@Field("user_id") String userId, @Field("credit_amt") String creditAmt,
                                                       @Field("bank_name") String bankName, @Field("bank_ifsc") String bankIfsc,
                                                       @Field("bank_account_no") String bankAccountNo,
                                                       @Field("account_holder_name") String accountHolderName,
                                                       @Field("bank_branch") String bankBranch);
}