package com.example.runningapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Date;
import java.util.Objects;

public class DetailsActivity extends AppCompatActivity {
    TextView dateTv, steps, time, cals;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        dateTv = findViewById(R.id.tvDate);
        steps = findViewById(R.id.tvMeters);
        time = findViewById(R.id.tvTime);
        cals = findViewById(R.id.tvCalories);


        //date
        Intent i = getIntent();
        String value = i.getStringExtra("key");
        String currentDateTimeString = java.text.DateFormat.getDateTimeInstance().format(new Date());
        dateTv.setText(currentDateTimeString);
        //Meters ran
        String metersRan = getIntent().getStringExtra("meters");
        steps.setText(metersRan);
        steps.setTextColor(getResources().getColor(R.color.orange));
        //calories Burned
        String caloriesBurned = getIntent().getStringExtra("calories");
        cals.setText(caloriesBurned);
        cals.setTextColor(getResources().getColor(R.color.red));

//time in seconds
        Intent intent = getIntent();
        String timeInSeconds = intent.getStringExtra("result");
        time.setText(timeInSeconds);
        time.setTextColor(getResources().getColor(R.color.green));


    }


}
