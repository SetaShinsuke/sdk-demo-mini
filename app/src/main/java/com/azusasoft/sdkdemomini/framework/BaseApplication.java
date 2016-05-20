package com.azusasoft.sdkdemomini.framework;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.DialogInterface;
import android.util.Log;
import android.widget.Toast;

import com.azusasoft.facehubcloudsdk.api.FacehubApi;
import com.azusasoft.facehubcloudsdk.api.utils.LogX;
import com.githang.androidcrash.AndroidCrash;
import com.githang.androidcrash.reporter.httpreporter.CrashHttpReporter;
import com.githang.androidcrash.reporter.mailreporter.CrashEmailReporter;

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

//        initHttpReporter();
    }

    /**
     * 使用EMAIL发送日志
     */
    private void initEmailReporter() {
        CrashEmailReporter reporter = new CrashEmailReporter(this);
        reporter.setReceiver("你的接收邮箱");
        reporter.setSender("irain_log@163.com");
        reporter.setSendPassword("xxxxxxxx");
        reporter.setSMTPHost("smtp.163.com");
        reporter.setPort("465");
        AndroidCrash.getInstance().setCrashReporter(reporter).init(this);

    }

    /**
     * 使用HTTP发送日志
     */
    private void initHttpReporter() {
        CrashHttpReporter reporter = new CrashHttpReporter(this) {
            /**
             * 重写此方法，可以弹出自定义的崩溃提示对话框，而不使用系统的崩溃处理。
             * @param thread
             * @param ex
             */
            @Override
            public void closeApp(Thread thread, Throwable ex) {
//                final Activity activity = AppManager.currentActivity();
//                Toast.makeText(activity, "发生异常，正在退出", Toast.LENGTH_SHORT).show();
//                // 自定义弹出对话框
//                new AlertDialog.Builder(activity).
//                        setMessage("程序发生异常，现在退出").
//                        setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                AppManager.AppExit(activity);
//                            }
//                        }).create().show();
                LogX.d("强制关闭 : + \nthead:" + Thread.currentThread().getName());
//                super.closeApp(thread,ex);
            }
        };
        reporter.setUrl("http://crashreport.jd-app.com/your_receiver").setFileParam("fileName")
                .setToParam("to").setTo("sweater@azusasoft.com")
                .setTitleParam("subject").setBodyParam("message");
        reporter.setCallback(new CrashHttpReporter.HttpReportCallback() {
            @Override
            public boolean isSuccess(int i, String s) {
                LogX.fastLog("崩溃回调 : " + s);
                return s.endsWith("ok");
            }
        });
        AndroidCrash.getInstance().setCrashReporter(reporter).init(this);
    }
}
