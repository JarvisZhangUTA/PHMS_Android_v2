package com.cse6324.phms;

import com.cse6324.service.MyApplication;
import com.cse6324.util.UserUtil;

import java.util.List;

import me.zhanghai.android.patternlock.PatternUtils;
import me.zhanghai.android.patternlock.PatternView;
import me.zhanghai.android.patternlock.SetPatternActivity;

/**
 * Created by Jarvis on 2017/4/26.
 */

public class SetLockActivity extends SetPatternActivity {
    @Override
    protected void onSetPattern(List<PatternView.Cell> pattern) {
        String patternSha1 = PatternUtils.patternToSha1String(pattern);
        // TODO: Save patternSha1 in SharedPreferences.
        MyApplication.setStringPref(UserUtil.LOCK,patternSha1);
    }
}
