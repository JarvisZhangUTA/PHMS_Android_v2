package com.cse6324.dialog;

/**
 * Created by gaolin on 4/26/17.
 */

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.text.method.PasswordTransformationMethod;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.cse6324.phms.LoginActivity;
import com.cse6324.phms.R;
import com.cse6324.phms.SetLockActivity;
import com.cse6324.service.MyApplication;
import com.cse6324.util.UserUtil;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.cse6324.adapter.ShareContactListAdapter;
import com.cse6324.http.Constant;
import com.cse6324.http.HttpUtil;
import com.cse6324.phms.R;
import com.cse6324.phms.SetLockActivity;
import com.cse6324.service.MyApplication;
import com.cse6324.util.UserUtil;
import com.rengwuxian.materialedittext.MaterialEditText;

import cn.finalteam.okhttpfinal.BaseHttpRequestCallback;
import okhttp3.Headers;
import okhttp3.Response;

/**
 * Created by Jarvis on 2017/4/26.
 */

public class ChangePasswordDialog {
    MaterialDialog dialog;
    MaterialEditText newPassword;
    Context context;
    TextView submit, cancel;
    public ChangePasswordDialog(final Context context) {
        this.context = context;
        dialog = new MaterialDialog
                .Builder(context)
                .customView(R.layout.dialog_change_password, false)
                .title("Change Password")
                .build();

        submit = (TextView) dialog.getCustomView().findViewById(R.id.submit);
        cancel = (TextView) dialog.getCustomView().findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        final MaterialEditText current_pass = (MaterialEditText) dialog.getCustomView().findViewById(R.id.current_password);
        newPassword = (MaterialEditText) dialog.getCustomView().findViewById(R.id.new_password);
        final MaterialEditText repeat_pass = (MaterialEditText) dialog.getCustomView().findViewById(R.id.repeat_password);
        current_pass.setTypeface(Typeface.DEFAULT);
        current_pass.setTransformationMethod(new PasswordTransformationMethod());
        newPassword.setTypeface(Typeface.DEFAULT);
        newPassword.setTransformationMethod(new PasswordTransformationMethod());
        repeat_pass.setTypeface(Typeface.DEFAULT);
        repeat_pass.setTransformationMethod(new PasswordTransformationMethod());

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(current_pass.getText().toString().isEmpty()
                        || newPassword.getText().toString().isEmpty()
                        || repeat_pass.getText().toString().isEmpty()){
                    Toast.makeText(context,"Input invalid! ",Toast.LENGTH_LONG).show();
                }else if(!current_pass.getText().toString().equals(MyApplication.getPreferences(UserUtil.PASSWORD))){
                    Toast.makeText(context,"Password is not correct!",Toast.LENGTH_LONG).show();
                }else if(!newPassword.getText().toString().equals(repeat_pass.getText().toString())){
                    Toast.makeText(context,"Enter the password twice inconsistent!",Toast.LENGTH_LONG).show();
                }else{
                    new HttpUtil(HttpUtil.NORMAL_PARAMS)
                            .add("uid",MyApplication.getPreferences(UserUtil.UID))
                            .add("token",MyApplication.getPreferences(UserUtil.TOKEN))
                            .add("password",newPassword.getText().toString())
                            .get(Constant.URL_CHANGEPASSWORD,callback);
                }

            }
        });



    }

    public void show() {
        dialog.show();
    }

    private BaseHttpRequestCallback callback = new BaseHttpRequestCallback() {
        @Override
        public void onResponse(Response httpResponse, String response, Headers headers) {


            if (response == null || response.length() == 0) {
                Toast.makeText(context, "Connect fail", Toast.LENGTH_SHORT).show();
            } else if (headers.get("Status-Code").equals("1")) {
                Toast.makeText(context, headers.get("summary"), Toast.LENGTH_SHORT).show();
                MyApplication.setStringPref(UserUtil.PASSWORD,newPassword.getText().toString() );
                dialog.dismiss();

            } else {
                Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
            }

        }
    };
}


