package com.azusasoft.sdkdemomini.framework;

import android.app.Application;
import android.util.Log;

import com.azusasoft.facehubcloudsdk.api.FacehubApi;
import com.azusasoft.facehubcloudsdk.api.LocalEmoPackageParseException;
import com.azusasoft.facehubcloudsdk.api.utils.LogX;
import com.azusasoft.sdkdemomini.Constants;

/**
 * Created by SETA on 2016/5/19.
 * 应用入口
 */
public class BaseApplication extends Application {
    private final String APP_ID = "65737441-7070-6c69-6361-74696f6e4944";

    //测试账号-外发Demo
    public static String USER_ID = "e214881f-98e2-4a85-9de6-037e724a6100";
    public static String AUTH_TOKEN = "953dca8a34bfef614237dcda5061f839";

    public static String USER_ID_2 = "73be42c0-af9d-42fc-916d-bf6588559d8f";
    public static String AUTH_TOKEN_2 = "6a5033e05e0339849130d5780b461839";

//    public static String USER_ID_2 = "530d41e7-e555-4c4b-8184-8f5bebe4d00d";
//    public static String AUTH_TOKEN_2 = "a9568429b1ba4170480e070122ed380f";

    //内测
//    public static String USER_ID = "530d41e7-e555-4c4b-8184-8f5bebe4d00d";
//    public static String AUTH_TOKEN = "a9568429b1ba4170480e070122ed380f";

    //测服
//    public static String USER_ID = "045978c8-5d13-4a81-beac-4ec28d1f304f";
//    public static String AUTH_TOKEN = "02db12b9350f7dceb158995c01e21a2a";


    @Override
    public void onCreate() {
        super.onCreate();
        //1.初始化api
        FacehubApi.init(getApplicationContext());
        FacehubApi.initViews(getApplicationContext());
        //2.设置接入应用id
        FacehubApi.getApi().setAppId(APP_ID);

        FacehubApi.getApi().setViewStyle(1);
        //3.设置主色调 (示例:蓝色)
//        FacehubApi.getApi().setThemeColor("#f33847");
        FacehubApi.getApi().setThemeColor("#3f51b5");
        FacehubApi.getApi().setActionBarColorString("#393a3e");
        //4.设置商店页面标题文字
        FacehubApi.getApi().setEmoStoreTitle("示例标题");

        /**
         * 配置本地预置表情
         * 参数说明 : {@link EmoticonKeyboardView#loadEmoticonFromLocal(int, String, boolean)}
         *                      1.配置文件版本号;
         *                      2.配置文件，在assets文件夹内的具体路径;
         *                      3.是否允许图文混排，若设置为true,则在显示本地预置表情时，显示键盘内的【删除按钮】和【发送按钮】;
         *                      4.抛出异常 : {@link LocalEmoPackageParseException} 配置JSON解析出错时抛出异常;
         */
        try {
            FacehubApi.getApi().loadEmoticonFromLocal(1, "emoticonDescription.json", true);
        } catch (LocalEmoPackageParseException e) {
            Log.e(Constants.TAG, "解析预置表情 配置Json出错 : " + e);
            e.printStackTrace();
        }

        initUncaughtExceptionHandler();
    }

    private void initUncaughtExceptionHandler(){
        final Thread.UncaughtExceptionHandler defaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.UncaughtExceptionHandler handler = new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable ex) {
                LogX.e("#############################################################\n"
                        + "发生崩溃 : " + ex
                        +"\n#############################################################");
                defaultHandler.uncaughtException(thread,ex);
            }
        };
        Thread.setDefaultUncaughtExceptionHandler(handler);
    }
}
