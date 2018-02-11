package com.bubble.crowdsourcingcryptocurrencyprediction;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.bubble.crowdsourcingcryptocurrencyprediction.model.RetrievedPredictionEntry;
import com.bubble.crowdsourcingcryptocurrencyprediction.model.UserPredictionEntry;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
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
}
