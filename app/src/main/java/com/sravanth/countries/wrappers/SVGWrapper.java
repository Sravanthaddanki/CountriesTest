package com.sravanth.countries.wrappers;

import android.app.Activity;

import android.widget.ImageView;

import com.ahmadrosid.svgloader.SvgLoader;


import androidx.annotation.NonNull;

public class SVGWrapper {

    public SVGWrapper() {
        svgLoader = SvgLoader.pluck();
    }

    public SVGWrapper with(@NonNull final Activity context) {


         svgLoader
                .with(context);
         return this;
    }

    public SVGWrapper setPlaceHolder(int placeHolderLoading, int placeHolderError) {
         svgLoader.setPlaceHolder(placeHolderLoading, placeHolderError);
         return this;
    }

    public SVGWrapper load(String url, ImageView imageView) {

         svgLoader.load(url, imageView);
         return this;
    }

    private SvgLoader svgLoader;
}
