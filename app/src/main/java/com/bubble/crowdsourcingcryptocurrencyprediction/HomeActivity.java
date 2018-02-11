package com.bubble.crowdsourcingcryptocurrencyprediction;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bubble.crowdsourcingcryptocurrencyprediction.fragments.NewsFragment;
import com.bubble.crowdsourcingcryptocurrencyprediction.fragments.PriceLineChart;
import com.bubble.crowdsourcingcryptocurrencyprediction.utilities.CurrPriceFetcherInterface;
import com.bubble.crowdsourcingcryptocurrencyprediction.utilities.NewsFetcherInterface;
import com.bubble.crowdsourcingcryptocurrencyprediction.utilities.ParseCurrPrice;
import com.bubble.crowdsourcingcryptocurrencyprediction.utilities.ParseNews;
import com.bubble.crowdsourcingcryptocurrencyprediction.utilities.ParsePrice;
import com.bubble.crowdsourcingcryptocurrencyprediction.utilities.PriceFetcherInterface;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;


import java.util.ArrayList;
import java.util.HashMap;

public class HomeActivity extends AppCompatActivity implements NewsFetcherInterface,
        PriceFetcherInterface, CurrPriceFetcherInterface, View.OnClickListener {
    public static final String newsUrl = "https://newsapi.org/v2/everything?q=bitcoin&language=en&sortBy=publishedAt&apiKey=66b0258bac8c46a080eeac9e80af22f2",
            currPriceUrl = "https://api.coinmarketcap.com/v1/ticker?limit=4",
            btcPriceUrl = "https://api.coindesk.com/v1/bpi/historical/close.json";
    public static HashMap<String, String> prices = new HashMap<String, String>();
    public static ArrayList<HashMap<String, String>> articles = new ArrayList<HashMap<String,
            String>>(), coins = new ArrayList<HashMap<String, String>>();
    public static TextView textviewTitle;
    Button buttonPredict;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(this));
        textviewTitle = (TextView) findViewById(R.id.btc_chart_title);
        buttonPredict = (Button) findViewById(R.id.buttonPredict);
        buttonPredict.setOnClickListener(this);

        ParsePrice price = new ParsePrice(this);
        ParseNews news = new ParseNews(this);
        ParseCurrPrice currPrice = new ParseCurrPrice(this);
        price.execute(btcPriceUrl);
        news.execute(newsUrl);
        currPrice.execute(currPriceUrl);
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
    public void onCurrPriceFinishFetcher(ArrayList<HashMap<String, String>> data) {
        coins = data;
        Log.i("name", data.get(1).get("name"));
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
