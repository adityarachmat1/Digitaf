package com.drife.digitaf.KalkulatorKredit.Model.DatabaseModel;

public class PremiType {
    private String Id;
    private String Name;
    private String Description;
    private String Code;

    public PremiType() {
    }

    public PremiType(String id, String name, String description, String code) {
        Id = id;
        Name = name;
        Description = description;
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

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }
}
