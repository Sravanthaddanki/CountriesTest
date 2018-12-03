

package com.sravanth.countries.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;

import androidx.annotation.NonNull;

import com.sravanth.countries.database.entities.Country;
import com.sravanth.countries.Utils.BasicItemDecoration;
import com.sravanth.countries.dagger2.scopes.PerActivity;
import com.sravanth.countries.wrappers.AndroidWrapper;
import com.sravanth.countries.wrappers.ButterknifeWrapper;
import com.sravanth.countries.wrappers.Navigator;
import com.sravanth.countries.wrappers.SVGWrapper;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;


/**
 *  Factory to get an adapter
 */
@PerActivity
public class AdapterFactory {
    @Inject
    public AdapterFactory(@NonNull Activity activity,
                          @NonNull ButterknifeWrapper butterknifeWrapper,
                          @NonNull AndroidWrapper androidWrapper,
                          @NonNull Navigator navigator,
                          @NonNull SVGWrapper svgWrapper,
                          @NonNull Resources resources,
                          @Named("errorDrawable") int errorDrawable )

    {
        this.androidWrapper = androidWrapper;
        this.activity = activity;
        this.butterknifeWrapper = butterknifeWrapper;
        this.navigator = navigator;
        this.svgWrapper = svgWrapper;
        this.resources = resources;
        this.errorDrawable = errorDrawable;
    }


    /**
     * Gets an Country adapter
     * @param countries list of countries
     * @return countries adapter
     */
    @NonNull
    public CountriesAdapter getCountriesAdapter(@NonNull List<Country> countries) {
        return new CountriesAdapter(activity, countries, errorDrawable, errorDrawable, androidWrapper, butterknifeWrapper, svgWrapper, navigator);
    }

    /**
     * Gets an Item Decarator
     * @param context context
     * @param dividerAfterLastItem whthere or not to have a divider after last item
     * @return Item decorator
     */
    @NonNull
    public BasicItemDecoration getBasicItemDecoration(@NonNull Context context, boolean dividerAfterLastItem) {
        return new BasicItemDecoration(context, androidWrapper, dividerAfterLastItem);
    }

    private final Activity activity;
    private final AndroidWrapper androidWrapper;
    private final ButterknifeWrapper butterknifeWrapper;
    private final Navigator navigator;
    private final SVGWrapper svgWrapper;
    private final Resources resources;
    private final int errorDrawable;
}