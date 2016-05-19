package com.azusasoft.sdkdemomini.framework;

import android.app.Application;

import com.azusasoft.facehubcloudsdk.api.FacehubApi;

/**
 * Created by SETA on 2016/5/19.
 */
public class BaseApplication extends Application {
    private final String APP_ID = "65737441-7070-6c69-6361-74696f6e4944";

    public static String USER_ID = "73be42c0-af9d-42fc-916d-bf6588559d8f";
    public static String AUTH_TOKEN = "6a5033e05e0339849130d5780b461839";

    @Override
    public void onCreate() {
        super.onCreate();
        FacehubApi.init(getApplicationContext());
        FacehubApi.initViews(getApplicationContext());
        FacehubApi.getApi().setAppId(APP_ID);
    }
}
