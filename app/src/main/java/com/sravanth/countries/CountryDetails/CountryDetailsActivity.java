package com.sravanth.countries.CountryDetails;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ahmadrosid.svgloader.SvgLoader;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.sravanth.countries.BaseApplication;
import com.sravanth.countries.R;
import com.sravanth.countries.database.entities.Country;
import com.sravanth.countries.CountriesList.viewmodel.CountriesViewModel;
import com.sravanth.countries.dagger2.components.DaggerActivityComponent;
import com.sravanth.countries.dagger2.modules.ActivityModule;
import com.sravanth.countries.database.entities.Currency;
import com.sravanth.countries.database.entities.Language;
import com.sravanth.countries.wrappers.ButterknifeWrapper;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class CountryDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        injectDependencies();

        initialize();

        setAppBarLayout();

        setDetails();
    }


    private void initialize() {
        setContentView(R.layout.activity_country_details);
        butterknifeWrapper.bind(this);
        setSupportActionBar(toolbar);
        country = getIntent().getParcelableExtra("country");
        if (country.getCurrencies() != null && !country.getCurrencies().isEmpty()) {
            isFromNetwork = true;
        }
        if( isFromNetwork )
        {
            save.show();
        }
        else
        {
            save.hide();
        }
        viewModel = ViewModelProviders.of(this).get(CountriesViewModel.class);
       viewModel.getRowId().observe(this, new Observer<Long>() {
            @Override
            public void onChanged(@Nullable Long aLong) {
                Snackbar.make(rootLayout,getString(R.string.country_inserted_successfully),Snackbar.LENGTH_SHORT ).show();
            }
        });
    }

    private void setAppBarLayout() {
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    isShow = true;
                    showOption(R.id.action_info);
                } else if (isShow) {
                    isShow = false;
                    hideOption(R.id.action_info);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        this.menu = menu;
        getMenuInflater().inflate(R.menu.menu_save, menu);
        hideOption(R.id.action_info);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        if (id == R.id.action_info) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void hideOption(int id) {
        MenuItem item = menu.findItem(id);
        item.setVisible(false);
    }

    private void showOption(int id) {
        MenuItem item = menu.findItem(id);
        item.setVisible(isFromNetwork);
    }

    private void setDetails() {
        collapsingToolbarLayout.setTitle(country.getName());

        SvgLoader.pluck()
                .with(this)
                .setPlaceHolder(R.mipmap.ic_launcher, R.mipmap.ic_launcher)
                .load(country.getFlag(), flag);
        countryName.setText(country.getName());
        region.setText(country.getRegion());
        subRegion.setText(country.getSubregion());
        capital.setText(country.getCapital());

        // if//api
        if (isFromNetwork) {
            if (country.getCallingCodesList() != null && !country.getCallingCodesList().isEmpty()) {
                StringBuilder code = new StringBuilder();
                for (String callingCode : country.getCallingCodesList()
                        ) {
                    code.append(callingCode);
                    code.append(System.getProperty("line.separator"));
                }
                callingCodes.setText(code.toString());
            }
        } else {
            callingCodes.setText(country.getCallingCodes().replace(",", System.getProperty("line.separator")));
        }

        //
        if (isFromNetwork) {
            if (country.getTimezonesList() != null && !country.getTimezonesList().isEmpty()) {
                StringBuilder timezones = new StringBuilder();
                for (String timeZone : country.getTimezonesList()
                        ) {
                    timezones.append(timeZone);
                    timezones.append(System.getProperty("line.separator"));
                    timezones.append(System.getProperty("line.separator"));
                }
                timezone.setText(timezones.toString());
            }
        } else {
            timezone.setText(country.getTimezones().replace(",", System.getProperty("line.separator")));
        }

        if (isFromNetwork) {
            setLanguageText(country.getLanguages());
            setCurrencies(country.getCurrencies());
        } else {
            viewModel.getCurrenciesList().observe(this, (@Nullable final List<Currency> currencies) -> {
                setCurrencies(currencies);
            });

            viewModel.getLaguagesList().observe(this, (@Nullable final List<Language> languages) -> {
                setLanguageText(languages);
            });

            viewModel.getLangugaes(country.getId());
            viewModel.getCurrencies(country.getId());
        }
    }

    private void setCurrencies(List<Currency> currencies) {
        if (currencies != null && !currencies.isEmpty()) {
            StringBuilder currencyString = new StringBuilder();

            for (Currency currency : currencies
                    ) {
                currencyString.append("Name  : ");
                currencyString.append(currency.getName());
                currencyString.append(System.getProperty("line.separator"));
                currencyString.append("Code  : ");
                currencyString.append(currency.getCode());
                currencyString.append(System.getProperty("line.separator"));
                currencyString.append("Symbol  : ");
                currencyString.append(currency.getSymbol());
                currencyString.append(System.getProperty("line.separator"));
                currencyString.append(System.getProperty("line.separator"));
                currencyString.append(System.getProperty("line.separator"));
            }

            currencyText.setText(currencyString);
        }
    }

    private void setLanguageText(List<Language> languages) {

        if (languages != null && !languages.isEmpty()) {
            StringBuilder languagesString = new StringBuilder();

            for (Language language : languages
                    ) {
                languagesString.append("iso639_1  : ");
                languagesString.append(language.getIso639_1());
                languagesString.append(System.getProperty("line.separator"));
                languagesString.append("iso639_2  : ");
                languagesString.append(language.getIso639_2());
                languagesString.append(System.getProperty("line.separator"));
                languagesString.append("Name  : ");
                languagesString.append(language.getName());
                languagesString.append(System.getProperty("line.separator"));
                languagesString.append("Native Name  : ");
                languagesString.append(language.getNativeName());
                languagesString.append(System.getProperty("line.separator"));
                languagesString.append(System.getProperty("line.separator"));
                languagesString.append(System.getProperty("line.separator"));
            }
            languageText.setText(languagesString);
        }
    }

    private void injectDependencies() {
        DaggerActivityComponent.builder()
                .applicationComponent(((BaseApplication) getApplication()).getApplicationComponent())
                .activityModule(new ActivityModule(this))
                .build()
                .inject(this);
    }


    @OnClick({R.id.save})
    void saveclicked() {
        viewModel.insert(country);
    }

    @Inject
    ButterknifeWrapper butterknifeWrapper;
    private CountriesViewModel viewModel;

    private Country country;


    @BindView(R.id.capital_text) TextView capital;
    @BindView(R.id.region_text) TextView region;
    @BindView(R.id.sub_region_text) TextView subRegion;
    @BindView(R.id.calling_codes_text) TextView callingCodes;
    @BindView(R.id.timezones_text) TextView timezone;
    @BindView(R.id.language_text) TextView languageText;
    @BindView(R.id.currency_text) TextView currencyText;
    @BindView(R.id.toolbar) androidx.appcompat.widget.Toolbar toolbar;
    @BindView(R.id.app_bar) AppBarLayout appBarLayout;
    @BindView(R.id.collasping_toolbar) CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.countryFlag) ImageView flag;
    @BindView(R.id.country_name_text) TextView countryName;
    @BindView(R.id.save) FloatingActionButton save;
    @BindView(R.id.rootLayout) CoordinatorLayout rootLayout;
    boolean isFromNetwork = false;
    private Menu menu;
}
