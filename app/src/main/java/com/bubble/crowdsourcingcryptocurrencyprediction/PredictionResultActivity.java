package com.bubble.crowdsourcingcryptocurrencyprediction;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bubble.crowdsourcingcryptocurrencyprediction.model.RetrievedPredictionEntry;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PredictionResultActivity extends AppCompatActivity {
    TextView tvQuestion, tvOption1, tvOption2, tvOption3, tvOption4, textViewTotalSubmitted;
    RetrievedPredictionEntry pEntry;
    TextView allTextView[] = new TextView[]{tvOption1, tvOption2, tvOption3, tvOption4};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_prediction_result);
        pEntry = (RetrievedPredictionEntry) getIntent().getSerializableExtra("data");
        tvQuestion = (TextView) findViewById(R.id.textViewQuestion);
        textViewTotalSubmitted = (TextView) findViewById(R.id.textViewTotalSubmitted);
        allTextView[0] = (TextView) findViewById(R.id.textViewOption1);
        allTextView[1] = (TextView) findViewById(R.id.textViewOption2);
        allTextView[2] = (TextView) findViewById(R.id.textViewOption3);
        allTextView[3] = (TextView) findViewById(R.id.textViewOption4);
        tvQuestion.setText(pEntry.text);
        getAllData();
    }

    private void getAllData() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference ref = database.getReference().child("all_predictions").child(pEntry.id);
        ref.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        HashMap<String, Integer> data = new HashMap<String, Integer>();
                        for (DataSnapshot dsInner : dataSnapshot.getChildren()) {
                            data.put(dsInner.getKey(), (int) dsInner.getChildrenCount());
                        }
                        int i = 0;
                        for (Map.Entry entry : data.entrySet()) {
                            allTextView[i].setText(entry.getKey() + ": " + entry.getValue() + "");
                            allTextView[i].setVisibility(View.VISIBLE);
                            i++;
                        }
                        textViewTotalSubmitted.setText("Total people perticipated in the prediction: " + i);
//                        tvOption1.setText(data.get(0)+"");
//                        tvOption2.setText(data.get(0)+"");
//                        tvOption3.setText(data.get(0)+"");
//                        tvOption4.setText(data.get(0)+"");


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError
                    }
                });
    }

    //    TextView tvQuestion, tvOption1, tvOption2, tvOption3, tvOption4, textViewTotalSubmitted;
//    TextView allTextView[] = new TextView[]{tvOption1, tvOption2, tvOption3, tvOption4};
//    RetrievedPredictionEntry pEntry;
//    String[] yLabels;
//    float max_x = 0;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.content_prediction_result);
//        pEntry = (RetrievedPredictionEntry) getIntent().getSerializableExtra("data");
//        tvQuestion = (TextView) findViewById(R.id.textViewQuestion);
//        textViewTotalSubmitted = (TextView) findViewById(R.id.textViewTotalSubmitted);
//        allTextView[0] = (TextView) findViewById(R.id.textViewOption1);
//        allTextView[1] = (TextView) findViewById(R.id.textViewOption2);
//        allTextView[2] = (TextView) findViewById(R.id.textViewOption3);
//        allTextView[3] = (TextView) findViewById(R.id.textViewOption4);
//        tvQuestion.setText(pEntry.text);
//        getAllData();
//    }

//    public void setUpChart(String[] values, ArrayList<BarEntry> yval) {
//        BarChart chart = (BarChart) findViewById(R.id.chart);
//
//        BarDataSet set1;
//        set1 = new BarDataSet(yval, "Overall Prediction");
//
//        set1.setColors(Color.parseColor("#F78B5D"), Color.parseColor("#FCB232"), Color.parseColor("#FDD930"), Color.parseColor("#ADD137"), Color.parseColor("#A0C25A"));
//        set1.setStackLabels(values);
//
//        ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
//        dataSets.add(set1);
//
//        BarData data = new BarData(dataSets);
//
//        // hide Y-axis
//        YAxis left = chart.getAxisLeft();
//        left.setDrawLabels(false);
//        YAxis right = chart.getAxisRight();
////        right.setValueFormatter(new MyYAxisValueFormatter(yLabels));
//
//        // custom X-axis labels
//        XAxis xAxis = chart.getXAxis();
//        xAxis.setAxisMinimum(0);
//        xAxis.setAxisMaximum(max_x);
//        xAxis.setValueFormatter(new MyXAxisValueFormatter(values));
//        //xAxis.setValueFormatter(new IndexAxisValueFormatter(values));
//
//        chart.setDrawValueAboveBar(false);
//        chart.setData(data);
//
//        // custom description
//        Description d = new Description();
//        d.setText(""); // Getting rid of the chart label... lol
//        chart.setDescription(d);
//        chart.getLegend().setTextColor(getResources().getColor(R.color.WHITE));
//
//        // hide legend
//        chart.getLegend().setEnabled(false);
//
//        chart.highlightValue(2,0);
//        chart.animateXY(0, 2000);
//        //chart.animateY(1000);
//        chart.invalidate();
//    }
//
//    private void getAllData() {
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
//        DatabaseReference ref = database.getReference().child("all_predictions").child(pEntry.id);
//        ref.addListenerForSingleValueEvent(
//                new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        HashMap<String, Integer> data = new HashMap<String, Integer>();
//                        ArrayList<String> xAxis = new ArrayList<>();
//                        ArrayList<BarEntry> valueSet1 = new ArrayList<>();
//                        int i = 0;
//                        for (DataSnapshot dsInner : dataSnapshot.getChildren()) {
//                            xAxis.add(dsInner.getKey());
//                            int total = (int)dsInner.getChildrenCount();
//                            max_x = (total > max_x) ? total : max_x; // Setting global max x-value
//                            BarEntry v1e1 = new BarEntry(i, total);
//                            valueSet1.add(v1e1);
//                            data.put(dsInner.getKey(), total);
//                            i++;
//                        }
//
//                        // Populate labels array
//                        yLabels = xAxis.toArray(new String[xAxis.size()]);
//
//                        i = 0;
//                        for (Map.Entry entry : data.entrySet()) {
//                            allTextView[i].setText(entry.getKey() + ": " + entry.getValue() + "");
//                            allTextView[i].setVisibility(View.VISIBLE);
//                            i++;
//                        }
//                        String[] xData = new String[i];
//                        for (int j = 0; j < i; j++) {
//                            xData[j] = xAxis.get(j);
//                        }
//                        textViewTotalSubmitted.setText("Total predictions: " + i);
//
//                        setUpChart(xData, valueSet1);
//
//
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//                        //handle databaseError
//                    }
//                });
//    }
//
//
//    public class MyXAxisValueFormatter implements IAxisValueFormatter {
//
//        private String[] mValues;
//
//        public MyXAxisValueFormatter(String[] values) {
//            this.mValues = values;
//        }
//
//        @Override
//        public String getFormattedValue(float value, AxisBase axis) {
//            return mValues[(int)value];
//            // return String.format("%.0f", value);
//        }
//    }
//
//    public class MyYAxisValueFormatter implements IAxisValueFormatter {
//
//        private String[] mValues;
//
//        public MyYAxisValueFormatter(String[] values) {
//            this.mValues = values;
//        }
//
//        @Override
//        public String getFormattedValue(float value, AxisBase axis) {
//            int idx = 0;
//            for (float f : axis.mEntries) {
//                if (f == value) {
//                    return mValues[idx];
//                }
//                idx++;
//            }
//            return String.format("%.0f", value);
//        }

//    }
}
