package com.tinycore.lifecompile.interceptors;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

@SuppressLint("Registered")
@SuppressWarnings("unused")
public class MyApp extends Application {
    private static MyApp instance;

    public static MyApp getInstance() {
        return instance;
    }

    public static Context getContext(){
        return instance;
    }

    @Override
    public void onCreate() {
        instance = this;
        super.onCreate();
    }
}
