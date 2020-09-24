package com.xcrino.moro.Mesibo;

import com.xcrino.moro.Mesibo.mesiboModels.MesiboUserCreation;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface MesiboAPIInterface {

    @FormUrlEncoded
    @POST("api.php")
    Call<MesiboUserCreation> createUserInMesibo(@Field("token") String token, @Field("op") String op, @Field("appid") String appid,
                                                @Field("addr") String addr);
}
