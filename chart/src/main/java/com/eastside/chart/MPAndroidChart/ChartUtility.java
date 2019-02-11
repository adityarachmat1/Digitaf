package com.eastside.chart.MPAndroidChart;

import com.eastside.chart.MPAndroidChart.Model.ChartData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;

public class ChartUtility {

    public static ArrayList<BarDataSet> getDataSet(ArrayList<ChartData> data) {
        ArrayList<BarDataSet> dataSets = new ArrayList<>();
        for (int i=0; i<data.size(); i++){
            ChartData chartData = data.get(i);
            String title = chartData.getTitle();
            ArrayList<BarEntry> setList = chartData.getData();
            int color = chartData.getColor();
            BarDataSet barDataSet = new BarDataSet(setList, title);
            barDataSet.setColor(color);
            dataSets.add(barDataSet);
        }
        return dataSets;
    }

    public static ArrayList<String> getXAxisValues(ArrayList<String> xAxisData) {
        ArrayList<String> xAxis = new ArrayList<>();
        for(int i=0; i<xAxisData.size(); i++){
            xAxis.add(xAxisData.get(i));
        }
        return xAxis;
    }
}
