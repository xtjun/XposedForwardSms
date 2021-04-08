package com.xtjun.xpForwardSms.common.utils;

import android.content.SharedPreferences;

import com.xtjun.xpForwardSms.common.constant.PrefConst;


public class SPUtils {

    /**
     * 总开关是否打开
     */
    public static boolean isEnabled(SharedPreferences preferences) {
        return preferences.getBoolean(PrefConst.KEY_ENABLE, true);
    }

    /**
     * 日志模式是否是verbose log模式
     */
    public static boolean isVerboseLogMode(SharedPreferences preferences) {
        return preferences.getBoolean(PrefConst.KEY_VERBOSE_LOG_MODE, false);
    }

    /**
     * 获取设备标识
     */
    public static String getDeviceId(SharedPreferences preferences) {
        return preferences.getString(PrefConst.PREF_CUSTOM_DEVICE_IDENTITY, android.os.Build.MODEL);
    }

    /**
     * 推送通道选择
     */
    public static String getForwardChannelType(SharedPreferences preferences) {
        return preferences.getString(PrefConst.PREF_FORWARD_CHANNEL_TYPE, "");
    }

    /**
     * 获取get的Url
     */
    public static String getGetUrl(SharedPreferences preferences) {
        return preferences.getString(PrefConst.PREF_CHANNEL_CONFIG_GET_URL, "");
    }

    /**
     * 获取post的Url
     */
    public static String getPostUrl(SharedPreferences preferences) {
        return preferences.getString(PrefConst.PREF_CHANNEL_CONFIG_POST_URL, "");
    }

    /**
     * 获取post类型
     */
    public static String getPostType(SharedPreferences preferences) {
        return preferences.getString(PrefConst.PREF_CHANNEL_CONFIG_POST_TYPE, "");
    }

    /**
     * 获取post请求内容
     */
    public static String getPostContent(SharedPreferences preferences) {
        return preferences.getString(PrefConst.PREF_CHANNEL_CONFIG_POST_BODY, "");
    }

    /**
     * 钉钉配置
     */
    public static String getDingKey(SharedPreferences preferences) {
        return preferences.getString(PrefConst.PREF_CHANNEL_CONFIG_DING_TOKEN, "");
    }

    /**
     * Bark配置
     */
    public static String getBarkUrl(SharedPreferences preferences) {
        return preferences.getString(PrefConst.PREF_CHANNEL_CONFIG_BARK_URL, "");
    }

    /**
     * 企业微信企业ID
     */
    public static String getWxCorpid(SharedPreferences preferences) {
        return preferences.getString(PrefConst.PREF_CHANNEL_CONFIG_WXCP_CORPID, "");
    }
    /**
     * 企业微信应用AgentId
     */
    public static String getWxAgentid(SharedPreferences preferences) {
        return preferences.getString(PrefConst.PREF_CHANNEL_CONFIG_WXCP_AGENTID, "");
    }
    /**
     * 企业微信应用Secret
     */
    public static String getWxCorpsecret(SharedPreferences preferences) {
        return preferences.getString(PrefConst.PREF_CHANNEL_CONFIG_WXCP_CORPSECRET, "");
    }
    /**
     * 企业微信推送UID
     */
    public static String getWxTouser(SharedPreferences preferences) {
        return preferences.getString(PrefConst.PREF_CHANNEL_CONFIG_WXCP_TOUSER, "");
    }

}