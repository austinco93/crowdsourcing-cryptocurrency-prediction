package com.bubble.crowdsourcingcryptocurrencyprediction.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bubble.crowdsourcingcryptocurrencyprediction.PredictionActivity;
import com.bubble.crowdsourcingcryptocurrencyprediction.R;
import com.bubble.crowdsourcingcryptocurrencyprediction.model.RetrievedPredictionEntry;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

/**
 * Created by hossaim3 on 2/10/2018.
 */

public class PredcitionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<RetrievedPredictionEntry> mList;
    Context context;
    HashMap<String, Integer> map = new HashMap<String, Integer>();
    private static final int TITLE = 0, DESC = 1;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, textviewPoint, textViewTimeRemaining;
        public ImageView image;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            textViewTimeRemaining = (TextView) view.findViewById(R.id.textviewTimeRemaining);
            textviewPoint = (TextView) view.findViewById(R.id.textviewPoint);
            image = (ImageView) view.findViewById(R.id.coin_image);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int itemPosition = getLayoutPosition();
                    Log.d("tonmoy", itemPosition + ":");
                    Intent intent = new Intent(context, PredictionActivity.class);
                    intent.putExtra("data", mList.get(itemPosition));
                    context.startActivity(intent);
                }
            });
        }
    }

    public class MyTitleViewHolder extends RecyclerView.ViewHolder {
        public TextView title;

        public MyTitleViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.textViewTitle);
        }
    }

    @Override
    public int getItemViewType(int position) {
        Log.d("tonmoy", " length: " + mList.get(position).mCryptoSet.length());
        return mList.get(position).mCryptoSet.length() > 0 ? TITLE : DESC;
    }

    public PredcitionAdapter(List<RetrievedPredictionEntry> moviesList, Context context) {
        this.mList = moviesList;
        this.context = context;
        map.put("BTC", R.drawable.ic_bitcoin);
        map.put("ETH", R.drawable.ic_ethereum);
        map.put("XRP", R.drawable.ic_ripple);
        map.put("LTC", R.drawable.ic_litecoin);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = null;
        switch (viewType) {
            case TITLE:
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.row_title, parent, false);
                return new MyTitleViewHolder(itemView);
            case DESC:
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.prediction_list_row, parent, false);
                return new MyViewHolder(itemView);
        }


        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case TITLE:
                ((MyTitleViewHolder) holder).title.setText(mList.get(position).mCryptoSet);
                break;
            case DESC:
                RetrievedPredictionEntry entry = mList.get(position);
                ((MyViewHolder) holder).title.setText(entry.text);
                String dateFormat = "MM/dd/yyyy HH:mm";
                long timeInMilliseconds = (long) entry.expireTimeUTC;
                ((MyViewHolder) holder).textViewTimeRemaining.setText(getDate(timeInMilliseconds, dateFormat));
                ((MyViewHolder) holder).textviewPoint.setText(entry.maxPoint+"");
                ((MyViewHolder) holder).image.setImageResource(map.get(entry.currency));
                break;
        }
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