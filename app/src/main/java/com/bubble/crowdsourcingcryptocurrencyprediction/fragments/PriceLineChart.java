package com.bubble.crowdsourcingcryptocurrencyprediction.fragments;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.bubble.crowdsourcingcryptocurrencyprediction.DataPoint;
import com.bubble.crowdsourcingcryptocurrencyprediction.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.EntryXComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by webert3 on 2/10/18.
 */

public class PriceLineChart extends Fragment {

    private static final ArrayList<DataPoint> myList = new ArrayList<DataPoint>() {{
        add(new DataPoint(1,3));
        add(new DataPoint(4,10));
        add(new DataPoint(2,5));
    }};


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.price_linechart, container, false);

        LineChart chart = (LineChart) view.findViewById(R.id.test_chart);

        configureChart(chart);
        configureAxes(chart);

        // Prep data
        LineData lineData = new LineData(style_dataset(create_entries()));

        // Draw data to chart.
        chart.setData(lineData);
        chart.invalidate();

        return view;
    }

    private void configureChart(LineChart chart) {
        chart.setBackgroundColor(getResources().getColor(R.color.md_blue_grey_800));
        Description d = new Description();
        d.setText("Current Price");
        d.setTextColor(getResources().getColor(R.color.DEFAULT_WHITE));
        d.setTextSize(13);
        chart.setDescription(d);
    }

    private void configureAxes(LineChart chart) {
        XAxis xAxis = chart.getXAxis();
        xAxis.setDrawGridLines(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextColor(getResources().getColor(R.color.DEFAULT_WHITE));

        YAxis yAxis = chart.getAxis(YAxis.AxisDependency.LEFT);
        chart.getAxisRight().setEnabled(false);
        yAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        yAxis.setTextColor(getResources().getColor(R.color.DEFAULT_WHITE));
        yAxis.setDrawLabels(true);
        yAxis.setDrawGridLines(true);
        yAxis.setDrawLimitLinesBehindData(false);
    }

    @NonNull
    private LineDataSet create_entries() {
        List<Entry> entries = new ArrayList<Entry>();
        for (DataPoint dp : myList) {
            entries.add(new Entry(dp.x, dp.y));
        }

        // Have to sort the entries I think?
        Collections.sort(entries, new EntryXComparator());
        return new LineDataSet(entries, "BTC");
    }

    private LineDataSet style_dataset(LineDataSet dataSet) {
        dataSet.setColor(Color.GRAY);
        dataSet.setCircleColor(Color.GRAY);
        dataSet.setLineWidth(1f);
        dataSet.setCircleRadius(3f);
        dataSet.setDrawCircleHole(false);
        dataSet.setValueTextSize(9f);
        dataSet.setDrawFilled(true);
        dataSet.setDrawValues(false);

        return dataSet;
    }
}

