package com.azusasoft.sdkdemomini;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

//import com.azusasoft.facehubcloudsdk.activities.ListsManageActivityNew;
import com.azusasoft.facehubcloudsdk.api.FacehubApi;
import com.azusasoft.facehubcloudsdk.api.LocalEmoPackageParseException;
import com.azusasoft.facehubcloudsdk.api.ResultHandlerInterface;
import com.azusasoft.facehubcloudsdk.api.models.Emoticon;
import com.azusasoft.facehubcloudsdk.api.models.Image;
import com.azusasoft.facehubcloudsdk.api.utils.LogX;
import com.azusasoft.facehubcloudsdk.views.EmoticonKeyboardView;
import com.azusasoft.facehubcloudsdk.views.EmoticonSendListener;
import com.azusasoft.facehubcloudsdk.views.OnDeleteListener;
import com.azusasoft.facehubcloudsdk.views.viewUtils.GifViewFC;
import com.azusasoft.sdkdemomini.views.FindEmoticonDialog;

public class MainActivity extends FragmentActivity {

    private TextView textView;
    private Context context;
    private EmoticonKeyboardView emoticonKeyboardView;
    private View progressBar,relateEmoContainer;
    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogX.fastLog("Main onCreate.");
        this.context = this;
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.log);
        progressBar = findViewById(R.id.progress_bar);
        relateEmoContainer = findViewById(R.id.relate_emo);
        toast = Toast.makeText(context, "toast", Toast.LENGTH_SHORT);
        relateEmoContainer.setVisibility(View.GONE);

        /** ======================== 表情键盘使用范例 ======================== */
        emoticonKeyboardView = (EmoticonKeyboardView) findViewById(R.id.emo_keyboard);
        /**
         * 一、
         * 键盘初始化参数说明 : {@link EmoticonKeyboardView#initKeyboard(boolean, String, View.OnClickListener)}
         *              1.是否有本地预置表情
         *              2.键盘右下角发送按钮的配色(RGB值)，可设置为空
         *              3.键盘右下角发送按钮的点击回调，可设置为空
         * */
        emoticonKeyboardView.initKeyboard(true, "#467fff", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String string = "点击发送按钮,发送消息.";
                textView.setText(string);
                showToast(string, false);
            }
        });
