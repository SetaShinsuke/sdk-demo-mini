package com.azusasoft.sdkdemomini.framework;

import android.app.Application;

import com.azusasoft.facehubcloudsdk.api.FacehubApi;

/**
 * Created by SETA on 2016/5/19.
 */
public class BaseApplication extends Application {
    private final String APP_ID = "65737441-7070-6c69-6361-74696f6e4944";

    //测试账号-外发Demo
    public static String USER_ID = "e214881f-98e2-4a85-9de6-037e724a6100";
    public static String AUTH_TOKEN = "953dca8a34bfef614237dcda5061f839";

    @Override
    public void onCreate() {
        super.onCreate();
        FacehubApi.init(getApplicationContext());
        FacehubApi.initViews(getApplicationContext());
        FacehubApi.getApi().setAppId(APP_ID);
    }
}
