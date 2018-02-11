package com.bubble.crowdsourcingcryptocurrencyprediction.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hossaim3 on 1/12/2018.
 */

public class PredictionEntry {
    public String id;
    public String text;
    public Map<String, String> options = new HashMap<String, String>();
    public int maxPoint;
    public double createdTimeUTC;
    public double expireTimeUTC;
    public String currency;

    public PredictionEntry(String id, String text, Map<String, String> map, int maxPoint, double createdTimeUTC, double expireTimeUTC, String currency) {
        this.id = id;
        this.text = text;
        this.options = map;
        this.maxPoint = maxPoint;
        this.createdTimeUTC = createdTimeUTC;
        this.expireTimeUTC = expireTimeUTC;
        this.currency = currency;
    }

    public PredictionEntry() {

    }

}
