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
//    private final String APP_ID = "65737441-7070-6c69-6361-74696f6e4944";
    //仿微信
    private final String APP_ID = "94f4d7a5-b716-48ae-a3d0-b64be1a037e7";

    //默认
//    public static final String ACCESS_KEY = "7d0e4978b9ebd66d6ff8fd43f4dbb513";
//    public static final String SIGN = "NjROt1d782bDGmI0D3ppnkn1mH4=\n";
//    public static final long DEADLINE = 2413198604L;

    //仿微信
//    public static final String ACCESS_KEY = "1a06168089802ae14bc245bccbda0c30";
//    public static final String SIGN = "4A4ZhtmNWPFsnv+DiDyXiZYcmVA=\n";
//    public static final long DEADLINE = 1784105243L;

    //测试账号-外发Demo
//    public static String USER_ID = "e214881f-98e2-4a85-9de6-037e724a6100";
//    public static String AUTH_TOKEN = "953dca8a34bfef614237dcda5061f839";

//    public static String USER_ID_2 = "73be42c0-af9d-42fc-916d-bf6588559d8f";
//    public static String AUTH_TOKEN_2 = "6a5033e05e0339849130d5780b461839";

//    public static String USER_ID_2 = "530d41e7-e555-4c4b-8184-8f5bebe4d00d";
//    public static String AUTH_TOKEN_2 = "a9568429b1ba4170480e070122ed380f";

    //内测
//    public static String USER_ID = "530d41e7-e555-4c4b-8184-8f5bebe4d00d";
//    public static String AUTH_TOKEN = "a9568429b1ba4170480e070122ed380f";

    //测服
//    public static String USER_ID = "045978c8-5d13-4a81-beac-4ec28d1f304f";
//    public static String AUTH_TOKEN = "02db12b9350f7dceb158995c01e21a2a";

    //测服
//    public static String USER_ID = "c819eaaa-31a2-4d37-8e2d-8b76c2d58aca";
//    public static String AUTH_TOKEN = "5d868255d2c08ebbdc0656dcdb560674";


    //仿微信
    public static String USER_ID = "89c5a668-fae4-440e-b79b-77cfb6613fb7";
    public static String AUTH_TOKEN = "6d6a2d47f2e9bea25481b119ef400afc";

    public static String USER_ID_2 = "06b6b925-7d5f-4662-83b6-ddd4a2dabf21";
    public static String AUTH_TOKEN_2 = "2389d355ebf00b051d20d63f53fcf957";

    @Override
    public void onCreate() {
        super.onCreate();
        //1.设置接入应用id
        //2.初始化api
//        FacehubApi.getApi().setAppId(APP_ID);
        FacehubApi.init(getApplicationContext(),APP_ID,true);
        FacehubApi.initViews(getApplicationContext());

        FacehubApi.getApi().setViewStyle(1);
        //3.设置主色调 (示例:蓝色)
//        FacehubApi.getApi().setThemeColor("#f33847");
//        FacehubApi.getApi().setThemeColor("#3f51b5");
//        FacehubApi.getApi().setActionBarColor("#393a3e");
        //4.设置商店页面标题文字
//        FacehubApi.getApi().setEmoStoreTitle("示例标题");

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
