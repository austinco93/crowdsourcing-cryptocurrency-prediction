package com.bubble.crowdsourcingcryptocurrencyprediction;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bubble.crowdsourcingcryptocurrencyprediction.fragments.NewsFragment;
import com.bubble.crowdsourcingcryptocurrencyprediction.fragments.PriceLineChart;
import com.bubble.crowdsourcingcryptocurrencyprediction.utilities.NewsFetcherInterface;
import com.bubble.crowdsourcingcryptocurrencyprediction.utilities.ParseNews;
import com.bubble.crowdsourcingcryptocurrencyprediction.utilities.ParsePrice;
import com.bubble.crowdsourcingcryptocurrencyprediction.utilities.PriceFetcherInterface;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.EntryXComparator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements NewsFetcherInterface, PriceFetcherInterface, View.OnClickListener {
    public static String newsUrl;
    public static String cryptoUrl;
    public static String cryptoUrl2;
    public static HashMap<String, String> prices = new HashMap<String, String>();
    public static ArrayList<HashMap<String, String>> articles = new ArrayList<HashMap<String, String>>();
    public static TextView textviewTitle;
    Button buttonPredict;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(this));
        newsUrl = "https://newsapi.org/v2/everything?q=bitcoin&language=en&sortBy=publishedAt&apiKey=66b0258bac8c46a080eeac9e80af22f2";
        textviewTitle = (TextView) findViewById(R.id.btc_chart_title);
        buttonPredict = (Button) findViewById(R.id.buttonPredict);
        buttonPredict.setOnClickListener(this);

        //newsUrl = "https://newsapi.org/v2/everything?q=bitcoin&sortBy=publishedAt&apiKey=66b0258bac8c46a080eeac9e80af22f2";
        cryptoUrl = "https://api.coinmarketcap.com/v1/ticker/bitcoin";
        cryptoUrl2 = "https://api.coindesk.com/v1/bpi/historical/close.json";


        ParsePrice price = new ParsePrice(this);
        ParseNews news = new ParseNews(this);
        price.execute(cryptoUrl2);
        news.execute(newsUrl);
    }

    @Override
    public void onPriceFinishFetcher(HashMap<String, String> data) {
        prices = data;
        Log.i("key", prices.get("2018-01-11"));
        PriceLineChart chart = new PriceLineChart();
        Bundle args = new Bundle(0);
        args.putSerializable("hashmap", data);
        chart.setArguments(args);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.btc_price_fragment, chart);
        transaction.addToBackStack(null);

        transaction.commit();
    }


    @Override
    public void onNewsFinishFetcher(ArrayList<HashMap<String, String>> data) {
        articles = data;
        Log.i("key", data.get(1).get("title"));
        NewsFragment chart = new NewsFragment();
        Bundle args = new Bundle(0);
        args.putSerializable("test", data);
        chart.setArguments(args);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.news_fragment, chart);
        transaction.addToBackStack(null);

        transaction.commit();
    }

    @Override
    public void onClick(View view) {
        if (view == buttonPredict) {
            startActivity(new Intent(this, PredictionListActivity.class));
        }
    }
}
