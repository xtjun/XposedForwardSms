package com.xtjun.xpForwardSms.ui.app;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {
        AndroidSupportInjectionModule.class,
        ApplicationModule.class,
        FragmentBindingModule.class,
})
public interface ApplicationComponent extends AndroidInjector<ForwardSmsApplication> {

    @Component.Factory
    interface Factory extends AndroidInjector.Factory<ForwardSmsApplication> {

    }

}
