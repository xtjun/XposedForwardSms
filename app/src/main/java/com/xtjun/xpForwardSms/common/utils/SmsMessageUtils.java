package com.xtjun.xpForwardSms.common.utils;

import android.content.Intent;
import android.os.Build;
import android.provider.Telephony;
import android.telephony.SmsMessage;

import java.lang.reflect.Method;

public class SmsMessageUtils {

    private static final int SMS_CHARACTER_LIMIT = 160;
    private static final Method sGetSubId;

    static {
        Method getSubId = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            try {
                getSubId = ReflectionUtils.getDeclaredMethod(SmsMessage.class, "getSubId");
            } catch (Exception e) {
                XLog.e("Could not find SmsMessage.getSubId() method");
            }
        }
        sGetSubId = getSubId;
    }

    private SmsMessageUtils() {
    }

    public static SmsMessage[] fromIntent(Intent intent) {
        return Telephony.Sms.Intents.getMessagesFromIntent(intent);
    }

    public static String getMessageBody(SmsMessage[] messageParts) {
        if (messageParts.length == 1) {
            return messageParts[0].getDisplayMessageBody();
        } else {
            StringBuilder sb = new StringBuilder(SMS_CHARACTER_LIMIT * messageParts.length);
            for (SmsMessage messagePart : messageParts) {
                sb.append(messagePart.getDisplayMessageBody());
            }
            return sb.toString();
        }
    }

    public static int getSubId(SmsMessage message) {
        try {
            if (sGetSubId != null) {
                return (Integer)ReflectionUtils.invoke(sGetSubId, message);
            }
        } catch (Exception e) {
            XLog.e("Failed to get SMS subscription ID", e);
        }
        return 0;
    }

}
