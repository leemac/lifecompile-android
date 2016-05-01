package com.tinycore.lifecompile.services;

import com.tinycore.lifecompile.models.Token;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

@SuppressWarnings("unused")
public interface LifeCompileService {
    @FormUrlEncoded
    @POST("token")
    Call<Token> login(@Field("email") String email, @Field("password") String password);
}