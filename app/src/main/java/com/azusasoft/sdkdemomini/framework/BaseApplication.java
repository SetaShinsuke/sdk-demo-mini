package com.azusasoft.sdkdemomini.framework;
import android.app.Application;

import com.azusasoft.facehubcloudsdk.api.FacehubApi;

/**
 * Created by SETA on 2016/5/19.
 * 应用入口
 */
public class BaseApplication extends Application {
    private final String APP_ID = "65737441-7070-6c69-6361-74696f6e4944";

    //测试账号-外发Demo
    public static String USER_ID = "e214881f-98e2-4a85-9de6-037e724a6100";
    public static String AUTH_TOKEN = "953dca8a34bfef614237dcda5061f839";

//    public static String USER_ID = "73be42c0-af9d-42fc-916d-bf6588559d8f";
//    public static String AUTH_TOKEN = "6a5033e05e0339849130d5780b461839";

    @Override
    public void onCreate() {
        super.onCreate();
        //1.初始化api
        FacehubApi.init(getApplicationContext());
        FacehubApi.initViews(getApplicationContext());
        //2.设置接入应用id
        FacehubApi.getApi().setAppId(APP_ID);
        //3.设置主色调 (示例:蓝色)
//        FacehubApi.getApi().setThemeColor("#f33847");
        FacehubApi.getApi().setThemeColor("#3f51b5");
        //4.设置商店页面标题文字
        FacehubApi.getApi().setEmoStoreTitle("事例标题");

    }
}