//        emoticonKeyboardView.initKeyboard(false,null,null);

        /**
         * 二、
         * 配置本地预置表情
         * 参数说明 : {@link EmoticonKeyboardView#loadEmoticonFromLocal(int, String, boolean)}
         *                      1.配置文件版本号;
         *                      2.配置文件，在assets文件夹内的具体路径;
         *                      3.是否允许图文混排，若设置为true,则在显示本地预置表情时，显示键盘内的【删除按钮】和【发送按钮】;
         *                      4.抛出异常 : {@link LocalEmoPackageParseException} 配置JSON解析出错时抛出异常;
         */
        try {
            emoticonKeyboardView.loadEmoticonFromLocal(1, "emoticonDescription.json", true);
        } catch (LocalEmoPackageParseException e) {
            Log.e(Constants.TAG, "解析预置表情 配置Json出错 : " + e);
            e.printStackTrace();
        }

        /**
         * 三、
         * 点击键盘内删除按钮的回调;
         * 如果允许图文混排，请设置此回调
         */
        emoticonKeyboardView.setOnDeleteListener(new OnDeleteListener() {
            @Override
            public void onDelete() {
                //删除输入框中的内容
                textView.setText("");
                showToast("点击删除", false);
            }
        });


        /**
         * 四、
         * 设置发送按钮是否可用
         * 如果【允许图文混排】，请根据输入框的内容设置【发送按钮】的状态
         * 调用{@link EmoticonKeyboardView#setSendButtonEnabled(boolean)} ;
         */


        /**
         * 五、
         * 点击表情后的回调
         * 可根据 {@link Emoticon#isLocal()} 来区分是否是预存的表情
         * 可根据{@link Emoticon#getFilePath(Image.Size)} 来拿取表情文件的路径
         *      注意! : 如果是本地表情，则返回其在assets的路径,如"emoji/emoji_id_1.png",因此其实际路径应为 "assets://emoji/emoji_id_1.png"
         */
        emoticonKeyboardView.setEmoticonSendListener(new EmoticonSendListener() {
            @Override
            public void onSend(Emoticon emoticon) {
                if (emoticon.isLocal()) {
                    String s = "输入表情 : [" + emoticon.getDescription() + "]";
                    String content = s + "\n本地表情资源路径 : " + "assets://" + emoticon.getFullPath();
                    textView.setText(content);
                    showToast(s, false);
                } else {
                    String s = "发送表情 : [" + emoticon.getId() + "]";
                    String content = s + "\n表情文件路径 : " + emoticon.getThumbPath();
                    textView.setText(content);
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
                Log.d(Constants.TAG, "str : " + str.charAt(2));
                break;

//            case R.id.login:
//                s = "登录中...";
//                progressBar.setVisibility(View.VISIBLE);
//                FacehubApi.getApi().login(BaseApplication.USER_ID, BaseApplication.AUTH_TOKEN, new ResultHandlerInterface() {
//                    @Override
//                    public void onResponse(Object o) {
//                        textView.setText("登录成功!");
//                        showToast("登录成功", true);
//                        emoticonKeyboardView.refresh();
//                        progressBar.setVisibility(View.GONE);
//                    }
//
//                    @Override
//                    public void onError(Exception e) {
//                        textView.setText("登录失败!\n" + e);
//                        showToast("登录失败", true);
//                        progressBar.setVisibility(View.GONE);
//                    }
//                }, new ProgressInterface() {
//                    @Override
//                    public void onProgress(double v) {
//                        LogX.fastLog("登录中..." + v);
//                        textView.setText("登录中..." + v + "%");
//                    }
//                });
//                break;
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
                Log.v(Constants.TAG, "退出登录");
                userId = FacehubApi.getApi().getUser().getUserId();
                if (userId == null || userId.equals("")) {
                    showToast("请先登录!", true);
                    return;
                }
                FacehubApi.getApi().logout();
                emoticonKeyboardView.hide();
                emoticonKeyboardView.refresh();
                textView.setText("已退出.");
                showToast("退出成功!", true);
                Intent intent = new Intent(context, LoginActivity.class);
                context.startActivity(intent);
                finish();
                break;
            case R.id.find:
//                String keyword = "0";
//                textView.setText("查找表情 【"+keyword+"】");
//                Emoticon emoticon = FacehubApi.getApi().findEmoticonByDescription(keyword);
//                textView.setText("查找表情 【"+keyword +"】 result : " + emoticon);
//                FacehubApi.getDbHelper().export();
                FindEmoticonDialog dialog = new FindEmoticonDialog();
                dialog.setResultHandlerInterface(new ResultHandlerInterface() {
                    @Override
                    public void onResponse(Object response) {
                        String keyword = (String) response;
                        textView.setText("查找表情 【" + keyword + "】");
                        Emoticon emoticon = FacehubApi.getApi().findEmoticonByDescription(keyword);
                        textView.setText("查找表情 【" + keyword + "】 result : " + emoticon);
                        if(emoticon!=null){
                            relateEmoContainer.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    relateEmoContainer.setVisibility(View.GONE);
                                }
                            });
                            GifViewFC imageView = (GifViewFC) relateEmoContainer.findViewById(R.id.relate_emo_image);
                            imageView.setGifPath(emoticon.getFullPath());
                            relateEmoContainer.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onError(Exception e) {
                        showToast(e+"",true);
                    }
                });
                dialog.show(getSupportFragmentManager(),"find");
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
        if (emoticonKeyboardView.getVisibility() == View.VISIBLE) {
            emoticonKeyboardView.hide();
            return;
        }
        super.onBackPressed();
    }
}
