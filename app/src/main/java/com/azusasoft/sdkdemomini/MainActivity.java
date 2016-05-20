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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = this;
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.log);
        progressBar = findViewById(R.id.progress_bar);
        emoticonKeyboardView = (EmoticonKeyboardView) findViewById(R.id.emo_keyboard);
        emoticonKeyboardView.initKeyboard();
        emoticonKeyboardView.setEmoticonSendListener(new EmoticonSendListener() {
            @Override
            public void onSend(Emoticon emoticon) {
                textView.setText("发送表情 : " + emoticon);
                Toast.makeText(context, "发送表情 : " + emoticon.getId() , Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onClick(View view){
        String s="";
        String userId;
        switch (view.getId()) {
            case R.id.login:
                s = "登录中...";
                progressBar.setVisibility(View.VISIBLE);
                FacehubApi.getApi().login(BaseApplication.USER_ID, BaseApplication.AUTH_TOKEN, new ResultHandlerInterface() {
                    @Override
                    public void onResponse(Object o) {
                        textView.setText("登录成功!");
                        Toast.makeText(context, "登录成功", Toast.LENGTH_LONG).show();
                        emoticonKeyboardView.refresh();
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Exception e) {
                        textView.setText("登录失败!\n" + e);
                        Toast.makeText(context, "登录失败", Toast.LENGTH_LONG).show();
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
                    Toast.makeText(context, "请先登录!", Toast.LENGTH_LONG).show();
                    return;
                }
                textView.setText("显示键盘");
                emoticonKeyboardView.show();
                break;
            case R.id.hide_keyboard:
                userId = FacehubApi.getApi().getUser().getUserId();
                if(userId==null || userId.equals("")){
                    Toast.makeText(context, "请先登录!", Toast.LENGTH_LONG).show();
                    return;
                }
                textView.setText("隐藏键盘");
                emoticonKeyboardView.hide();
                break;
            case R.id.logout:
                LogX.fastLog("退出登录");
                userId = FacehubApi.getApi().getUser().getUserId();
                if(userId==null || userId.equals("")){
                    Toast.makeText(context, "请先登录!", Toast.LENGTH_LONG).show();
                    return;
                }
                FacehubApi.getApi().logout();
                emoticonKeyboardView.hide();
                emoticonKeyboardView.refresh();
                textView.setText("已退出.");
                Toast.makeText(context, "退出成功!", Toast.LENGTH_LONG).show();
                break;
        }
    }
}
