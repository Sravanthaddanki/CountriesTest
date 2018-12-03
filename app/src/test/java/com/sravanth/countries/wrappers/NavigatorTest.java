package com.sravanth.countries.wrappers;

import android.app.Activity;
import android.content.Intent;

import com.sravanth.countries.CountryDetails.CountryDetailsActivity;
import com.sravanth.countries.database.entities.Country;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import timber.log.Timber;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class NavigatorTest {

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        navigator = new Navigator(androidWrapper);
    }

    @Test
    public void testOnNavigateToDetails() {

        Country country = new Country();
        when( androidWrapper.createIntent( activity, CountryDetailsActivity.class )).thenReturn( mock(Intent.class));
        navigator.navigateToDetails( activity , country );

        final Intent intent = androidWrapper.createIntent(activity, CountryDetailsActivity.class);
        verify(intent).putExtra("country", country);
        activity.startActivity(intent);
    }

    private Navigator navigator;
    @Mock  private AndroidWrapper androidWrapper;
    @Mock private Activity activity;

}