

package com.sravanth.countries.wrappers;

import android.app.Activity;
import androidx.annotation.NonNull;
import android.view.View;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.ButterKnife.Action;
import butterknife.ButterKnife.Setter;


public class ButterknifeWrapper {

    public void bind(@NonNull final Activity activity) {
        ButterKnife.bind(activity);
    }


    public void bind(@NonNull final Object target, @NonNull final View source) {
        ButterKnife.bind(target, source);
    }

    public <T extends View, V> void apply(@NonNull List<T> list, @NonNull Setter<? super T, V> setter, V value )
    {
        ButterKnife.apply( list, setter, value );
    }

    public <T extends View, V> void apply(@NonNull List<T> list, @NonNull Action<? super T> action )
    {
        ButterKnife.apply( list, action );
    }
}