package com.drife.digitaf.Service.Submit;

public interface SubmitCallback {
    void onSuccessSubmit(String formId);
    void onFailedSubmit(String message);
}
