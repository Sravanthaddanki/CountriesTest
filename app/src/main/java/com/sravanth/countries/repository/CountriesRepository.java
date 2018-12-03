package com.sravanth.countries.repository;

import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;
import androidx.annotation.NonNull;


import com.sravanth.countries.Utils.Utils;
import com.sravanth.countries.database.entities.Country;
import com.sravanth.countries.Network.NetworkService;
import com.sravanth.countries.database.AppDatabase;
import com.sravanth.countries.database.entities.Currency;
import com.sravanth.countries.database.entities.Language;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import lombok.Getter;
import okhttp3.ResponseBody;
import timber.log.Timber;

public class CountriesRepository {

    public CountriesRepository(@NonNull NetworkService service, AppDatabase appDatabase, Context baseApplication
    ) {

        this.service = service;
        this.appDatabase = appDatabase;
        this.baseApplication = baseApplication;

    }

    public void getCountriesByName(String name) {
        loadCountries(name);
    }

    private void loadCountries(String name) {

        if (Utils.isNetworkAvailable(baseApplication)) {
            service.getCountriesByName(name)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::fetchCountriesSucceeded, this::fetchCountriesFailed);
        } else {
            appDatabase.getCountriesDao().getCountriesByName(name)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::fetchCountriesSucceeded, this::fetchCountriesFailed);
        }
    }

    public void fetchCurrencies( int countryId)
    {
        appDatabase.getCurrencyDao().getCurrenciesByCountry(countryId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::fetchCurrenciesSucceeded, this::fetchCountriesFailed);
    }

    public void fetchLanguages( int countryId)
    {
        appDatabase.getLanguageDao().getLanguagesByCountry(countryId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::fetchLanguagesSucceeded, this::fetchCountriesFailed);
    }

    private void fetchCurrenciesSucceeded( @NonNull List<Currency> currencies )
    {
        currenciesList.setValue(currencies);
    }

    private void fetchLanguagesSucceeded( @NonNull List<Language> languages )
    {
        laguagesList.setValue(languages);
    }

    private void fetchCountriesSucceeded(@NonNull List<Country> countries) {
        countriesList.setValue(countries);
    }

    private void fetchCountriesFailed(@NonNull Throwable throwable) {
        Timber.d("Fetch Countries failed");
    }

    public void insert(Country country) {

        this.country = country;
        String callingCodes = "";
        String timezones = "";
        country.setFlagPath(baseApplication.getFilesDir() + "/" + country.getName() + ".svg");
        if (country.getCallingCodesList() != null && !country.getCallingCodesList().isEmpty()) {
            callingCodes = TextUtils.join(",", country.getCallingCodesList());
        }

        if (country.getTimezonesList() != null && !country.getTimezonesList().isEmpty()) {
            timezones = TextUtils.join(",", country.getTimezonesList());
        }
        country.setCallingCodes(callingCodes);
        country.setTimezones(timezones);
        appDatabase.getCountriesDao().insert(country).toObservable().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(this::insertCountrySucceded, this::insertCountryFailed);

    }

    private void insertCountrySucceded(Long id) {
        Timber.d("inserted country");
        rowId.setValue(id);
        insertLanguageCurrency(id);

        service.downloadFlagWithDynamicUrlSync(this.country.getFlag()).subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io()).subscribe(this::fetchingFlagSucceded, this::fetchingFlagFailed);
    }

    private void fetchingFlagSucceded(ResponseBody id) {

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                writeResponseBodyToDisk(id);

                return null;
            }
        }.execute();

    }

    private void fetchingFlagFailed(Throwable throwable) {

    }

    private void insertCountryFailed(Throwable throwable) {

    }

    private boolean writeResponseBodyToDisk(ResponseBody body) {
        try {

            File mydir = new File(baseApplication.getFilesDir(), this.country.getName() + ".svg");
            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(mydir);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }
                    outputStream.write(fileReader, 0, read);
                    fileSizeDownloaded += read;
                }
                outputStream.flush();

                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }

    private void insertLanguageCurrency(Long id) {
        if (this.country.getLanguages() != null && !this.country.getLanguages().isEmpty()) {

            List<Language> languageList = new ArrayList<>();
            for (Language language : this.country.getLanguages()) {

                language.setCountryId(id);
                languageList.add(language);
            }

            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    appDatabase.getLanguageDao().insertAll(languageList);

                    return null;
                }
            }.execute();
            Timber.d("inserted language");
        }

        if (this.country.getCurrencies() != null && !this.country.getCurrencies().isEmpty()) {
            List<Currency> currencyList = new ArrayList<>();
            for (Currency currency : this.country.getCurrencies()) {
                currency.setCountryId(id);
                currencyList.add(currency);
            }
            Timber.d("inserted currency");


            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    appDatabase.getCurrencyDao().insertAll(currencyList);

                    return null;
                }
            }.execute();
        }

    }

    private final NetworkService service;
    private final AppDatabase appDatabase;

    @Getter
    private MutableLiveData<List<Country>> countriesList = new MutableLiveData<>();
    @Getter
    private MutableLiveData<Long> rowId = new MutableLiveData<>();

    @Getter MutableLiveData<List<Currency>> currenciesList = new MutableLiveData<>();
    @Getter MutableLiveData<List<Language>> laguagesList = new MutableLiveData<>();
    private Country country;
    private Context baseApplication;


}
