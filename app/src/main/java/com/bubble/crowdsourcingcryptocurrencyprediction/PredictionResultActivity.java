package com.bubble.crowdsourcingcryptocurrencyprediction;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.bubble.crowdsourcingcryptocurrencyprediction.model.RetrievedPredictionEntry;
import com.bubble.crowdsourcingcryptocurrencyprediction.model.UserPredictionEntry;
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
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
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

    public void setUpChart(String[] values, ArrayList<BarEntry> yval) {
        HorizontalBarChart chart = (HorizontalBarChart) findViewById(R.id.chart);

        BarDataSet set1;
        set1 = new BarDataSet(yval, "Overall Prediction");

        set1.setColors(Color.parseColor("#F78B5D"), Color.parseColor("#FCB232"), Color.parseColor("#FDD930"), Color.parseColor("#ADD137"), Color.parseColor("#A0C25A"));

        ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
        dataSets.add(set1);

        BarData data = new BarData(dataSets);

        // hide Y-axis
        YAxis left = chart.getAxisLeft();
        left.setDrawLabels(false);

        // custom X-axis labels
        XAxis xAxis = chart.getXAxis();
        xAxis.setValueFormatter(new MyXAxisValueFormatter(values));

        chart.setData(data);

        // custom description
        Description description = new Description();
        description.setText("Rating");
        chart.setDescription(description);

        // hide legend
        chart.getLegend().setEnabled(false);

        chart.animateY(1000);
        chart.invalidate();
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
                        ArrayList<String> xAxis = new ArrayList<>();
                        ArrayList<BarEntry> valueSet1 = new ArrayList<>();
                        int i = 0;
                        for (DataSnapshot dsInner : dataSnapshot.getChildren()) {
                            xAxis.add(dsInner.getKey());
                            BarEntry v1e1 = new BarEntry(i, (int) dsInner.getChildrenCount());
                            valueSet1.add(v1e1);
                            data.put(dsInner.getKey(), (int) dsInner.getChildrenCount());
                            i++;
                        }
                        i = 0;
                        for (Map.Entry entry : data.entrySet()) {
                            allTextView[i].setText(entry.getKey() + ": " + entry.getValue() + "");
                            allTextView[i].setVisibility(View.VISIBLE);
                            i++;
                        }
                        String[] xData = new String[i];
                        for (int j = 0; j < i; j++) {
                            xData[j] = xAxis.get(j);
                        }
                        textViewTotalSubmitted.setText("Total people perticipated in the prediction: " + i);
//                        tvOption1.setText(data.get(0)+"");
//                        tvOption2.setText(data.get(0)+"");
//                        tvOption3.setText(data.get(0)+"");
//                        tvOption4.setText(data.get(0)+"");

                        setUpChart(xData, valueSet1);


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError
                    }
                });
    }


    public class MyXAxisValueFormatter implements IAxisValueFormatter {

        private String[] mValues;

        public MyXAxisValueFormatter(String[] values) {
            this.mValues = values;
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            return mValues[(int) value];
        }

    }
}
