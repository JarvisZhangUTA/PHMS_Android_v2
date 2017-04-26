package com.cse6324.phms;

import android.content.Intent;
import android.text.TextUtils;

import com.cse6324.service.MyApplication;
import com.cse6324.util.UserUtil;

import java.util.List;

import me.zhanghai.android.patternlock.ConfirmPatternActivity;
import me.zhanghai.android.patternlock.PatternUtils;
import me.zhanghai.android.patternlock.PatternView;

/**
 * Created by Jarvis on 2017/4/26.
 */

public class ConfirmLockActivity extends ConfirmPatternActivity {

    @Override
    protected boolean isStealthModeEnabled() {
        // TODO: Return the value from SharedPreferences.
        return false;
    }

    @Override
    protected boolean isPatternCorrect(List<PatternView.Cell> pattern) {
        boolean correct = TextUtils.equals(PatternUtils.patternToSha1String(pattern),
                MyApplication.getPreferences(UserUtil.LOCK));

        if(correct){
            Intent intent = new Intent(this,EditVitalSignsActivity.class);
            startActivity(intent);
        }

        return correct;
    }

}
