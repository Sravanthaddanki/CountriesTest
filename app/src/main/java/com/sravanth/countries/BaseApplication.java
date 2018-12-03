package com.sravanth.countries;

import android.app.Application;

import com.sravanth.countries.dagger2.components.ApplicationComponent;
import com.sravanth.countries.dagger2.components.DaggerApplicationComponent;
import com.sravanth.countries.dagger2.modules.ApplicationModule;

import lombok.Getter;
import timber.log.Timber;

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        injectDependencies();
        initializeTimber();
    }

    private void initializeTimber() {
        if (BuildConfig.DEBUG)
            Timber.plant(new Timber.DebugTree());
    }

    private void injectDependencies() {
        applicationComponent = DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this)).build();
        applicationComponent.inject(this);
    }

    @Getter
    private ApplicationComponent applicationComponent;
}
