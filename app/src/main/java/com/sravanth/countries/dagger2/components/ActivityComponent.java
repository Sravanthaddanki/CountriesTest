

package com.sravanth.countries.dagger2.components;

import com.sravanth.countries.CountriesList.CountriesActivity;
import com.sravanth.countries.CountryDetails.CountryDetailsActivity;
import com.sravanth.countries.dagger2.modules.ActivityModule;
import com.sravanth.countries.dagger2.scopes.PerActivity;

import dagger.Component;


@PerActivity
@Component( dependencies = ApplicationComponent.class, modules = ActivityModule.class )
public interface ActivityComponent
{
    void inject( CountriesActivity activity );
    void inject(CountryDetailsActivity activity );
}