package com.xtjun.xpForwardSms.xp.hook.battery;

import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;

import com.github.xtjun.xposed.forwardSms.BuildConfig;
import com.xtjun.xpForwardSms.common.utils.XLog;
import com.xtjun.xpForwardSms.xp.hook.BaseHook;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class BatteryHook extends BaseHook {
    public static final String BATTERY_PACKAGE = "android.content.Intent";
    public static final String BATTERY_METHOD = "getIntExtra";
    private static final String SELF_PACKAGE = BuildConfig.APPLICATION_ID;
    private Context mPhoneContext;
    private Context mAppContext;

    @Override
    public void onLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) {
        XposedHelpers.findAndHookMethod(
                BATTERY_PACKAGE,
                lpparam.classLoader,
                BATTERY_METHOD,
                String.class,
                int.class,
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) {
                        Intent intent = (Intent) param.thisObject;
                        final String action = intent.getAction();
                        if (action.equals(Intent.ACTION_BATTERY_CHANGED)) {
                            if (BatteryManager.EXTRA_LEVEL.equals(param.args[0] + "")) {
                                //XLog.d("------BatteryHook：level：" + param.getResult());
                                //param.setResult(1);
                            } else if (BatteryManager.EXTRA_STATUS.equals(param.args[0] + "")) {
                                //XLog.d("------BatteryHook：status：" + param.getResult());
                                //param.setResult(BatteryManager.BATTERY_STATUS_NOT_CHARGING);
                            }
                        }
                    }

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) {
                        if (mPhoneContext == null /*|| mAppContext == null*/) {
                            mPhoneContext = (Context) param.args[1];
                            try {
                                mAppContext = mPhoneContext.createPackageContext(SELF_PACKAGE, Context.CONTEXT_IGNORE_SECURITY);
                            } catch (Exception e) {
                                XLog.e("Create app context failed: %s", e);
                            }
                        }
                    }
                }
        );
    }
}
