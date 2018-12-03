package com.sravanth.countries.Utils;

import androidx.annotation.Nullable;
import android.text.Editable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

final public class StringUtil {
    private StringUtil() {
        // prevent instantiation
    }


    public static boolean isNullOrWhitespace( @Nullable final String value )
    {
        return value == null || value.trim().isEmpty() || value.trim().equalsIgnoreCase( NULL );
    }

    private static final String NULL = "null";
}