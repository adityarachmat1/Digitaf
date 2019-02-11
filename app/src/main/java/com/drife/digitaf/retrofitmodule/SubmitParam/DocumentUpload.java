package com.drife.digitaf.retrofitmodule.SubmitParam;

import java.io.Serializable;

public class DocumentUpload implements Serializable{
    private String code;
    private String status;
    private String promise_date;

    public DocumentUpload() {
    }

    public DocumentUpload(String code, String status, String promise_date) {
        this.code = code;
        this.status = status;
        this.promise_date = promise_date;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPromise_date() {
        return promise_date;
    }

    public void setPromise_date(String promise_date) {
        this.promise_date = promise_date;
    }
}
