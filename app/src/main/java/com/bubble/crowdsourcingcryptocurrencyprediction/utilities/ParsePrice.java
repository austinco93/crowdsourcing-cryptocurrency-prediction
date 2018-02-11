package com.bubble.crowdsourcingcryptocurrencyprediction.utilities;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.io.IOUtils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ParsePrice extends AsyncTask<String, Void, String> {
    public static HashMap<String,String> map = new HashMap<String,String>();
    PriceFetcherInterface callBack;

    public ParsePrice(PriceFetcherInterface callBack)
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(json);
        if (element.isJsonObject()) {
            JsonObject data = element.getAsJsonObject();
            JsonObject bpi = data.get("bpi").getAsJsonObject();
            Set<Map.Entry<String, JsonElement>> entries = bpi.entrySet();
            for (Map.Entry<String, JsonElement> entry: entries) {
                map.put(entry.getKey(),bpi.get(entry.getKey()).getAsString());
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        callBack.onPriceFinishFetcher(this.map);
    }

}
