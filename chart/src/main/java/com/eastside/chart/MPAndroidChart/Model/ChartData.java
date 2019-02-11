package com.eastside.chart.MPAndroidChart.Model;

import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;

public class ChartData {
    private String title;
    private ArrayList<BarEntry> data;
    private int color;

    public ChartData() {
    }

    public ChartData(String title) {
        this.title = title;
    }

    public ChartData(String title, ArrayList<BarEntry> data) {
        this.title = title;
        this.data = data;
    }

    public ChartData(String title, ArrayList<BarEntry> data, int color) {
        this.title = title;
        this.data = data;
        this.color = color;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<BarEntry> getData() {
        return data;
    }

    public void setData(ArrayList<BarEntry> data) {
        this.data = data;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
