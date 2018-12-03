package com.sravanth.countries.CountriesList.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.annotation.NonNull;


import com.sravanth.countries.database.entities.Country;
import com.sravanth.countries.database.entities.Currency;
import com.sravanth.countries.database.entities.Language;
import com.sravanth.countries.repository.CountriesRepository;
import com.sravanth.countries.dagger2.components.ApplicationComponent;
import com.sravanth.countries.dagger2.components.DaggerApplicationComponent;
import com.sravanth.countries.dagger2.modules.ApplicationModule;

import java.util.List;

import javax.inject.Inject;

import lombok.Getter;

public class CountriesViewModel extends AndroidViewModel {



    public CountriesViewModel(@NonNull Application application) {
        super(application);

        applicationComponent = DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(getApplication())).build();
        applicationComponent.inject(this);
        countriesList = repository.getCountriesList();
        rowId = repository.getRowId();
        currenciesList = repository.getCurrenciesList();
        laguagesList = repository.getLaguagesList();
    }

    public void getCountries(String name) {
        repository.getCountriesByName(name);
    }

    public void insert(Country country) {
        repository.insert(country);
    }

    public void getCurrencies(int countryId) {
        repository.fetchCurrencies(countryId);
    }

    public void getLangugaes(int countryId)

    {
        repository.fetchLanguages(countryId);
    }

    @Getter private LiveData<List<Country>> countriesList;
    @Getter MutableLiveData<List<Currency>> currenciesList;
    @Getter MutableLiveData<List<Language>> laguagesList;
    @Getter private MutableLiveData<Long> rowId;
    @Inject CountriesRepository repository;
    private ApplicationComponent applicationComponent;
}
