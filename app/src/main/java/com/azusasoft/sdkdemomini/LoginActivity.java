package com.azusasoft.sdkdemomini;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
    private View loginBtn1,loginBtn2,jumpBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.context = this;
        loginBtn1 = findViewById(R.id.login1);
        loginBtn2 = findViewById(R.id.login2);
        jumpBtn  = findViewById(R.id.jump);

        progressBar = findViewById(R.id.progress_bar);
        progressBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        String userId = FacehubApi.getApi().getUser().getUserId();
//        if (userId != null && !userId.equals("")) {
//            loginBtn1.setVisibility(View.GONE);
//            loginBtn2.setVisibility(View.GONE);
//            jumpBtn.setVisibility(View.VISIBLE);
//        }else {
//            loginBtn1.setVisibility(View.VISIBLE);
//            loginBtn2.setVisibility(View.VISIBLE);
//            jumpBtn.setVisibility(View.GONE);
//        }
    }

    public void onLoginClick(View view){
        switch (view.getId()){
            case R.id.login1:
                progressBar.setVisibility(View.VISIBLE);
                FacehubApi.getApi().login(BaseApplication.USER_ID, BaseApplication.AUTH_TOKEN, new ResultHandlerInterface() {
                    @Override
                    public void onResponse(Object o) {
                        progressBar.setVisibility(View.GONE);
                        Intent intent  = new Intent(context,MainActivity.class);
                        context.startActivity(intent);
                        finish();
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
                break;

            case R.id.login2:
                progressBar.setVisibility(View.VISIBLE);
                FacehubApi.getApi().login("73be42c0-af9d-42fc-916d-bf6588559d8f"
                        , "6a5033e05e0339849130d5780b461839"
                        , new ResultHandlerInterface() {
                    @Override
                    public void onResponse(Object o) {
                        progressBar.setVisibility(View.GONE);
                        Intent intent  = new Intent(context,MainActivity.class);
                        context.startActivity(intent);
                        finish();
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
                break;

            case R.id.jump:
                Intent intent  = new Intent(context,MainActivity.class);
                context.startActivity(intent);
//                finish();
                break;
        }
    }
}
