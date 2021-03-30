package com.xtjun.xpForwardSms.common.constant;

import com.github.xtjun.xposed.forwardSms.BuildConfig;

/**
 * Constant about 3rd app
 */
public interface Const {

    /* Alipay begin */
    String ALIPAY_PACKAGE_NAME = "com.eg.android.AlipayGphone";
    String ALIPAY_QRCODE_URI_PREFIX = "alipayqr://platformapi/startapp?saId=10000007&qrcode=";
    String ALIPAY_QRCODE_URL = "https://qr.alipay.com/fkx107028lhhzn6edtasnf3";
    /* Alipay end */

    /* Xposed SmsCode begin */
    String HOME_ACTIVITY_ALIAS = BuildConfig.APPLICATION_ID + ".HomeActivityAlias";

    String PROJECT_SOURCE_CODE_URL = "https://github.com/xtjun/XposedForwardSms";
    String PROJECT_GITHUB_LATEST_RELEASE_URL = PROJECT_SOURCE_CODE_URL + "/releases/latest";
    /* Xposed SmsCode end */

    /* Taichi begin */
    String TAICHI_PACKAGE_NAME = "me.weishu.exp";
    String TAICHI_MAIN_PAGE = "me.weishu.exp.ui.MainActivity";
    /* Taichi end */

    /* Xposed Installer begin */
    String XPOSED_PACKAGE = "de.robv.android.xposed.installer";
    // Old Xposed installer
    String XPOSED_OPEN_SECTION_ACTION = XPOSED_PACKAGE + ".OPEN_SECTION";
    String XPOSED_EXTRA_SECTION = "section";
    // New Xposed installer
    String XPOSED_ACTIVITY = XPOSED_PACKAGE + ".WelcomeActivity";
    String XPOSED_EXTRA_FRAGMENT = "fragment";
    /* Xposed Installer end */

    /* CoolApk */
    String COOL_MARKET_PACKAGE_NAME = "com.coolapk.market";
    /* CoolApk end */


    /* channel */
    String CHANNEL_DEFAULT = "GET";
    String CHANNEL_GET = "GET";
    String CHANNEL_POST = "POST";
    String CHANNEL_DING = "DING";
    String CHANNEL_BARK = "BARK";
    String CHANNEL_WXCP = "WXCP";

    String POST_TYPE_JSON = "JSON";
    String POST_TYPE_FROM = "FORM";
    String POST_TYPE_DEFAULT = "JSON";
    /* channel end */

}
