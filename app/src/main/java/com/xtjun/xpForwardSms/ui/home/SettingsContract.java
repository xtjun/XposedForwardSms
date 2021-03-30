package com.xtjun.xpForwardSms.ui.home;

import android.os.Bundle;

import com.xtjun.xpForwardSms.common.mvp.BasePresenter;
import com.xtjun.xpForwardSms.common.mvp.BaseView;
import com.xtjun.xpForwardSms.data.http.entity.ApkVersion;

public interface SettingsContract {

    interface View extends BaseView {

        void showCheckError(Throwable t);

        void showUpdateDialog(ApkVersion latestVersion);

        void showAppAlreadyNewest();

        void updateUIByModuleStatus(boolean moduleEnabled);
    }

    interface Presenter extends BasePresenter<View> {

        void handleArguments(Bundle args);

        void setPreferenceWorldWritable(String preferencesName);

        void hideOrShowLauncherIcon(boolean hide);

        void showSourceProject();

        void setInternalFilesWritable();

        void checkUpdate();

        void updateFromGithub();

        void updateFromCoolApk();
    }

}
