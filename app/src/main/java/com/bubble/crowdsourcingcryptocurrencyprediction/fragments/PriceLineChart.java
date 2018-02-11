package com.bubble.crowdsourcingcryptocurrencyprediction.fragments;

import android.content.Context;
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
import com.bubble.crowdsourcingcryptocurrencyprediction.HomeActivity;
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
import com.github.mikephil.charting.highlight.Highlight;
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
    private PriceMarkerView mv;
    private LineChart mChart;

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
        View view = inflater.inflate(R.layout.price_linechart, container, false);

        mChart = (LineChart) view.findViewById(R.id.test_chart);
        mChart.setMarker(mv);

        configureChart();
        configureAxes();

        // Prep data
        LineData lineData = new LineData(style_dataset(create_entries(data)));

        // Draw data to chart.
        mChart.setData(lineData);
        mChart.invalidate();

        return view;
    }

    @Override
    public void onAttach(Context context) {
        mv = new PriceMarkerView(context, R.layout.price_markerview);
        super.onAttach(context);

    }

    private void configureChart() {
        mChart.setBackgroundColor(getResources().getColor(R.color.md_blue_grey_800));
        Description d = new Description();
        d.setText(""); // Getting rid of the chart label... lol
        mChart.setDescription(d);
        mChart.getLegend().setTextColor(getResources().getColor(R.color.WHITE));
    }

    private void configureAxes() {
        XAxis xAxis = mChart.getXAxis();
        xAxis.setDrawGridLines(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextColor(getResources().getColor(R.color.DEFAULT_WHITE));
        xAxis.setValueFormatter(new MyXAxisValueFormatter());

        YAxis yAxis = mChart.getAxis(YAxis.AxisDependency.LEFT);
        mChart.getAxisRight().setEnabled(false);
        yAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        yAxis.setTextColor(getResources().getColor(R.color.DEFAULT_WHITE));
        yAxis.setDrawLabels(true);
        yAxis.setDrawGridLines(true);
        yAxis.setDrawLimitLinesBehindData(false);
        yAxis.setValueFormatter(new MyYAxisValueFormatter());
    }

    @NonNull
    private LineDataSet create_entries(HashMap<String, String> data) {
        List<Entry> entries = new ArrayList<Entry>();
        for (Map.Entry<String, String> entry : data.entrySet()) {
            entries.add(new Entry(string_to_UTS(entry.getKey()), Float.parseFloat(entry.getValue())));
        }

        // Have to sort the entries I think?
        Collections.sort(entries, new EntryXComparator());
        setChartTitle(entries.get(0).getX());

        return new LineDataSet(entries, "BTC");
    }

    private void setChartTitle(float uts) {
        HomeActivity.textviewTitle.setText("BTC Closing Prices Since "+title_formatter.format(uts));
        HomeActivity.textviewTitle.setTextColor(getResources().getColor(R.color.WHITE));
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

    private class MyYAxisValueFormatter implements IAxisValueFormatter {
        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            return String.format("$%.0f", value);
        }
    }
}

