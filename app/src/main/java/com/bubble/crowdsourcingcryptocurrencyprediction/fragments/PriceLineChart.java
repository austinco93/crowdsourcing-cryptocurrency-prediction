package com.bubble.crowdsourcingcryptocurrencyprediction.fragments;

import android.support.v4.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bubble.crowdsourcingcryptocurrencyprediction.DataPoint;
import com.bubble.crowdsourcingcryptocurrencyprediction.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.utils.EntryXComparator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by webert3 on 2/10/18.
 */

public class PriceLineChart extends Fragment {

    public static final SimpleDateFormat json_formatter = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat chart_formatter = new SimpleDateFormat("MM/dd");
    public static final SimpleDateFormat title_formatter = new SimpleDateFormat("MM/dd/yyyy");
    // TODO: This is unnecessary, fix this later.
    View view;
    View outer_view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Grab data passed from main activity
        HashMap<String,String> data = new HashMap<String,String>();
        Bundle b = this.getArguments();
        if(b != null && b.getSerializable("hashmap") != null) {
            data = (HashMap<String, String>) b.getSerializable("hashmap");
        }

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.price_linechart, container, false);
        outer_view = inflater.inflate(R.layout.activity_home, container, false);

        LineChart chart = (LineChart) view.findViewById(R.id.test_chart);
        configureChart(chart);
        configureAxes(chart);

        // Prep data
        LineData lineData = new LineData(style_dataset(create_entries(data)));

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
        xAxis.setValueFormatter(new MyXAxisValueFormatter());

        YAxis yAxis = chart.getAxis(YAxis.AxisDependency.LEFT);
        chart.getAxisRight().setEnabled(false);
        yAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        yAxis.setTextColor(getResources().getColor(R.color.DEFAULT_WHITE));
        yAxis.setDrawLabels(true);
        yAxis.setDrawGridLines(true);
        yAxis.setDrawLimitLinesBehindData(false);
    }

    @NonNull
    private LineDataSet create_entries(HashMap<String, String> data) {
        List<Entry> entries = new ArrayList<Entry>();
        for (Map.Entry<String, String> entry : data.entrySet()) {
            // Might need to convert key
            entries.add(new Entry(string_to_UTS(entry.getKey()), Float.parseFloat(entry.getValue()
            )));
        }

        // Have to sort the entries I think?
        Collections.sort(entries, new EntryXComparator());

        setChartTitle(entries.get(0).getX());

        return new LineDataSet(entries, "BTC");
    }


    // TODO: Broken. Ask Monsur for help :^)
    private void setChartTitle(float uts) {
        String last_date = title_formatter.format(uts);

        TextView textviewTitle = (TextView) outer_view.findViewById(R.id.btc_chart_title);
        textviewTitle.setText("BTC Closing Prices Since "+last_date);
        textviewTitle.setTextColor(getResources().getColor(R.color.WHITE));
    }

    private float string_to_UTS(String date_str) {
        Date date = null;
        try {
            date = json_formatter.parse(date_str);
        } catch (ParseException e) {
            Log.i("PRICE LINECHART", "Unable to parse date...");
        }

        return date.getTime();
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

    private class MyXAxisValueFormatter implements IAxisValueFormatter {
        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            return chart_formatter.format(new Date((long)value));
        }
    }
}

