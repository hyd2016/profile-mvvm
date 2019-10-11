package com.example.userprofile;

import android.app.Application;
import android.content.Context;

/**
 * @descriptioon:
 * @author: dinghaoyu
 * @date: 2019-10-07 16:33
 */
public class BaseApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }
}