package com.bubble.crowdsourcingcryptocurrencyprediction.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hossaim3 on 1/12/2018.
 */

public class RetrievedPredictionEntry {
    public String id;
    public String text;
    public List<String> options = new ArrayList<String>();
    public String maxPoint;
    public double createdTimeUTC;
    public double expireTimeUTC;

    public RetrievedPredictionEntry(String id, String text, List<String> map, String maxPoint, double createdTimeUTC, double expireTimeUTC) {
        this.id = id;
        this.text = text;
        this.options = map;
        this.maxPoint = maxPoint;
        this.createdTimeUTC = createdTimeUTC;
        this.expireTimeUTC = expireTimeUTC;
    }

    public RetrievedPredictionEntry() {

    }

}
