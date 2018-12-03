package com.sravanth.countries.adapters;

import android.app.Activity;
import android.content.res.Resources;
import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.ahmadrosid.svgloader.SvgLoader;

import com.sravanth.countries.R;
import com.sravanth.countries.Utils.StringUtil;
import com.sravanth.countries.database.entities.Country;
import com.sravanth.countries.wrappers.AndroidWrapper;
import com.sravanth.countries.wrappers.ButterknifeWrapper;
import com.sravanth.countries.wrappers.Navigator;
import com.sravanth.countries.wrappers.SVGWrapper;

import java.util.List;

import butterknife.BindView;

public class CountriesAdapter extends Adapter<CountriesAdapter.CountryViewHolder> {

    CountriesAdapter(@NonNull Activity activity,
                     @NonNull List<Country> countries,
                     final int errorDrawable,
                     final int noPhotoDrawable,
                     @NonNull AndroidWrapper androidWrapper,
                     @NonNull ButterknifeWrapper butterknifeWrapper,
                     @NonNull SVGWrapper svgWrapper,
                     @NonNull Navigator navigator

    ) {
        this.activity = activity;
        this.countries = countries;
        this.noPhotoDrawable = noPhotoDrawable;
        this.butterknifeWrapper = butterknifeWrapper;
        this.svgWrapper = svgWrapper;
        this.errorDrawable = errorDrawable;
        layoutInflater = activity.getLayoutInflater();
        resources = activity.getResources();
        this.navigator = navigator;
    }

    @NonNull
    @Override
    public CountryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.list_item_countries, parent, false);
        CountryViewHolder countryViewHolder = getNewCountryViewHolder(view, butterknifeWrapper);
        countryViewHolder.countryInfoLayout.setOnClickListener(this::countryInfoSectionClicked);
        countryViewHolder.countryInfoLayout.setTag(countryViewHolder);
        return countryViewHolder;
    }

    @VisibleForTesting
    void countryInfoSectionClicked( @NonNull final View view )
    {
        CountryViewHolder holder = (CountryViewHolder) view.getTag();


        navigator.navigateToDetails( activity, holder.country);
    }

    @Override
    public int getItemCount() {
        return countries.size();
    }

    @Override
    public void onBindViewHolder(@NonNull CountryViewHolder holder, int position) {

        Country country = countries.get(position);
        holder.country = country;
        holder.countryName.setText(country.getName());


        if (!StringUtil.isNullOrWhitespace(country.getFlag())) {

            svgWrapper
                    .with(activity)
                    .setPlaceHolder(R.mipmap.ic_launcher, R.mipmap.ic_launcher)
                    .load(country.getFlag(), holder.flag);
                    //.load(activity.getFilesDir()+, this.country.getName() + \".svg\"")
        }

    }

    public void add(@NonNull Country vehicle) {
        countries.add(vehicle);
    }

    public void addAll(@NonNull List<Country> vehicles) {
        countries.addAll(vehicles);
    }

    public void clearAll() {
        countries.clear();
    }

    CountryViewHolder getNewCountryViewHolder(@NonNull View view, @NonNull ButterknifeWrapper butterknifeWrapper) {
        return new CountryViewHolder(view, butterknifeWrapper);
    }


    static class CountryViewHolder extends RecyclerView.ViewHolder {

        CountryViewHolder(@NonNull View view, @NonNull ButterknifeWrapper butterknifeWrapper) {
            super(view);
            butterknifeWrapper.bind(this, view);
        }

        @BindView(R.id.flag) ImageView flag;
        @BindView(R.id.country_name) TextView countryName;
        @BindView(R.id.country_information_section) ConstraintLayout countryInfoLayout;
        Country country;
    }

    private final Activity activity;
    private final ButterknifeWrapper butterknifeWrapper;
    private final List<Country> countries;
    private final LayoutInflater layoutInflater;
    private final int noPhotoDrawable;
    private final SVGWrapper svgWrapper;
    private final Resources resources;
    private final int errorDrawable;
    private final Navigator navigator;
}