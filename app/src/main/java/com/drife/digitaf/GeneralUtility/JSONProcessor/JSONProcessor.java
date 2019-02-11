package com.drife.digitaf.GeneralUtility.JSONProcessor;

import com.google.gson.Gson;

import java.util.List;

public class JSONProcessor {
    public static String toJSON(Object o){
        return new Gson().toJson(o);
    }

    public static String toJSON(List list){
        return new Gson().toJson(list);
    }
}
