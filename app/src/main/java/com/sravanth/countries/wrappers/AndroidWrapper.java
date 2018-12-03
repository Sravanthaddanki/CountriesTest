

package com.sravanth.countries.wrappers;


import android.content.Context;
import android.content.Intent;

import android.graphics.drawable.Drawable;


import androidx.annotation.NonNull;

import androidx.core.content.ContextCompat;

import androidx.recyclerview.widget.LinearLayoutManager;



public class AndroidWrapper
{

    public AndroidWrapper()
    {
    
    }

    @NonNull
    public Intent createIntent(@NonNull final Context context, final Class<?> clazz )
    {
        return new Intent( context, clazz );
    }


    @NonNull
    public LinearLayoutManager newLinearLayoutManager(@NonNull Context context )
    {
        return new LinearLayoutManager( context );
    }
    
    public Drawable getDrawable(@NonNull final Context context, final int resId )
    {
        return ContextCompat.getDrawable( context, resId );
    }
}