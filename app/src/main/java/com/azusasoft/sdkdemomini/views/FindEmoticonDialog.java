package com.azusasoft.sdkdemomini.views;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.azusasoft.facehubcloudsdk.api.FacehubApi;
import com.azusasoft.facehubcloudsdk.api.ResultHandlerInterface;
import com.azusasoft.sdkdemomini.R;

public class FindEmoticonDialog extends DialogFragment {
    private Context mContext;
    private FacehubApi api;
    private ResultHandlerInterface resultHandlerInterface;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity() , AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.edit_text_diaolog, null);
        EditText editText = (EditText)view.findViewById(R.id.keyword_text);
        builder.setView(view)
                //.setMessage("Message")
                //.setTitle("Title")
                .setPositiveButton("确认", new PositiveFindBtnClickListener( editText ))
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        return builder.create();
    }

    public void setResultHandlerInterface(ResultHandlerInterface resultHandlerInterface) {
        this.resultHandlerInterface = resultHandlerInterface;
    }

    class PositiveFindBtnClickListener implements DialogInterface.OnClickListener{
        private EditText editText;
        PositiveFindBtnClickListener(EditText editText){
            this.editText = editText;
        }
        @Override
        public void onClick(DialogInterface dialog, int which) {
            //添加列表
            if(editText.getText()==null){
                resultHandlerInterface.onError(new Exception("无法查询!"));
                return;
            }
            resultHandlerInterface.onResponse(editText.getText()+"");
        }
    }

}