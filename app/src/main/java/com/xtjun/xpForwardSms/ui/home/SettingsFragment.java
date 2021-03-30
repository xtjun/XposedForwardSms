package com.xtjun.xpForwardSms.ui.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.preference.EditTextPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import com.afollestad.materialdialogs.MaterialDialog;
import com.github.xtjun.xposed.forwardSms.BuildConfig;
import com.github.xtjun.xposed.forwardSms.R;
import com.jaredrummler.cyanea.prefs.CyaneaSettingsActivity;
import com.xtjun.xpForwardSms.common.action.entity.SmsMsg;
import com.xtjun.xpForwardSms.ui.home.action.impl.TestSmsAction;
import com.xtjun.xpForwardSms.common.constant.Const;
import com.xtjun.xpForwardSms.common.constant.MPrefConst;
import com.xtjun.xpForwardSms.common.constant.PrefConst;
import com.xtjun.xpForwardSms.common.msp.MultiProcessSharedPreferences;
import com.xtjun.xpForwardSms.common.utils.ModuleUtils;
import com.xtjun.xpForwardSms.common.utils.PackageUtils;
import com.xtjun.xpForwardSms.common.utils.SnackbarHelper;
import com.xtjun.xpForwardSms.common.utils.StringUtils;
import com.xtjun.xpForwardSms.common.utils.SystemUtil;
import com.xtjun.xpForwardSms.common.utils.XLog;
import com.xtjun.xpForwardSms.data.http.entity.ApkVersion;

import java.util.Map;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasAndroidInjector;
import dagger.android.support.AndroidSupportInjection;

/**
 * 首选项Fragment
 */
