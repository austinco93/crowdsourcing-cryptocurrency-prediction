package com.bubble.crowdsourcingcryptocurrencyprediction.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bubble.crowdsourcingcryptocurrencyprediction.PredictionActivity;
import com.bubble.crowdsourcingcryptocurrencyprediction.PredictionResultActivity;
import com.bubble.crowdsourcingcryptocurrencyprediction.R;
import com.bubble.crowdsourcingcryptocurrencyprediction.model.RetrievedPredictionEntry;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
        public TextView title, textviewPoint, textViewTimeRemaining, textViewDueDate;
        public ImageView image;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            textViewTimeRemaining = (TextView) view.findViewById(R.id.textviewTimeRemaining);
            textviewPoint = (TextView) view.findViewById(R.id.textviewPoint);
            textViewDueDate = (TextView) view.findViewById(R.id.textviewDueDate);
            image = (ImageView) view.findViewById(R.id.coin_image);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int itemPosition = getLayoutPosition();
                    Log.d("tonmoy", itemPosition + ":");
                    Intent intent = new Intent(context, PredictionActivity.class);
                    intent.putExtra("data", mList.get(itemPosition));

                    if (mList.get(itemPosition).isExpired.equalsIgnoreCase("true")) {
                        intent = new Intent(context, PredictionResultActivity.class);
                        intent.putExtra("data", mList.get(itemPosition));
                    }
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
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        switch (holder.getItemViewType()) {
            case TITLE:
                ((MyTitleViewHolder) holder).title.setText(mList.get(position).mCryptoSet);
                break;
            case DESC:
                RetrievedPredictionEntry entry = mList.get(position);
                ((MyViewHolder) holder).title.setText(entry.text);
                String dateFormat = "MM/dd/yyyy HH:mm";
                long timeInMilliseconds = (long) entry.expireTimeUTC;
                long crtTime = (long) entry.createdTimeUTC;
                double ratio = (timeInMilliseconds - crtTime) / crtTime;
                double point = entry.maxPoint * ratio;

                Date now = new Date();
                final long remainingTime = timeInMilliseconds - now.getTime();


                final String remainingStr;
                if (remainingTime <= 0) {
                    remainingStr = "Expired";
                } else {
                    remainingStr = DateUtils.formatElapsedTime(remainingTime / 1000);
                }
                //String remaining1 = DateUtils.formatElapsedTime((then.getTime() - now.getTime()) / 1000); // Remaining time to seconds

                ((MyViewHolder) holder).textViewTimeRemaining.setText(remainingStr);
                ((MyViewHolder) holder).textViewDueDate.setText(getDate(timeInMilliseconds, dateFormat));
                ((MyViewHolder) holder).textviewPoint.setText(entry.maxPoint + "");
                ((MyViewHolder) holder).image.setImageResource(map.get(entry.currency));
                new CountDownTimer(remainingTime, 1000) {

                    public void onTick(long millisUntilFinished) {
                        long Days = millisUntilFinished / (24 * 60 * 60 * 1000);
                        long Hours = millisUntilFinished / (60 * 60 * 1000) % 24;
                        long Minutes = millisUntilFinished / (60 * 1000) % 60;
                        long Seconds = millisUntilFinished / 1000 % 60;
                        String str = "";
                        ((MyViewHolder) holder).textViewTimeRemaining.setTextColor(Color.BLACK);
                        if (Seconds > 0) {
                            str += String.format("%02d S\n", Seconds);
                            ((MyViewHolder) holder).textViewTimeRemaining.setTextColor(ContextCompat.getColor(context, R.color.less_opaque_red));
                        }
                        if (Minutes > 0) {
                            str += String.format("%02d mm\n", Minutes);
                            ((MyViewHolder) holder).textViewTimeRemaining.setTextColor(ContextCompat.getColor(context, R.color.less_opaque_red));
                        }
                        if (Hours > 0) {
                            str += String.format("%02d h\n", Hours);
                            ((MyViewHolder) holder).textViewTimeRemaining.setTextColor(ContextCompat.getColor(context, R.color.AMBER));
                            if (Hours < 6) {
                                ((MyViewHolder) holder).textViewTimeRemaining.setTextColor(ContextCompat.getColor(context, R.color.less_opaque_red));
                            }

                        }
                        if (Days > 0) {
                            // MM/dd/yyyy HH:mm
                            ((MyViewHolder) holder).textViewTimeRemaining.setTextColor(ContextCompat.getColor(context, R.color.GREEN));
                            str += String.format("%02d d\n", Days);
                        }
                        ((MyViewHolder) holder).textViewTimeRemaining.setText(str);

                        //here you can have your logic to set text to edittext
                    }

                    public void onFinish() {
                        ((MyViewHolder) holder).textViewTimeRemaining.setText("Expired");
                        ((MyViewHolder) holder).textViewTimeRemaining.setTextColor(Color.RED);
                        mList.get(position).isExpired = "true";
                    }

                }.start();

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