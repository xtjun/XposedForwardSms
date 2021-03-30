package com.xtjun.xpForwardSms.ui.app;

import android.content.res.Resources;

import com.jaredrummler.cyanea.Cyanea;
import com.jaredrummler.cyanea.CyaneaResources;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;

public class ForwardSmsApplication extends DaggerApplication {

    private CyaneaResources mResources = null;

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerApplicationComponent.factory().create(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Cyanea.init(this, super.getResources());
        if (!Cyanea.getInstance().isThemeModified()) {
            Cyanea.getInstance().edit()
                    .baseTheme(Cyanea.BaseTheme.LIGHT)
                    .apply();
        }

//        installDefaultEventBus();
//        initNotificationChannel();
//        performTransitionTask();
    }

    @Override
    public Resources getResources() {
        if (Cyanea.isInitialized()) {
            if (mResources == null) {
                mResources = new CyaneaResources(super.getResources(), Cyanea.getInstance());
            }
            return mResources;
        }
        return super.getResources();
    }

}
