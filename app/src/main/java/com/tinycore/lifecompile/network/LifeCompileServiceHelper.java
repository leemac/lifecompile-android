package com.tinycore.lifecompile.network;

import com.tinycore.lifecompile.interceptors.AccessTokenInterceptor;
import com.tinycore.lifecompile.services.LifeCompileService;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LifeCompileServiceHelper {
    public static LifeCompileService GetService(){
        OkHttpClient okClient = new OkHttpClient.Builder()
                .addInterceptor(new AccessTokenInterceptor())
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiConfig.GetUrl())
                .client(okClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(LifeCompileService.class);
    }
}
