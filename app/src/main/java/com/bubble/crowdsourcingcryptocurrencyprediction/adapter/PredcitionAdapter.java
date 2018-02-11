package com.bubble.crowdsourcingcryptocurrencyprediction.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bubble.crowdsourcingcryptocurrencyprediction.R;
import com.bubble.crowdsourcingcryptocurrencyprediction.model.RetrievedPredictionEntry;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Created by hossaim3 on 2/10/2018.
 */

public class PredcitionAdapter extends RecyclerView.Adapter<PredcitionAdapter.MyViewHolder> {

    private List<RetrievedPredictionEntry> mList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, textviewPoint, textViewTimeRemaining;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            textViewTimeRemaining = (TextView) view.findViewById(R.id.textviewTimeRemaining);
            textviewPoint = (TextView) view.findViewById(R.id.textviewPoint);
        }
    }


    public PredcitionAdapter(List<RetrievedPredictionEntry> moviesList) {
        this.mList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.prediction_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        RetrievedPredictionEntry entry = mList.get(position);
        holder.title.setText(entry.text);
        String dateFormat = "MM/dd/yyyy HH:mm";
        long timeInMilliseconds = (long) entry.expireTimeUTC;
        holder.textViewTimeRemaining.setText( getDate(timeInMilliseconds, dateFormat));
        holder.textviewPoint.setText(entry.maxPoint);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
    public static String getDate(long milliSeconds, String dateFormat) {
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }
}