package com.bubble.crowdsourcingcryptocurrencyprediction;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.bubble.crowdsourcingcryptocurrencyprediction.adapter.PredcitionAdapter;
import com.bubble.crowdsourcingcryptocurrencyprediction.model.PredictionEntry;
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

        mAdapter = new PredcitionAdapter(mlist);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        prepareData();
    }

    private void prepareData() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("prediction_set_7_days").child("BTC");
        ref.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Get map of users in datasnapshot
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            Log.d("tonmoy", ds.getValue().toString());
                            mlist.add(ds.getValue(RetrievedPredictionEntry.class));
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
