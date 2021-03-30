package com.xtjun.xpForwardSms.ui.home.action.impl;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.widget.Toast;

import com.github.xtjun.xposed.forwardSms.R;
import com.xtjun.xpForwardSms.common.action.RunnableAction;
import com.xtjun.xpForwardSms.common.action.entity.SmsMsg;
import com.xtjun.xpForwardSms.common.constant.Const;
import com.xtjun.xpForwardSms.common.utils.HttpUtils;
import com.xtjun.xpForwardSms.common.utils.SPUtils;
import com.xtjun.xpForwardSms.common.utils.StringUtils;
import com.xtjun.xpForwardSms.common.utils.XLog;


/**
 * 记录验证码短信
 */
public class TestSmsAction extends RunnableAction {
    Context context;
    public TestSmsAction(Context context, SmsMsg smsMsg, SharedPreferences sp) {
        super(smsMsg, sp);
        this.context = context;
    }

    @Override
    public Bundle action() {
        forwardSmsMsg(mSmsMsg);
        return null;
    }

    private void forwardSmsMsg(SmsMsg smsMsg) {
        String channelType = SPUtils.getForwardChannelType(sp);
        XLog.d("start forward: " + channelType);
        String title = "来自" + smsMsg.getSender() + "的新消息";
        String content = smsMsg.getBody() + "\n--来自设备：【" + SPUtils.getDeviceId(sp) + "】";
        try {
            boolean suc = false;
            switch (channelType) {
                case Const.CHANNEL_GET:
                    suc = HttpUtils.custGet(SPUtils.getGetUrl(sp), title, content);
                    break;
                case Const.CHANNEL_POST:
                    suc = HttpUtils.custPost(SPUtils.getPostUrl(sp), SPUtils.getPostType(sp), SPUtils.getPostContent(sp), title, content);
                    break;
                case Const.CHANNEL_DING:
                    suc = HttpUtils.postDingTalk(SPUtils.getDingKey(sp), title, content);
                    break;
                case Const.CHANNEL_BARK:
                    suc = HttpUtils.getBark(SPUtils.getBarkUrl(sp), title, content);
                    break;
                case Const.CHANNEL_WXCP:
                    long now = System.currentTimeMillis();
                    long expDate = sp.getLong("wxcp_expDate", 0L);
                    String token = sp.getString("excp_token", "");
                    if (now > (expDate + 3600000)){
                       String wxcpToken = HttpUtils.getWxcpToken(SPUtils.getWxCorpid(sp), SPUtils.getWxCorpsecret(sp));
                        if (StringUtils.isNotEmpty(wxcpToken)) {
                            token = wxcpToken;
                            sp.edit().putLong("wxcp_expDate", now).putString("excp_token", wxcpToken).apply();
                        }
                    }
                    suc = HttpUtils.postWxcpMsg(token,SPUtils.getWxAgentid(sp),SPUtils.getWxTouser(sp),title,content);
                    break;
                default:
                    break;
            }
            Looper.prepare();
            Toast.makeText(context, suc ? R.string.pref_test_sms_suc : R.string.pref_test_sms_err, Toast.LENGTH_LONG).show();
            Looper.loop();
        } catch (Exception e) {
            Looper.prepare();
            Toast.makeText(context, R.string.pref_test_sms_err, Toast.LENGTH_LONG).show();
            Looper.loop();
        }
    }
}
