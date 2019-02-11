package com.drife.digitaf.KalkulatorKredit.Model.DatabaseModel;

public class KatagoriMobil {
    private String Id;
    private String Name;
    private String Code;

    public KatagoriMobil() {
    }

    public KatagoriMobil(String id, String name, String code) {
        Id = id;
        Name = name;
        Code = code;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }
}
