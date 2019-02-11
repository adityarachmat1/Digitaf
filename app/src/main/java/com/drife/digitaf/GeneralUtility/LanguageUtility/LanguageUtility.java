package com.drife.digitaf.GeneralUtility.LanguageUtility;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;

import java.util.Locale;

public class LanguageUtility {
    public static void setLanguage(Context context, String language){
        //String languageToLoad  = "fa"; // your language
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            config.setLocale(locale);
        }else{
            config.locale = locale;
        }
        context.getResources().updateConfiguration(config,
                null);
    }
}
