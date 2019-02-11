package com.drife.digitaf.KalkulatorKredit.Model.DatabaseModel;

public class Asuransi {
    private String Id;
    private String Name;
    private String Code;

    public Asuransi() {
    }

    public Asuransi(String id, String name, String code) {
        this.Id = id;
        Name = name;
        Code = code;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        this.Id = id;
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
