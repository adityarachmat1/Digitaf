package com.drife.digitaf.Module.home.model;

public class FilterHari {
    private String name = "";
    private String hari = "0";

    public FilterHari() {
    }

    public FilterHari(String name, String hari) {
        this.name = name;
        this.hari = hari;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHari() {
        return hari;
    }

    public void setHari(String hari) {
        this.hari = hari;
    }

    @Override
    public String toString() {
        return getName();
    }
}
