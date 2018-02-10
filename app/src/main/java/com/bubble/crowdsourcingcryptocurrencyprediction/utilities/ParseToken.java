package com.bubble.crowdsourcingcryptocurrencyprediction.utilities;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.StringReader;
import java.net.URL;

public class ParseToken {

    public static class AsyncHttpTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            URL url;
            String json = null;
            JsonReader reader = null;

            try {
                url = new URL(urls[0]);
                json = IOUtils.toString(url);
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                reader = new JsonReader(new StringReader(json));
                JsonToken token = reader.peek();
                if (token.equals(JsonToken.BEGIN_ARRAY))
                    handleArray(reader);
                else
                    handleObject(reader);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }

    public static void handleObject(com.google.gson.stream.JsonReader reader) throws IOException
    {
        reader.beginObject();
        while (reader.hasNext()) {
            JsonToken token = reader.peek();
            if (token.equals(JsonToken.BEGIN_ARRAY))
                handleArray(reader);
            else if (token.equals(JsonToken.END_OBJECT)) {
                reader.endObject();
                return;
            } else
                handleNonArrayToken(reader, token);
        }
    }

    public static void handleArray(com.google.gson.stream.JsonReader reader) throws IOException
    {
        reader.beginArray();
        while (true) {
            JsonToken token = reader.peek();
            if (token.equals(JsonToken.END_ARRAY)) {
                reader.endArray();
                break;
            } else if (token.equals(JsonToken.BEGIN_OBJECT)) {
                handleObject(reader);
            } else if (token.equals(JsonToken.END_OBJECT)) {
                reader.endObject();
            } else
                handleNonArrayToken(reader, token);
        }
    }

    public static void handleNonArrayToken(com.google.gson.stream.JsonReader reader, JsonToken token) throws IOException
    {
        if (token.equals(JsonToken.NAME))
            Log.i("1",reader.nextName());
        else if (token.equals(JsonToken.STRING))
            Log.i("2",reader.nextString());

        else if (token.equals(JsonToken.NUMBER))
            Log.i("3",Double.toString(reader.nextDouble()));
        else
            reader.skipValue();
    }
}
