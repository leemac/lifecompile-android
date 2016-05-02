package com.tinycore.lifecompile.services;

import com.tinycore.lifecompile.models.Note;
import com.tinycore.lifecompile.models.Token;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

@SuppressWarnings("unused")
public interface LifeCompileService {
    @FormUrlEncoded
    @POST("api-token-auth/")
    Call<Token> login(@Field("username") String username, @Field("password") String password);

    @GET("api/notes/")
    Call<ArrayList<Note>> getNotes();
}