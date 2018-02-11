package com.bubble.crowdsourcingcryptocurrencyprediction.model;

/**
 * Created by hossaim3 on 2/10/2018.
 */

public class UserPredictionEntry {

    public String predic_entry_id;
    public String user_prediction;
    public String actual_answer;
    public String result;
    public int max_point;
    public int earned_point = 0;

    public UserPredictionEntry(String predic_entry_id, String user_prediction, String actual_answer, String result, int max_point, int earned_point) {
        this.predic_entry_id = predic_entry_id;
        this.user_prediction = user_prediction;
        this.actual_answer = actual_answer;
        this.result = result;
        this.max_point = max_point;
        this.earned_point = earned_point;

    }

    public UserPredictionEntry() {

    }


}
