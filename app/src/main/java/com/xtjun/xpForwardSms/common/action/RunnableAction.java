package com.xtjun.xpForwardSms.common.action;

import android.content.SharedPreferences;

import com.xtjun.xpForwardSms.common.action.entity.SmsMsg;

/**
 * Runnable + Action + Callable
 */
public abstract class RunnableAction extends CallableAction implements Runnable {

    public RunnableAction(SmsMsg smsMsg, SharedPreferences sp) {
        super(smsMsg, sp);
    }

    @Override
    public void run() {
        call();
    }
}
