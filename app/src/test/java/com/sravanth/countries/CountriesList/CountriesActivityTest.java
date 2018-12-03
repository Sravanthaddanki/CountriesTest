package com.sravanth.countries.CountriesList;

import android.text.InputType;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;

import com.sravanth.countries.CountriesList.viewmodel.CountriesViewModel;
import com.sravanth.countries.R;
import com.sravanth.countries.Utils.BasicItemDecoration;
import com.sravanth.countries.adapters.AdapterFactory;
import com.sravanth.countries.adapters.CountriesAdapter;
import com.sravanth.countries.wrappers.AndroidWrapper;
import com.sravanth.countries.wrappers.ButterknifeWrapper;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class CountriesActivityTest {


    @Before
    public void setUp()
    {
        initMocks( this );
        initialize();
    }


    @Test
    public void testOnCreateOverride()
    {
        activity.countries = new ArrayList<>();
        when( activity.getCountriesViewModel() ).thenReturn( viewModel);
        when( adapterFactory .getCountriesAdapter( new ArrayList<>())).thenReturn(adapter);
        when( androidWrapper.newLinearLayoutManager(activity)).thenReturn(mock( LinearLayoutManager.class));
        when( adapterFactory.getBasicItemDecoration(activity, false)).thenReturn(mock( BasicItemDecoration.class ));
        doCallRealMethod().when( activity ).onCreateOverride();
        activity.onCreateOverride();
        verify( butterknifeWrapper ).bind( activity );
        verify( activity ).injectDependencies();
        verify( activity ).setContentView( R.layout.activity_countries );

        verify( countries_list_view ).setAdapter(adapter);
        verify( countries_list_view ).setLayoutManager(androidWrapper.newLinearLayoutManager(activity));
        verify(countries_list_view ).addItemDecoration(adapterFactory.getBasicItemDecoration(activity, false));
    }

    @Test
    public void testOnClearClicked()
    {
        doCallRealMethod().when( activity ).onClearClicked();
        activity.onClearClicked();

        verify(clear ).setVisibility(View.GONE);
        verify(searchView ).setText("");
    }


    private void initialize()
    {
        activity = mock( CountriesActivity.class );
        activity.butterknifeWrapper = butterknifeWrapper;
        activity.searchView = searchView;
        activity.countries_list_view = countries_list_view;
        activity.clear = clear;
        activity.adapterFactory = adapterFactory;
        activity.androidWrapper = androidWrapper;
        activity.butterknifeWrapper = butterknifeWrapper;
        activity.adapter = adapter;
        activity.viewModel = viewModel;
        activity.androidWrapper = androidWrapper;


    }

    private CountriesActivity activity;
    @Mock private EditText searchView;
    @Mock private RecyclerView countries_list_view;
    @Mock private ImageView clear;
    @Mock private AdapterFactory adapterFactory;
    @Mock private AndroidWrapper androidWrapper;
    @Mock private ButterknifeWrapper butterknifeWrapper;
    @Mock private CountriesAdapter adapter;
    @Mock private CountriesViewModel viewModel;
}