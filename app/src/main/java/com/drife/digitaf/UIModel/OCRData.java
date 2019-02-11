package com.drife.digitaf.UIModel;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

public class OCRData implements Serializable{
    private List<HashMap<String,String>> data;
    private HashMap<String,String> dataHashMap = new HashMap<>();

    public OCRData() {
    }

    public OCRData(List<HashMap<String, String>> data) {
        this.data = data;
    }

    public List<HashMap<String, String>> getData() {
        return data;
    }

    public void setData(List<HashMap<String, String>> data) {
        this.data = data;
    }

    public HashMap<String, String> getDataHashMap() {
        return dataHashMap;
    }

    public void setDataHashMap(HashMap<String, String> dataHashMap) {
        this.dataHashMap = dataHashMap;
    }
}
