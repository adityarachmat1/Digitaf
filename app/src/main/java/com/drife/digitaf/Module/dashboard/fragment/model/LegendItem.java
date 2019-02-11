package com.drife.digitaf.Module.dashboard.fragment.model;

public class LegendItem {
    private String name = "";
    private int color = 0;

    public LegendItem(String name, int color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
