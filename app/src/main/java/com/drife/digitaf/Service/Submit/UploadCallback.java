package com.drife.digitaf.Service.Submit;

public interface UploadCallback {
    void onSuccessUpload();
    void onFailedUpload();
    void onLoopEnd();
    void onResultError();
}
