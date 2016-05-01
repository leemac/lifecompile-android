package com.tinycore.lifecompile.interceptors;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AccessTokenInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain)
            throws IOException {
        Request request = chain.request();

        SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(MyApp.getContext());
        String token = settings.getString("token", "");

        Request newRequest = request.newBuilder()
                .header("x-access-token", token)
                .build();

        return chain.proceed(newRequest);
    }
}
