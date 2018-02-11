package com.bubble.crowdsourcingcryptocurrencyprediction;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.bubble.crowdsourcingcryptocurrencyprediction.adapter.PredcitionAdapter;
import com.bubble.crowdsourcingcryptocurrencyprediction.model.RetrievedPredictionEntry;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PredictionListActivity extends AppCompatActivity {
    private List<RetrievedPredictionEntry> mlist = new ArrayList<RetrievedPredictionEntry>();
    private RecyclerView recyclerView;
    private PredcitionAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prediction_list);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mAdapter = new PredcitionAdapter(mlist, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        // row click listener

        recyclerView.setAdapter(mAdapter);

        prepareData();
    }

    private void prepareData() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("prediction_set_7_days");
        RetrievedPredictionEntry entry = new RetrievedPredictionEntry();
        entry.mCryptoSet = "Predictions: 7 days";
        mlist.add(entry);
        ref.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Get map of users in datasnapshot
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            for (DataSnapshot dsInner : ds.getChildren()) {
                                Log.d("tonmoy", dsInner.getValue().toString());
                                mlist.add(dsInner.getValue(RetrievedPredictionEntry.class));
                            }
                        }
                        // Log.d("tonmoy", dataSnapshot.getValue().toString());
                        mAdapter.notifyDataSetChanged();
                        RetrievedPredictionEntry entry = new RetrievedPredictionEntry();
                        entry.mCryptoSet = "Predictions: 6 Hour";
                        mlist.add(entry);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError
                    }
                });

        ///////
        ref = FirebaseDatabase.getInstance().getReference().child("prediction_set_6_Hours");
        ref.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Get map of users in datasnapshot
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            for (DataSnapshot dsInner : ds.getChildren()) {
                                Log.d("tonmoy", dsInner.getValue().toString());
                                mlist.add(dsInner.getValue(RetrievedPredictionEntry.class));
                            }
                        }
                        // Log.d("tonmoy", dataSnapshot.getValue().toString());
                        mAdapter.notifyDataSetChanged();
                        RetrievedPredictionEntry entry = new RetrievedPredictionEntry();
                        entry.mCryptoSet = "Predictions: 1 Hour";
                        mlist.add(entry);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError
                    }
                });


        ///////
        ref = FirebaseDatabase.getInstance().getReference().child("prediction_set_1_Hours");
        ref.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Get map of users in datasnapshot
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            for (DataSnapshot dsInner : ds.getChildren()) {
                                Log.d("tonmoy", dsInner.getValue().toString());
                                mlist.add(dsInner.getValue(RetrievedPredictionEntry.class));

                            }
                        }
                        // Log.d("tonmoy", dataSnapshot.getValue().toString());
                        mAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError
                    }
                });
    }
}
