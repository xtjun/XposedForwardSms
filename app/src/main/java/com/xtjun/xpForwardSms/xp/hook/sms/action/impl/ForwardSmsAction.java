package com.xtjun.xpForwardSms.xp.hook.sms.action.impl;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.xtjun.xpForwardSms.common.action.RunnableAction;
import com.xtjun.xpForwardSms.common.action.entity.SmsMsg;
import com.xtjun.xpForwardSms.common.constant.Const;
import com.xtjun.xpForwardSms.common.utils.StringUtils;
import com.xtjun.xpForwardSms.common.utils.XHttpUtils;
import com.xtjun.xpForwardSms.common.utils.XLog;
import com.xtjun.xpForwardSms.common.utils.XSPUtils;

/**
 * 记录验证码短信
 */
public class ForwardSmsAction extends RunnableAction {

    public ForwardSmsAction(SmsMsg smsMsg, SharedPreferences sp) {
        super(smsMsg, sp);
    }

    @Override
    public Bundle action() {
        forwardSmsMsg(mSmsMsg);
        return null;
    }

    private void forwardSmsMsg(SmsMsg smsMsg) {
        String channelType = XSPUtils.getForwardChannelType(sp);
        XLog.d("start forward: " + channelType);
        String title = "卡"+smsMsg.getSubId()+"收到" + smsMsg.getSender() + "的新消息";
        String content = smsMsg.getBody() + "\n--来自设备：【" + XSPUtils.getDeviceId(sp) + "】";
        try {
            boolean suc = false;
            switch (channelType) {
                case Const.CHANNEL_GET:
                    suc = XHttpUtils.custGet(XSPUtils.getGetUrl(sp), title, content);
                    break;
                case Const.CHANNEL_POST:
                    suc = XHttpUtils.custPost(XSPUtils.getPostUrl(sp), XSPUtils.getPostType(sp), XSPUtils.getPostContent(sp), title, content);
                    break;
                case Const.CHANNEL_DING:
                    suc = XHttpUtils.postDingTalk(XSPUtils.getDingKey(sp), title, content);
                    break;
                case Const.CHANNEL_BARK:
                    suc = XHttpUtils.getBark(XSPUtils.getBarkUrl(sp), title, content);
                    break;
                case Const.CHANNEL_WXCP:
                    long now = System.currentTimeMillis();
                    long expDate = sp.getLong("wxcp_expDate", 0L);
                    String token = sp.getString("excp_token", "");
                    if (now > (expDate + 3600000)){
                        String wxcpToken = XHttpUtils.getWxcpToken(XSPUtils.getWxCorpid(sp), XSPUtils.getWxCorpsecret(sp));
                        if (StringUtils.isNotEmpty(wxcpToken)) {
                            token = wxcpToken;
                            sp.edit().putLong("wxcp_expDate", now).putString("excp_token", wxcpToken).apply();
                        }
                    }
                    suc = XHttpUtils.postWxcpMsg(token,XSPUtils.getWxAgentid(sp),XSPUtils.getWxTouser(sp),title,content);
                    break;
                default:
                    break;
            }
            XLog.d("forward result: " + suc);
        } catch (Exception e) {
            XLog.e("forward error: " + e.getMessage());
        }
    }
}
