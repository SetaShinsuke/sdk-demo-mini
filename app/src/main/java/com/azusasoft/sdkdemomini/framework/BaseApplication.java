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
//    public static String USER_ID = "e214881f-98e2-4a85-9de6-037e724a6100";
//    public static String AUTH_TOKEN = "953dca8a34bfef614237dcda5061f839";

    //内测
//    public static String USER_ID = "530d41e7-e555-4c4b-8184-8f5bebe4d00d";
//    public static String AUTH_TOKEN = "a9568429b1ba4170480e070122ed380f";

    //内网
    public static String USER_ID = "045978c8-5d13-4a81-beac-4ec28d1f304f";
    public static String AUTH_TOKEN = "02db12b9350f7dceb158995c01e21a2a";


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
