package com.drife.digitaf.UIModel;

import java.io.Serializable;
import java.util.List;

public class ListOCRData implements Serializable{
    private List<OCRData> ocrDataList;

    public ListOCRData() {
    }

    public ListOCRData(List<OCRData> ocrDataList) {
        this.ocrDataList = ocrDataList;
    }

    public List<OCRData> getOcrDataList() {
        return ocrDataList;
    }

    public void setOcrDataList(List<OCRData> ocrDataList) {
        this.ocrDataList = ocrDataList;
    }
}
