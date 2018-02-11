package com.bubble.crowdsourcingcryptocurrencyprediction.utilities;

import android.os.AsyncTask;
import android.util.Log;

import java.net.URL;

import org.apache.commons.io.IOUtils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.ArrayList;
import java.util.HashMap;

public class ParseNews extends AsyncTask<String, Void, String> {
    ArrayList<HashMap<String,String>> articleList = new ArrayList<HashMap<String, String>>();
    NewsFetcherInterface callBack;

    public ParseNews(NewsFetcherInterface callBack)
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
            Log.i("status",data.get("status").getAsString());
            JsonArray articles = data.getAsJsonArray("articles");
            for (int i = 0; i < articles.size(); i++) {
                HashMap<String,String> temp = new HashMap<String,String>();
                JsonObject article = articles.get(i).getAsJsonObject();
                //Log.i("title",article.get("title").getAsString());
                //Log.i("description",article.get("description").getAsString());
                //Log.i("author",article.get("author").getAsString());
                //Log.i("url",article.get("url").getAsString());
                //Log.i("urlToImage",article.get("urlToImage").getAsString());
                if(article.get("title") != null) {
                    temp.put("title", article.get("title").getAsString());
                } else {
                    temp.put("title","");
                }

                if(article.get("description") != null){
                    temp.put("description",article.get("description").getAsString());
                } else {
                    temp.put("description","");
                }

                if(article.get("url") != null){
                    temp.put("url",article.get("url").getAsString());
                } else {
                    temp.put("url","");
                }

                if(article.get("urlToImage") != null) {
                    temp.put("urlToImage", article.get("urlToImage").toString());
                    Log.i("test", temp.get("urlToImage"));
                } else {
                    temp.put("urlToImage","");
                }

                articleList.add(temp);
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        callBack.onNewsFinishFetcher(this.articleList);
    }
}
