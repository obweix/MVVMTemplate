package com.example.mvvmapplication.ui.dashboard.chart;

import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.mvvmapplication.R;
import com.example.mvvmapplication.databinding.FragmentChartBinding;
import com.example.mvvmapplication.ui.base.BaseFragment;
import com.example.mvvmapplication.ui.dashboard.chart.custom.DayAxisValueFormatter;
import com.example.mvvmapplication.ui.dashboard.chart.custom.MyAxisValueFormatter;
import com.example.mvvmapplication.ui.dashboard.chart.custom.XYMarkerView;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.*;
import com.qmuiteam.qmui.widget.QMUISlider;

import java.util.ArrayList;
import java.util.List;


public class ChartFragment extends BaseFragment implements SeekBar.OnSeekBarChangeListener, OnChartValueSelectedListener {
    private FragmentChartBinding mBinding;
    private QMUISlider mSlider;

    private BarChart chart;
    private SeekBar seekBarX, seekBarY;
    private TextView tvX, tvY;

    protected Typeface tfRegular;
    protected Typeface tfLight;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentChartBinding.inflate(inflater, container, false);
        mSlider = mBinding.slider;

        mSlider.setCallback(new QMUISlider.Callback() {
            @Override
            public void onProgressChange(QMUISlider slider, int progress, int tickCount, boolean fromUser) {
                slider.setCurrentProgress(progress);
                mBinding.textView.setText(String.valueOf(progress));
            }

            @Override
            public void onTouchDown(QMUISlider slider, int progress, int tickCount, boolean hitThumb) {
                mBinding.textView.setText(String.valueOf(progress));
            }

            @Override
            public void onTouchUp(QMUISlider slider, int progress, int tickCount) {
                mBinding.textView.setText(String.valueOf(progress));
            }

            @Override
            public void onStartMoving(QMUISlider slider, int progress, int tickCount) {
                mBinding.textView.setText(String.valueOf(progress));
            }

            @Override
            public void onStopMoving(QMUISlider slider, int progress, int tickCount) {
                mBinding.textView.setText(String.valueOf(progress));
            }


        });

        // MPAndroidChart
        tvX = mBinding.tvXMax;
        tvY = mBinding.tvYMax;

        seekBarX = mBinding.seekBar1;
        seekBarY = mBinding.seekBar2;

        seekBarY.setOnSeekBarChangeListener(this);
        seekBarX.setOnSeekBarChangeListener(this);

        chart = mBinding.chart1;
        chart.setOnChartValueSelectedListener(this);

        chart.setDrawBarShadow(false);
        chart.setDrawValueAboveBar(true);

        chart.getDescription().setEnabled(false);

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        chart.setMaxVisibleValueCount(60);

        // scaling can now only be done on x- and y-axis separately
        chart.setPinchZoom(false);

        chart.setDrawGridBackground(false);
        // chart.setDrawYLabels(false);

        ValueFormatter xAxisFormatter = new  DayAxisValueFormatter(chart);

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTypeface(tfLight);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f); // only intervals of 1 day
        xAxis.setLabelCount(7);
        xAxis.setValueFormatter(xAxisFormatter);

        final ValueFormatter custom = new MyAxisValueFormatter();
        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setTypeface(tfLight);
        leftAxis.setLabelCount(8, false);
        leftAxis.setValueFormatter(custom);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setTypeface(tfLight);
        rightAxis.setLabelCount(8, false);
        rightAxis.setValueFormatter(custom);
        rightAxis.setSpaceTop(15f);
        rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        Legend l = chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setForm(Legend.LegendForm.SQUARE);
        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(4f);

        XYMarkerView mv = new XYMarkerView(getContext(), xAxisFormatter);
        mv.setChartView(chart); // For bounds control
        chart.setMarker(mv); // Set the marker to the chart

        // setting data
        seekBarY.setProgress(50);
        seekBarX.setProgress(12);


        return mBinding.getRoot();
    }

    private void setData(int count, float range) {

        float start = 1f;

        ArrayList<BarEntry> values = new ArrayList<>();

        for (int i = (int) start; i < start + count; i++) {
            float val = (float) (Math.random() * (range + 1));

            if (Math.random() * 100 < 25) {
                values.add(new BarEntry(i, val, getResources().getDrawable(R.drawable.star)));
            } else {
                values.add(new BarEntry(i, val));
            }
        }

        BarDataSet set1;

        if (chart.getData() != null &&
                chart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) chart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();

        } else {
            set1 = new BarDataSet(values, "The year 2017");

            set1.setDrawIcons(false);

            int startColor1 = ContextCompat.getColor(getContext(), android.R.color.holo_orange_light);
            int startColor2 = ContextCompat.getColor(getContext(), android.R.color.holo_blue_light);
            int startColor3 = ContextCompat.getColor(getContext(), android.R.color.holo_orange_light);
            int startColor4 = ContextCompat.getColor(getContext(), android.R.color.holo_green_light);
            int startColor5 = ContextCompat.getColor(getContext(), android.R.color.holo_red_light);
            int endColor1 = ContextCompat.getColor(getContext(), android.R.color.holo_blue_dark);
            int endColor2 = ContextCompat.getColor(getContext(), android.R.color.holo_purple);
            int endColor3 = ContextCompat.getColor(getContext(), android.R.color.holo_green_dark);
            int endColor4 = ContextCompat.getColor(getContext(), android.R.color.holo_red_dark);
            int endColor5 = ContextCompat.getColor(getContext(), android.R.color.holo_orange_dark);

//            List<Fill> gradientFills = new ArrayList<>();
//            gradientFills.add(new Fill(startColor1, endColor1));
//            gradientFills.add(new Fill(startColor2, endColor2));
//            gradientFills.add(new Fill(startColor3, endColor3));
//            gradientFills.add(new Fill(startColor4, endColor4));
//            gradientFills.add(new Fill(startColor5, endColor5));

//            set1.setFills(gradientFills);

            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);

            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);
            data.setValueTypeface(tfLight);
            data.setBarWidth(0.9f);

            chart.setData(data);
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        tvX.setText(String.valueOf(seekBarX.getProgress()));
        tvY.setText(String.valueOf(seekBarY.getProgress()));

        setData(seekBarX.getProgress(), seekBarY.getProgress());
        chart.invalidate();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    private final RectF onValueSelectedRectF = new RectF();

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        if (e == null)
            return;

        RectF bounds = onValueSelectedRectF;
        chart.getBarBounds((BarEntry) e, bounds);
        MPPointF position = chart.getPosition(e, YAxis.AxisDependency.LEFT);

        Log.i("bounds", bounds.toString());
        Log.i("position", position.toString());

        Log.i("x-index",
                "low: " + chart.getLowestVisibleX() + ", high: "
                        + chart.getHighestVisibleX());

        MPPointF.recycleInstance(position);
    }

    @Override
    public void onNothingSelected() {

    }
}
