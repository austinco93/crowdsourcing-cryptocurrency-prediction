package com.bubble.crowdsourcingcryptocurrencyprediction.utilities;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by webert3 on 2/11/18.
 */

public interface CurrPriceFetcherInterface {
    public void onCurrPriceFinishFetcher(ArrayList<HashMap<String,String>> data);
}
