package com.bubble.crowdsourcingcryptocurrencyprediction;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class PredictionActivity extends AppCompatActivity implements View.OnClickListener {
    EditText editTextUsersRational;
    RadioGroup options;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prediction);

        TextView textViewQuestion = (TextView) findViewById(R.id.textviewQuestion);
//        textViewQuestion.setText(question.text);
        editTextUsersRational = (EditText) findViewById(R.id.editTextOther);
//
//
//        options = (RadioGroup) findViewById(R.id.radioGroupAnswers);
//        for (int i = 1; i < question.answers.size(); i++) {
//            Answer ans = question.answers.get(i);
//            RadioButton rbn = new RadioButton(getActivity());
//            rbn.setId(i + 1000);
//            rbn.setText(ans.text);
//            rgp.addView(rbn);
//        }
//        rgp.setOnCheckedChangeListener(this);
//        return view;
    }

    @Override
    public void onClick(View view) {

    }
}
