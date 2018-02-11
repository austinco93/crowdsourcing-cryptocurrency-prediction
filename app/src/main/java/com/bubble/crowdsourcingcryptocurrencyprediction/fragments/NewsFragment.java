package com.bubble.crowdsourcingcryptocurrencyprediction.fragments;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.bubble.crowdsourcingcryptocurrencyprediction.DataPoint;
import com.bubble.crowdsourcingcryptocurrencyprediction.HomeActivity;
import com.bubble.crowdsourcingcryptocurrencyprediction.R;
import com.bubble.crowdsourcingcryptocurrencyprediction.utilities.CustomList;
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
import java.util.HashMap;
import java.util.List;

public class NewsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Grab data passed from main activity
        ArrayList<HashMap<String,String>> data = new ArrayList<HashMap<String,String>>();
        Bundle b = this.getArguments();
        if(b != null && b.getSerializable("test") != null) {
            data = (ArrayList<HashMap<String, String>>) b.getSerializable("test");
        }

        Log.i("frag", data.get(0).get("title"));

        String[] titles = new String[data.size()];
        for(int i = 0; i < data.size(); i++){
            titles[i] = data.get(i).get("title");
        }

        String[] images = new String[data.size()];
        for(int i = 0; i < data.size(); i++){
            images[i] = data.get(i).get("urlToImage");
            Log.i("test2", images[i]);
        }



        View view = inflater.inflate(R.layout.fragment_news, container, false);

        //ListView list = view.findViewById(R.id.newsList);


        CustomList adapter = new CustomList(getActivity(), titles, images);
        ListView list = view.findViewById(R.id.newsList);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {


            }
        });



        /*ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(
              getActivity(),
                android.R.layout.simple_list_item_1,
                titles
        );

        newsList.setAdapter(listViewAdapter);*/
        return view;
    }
}
