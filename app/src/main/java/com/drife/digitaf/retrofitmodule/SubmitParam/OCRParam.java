package com.drife.digitaf.retrofitmodule.SubmitParam;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OCRParam implements Serializable {
    @SerializedName("image")
    private String ImageParam;

    @SerializedName("optimized")
    private String Optimized;

    public OCRParam() {
    }

    public OCRParam(String imageParam, String optimized) {
        ImageParam = imageParam;
        Optimized = optimized;
    }

    public String getImageParam() {
        return ImageParam;
    }

    public void setImageParam(String imageParam) {
        ImageParam = imageParam;
    }

    public String getOptimized() {
        return Optimized;
    }

    public void setOptimized(String optimized) {
        Optimized = optimized;
    }
}
