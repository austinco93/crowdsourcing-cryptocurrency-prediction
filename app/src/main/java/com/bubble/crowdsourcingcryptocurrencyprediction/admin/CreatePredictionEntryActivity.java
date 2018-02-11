package com.bubble.crowdsourcingcryptocurrencyprediction.admin;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.bubble.crowdsourcingcryptocurrencyprediction.PredictionActivity;
import com.bubble.crowdsourcingcryptocurrencyprediction.R;
import com.bubble.crowdsourcingcryptocurrencyprediction.model.PredictionEntry;
import com.bubble.crowdsourcingcryptocurrencyprediction.utilities.CurrPriceFetcherInterface;
import com.bubble.crowdsourcingcryptocurrencyprediction.utilities.ParseCurrPrice;
import com.bubble.crowdsourcingcryptocurrencyprediction.utilities.ParsePrice;
import com.bubble.crowdsourcingcryptocurrencyprediction.utilities.PriceFetcherInterface;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CreatePredictionEntryActivity extends AppCompatActivity implements View.OnClickListener, CurrPriceFetcherInterface {
    Button buttonSubmit;
    EditText editTextQuestion, editTextMaxPoint, editTextOption1, editTextOption2, editTextOption3, editTextOption4, editTextOption5, editTextOption6;
    TextView textviewCoinPrice;
    Spinner spinnerPredictionType, spinnerCryptoType;
    String[] predictionSet = new String[]{"prediction_set_7_days", "prediction_set_1_days", "prediction_set_6_Hours", "prediction_set_1_Hours"};
    int[] expireTime = new int[]{7 * 24 * 60 * 60 * 1000, 1 * 24 * 60 * 60 * 1000, 6 * 60 * 60 * 1000, 1 * 60 * 60 * 1000,};
    String[] cryptoSet = new String[]{"BTC", "ETH", "XRP","BCH", "ADA"};
    DatabaseReference refPSets, refCSets, refAnswers;

    public static ArrayList<HashMap<String, String>> coins = new ArrayList<HashMap<String, String>>();
    HashMap<String, String> tickerPriceMap = new HashMap<String, String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_prediction);

        buttonSubmit = (Button) findViewById(R.id.buttonAddQuestion);
        buttonSubmit.setOnClickListener(this);
        textviewCoinPrice = (TextView) findViewById(R.id.textviewCoinPrice);
        editTextQuestion = (EditText) findViewById(R.id.editTextQuestion);
        editTextMaxPoint = (EditText) findViewById(R.id.editTextPoint);
        editTextOption1 = (EditText) findViewById(R.id.editTextOption1);
        editTextOption2 = (EditText) findViewById(R.id.editTextOption2);
        editTextOption3 = (EditText) findViewById(R.id.editTextOption3);
        editTextOption4 = (EditText) findViewById(R.id.editTextOption4);
        editTextOption5 = (EditText) findViewById(R.id.editTextOption5);
        editTextOption6 = (EditText) findViewById(R.id.editTextOption6);
        spinnerPredictionType = (Spinner) findViewById(R.id.spinnerPredictionType);
        spinnerCryptoType = (Spinner) findViewById(R.id.spinnerCryptoType);
        spinnerCryptoType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                ParseCurrPrice currPrice = new ParseCurrPrice(CreatePredictionEntryActivity.this);
//                currPrice.execute("https://api.coinmarketcap.com/v1/ticker?limit=4");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    textviewCoinPrice.setText(tickerPriceMap.getOrDefault(cryptoSet[i],"Fetching..")+"$");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        ParseCurrPrice currPrice = new ParseCurrPrice(this);
        currPrice.execute("https://api.coinmarketcap.com/v1/ticker?limit=5");
    }

    @Override
    public void onClick(View view) {
        if (view == buttonSubmit) {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            String predictSet = predictionSet[spinnerPredictionType.getSelectedItemPosition()];
            String mCryptoSet = cryptoSet[spinnerCryptoType.getSelectedItemPosition()];
            refPSets = database.getReference().child(predictSet);
            refCSets = refPSets.child(mCryptoSet);

            ArrayList<String> options = new ArrayList<String>();
            if (!TextUtils.isEmpty(editTextOption1.getText().toString())) {
                options.add(editTextOption1.getText().toString());
            }
            if (!TextUtils.isEmpty(editTextOption2.getText().toString())) {
                options.add(editTextOption2.getText().toString());
            }
            if (!TextUtils.isEmpty(editTextOption3.getText().toString())) {
                options.add(editTextOption3.getText().toString());
            }
            if (!TextUtils.isEmpty(editTextOption4.getText().toString())) {
                options.add(editTextOption4.getText().toString());
            }
            if (!TextUtils.isEmpty(editTextOption5.getText().toString())) {
                options.add(editTextOption5.getText().toString());
            }
            if (!TextUtils.isEmpty(editTextOption6.getText().toString())) {
                options.add(editTextOption6.getText().toString());
            }
            Map<String, String> optionsHashMap = new HashMap<String, String>();
            int i = 1;
            for (String option : options) {
                optionsHashMap.put(i + "", option);
                i++;
            }
            DatabaseReference ref = refCSets.push();
            String key = ref.getKey();
            double creationDate = (double) System.currentTimeMillis();
            double expirationDate = creationDate + (double) expireTime[spinnerPredictionType.getSelectedItemPosition()];
            Log.d("tonmoy", "creationDate :" + creationDate);
            Log.d("tonmoy", "expirationDate :" + expirationDate);
            PredictionEntry entry = new PredictionEntry(key, editTextQuestion.getText().toString(), optionsHashMap, Integer.parseInt(editTextMaxPoint.getText().toString()), creationDate, expirationDate, mCryptoSet);
            ref.setValue(entry);


        }
    }

    @Override
    public void onCurrPriceFinishFetcher(ArrayList<HashMap<String, String>> data) {

        coins = data;

       // Log.i("name", data.get(1).get("name"));
        for (int i = 0; i < data.size(); i++) {
            HashMap<String, String> map = data.get(i);
            tickerPriceMap.put(map.get("symbol"), map.get("price_usd"));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            textviewCoinPrice.setText(tickerPriceMap.getOrDefault(cryptoSet[spinnerCryptoType.getSelectedItemPosition()],"Fetching..")+"$");
        }

    }
}
