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
import com.azusasoft.facehubcloudsdk.api.models.Image;
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
        toast = Toast.makeText(context, "toast", Toast.LENGTH_SHORT);

        /** ======================== 表情键盘使用范例 ======================== */
        emoticonKeyboardView = (EmoticonKeyboardView) findViewById(R.id.emo_keyboard);
        /**
         * 键盘初始化参数说明 : {@link EmoticonKeyboardView#initKeyboard(boolean, String, View.OnClickListener)}
         *              1.是否有本地预置表情
         *              2.键盘右下角发送按钮的配色(RGB值)，可设置为空
         *              3.键盘右下角发送按钮的点击回调，可设置为空
         * */
//        emoticonKeyboardView.initKeyboard(true, "#467fff", new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String string = "点击发送按钮,发送消息.";
//                textView.setText(string);
//                showToast(string, false);
//            }
//        });
        emoticonKeyboardView.initKeyboard(false,null,null);

        /**
         * 点击表情后的回调
         * 可根据 {@link Emoticon#isLocal()} 来区分是否是预存的表情
         * 可根据{@link Emoticon#getFilePath(Image.Size)} 来拿取表情文件的路径
         *      注意! : 如果是本地表情，则返回其文件名,如"emoji_id_1.png",因此起路径应为 "assets://emoji/emoji_id_1.png"
         */
        emoticonKeyboardView.setEmoticonSendListener(new EmoticonSendListener() {
            @Override
            public void onSend(Emoticon emoticon) {
                if(emoticon.isLocal()) {
                    String  s = "发送表情 : [" + emoticon.getDescription() + "]";
                    s+="\n本地表情资源路径 : " + "assets://emoji/" + emoticon.getFilePath(Image.Size.FULL);
                    textView.setText(s);
                    showToast(s, false);
                }else {
                    String  s = "发送表情 : [" + emoticon.getId() + "]";
                    s+="\n表情文件路径 : " + emoticon.getFilePath(Image.Size.FULL);
                    textView.setText(s);
                    showToast(s, false);
                }
            }
        });
    }

    public void onClick(View view) {
        String s = "";
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
                        showToast("登录成功", true);
                        emoticonKeyboardView.refresh();
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Exception e) {
                        textView.setText("登录失败!\n" + e);
                        showToast("登录失败", true);
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
                if (userId == null || userId.equals("")) {
                    showToast("请先登录!", true);
                    return;
                }
                textView.setText("显示键盘");
                emoticonKeyboardView.show();
                break;
            case R.id.hide_keyboard:
                userId = FacehubApi.getApi().getUser().getUserId();
                if (userId == null || userId.equals("")) {
                    showToast("请先登录!", true);
                    return;
                }
                textView.setText("隐藏键盘");
                emoticonKeyboardView.hide();
                break;
            case R.id.logout:
                LogX.fastLog("退出登录");
                userId = FacehubApi.getApi().getUser().getUserId();
                if (userId == null || userId.equals("")) {
                    showToast("清先登录!", true);
                    return;
                }
                FacehubApi.getApi().logout();
                emoticonKeyboardView.hide();
                emoticonKeyboardView.refresh();
                textView.setText("已退出.");
                showToast("退出成功!", true);
                break;
        }
    }

    private void showToast(CharSequence content, boolean isLong) {
        toast.cancel();
        if (isLong) {
            toast = Toast.makeText(context, content, Toast.LENGTH_LONG);
        } else {
            toast = Toast.makeText(context, content, Toast.LENGTH_SHORT);
        }
        toast.show();
    }

    @Override
    public void onBackPressed() {
        if(emoticonKeyboardView.getVisibility()==View.VISIBLE){
            emoticonKeyboardView.hide();
            return;
        }
        super.onBackPressed();
    }
}
