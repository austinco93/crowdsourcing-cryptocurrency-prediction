package com.bubble.crowdsourcingcryptocurrencyprediction.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hossaim3 on 1/12/2018.
 */

public class RetrievedPredictionEntry implements Serializable {
    public String id;
    public String text;
    public List<String> options = new ArrayList<String>();
    public int maxPoint;
    public String currency;
    public double createdTimeUTC;
    public double expireTimeUTC;
    public String mCryptoSet ="";
    public String isExpired ="false";

    public RetrievedPredictionEntry(String id, String text, List<String> map, int maxPoint, double createdTimeUTC, double expireTimeUTC, String currency) {
        this.id = id;
        this.text = text;
        this.options = map;
        this.maxPoint = maxPoint;
        this.createdTimeUTC = createdTimeUTC;
        this.expireTimeUTC = expireTimeUTC;
        this.currency = currency;
    }

    public RetrievedPredictionEntry() {

    }

}
