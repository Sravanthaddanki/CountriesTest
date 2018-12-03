
package com.sravanth.countries.wrappers;

import android.app.Activity;
import android.content.Intent;
import androidx.annotation.NonNull;


import com.sravanth.countries.CountryDetails.CountryDetailsActivity;
import com.sravanth.countries.database.entities.Country;

import timber.log.Timber;

public class Navigator {

    public Navigator(@NonNull AndroidWrapper androidWrapper) {
        this.androidWrapper = androidWrapper;

    }

    public void navigateToDetails(@NonNull Activity activity, @NonNull Country country)
    {
        Timber.d( "navigating to vehicle info screen" );
        final Intent intent = androidWrapper.createIntent( activity, CountryDetailsActivity.class );
        intent.putExtra( "country", country);
        activity.startActivity( intent );
    }


    private final AndroidWrapper androidWrapper;

}