package com.bubble.crowdsourcingcryptocurrencyprediction.utilities;

import java.util.HashMap;

public interface PriceFetcherInterface {
    public void onPriceFinishFetcher(HashMap<String,String> data);
}
