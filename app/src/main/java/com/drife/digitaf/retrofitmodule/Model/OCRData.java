package com.drife.digitaf.retrofitmodule.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OCRData<T> implements Serializable {
    @SerializedName("data")
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
