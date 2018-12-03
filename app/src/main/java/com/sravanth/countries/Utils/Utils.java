package com.sravanth.countries.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;



public class Utils {

    public static final String APP_ROOT_FOLDER_PATH = "countries";
    public static final String APP_FLAGS_FOLDER_PATH=APP_ROOT_FOLDER_PATH+"/"+"flags";


    public static boolean isNetworkAvailable(Context context) {
        boolean isConnected = false;
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivity.getActiveNetworkInfo();

        if (activeNetwork != null) {
            if (activeNetwork.isConnected()) {
                isConnected = true;
            }
        }
        return isConnected;
    }
}
