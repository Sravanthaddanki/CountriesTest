
package com.sravanth.countries.dagger2.components;

import android.content.res.Resources;


import com.google.gson.Gson;
import com.sravanth.countries.wrappers.AndroidWrapper;
import com.sravanth.countries.BaseApplication;
import com.sravanth.countries.wrappers.ButterknifeWrapper;
import com.sravanth.countries.CountriesList.viewmodel.CountriesViewModel;
import com.sravanth.countries.repository.CountriesRepository;
import com.sravanth.countries.wrappers.Navigator;
import com.sravanth.countries.Network.NetworkService;
import com.sravanth.countries.dagger2.modules.ApplicationModule;
import com.sravanth.countries.database.AppDatabase;
import com.sravanth.countries.wrappers.SVGWrapper;


import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Component;
import io.reactivex.Scheduler;
import okhttp3.OkHttpClient;



@Singleton
@Component( modules = ApplicationModule.class )
public interface ApplicationComponent
{
    void inject(BaseApplication application);
    

    void inject (CountriesViewModel application);
    NetworkService getNetworkService();
    
    @Named( "io" )
    Scheduler getIoScheduler();
    
    @Named( "mainThread" )
    Scheduler getMainThreadScheduler();
    


    Gson getGson();

    OkHttpClient provideOkHttpClient();

    Resources provideResources();

    AppDatabase provideAppDatabase();

    CountriesRepository providerCountriesRepository();

    ButterknifeWrapper getButterknifeWrapper();


    @Named( "errorDrawable" )
    int errorDrawable();

    AndroidWrapper getAndroidWrapper();

    Navigator getNavigator();

    SVGWrapper getSVGWrapper();
}