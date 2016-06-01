package com.azusasoft.sdkdemomini;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.azusasoft.facehubcloudsdk.api.FacehubApi;
import com.azusasoft.facehubcloudsdk.api.ProgressInterface;
import com.azusasoft.facehubcloudsdk.api.ResultHandlerInterface;
import com.azusasoft.facehubcloudsdk.api.models.Emoticon;
import com.azusasoft.facehubcloudsdk.api.utils.LogX;
import com.azusasoft.facehubcloudsdk.views.EmoticonKeyboardView;
import com.azusasoft.facehubcloudsdk.views.EmoticonSendListener;
import com.azusasoft.sdkdemomini.framework.BaseApplication;

public class MainActivity extends FragmentActivity {
    private TextView textView;
    private Context context;
    private EmoticonKeyboardView emoticonKeyboardView;
    private View progressBar;
    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = this;
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.log);
        progressBar = findViewById(R.id.progress_bar);
        toast = Toast.makeText(context,"toast",Toast.LENGTH_SHORT);
        emoticonKeyboardView = (EmoticonKeyboardView) findViewById(R.id.emo_keyboard);
        emoticonKeyboardView.initKeyboard();
        emoticonKeyboardView.setEmoticonSendListener(new EmoticonSendListener() {
            @Override
            public void onSend(Emoticon emoticon) {
                textView.setText("发送表情 : " + emoticon);
                showToast("发送表情 : " + emoticon.getId(),false);
            }
        });

//        final Thread.UncaughtExceptionHandler defaultHandler = Thread.getDefaultUncaughtExceptionHandler();
//        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
//            @Override
//            public void uncaughtException(Thread thread, Throwable ex) {
//                LogX.e("崩溃了 : " + ex);
//                LogX.e(("崩溃详情 : " ));
//                for(int i=0;i<ex.getStackTrace().length;i++){
//                    LogX.e(i + " : " + ex.getStackTrace()[i]);
//                }
//                LogX.w("Cause : ");
//                for (int i=0;i<ex.getCause().getStackTrace().length;i++){
//                    LogX.w( i + " : " + ex.getCause().getStackTrace()[i]);
//                }
//                defaultHandler.uncaughtException(thread,ex);
//            }
//        });

//        String str = "a";
//        LogX.d("str : " + str.charAt(2));
    }

    public void onClick(View view){
        String s="";
        String userId;
        switch (view.getId()) {
            case R.id.crash:
                String str = "a";
                LogX.d("str : " + str.charAt(2));
                break;

            case R.id.login:
                s = "登录中...";
                progressBar.setVisibility(View.VISIBLE);
                FacehubApi.getApi().login(BaseApplication.USER_ID, BaseApplication.AUTH_TOKEN, new ResultHandlerInterface() {
                    @Override
                    public void onResponse(Object o) {
                        textView.setText("登录成功!");
                        showToast("登录成功",true);
                        emoticonKeyboardView.refresh();
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Exception e) {
                        textView.setText("登录失败!\n" + e);
                        showToast("登录失败",true);
                        progressBar.setVisibility(View.GONE);
                    }
                }, new ProgressInterface() {
                    @Override
                    public void onProgress(double v) {
                        LogX.fastLog("登录中..." + v);
                        textView.setText("登录中..." + v + "%");
                    }
                });
                break;
            case R.id.show_keyboard:
                userId = FacehubApi.getApi().getUser().getUserId();
                if(userId==null || userId.equals("")){
                    showToast("请先登录!",true);
                    return;
                }
                textView.setText("显示键盘");
                emoticonKeyboardView.show();
                break;
            case R.id.hide_keyboard:
                userId = FacehubApi.getApi().getUser().getUserId();
                if(userId==null || userId.equals("")){
                    showToast("请先登录!",true);
                    return;
                }
                textView.setText("隐藏键盘");
                emoticonKeyboardView.hide();
                break;
            case R.id.logout:
                LogX.fastLog("退出登录");
                userId = FacehubApi.getApi().getUser().getUserId();
                if(userId==null || userId.equals("")){
                    showToast("清先登录!",true);
                    return;
                }
                FacehubApi.getApi().logout();
                emoticonKeyboardView.hide();
                emoticonKeyboardView.refresh();
                textView.setText("已退出.");
                showToast("退出成功!",true);
                break;
        }
    }

    private void showToast(CharSequence content,boolean isLong){
        toast.cancel();
        if(isLong) {
            toast = Toast.makeText(context, content, Toast.LENGTH_LONG);
        }else {
            toast = Toast.makeText(context, content, Toast.LENGTH_SHORT);
        }
        toast.show();
    }
}
