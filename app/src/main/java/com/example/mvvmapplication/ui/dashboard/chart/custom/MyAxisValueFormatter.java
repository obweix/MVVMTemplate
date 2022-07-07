package com.example.mvvmapplication.ui.dashboard.chart.custom;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.text.DecimalFormat;

public class MyAxisValueFormatter extends ValueFormatter {
    private final DecimalFormat mFormat;

    public MyAxisValueFormatter() {
        mFormat = new DecimalFormat("###,###,###,##0.0");
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        return mFormat.format(value) + " $";
    }

    @Override
    public String getAxisLabel(float value, AxisBase axis) {
        return mFormat.format(value) + " $";
    }
}
