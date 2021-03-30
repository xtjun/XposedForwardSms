package com.xtjun.xpForwardSms.xp.hook.sms;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.github.xtjun.xposed.forwardSms.BuildConfig;
import com.xtjun.xpForwardSms.common.action.entity.SmsMsg;
import com.xtjun.xpForwardSms.common.constant.MPrefConst;
import com.xtjun.xpForwardSms.common.msp.MultiProcessSharedPreferences;
import com.xtjun.xpForwardSms.common.utils.XSPUtils;
import com.xtjun.xpForwardSms.common.utils.XLog;
import com.xtjun.xpForwardSms.xp.hook.sms.action.impl.ForwardSmsAction;
import com.xtjun.xpForwardSms.xp.hook.sms.action.impl.SmsGetAction;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ForwardSmsWorker {

    private Context mPhoneContext;
    private Context mAppContext;
    //private XSharedPreferences xsp;
    private SharedPreferences msp;
    private Intent mSmsIntent;

    private Handler mUIHandler;

    private ScheduledExecutorService mScheduledExecutor;

    ForwardSmsWorker(Context appContext, Context phoneContext, Intent smsIntent) {
        mAppContext = appContext;
        mPhoneContext = phoneContext;
        //xsp = new XSharedPreferences(BuildConfig.APPLICATION_ID);
        msp = MultiProcessSharedPreferences.getSharedPreferences(appContext, MPrefConst.SP_NAME, Context.MODE_PRIVATE);

        mSmsIntent = smsIntent;

        mUIHandler = new Handler(Looper.getMainLooper());

        mScheduledExecutor = Executors.newSingleThreadScheduledExecutor();
    }

    public ParseResult parse() {
        if (!XSPUtils.isEnabled(msp)) {
            XLog.i("XposedForwardSms disabled, exiting");
            return null;
        }

        boolean verboseLog = XSPUtils.isVerboseLogMode(msp);
        if (verboseLog) {
            XLog.setLogLevel(Log.VERBOSE);
        } else {
            XLog.setLogLevel(BuildConfig.LOG_LEVEL);
        }

        SmsGetAction smsGetAction = new SmsGetAction(null, msp);
        smsGetAction.setSmsIntent(mSmsIntent);
        ScheduledFuture<Bundle> smsParseFuture = mScheduledExecutor.schedule(smsGetAction, 0, TimeUnit.MILLISECONDS);

        SmsMsg smsMsg;
        try {
            Bundle parseBundle = smsParseFuture.get();
            if (parseBundle == null) {
                return null;
            }
            smsMsg = parseBundle.getParcelable(SmsGetAction.SMS_MSG);
        } catch (Exception e) {
            XLog.e("Error occurs when get SmsGetAction call value", e);
            return null;
        }

        // 转发 Action
        new Thread(new ForwardSmsAction(smsMsg, msp)).start();

        return buildParseResult();
    }

    private ParseResult buildParseResult() {
        ParseResult parseResult = new ParseResult();
        parseResult.setBlockSms(false);
        return parseResult;
    }
}
