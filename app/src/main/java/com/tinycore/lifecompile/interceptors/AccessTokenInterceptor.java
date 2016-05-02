package com.tinycore.lifecompile.interceptors;

import android.content.Context;
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

        Context context = MyApp.getContext();
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);

        String token = settings.getString("token", "");

        if(token != "") {
            Request newRequest = request.newBuilder()
                    .header("Authorization", "Token " + token)
                    .build();

            Log.d("Token Intercepter", token);

            return chain.proceed(newRequest);
        }

        return chain.proceed(request);
    }
}
