package com.sravanth.countries.dagger2.modules;


import androidx.room.Room;
import android.content.Context;
import android.content.res.Resources;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.sravanth.countries.wrappers.AndroidWrapper;
import com.sravanth.countries.BaseApplication;
import com.sravanth.countries.BuildConfig;
import com.sravanth.countries.wrappers.ButterknifeWrapper;
import com.sravanth.countries.repository.CountriesRepository;
import com.sravanth.countries.wrappers.Navigator;
import com.sravanth.countries.Network.NetworkService;
import com.sravanth.countries.R;
import com.sravanth.countries.database.AppDatabase;
import com.sravanth.countries.wrappers.SVGWrapper;


import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;
import javax.net.ssl.HostnameVerifier;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Cache;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;




@Module
public class ApplicationModule {

    public ApplicationModule(final BaseApplication application) {
        this.application = application;
    }


    @Provides
    @Singleton
    Context provideApplication() {
        return application;
    }


    @Provides
    @Singleton
    HttpLoggingInterceptor provideHttpLoggingInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        if (!"release".equalsIgnoreCase(BuildConfig.BUILD_TYPE)) {
            interceptor.setLevel(Level.BODY);
        } else {
            interceptor.setLevel(Level.NONE);
        }
        return interceptor;
    }

    @Provides
    @Singleton
    NetworkService provideNetworkService(final OkHttpClient client, Gson gson) {
        final Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return retrofit.create(NetworkService.class);
    }

    @Provides
    @Singleton
    Gson provideGson() {
        return new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                .create();
    }

    @Provides
    @Singleton
    @Named("io")
    Scheduler providerIoScheduler() {
        return Schedulers.io();
    }

    @Provides
    @Singleton
    @Named("mainThread")
    Scheduler providerMainThreadScheduler() {
        return AndroidSchedulers.mainThread();
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(HttpLoggingInterceptor loggingInterceptor, Cache cache) {
        HostnameVerifier hostnameVerifier = (hostname, session) -> true;
        // interceptor order is important
        return new OkHttpClient.Builder().hostnameVerifier(hostnameVerifier)
                .readTimeout(1, TimeUnit.MINUTES)
                .connectTimeout(2, TimeUnit.MINUTES)
                .cache(cache)
                .addInterceptor(loggingInterceptor)
                .build();
    }

    @Provides
    @Singleton
    Cache provideCache() {
        // 10 MB network cache
        return new Cache(application.getCacheDir(), 10 * 1024 * 1024);
    }

    @Provides
    @Singleton
    Resources provideResources() {
        return application.getResources();
    }

    @Provides
    @Singleton
    AppDatabase provideAppDatabase() {
        return Room.databaseBuilder(application, AppDatabase.class, DB_NAME).build();
    }

    @Provides
    @Singleton
    CountriesRepository provideCountriesRepository( NetworkService service, AppDatabase appDatabase, Context application )
    {
        return new CountriesRepository(service, appDatabase, application);
    }

    @Provides
    @Singleton
    ButterknifeWrapper provideButterknifeWrapper()
    {
        return new ButterknifeWrapper();
    }

    @Provides
    @Singleton
    @Named( "errorDrawable" )
    int provideErrorDrawable()
    {
        return R.mipmap.ic_launcher;
    }

    @Provides
    @Singleton
    AndroidWrapper provideAndroidWrapper()
    {
        return new AndroidWrapper();
    }

    @Provides
    @Singleton
    Navigator provideNavigator( AndroidWrapper androidWrapper )
    {
        return new Navigator( androidWrapper);
    }

    @Provides
    @Singleton
    SVGWrapper provideSVGWrapper()
    {
        return new SVGWrapper();
    }

    private final BaseApplication application;
    private final String BASE_URL = "https://restcountries.eu/rest/v2/";
    private static final String DB_NAME = "countries_db";
}