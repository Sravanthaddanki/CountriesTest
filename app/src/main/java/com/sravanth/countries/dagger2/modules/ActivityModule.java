package com.sravanth.countries.dagger2.modules;

import android.app.Activity;

import com.sravanth.countries.dagger2.scopes.PerActivity;

import dagger.Module;
import dagger.Provides;


@Module
public class ActivityModule
{

    public ActivityModule(final Activity activity )
    {
        this.activity = activity;
    }
    

    @Provides
    @PerActivity
    Activity provideActivity() {
        return activity;
    }


    private final Activity activity;
}