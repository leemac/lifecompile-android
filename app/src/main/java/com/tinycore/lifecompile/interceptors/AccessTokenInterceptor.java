package com.tinycore.lifecompile.interceptors;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AccessTokenInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain)
            throws IOException {
        Request request = chain.request();

        Log.d("Token Intercepter", request.url().toString());

        Log.d("Token Intercepter", request.body().toString());
//
//        SharedPreferences settings = PreferenceManager
//                .getDefaultSharedPreferences(MyApp.getContext());
//        String token = settings.getString("token", "");

        Request newRequest = request.newBuilder()
                .header("x-access-token", "foo")
                .build();

        return chain.proceed(newRequest);
    }
}
