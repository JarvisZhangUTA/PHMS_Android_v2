package com.cse6324.dialog;

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

/**
 * Created by Jarvis on 2017/4/26.
 */

public class PasswordDialog {
    MaterialDialog dialog;
    EditText editText;

    public PasswordDialog(final Context context, final boolean open, final Switch pin) {

        dialog = new MaterialDialog
                .Builder(context)
                .customView(R.layout.dialog_password, false)
                .title("Password")
                .positiveText("Submit")
                .onPositive(
                        new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                EditText editText = (EditText) dialog.getCustomView().findViewById(R.id.edit);

                                if (editText.getText().toString().equals(
                                        MyApplication.getPreferences(UserUtil.PASSWORD))) {
                                    if (open) {
                                        Intent intent = new Intent(context, SetLockActivity.class);
                                        context.startActivity(intent);
                                        dialog.dismiss();
                                    } else {
                                        MyApplication.setStringPref(UserUtil.LOCK, null);
                                    }
                                } else {
                                    Toast.makeText(context, "Wrong password", Toast.LENGTH_LONG).show();
                                    pin.setChecked(!open);
                                }
                            }
                        }
                )
                .build();
    }

    public void show() {
        dialog.show();
    }
}
