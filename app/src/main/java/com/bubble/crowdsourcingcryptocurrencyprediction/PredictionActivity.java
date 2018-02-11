package com.bubble.crowdsourcingcryptocurrencyprediction;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bubble.crowdsourcingcryptocurrencyprediction.model.PredictionEntry;
import com.bubble.crowdsourcingcryptocurrencyprediction.model.RetrievedPredictionEntry;
import com.bubble.crowdsourcingcryptocurrencyprediction.model.UserPredictionEntry;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class PredictionActivity extends AppCompatActivity implements View.OnClickListener {
    EditText editTextUsersRational;
    RadioGroup options;
    RetrievedPredictionEntry pEntry;
    Button buttonSubmit, buttonOtherPredict;
    DatabaseReference refUsersData;
    DatabaseReference refAllPredict;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prediction);

        TextView textViewQuestion = (TextView) findViewById(R.id.textviewQuestion);
        editTextUsersRational = (EditText) findViewById(R.id.editTextOther);
        pEntry = (RetrievedPredictionEntry) getIntent().getSerializableExtra("data");
        textViewQuestion.setText(pEntry.text);

        buttonSubmit = (Button) findViewById(R.id.buttonSubmit);
        buttonOtherPredict = (Button) findViewById(R.id.buttonOtherPrediction);
        buttonSubmit.setOnClickListener(this);
        buttonOtherPredict.setOnClickListener(this);


        options = (RadioGroup) findViewById(R.id.radioGroupAnswers);
        generateRadioButton();
        getOldAnswer();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void generateRadioButton() {
        for (int i = 1; i < pEntry.options.size(); i++) {
            String option = pEntry.options.get(i);
            RadioButton rbn = new RadioButton(this);
            rbn.setId(i + 1000);
            rbn.setText(option);
            options.addView(rbn);
        }
    }

    private void getOldAnswer() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference ref = database.getReference().child("users_prediction").child(currentUserId);
        ref.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild(pEntry.id)) {
                            for (DataSnapshot dsInner : dataSnapshot.getChildren()) {
                                Log.d("tonmoy", dsInner.getValue().toString());
                                if (dsInner.getKey().equalsIgnoreCase(pEntry.id)) {
                                    UserPredictionEntry uentry = (dsInner.getValue(UserPredictionEntry.class));
                                    makeRadioButtonChecked(options, uentry.user_prediction);
                                    updateButtonsState(buttonOtherPredict, buttonSubmit);
                                }
                            }
                        } else {
                            updateButtonsState(buttonSubmit, buttonOtherPredict);
                        }


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError
                    }
                });
    }


    private void makeRadioButtonChecked(RadioGroup parent, String text) {
        for (int i = 0; i < parent.getChildCount(); i++) {
            int id = parent.getChildAt(i).getId();
            String str = ((RadioButton) findViewById(id)).getText().toString();
            if (str.equalsIgnoreCase(text)) {
                ((RadioButton) findViewById(id)).setChecked(true);
            }
        }


    }

    @Override
    public void onClick(View view) {
        if (view == buttonSubmit) {
            uploadAnswer();
        }
        if (view == buttonOtherPredict) {
            Intent intent = new Intent(this, PredictionResultActivity.class);
            intent.putExtra("data", pEntry);
            startActivity(intent);
        }
    }

    private void uploadAnswer() {

        RadioButton checkedRadioButton = (RadioButton) options.findViewById(options.getCheckedRadioButtonId());
        boolean isChecked = checkedRadioButton.isChecked();
        if (isChecked) {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
            String name = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
            refUsersData = database.getReference().child("users_prediction").child(currentUserId).child(pEntry.id);
            // (String predic_entry_id, String user_prediction, String actual_answer, String result, int max_point, int earned_point)
            UserPredictionEntry uEntry = new UserPredictionEntry(pEntry.id, checkedRadioButton.getText().toString(), "n/a", "n/a", pEntry.maxPoint, 0);
            refUsersData.setValue(uEntry);

            refAllPredict = database.getReference().child("all_predictions").child(pEntry.id).child(checkedRadioButton.getText().toString().replace(".", "_"));
            refAllPredict.child(currentUserId).setValue(name);
        }
    }

    private void updateButtonsState(Button bt1, Button bt2) {
        bt2.setEnabled(false);
        bt1.setEnabled(true);
    }
}
