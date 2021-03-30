package com.xtjun.xpForwardSms.common.constant;

import com.github.xtjun.xposed.forwardSms.BuildConfig;

/**
 * Preference相关的常量
 */
public interface PrefConst {

    String REMOTE_PREF_NAME = BuildConfig.APPLICATION_ID + "_preferences";

    // General
    String KEY_ENABLE = "pref_enable";
    String KEY_HIDE_LAUNCHER_ICON = "pref_hide_launcher_icon";
    String KEY_CHOOSE_THEME = "pref_choose_theme";
    String PREF_CUSTOM_DEVICE_IDENTITY = "pref_custom_device_identity";

    // Others
    String KEY_VERBOSE_LOG_MODE = "pref_verbose_log_mode";
    String PREF_TEST_SMS = "pref_test_sms";

    //转发通道
    String PREF_FORWARD_CHANNEL_TYPE = "pref_forward_channel_type";

    String PREF_CHANNEL_CONFIG_GET_URL = "pref_channel_config_get_url";

    String PREF_CHANNEL_CONFIG_POST_URL = "pref_channel_config_post_url";
    String PREF_CHANNEL_CONFIG_POST_TYPE = "pref_channel_config_post_type";
    String PREF_CHANNEL_CONFIG_POST_BODY = "pref_channel_config_post_body";

    String PREF_CHANNEL_CONFIG_DING_TOKEN = "pref_channel_config_ding_token";

    String PREF_CHANNEL_CONFIG_BARK_URL = "pref_channel_config_bark_url";

    String PREF_CHANNEL_CONFIG_WXCP_CORPID = "pref_channel_config_wxcp_corpid";
    String PREF_CHANNEL_CONFIG_WXCP_AGENTID = "pref_channel_config_wxcp_agentid";
    String PREF_CHANNEL_CONFIG_WXCP_CORPSECRET = "pref_channel_config_wxcp_corpsecret";
    String PREF_CHANNEL_CONFIG_WXCP_TOUSER = "pref_channel_config_wxcp_touser";

    // About
    String KEY_VERSION = "pref_version";
    String KEY_SOURCE_CODE = "pref_source_code";
    String KEY_DONATE_BY_ALIPAY = "pref_donate_by_alipay";
}
