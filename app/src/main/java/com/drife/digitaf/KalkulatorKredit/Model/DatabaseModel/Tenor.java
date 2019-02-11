package com.drife.digitaf.KalkulatorKredit.Model.DatabaseModel;

import java.io.Serializable;

public class Tenor implements Serializable{
    private String Id;
    private String Name;

    public Tenor() {
    }

    public Tenor(String id, String name) {
        Id = id;
        Name = name;
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

    @Override
    public String toString() {
        return Name;
    }
}
