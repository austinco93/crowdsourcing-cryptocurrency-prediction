package com.bubble.crowdsourcingcryptocurrencyprediction.utilities;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by webert3 on 2/11/18.
 */

public class ParseCurrPrice extends AsyncTask<String, Void, String> {
    ArrayList<HashMap<String,String>> coinList = new ArrayList<HashMap<String, String>>();
    CurrPriceFetcherInterface callBack;

    public ParseCurrPrice(CurrPriceFetcherInterface callBack)
    {
        this.callBack=callBack;
    }

    @Override
    protected String doInBackground(String... urls) {
        URL url;
        String json = "";
        try {
            url = new URL(urls[0]);
            json = IOUtils.toString(url);
            JSONArray jArr = new JSONArray(json);
            for(int i=0; i< jArr.length();i++)
            {
                HashMap<String,String> temp = new HashMap<>();
                JSONObject coin =jArr.getJSONObject(i);
                if (coin.get("name") != null) {
                    temp.put("name", coin.getString("name"));
                } else {
                    temp.put("name", "");
                }

                if (coin.get("symbol") != null) {
                    temp.put("symbol", coin.getString("symbol"));
                } else {
                    temp.put("symbol", "");
                }

                if (coin.get("price_usd") != null) {
                    temp.put("price_usd", coin.getString("price_usd"));
                } else {
                    temp.put("price_usd", "");
                }

                coinList.add(temp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        callBack.onCurrPriceFinishFetcher(this.coinList);
    }
}
