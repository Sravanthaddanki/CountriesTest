package com.sravanth.countries.CountriesList;

import androidx.annotation.VisibleForTesting;
import androidx.lifecycle.ViewModelProviders;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;


import com.sravanth.countries.database.entities.Country;
import com.sravanth.countries.CountriesList.viewmodel.CountriesViewModel;
import com.sravanth.countries.adapters.AdapterFactory;
import com.sravanth.countries.wrappers.AndroidWrapper;
import com.sravanth.countries.BaseApplication;
import com.sravanth.countries.adapters.CountriesAdapter;
import com.sravanth.countries.R;

import com.sravanth.countries.dagger2.components.DaggerActivityComponent;
import com.sravanth.countries.dagger2.modules.ActivityModule;
import com.sravanth.countries.wrappers.ButterknifeWrapper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import timber.log.Timber;

public class CountriesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        onCreateOverride();
    }

    @VisibleForTesting
    void onCreateOverride()
    {
        injectDependencies();

        initialize();

        registerFordataChaanges();
    }


    @OnTextChanged(R.id.searchView)
    protected void onTextChanged() {

        if (searchView.getText().toString().isEmpty()) {
            clear.setVisibility(View.GONE);
        } else {
            Timber.d(" Fetching countries");
            clear.setVisibility(View.VISIBLE);
            viewModel.getCountries(searchView.getText().toString());
        }
    }

    @OnClick(R.id.clear)
    protected void onClearClicked() {
        Timber.d(" clicked clear");
        clear.setVisibility(View.GONE);
        searchView.setText("");
    }

    @VisibleForTesting
     void injectDependencies() {
        DaggerActivityComponent.builder()
                .applicationComponent(((BaseApplication) getApplication()).getApplicationComponent())
                .activityModule(new ActivityModule(this))
                .build()
                .inject(this);
    }

    private void initialize() {
        setContentView(R.layout.activity_countries);
        butterknifeWrapper.bind(this);
        viewModel = getCountriesViewModel();
        adapter = adapterFactory.getCountriesAdapter(countries);
        countries_list_view.setAdapter(adapter);
        countries_list_view.setLayoutManager(androidWrapper.newLinearLayoutManager(this));
        countries_list_view.addItemDecoration(adapterFactory.getBasicItemDecoration(this, false));
    }

    @VisibleForTesting
     CountriesViewModel getCountriesViewModel()
    {
        return ViewModelProviders.of(this).get(CountriesViewModel.class);
    }

    @VisibleForTesting
    void registerFordataChaanges() {
        viewModel.getCountriesList().observe(this, (@Nullable final List<Country> countries) -> {
            adapter.clearAll();
            adapter.addAll(countries);
            adapter.notifyDataSetChanged();
        });

    }


    @BindView(R.id.searchView) EditText searchView;
    @BindView(R.id.countries_list_view) RecyclerView countries_list_view;
    @BindView(R.id.clear) ImageView clear;
    @Inject AdapterFactory adapterFactory;
    @Inject AndroidWrapper androidWrapper;
    @Inject ButterknifeWrapper butterknifeWrapper;
    @VisibleForTesting CountriesAdapter adapter;
    @VisibleForTesting CountriesViewModel viewModel;
    List<Country> countries = new ArrayList<>();
}
