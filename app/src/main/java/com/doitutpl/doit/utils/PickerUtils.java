package com.doitutpl.doit.utils;

import android.content.Context;

import com.doitutpl.doit.prefs.AppPreferences;
import com.kbeanie.multipicker.api.CacheLocation;

public class PickerUtils {

    public static int getSavedCacheLocation(Context context) {
        AppPreferences preferences = new AppPreferences(context);
        switch (preferences.getCacheLocation()) {
            case 0:
                return CacheLocation.EXTERNAL_STORAGE_APP_DIR;
            case 1:
                return CacheLocation.EXTERNAL_STORAGE_PUBLIC_DIR;
            case 2:
                return CacheLocation.EXTERNAL_CACHE_DIR;
            case 3:
                return CacheLocation.INTERNAL_APP_DIR;
        }
        return CacheLocation.EXTERNAL_STORAGE_APP_DIR;
    }

}
