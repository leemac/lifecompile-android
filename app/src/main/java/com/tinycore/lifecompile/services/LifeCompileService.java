package com.tinycore.lifecompile.services;

import com.tinycore.lifecompile.models.Note;
import com.tinycore.lifecompile.models.Tag;
import com.tinycore.lifecompile.models.Token;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

@SuppressWarnings("unused")
public interface LifeCompileService {
    @FormUrlEncoded
    @POST("api-token-auth/")
    Call<Token> login(@Field("username") String username, @Field("password") String password);

    @GET("api/notes/")
    Call<ArrayList<Note>> getNotes();

    @GET("api/notes/{id}")
    Call<Note> getNote(@Path("id") long id);

    @FormUrlEncoded
    @POST("api/notes/")
    Call<Note> createNote(@Field("content") String content, @Field("tags") String tags);

    @FormUrlEncoded
    @PUT("api/notes/{id}")
    Call<Note> updateNote(@Path("id") long id, @Field("content") String content, @Field("tags") String tags);

    @DELETE("api/notes/{id}")
    Call<Note> deleteNote(@Path("id") long id);

    @GET("api/tags/")
    Call<ArrayList<Tag>> getTags();

    @GET("api/tags/{id}")
    Call<Note> getTag(@Path("id") long id);

    @DELETE("api/tags/{id}")
    Call<Note> deleteTag(@Path("id") long id);
}