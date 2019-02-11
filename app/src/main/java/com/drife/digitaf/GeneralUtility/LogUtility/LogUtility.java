package com.drife.digitaf.GeneralUtility.LogUtility;

import android.util.Log;

public class LogUtility {

    private static boolean enable = true;
    public static int errorLog = 0;
    public static int debugLog = 1;
    public static int infoLog = 2;
    public static int warningLog = 3;
    public static String key = "logging";

    public static void logging(String tag, int type, String title, String log){
        if (enable){
            if(type == errorLog){
                Log.e(key,tag+" : "+title+" : "+log);
            } else if (type == debugLog){
                Log.d(key,tag+" : "+title+" : "+log);
            } else if (type == infoLog){
                Log.i(key,tag+" : "+title+" : "+log);
            } else if (type == warningLog){
                Log.w(key,tag+" : "+title+" : "+log);
            }
        }
    }

    public static void logging(String tag, int type, String title, String subTitle, String log){
        if (enable){
            if(type == errorLog){
                Log.e(key,tag+" : "+title+" : "+subTitle+" : "+log);
            } else if (type == debugLog){
                Log.d(key,tag+" : "+title+" : "+subTitle+" : "+log);
            } else if (type == infoLog){
                Log.i(key,tag+" : "+title+" : "+subTitle+" : "+log);
            } else if (type == warningLog){
                Log.w(key,tag+" : "+title+" : "+subTitle+" : "+log);
            }
        }
    }
}
