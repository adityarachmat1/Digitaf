package com.drife.digitaf.retrofitmodule.SubmitParam;

import java.io.Serializable;
import java.util.List;

public class Document implements Serializable {
    private List<DocumentUpload> required;
    private List<DocumentUpload> optional;

    public Document() {
    }

    public Document(List<DocumentUpload> required, List<DocumentUpload> optional) {
        this.required = required;
        this.optional = optional;
    }

    public List<DocumentUpload> getRequired() {
        return required;
    }

    public void setRequired(List<DocumentUpload> required) {
        this.required = required;
    }

    public List<DocumentUpload> getOptional() {
        return optional;
    }

    public void setOptional(List<DocumentUpload> optional) {
        this.optional = optional;
    }
}
