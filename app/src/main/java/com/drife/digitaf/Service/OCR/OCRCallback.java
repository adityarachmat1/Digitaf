package com.drife.digitaf.Service.OCR;

import com.drife.digitaf.retrofitmodule.Model.CarModel;
import com.drife.digitaf.retrofitmodule.Model.OCRResult;

import java.util.List;

public interface OCRCallback {
    void onSuccessOCR(OCRResult ocrResult);
    void onFailedOCR(String message);
}
