package com.drife.digitaf.retrofitmodule.Model;

import com.google.gson.annotations.SerializedName;

public class BaseObjectResponse<T> {
    @SerializedName("DATA")
    private T data;
    @SerializedName("MESSAGE")
    private String message;
    @SerializedName("STATUS")
    private int status;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
