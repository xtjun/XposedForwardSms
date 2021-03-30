package com.xtjun.xpForwardSms.common.action;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.xtjun.xpForwardSms.common.action.entity.SmsMsg;
import com.xtjun.xpForwardSms.common.utils.XLog;

import java.util.concurrent.Callable;

/**
 * Action + Callable
 */
public abstract class CallableAction implements Action<Bundle>, Callable<Bundle> {

    protected SmsMsg mSmsMsg;
    protected SharedPreferences sp;

    public CallableAction(SmsMsg smsMsg, SharedPreferences sp) {
        this.mSmsMsg = smsMsg;
        this.sp = sp;
    }

    @Override
    public Bundle call() {
        try {
            return action();
        } catch (Throwable t) {
            XLog.e("Error in CallableAction#call()", t);
            return null;
        }
    }
}