public class SettingsFragment extends PreferenceFragmentCompat implements
        Preference.OnPreferenceClickListener,
        Preference.OnPreferenceChangeListener,
        HasAndroidInjector,
        SettingsContract.View {

    static final String EXTRA_ACTION = "extra_action";
    static final String ACTION_GET_RED_PACKET = "get_red_packet";

    private HomeActivity mActivity;
    private SharedPreferences msp;
    @Inject
    DispatchingAndroidInjector<Object> androidInjector;

    @Inject
    SettingsContract.Presenter mPresenter;

    public SettingsFragment() {
    }

    public static SettingsFragment newInstance() {
        return newInstance(null);
    }

    public static SettingsFragment newInstance(String extraAction) {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        args.putString(EXTRA_ACTION, extraAction);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public AndroidInjector<Object> androidInjector() {
        return androidInjector;
    }

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActivity = (HomeActivity) requireActivity();

        mPresenter.handleArguments(getArguments());
    }

    @Override
    public void onPause() {
        super.onPause();
        String preferencesName = getPreferenceManager().getSharedPreferencesName();
        mPresenter.setPreferenceWorldWritable(preferencesName);
        mPresenter.setInternalFilesWritable();
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.settings);
        FragmentActivity activity = getActivity();
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(activity);
        msp = MultiProcessSharedPreferences.getSharedPreferences(activity, MPrefConst.SP_NAME, Context.MODE_PRIVATE);
        initMsp(sp, msp);
        // general group
        if (!ModuleUtils.isModuleEnabled()) {
            Preference enablePref = findPreference(PrefConst.KEY_ENABLE);
            enablePref.setSummary(R.string.pref_enable_summary_alt);
        }

        //device id
        onDeviceIdChange(findPreference(PrefConst.PREF_CUSTOM_DEVICE_IDENTITY), sp.getString(PrefConst.PREF_CUSTOM_DEVICE_IDENTITY, null));

        //channel
        onForwardChannelSwitched(sp.getString(PrefConst.PREF_FORWARD_CHANNEL_TYPE, Const.CHANNEL_DEFAULT));

        // version info preference
        showVersionInfo(findPreference(PrefConst.KEY_VERSION));

        //Listener
        setClickListener(this);
        setChangedListener(this);
    }

    private void setClickListener(Preference.OnPreferenceClickListener listener) {
            findPreference(PrefConst.KEY_CHOOSE_THEME).setOnPreferenceClickListener(listener);
            findPreference(PrefConst.PREF_TEST_SMS).setOnPreferenceClickListener(listener);
            findPreference(PrefConst.KEY_VERSION).setOnPreferenceClickListener(listener);
            findPreference(PrefConst.KEY_SOURCE_CODE).setOnPreferenceClickListener(listener);
            findPreference(PrefConst.KEY_DONATE_BY_ALIPAY).setOnPreferenceClickListener(listener);
    }

    private void setChangedListener(Preference.OnPreferenceChangeListener listener) {
            findPreference(PrefConst.KEY_HIDE_LAUNCHER_ICON).setOnPreferenceChangeListener(listener);
            findPreference(PrefConst.PREF_CUSTOM_DEVICE_IDENTITY).setOnPreferenceChangeListener(listener);
            findPreference(PrefConst.KEY_VERBOSE_LOG_MODE).setOnPreferenceChangeListener(listener);
            findPreference(PrefConst.PREF_FORWARD_CHANNEL_TYPE).setOnPreferenceChangeListener(listener);
            findPreference(PrefConst.PREF_CHANNEL_CONFIG_GET_URL).setOnPreferenceChangeListener(listener);
            findPreference(PrefConst.PREF_CHANNEL_CONFIG_POST_URL).setOnPreferenceChangeListener(listener);
            findPreference(PrefConst.PREF_CHANNEL_CONFIG_POST_TYPE).setOnPreferenceChangeListener(listener);
            findPreference(PrefConst.PREF_CHANNEL_CONFIG_POST_BODY).setOnPreferenceChangeListener(listener);
            findPreference(PrefConst.PREF_CHANNEL_CONFIG_DING_TOKEN).setOnPreferenceChangeListener(listener);
            findPreference(PrefConst.PREF_CHANNEL_CONFIG_BARK_URL).setOnPreferenceChangeListener(listener);
            findPreference(PrefConst.PREF_CHANNEL_CONFIG_WXCP_CORPID).setOnPreferenceChangeListener(listener);
            findPreference(PrefConst.PREF_CHANNEL_CONFIG_WXCP_AGENTID).setOnPreferenceChangeListener(listener);
            findPreference(PrefConst.PREF_CHANNEL_CONFIG_WXCP_CORPSECRET).setOnPreferenceChangeListener(listener);
            findPreference(PrefConst.PREF_CHANNEL_CONFIG_WXCP_TOUSER).setOnPreferenceChangeListener(listener);
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        switch (preference.getKey()) {
            case PrefConst.KEY_CHOOSE_THEME:
                startActivity(new Intent(mActivity, CyaneaSettingsActivity.class));
                break;
            case PrefConst.KEY_SOURCE_CODE:
                mPresenter.showSourceProject();
                break;
            case PrefConst.KEY_DONATE_BY_ALIPAY:
                donateByAlipay();
                break;
            case PrefConst.KEY_VERSION:
                mPresenter.checkUpdate();
                break;
            case PrefConst.PREF_TEST_SMS:
                testSms();
                break;
            default:
                return false;
        }
        return true;
    }


    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        String key = preference.getKey();
        putMsp(msp, key, newValue);
        switch (key) {
            case PrefConst.KEY_HIDE_LAUNCHER_ICON:
                mPresenter.hideOrShowLauncherIcon((Boolean) newValue);
                break;
            case PrefConst.KEY_VERBOSE_LOG_MODE:
                onVerboseLogModeSwitched((Boolean) newValue);
                break;
            case PrefConst.PREF_FORWARD_CHANNEL_TYPE:
                onForwardChannelSwitched((String) newValue);
                break;
            case PrefConst.PREF_CUSTOM_DEVICE_IDENTITY:
                return onDeviceIdChange(preference, (String) newValue);
            default:
                break;
        }
        return true;
    }

    private boolean onDeviceIdChange(Preference preference, String newValue) {
        EditTextPreference pref = (EditTextPreference) preference;
        if (StringUtils.isEmpty(newValue)) {
            String systemModel = SystemUtil.getSystemModel();
            pref.setSummary(systemModel);
            pref.setText(systemModel);
            return false;
        } else {
            pref.setSummary(newValue);
            return true;
        }
    }

    private void onForwardChannelSwitched(String newValue) {
        setPrefVisible(false,
                PrefConst.PREF_CHANNEL_CONFIG_DING_TOKEN,
                PrefConst.PREF_CHANNEL_CONFIG_BARK_URL,
                PrefConst.PREF_CHANNEL_CONFIG_GET_URL,
                PrefConst.PREF_CHANNEL_CONFIG_POST_URL,
                PrefConst.PREF_CHANNEL_CONFIG_POST_TYPE,
                PrefConst.PREF_CHANNEL_CONFIG_POST_BODY,
                PrefConst.PREF_CHANNEL_CONFIG_WXCP_CORPID,
                PrefConst.PREF_CHANNEL_CONFIG_WXCP_AGENTID,
                PrefConst.PREF_CHANNEL_CONFIG_WXCP_CORPSECRET,
                PrefConst.PREF_CHANNEL_CONFIG_WXCP_TOUSER
        );
        switch (newValue) {
            case Const.CHANNEL_GET:
                setPrefVisible(true,
                        PrefConst.PREF_CHANNEL_CONFIG_GET_URL
                );
                break;
            case Const.CHANNEL_POST:
                setPrefVisible(true,
                        PrefConst.PREF_CHANNEL_CONFIG_POST_URL,
                        PrefConst.PREF_CHANNEL_CONFIG_POST_TYPE,
                        PrefConst.PREF_CHANNEL_CONFIG_POST_BODY
                );
                break;
            case Const.CHANNEL_DING:
                setPrefVisible(true, PrefConst.PREF_CHANNEL_CONFIG_DING_TOKEN);

                break;
            case Const.CHANNEL_BARK:
                setPrefVisible(true, PrefConst.PREF_CHANNEL_CONFIG_BARK_URL);
                break;
            case Const.CHANNEL_WXCP:
                setPrefVisible(true,
                        PrefConst.PREF_CHANNEL_CONFIG_WXCP_CORPID,
                        PrefConst.PREF_CHANNEL_CONFIG_WXCP_AGENTID,
                        PrefConst.PREF_CHANNEL_CONFIG_WXCP_CORPSECRET,
                        PrefConst.PREF_CHANNEL_CONFIG_WXCP_TOUSER
                );
                break;
            default:
                break;
        }
    }

    private void showEnableModuleDialog() {
        new MaterialDialog.Builder(mActivity)
                .title(R.string.enable_module_title)
                .content(R.string.enable_module_message)
                .neutralText(R.string.ignore)
                .negativeText(R.string.taichi_users_notice)
                .onNegative((dialog, which) -> mActivity.onTaichiUsersNoticeSelected())
                .positiveText(R.string.edxposed_users_notice)
                .onPositive((dialog, which) -> mActivity.onEdxposedUsersNoticeSelected())
                .show();
    }

    @Override
    public void showAppAlreadyNewest() {
        SnackbarHelper.makeLong(getListView(), R.string.app_already_newest).show();
    }

    private void setPrefVisible(boolean isVisible, String... prefChannelConfigDingToken) {
        for (String key : prefChannelConfigDingToken) {
            findPreference(key).setVisible(isVisible);
        }
    }

    private void onVerboseLogModeSwitched(boolean on) {
        XLog.setLogLevel(on ? Log.VERBOSE : BuildConfig.LOG_LEVEL);
    }

    @Override
    public void showCheckError(Throwable t) {
        SnackbarHelper.makeShort(getListView(), R.string.check_update_failed).show();
    }

    @Override
    public void showUpdateDialog(ApkVersion latestVersion) {
        new MaterialDialog.Builder(mActivity)
                .title(R.string.new_version_found)
                .content(latestVersion.getVersionInfo())
                .positiveText(R.string.update_from_coolapk)
                .onPositive((dialog, which) -> mPresenter.updateFromCoolApk())
                .negativeText(R.string.update_from_github)
                .onNegative((dialog, which) -> mPresenter.updateFromGithub())
                .show();
    }

    @Override
    public void updateUIByModuleStatus(boolean moduleEnabled) {
        Preference enablePref = findPreference(PrefConst.KEY_ENABLE);
        if (moduleEnabled) {
            enablePref.setSummary(R.string.pref_enable_summary);
        } else {
            enablePref.setSummary(R.string.pref_enable_summary_alt);

            showEnableModuleDialog();
        }
    }

    private void initMsp(SharedPreferences sp, SharedPreferences msp) {
        for (Map.Entry<String, ?> entry : sp.getAll().entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            putMsp(msp, key, value);
        }
    }

    private void putMsp(SharedPreferences msp, String key, Object value) {
        if (value instanceof Boolean) {
            msp.edit().putBoolean(key, (Boolean) value).apply();
        } else if (value instanceof Long) {
            msp.edit().putLong(key, (Long) value).apply();
        } else {
            msp.edit().putString(key, (String) value).apply();
        }
    }

    private void testSms() {
        SmsMsg smsMsg = new SmsMsg().setSender(getString(R.string.pref_test_sms_sender)).setBody(getString(R.string.pref_test_sms_body));
        new Thread(new TestSmsAction(mActivity, smsMsg, msp)).start();
    }

    private void showVersionInfo(Preference preference) {
        String summary = getString(R.string.pref_version_summary, BuildConfig.VERSION_NAME, BuildConfig.VERSION_CODE);
        preference.setSummary(summary);
    }

    private void donateByAlipay() {
        PackageUtils.startAlipayDonatePage(mActivity);
    }

}
