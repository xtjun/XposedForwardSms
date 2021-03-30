package com.xtjun.xpForwardSms.ui.app;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class ApplicationModule {

    @Singleton
    @Binds
    abstract Context provideContext(ForwardSmsApplication application);
}
