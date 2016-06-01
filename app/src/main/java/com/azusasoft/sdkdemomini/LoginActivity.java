package com.azusasoft.sdkdemomini;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.MotionEvent;
import android.view.View;

import com.azusasoft.facehubcloudsdk.activities.BaseActivity;
import com.azusasoft.facehubcloudsdk.api.FacehubApi;
import com.azusasoft.facehubcloudsdk.api.ProgressInterface;
import com.azusasoft.facehubcloudsdk.api.ResultHandlerInterface;
import com.azusasoft.facehubcloudsdk.api.utils.LogX;
import com.azusasoft.sdkdemomini.framework.BaseApplication;

/**
 * Created by SETA on 2016/6/1.
 */
public class LoginActivity extends BaseActivity {
    private Context context;
    private View progressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.context = this;
        progressBar = findViewById(R.id.progress_bar);
        progressBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        String userId = FacehubApi.getApi().getUser().getUserId();
        if (userId != null && !userId.equals("")) {
            Intent intent  = new Intent(context,MainActivity.class);
            context.startActivity(intent);
        }
    }

    public void onLoginClick(View view){
        progressBar.setVisibility(View.VISIBLE);
        FacehubApi.getApi().login(BaseApplication.USER_ID, BaseApplication.AUTH_TOKEN, new ResultHandlerInterface() {
            @Override
            public void onResponse(Object o) {
                progressBar.setVisibility(View.GONE);
                Intent intent  = new Intent(context,MainActivity.class);
                context.startActivity(intent);
            }

            @Override
            public void onError(Exception e) {
                progressBar.setVisibility(View.GONE);
            }
        }, new ProgressInterface() {
            @Override
            public void onProgress(double v) {
                LogX.fastLog("登录中..." + v);
            }
        });
    }
}
