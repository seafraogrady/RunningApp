package com.example.runningapp;

import  androidx.appcompat.app.AppCompatActivity;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private double InitialMagnitude = 0;
    private Integer  stepCount = 0;
    Button startButton, stopButton, restButton,detailsButton;
    CountUpTimer timer;
    TextView time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        startButton = findViewById(R.id.start);
        stopButton = findViewById(R.id.stop);
        restButton = findViewById(R.id.resetRun);
        detailsButton = findViewById(R.id.showRun);
        time = findViewById(R.id.runTimer);


        timer = new CountUpTimer(900000000) {


            public void onTick(int second) {
                time.setText(String.valueOf(second));
            }
        };

        textView = findViewById(R.id.runSteps);

        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        SensorEventListener stepCounter = new SensorEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onSensorChanged(SensorEvent event) {
                if (event != null) {
                    float x_acceleration = event.values[0];
                    float y_acceleration = event.values[0];
                    float z_acceleration = event.values[0];

                    double Magnitude = Math.sqrt(x_acceleration * x_acceleration + y_acceleration * y_acceleration + z_acceleration * z_acceleration);
                    double MagDifference = Magnitude - InitialMagnitude;
                    InitialMagnitude = Magnitude;

                    if (MagDifference > 6) {
                        stepCount++;
                    }

                textView.setText(stepCount.toString());
            }
        }
            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timer.start();

                sensorManager.registerListener(stepCounter, sensor , SensorManager.SENSOR_DELAY_NORMAL);
            }
        });
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timer.cancel();
                        sensorManager.unregisterListener(stepCounter, sensor );
            }
        });
        restButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stepCount = 0;
                textView.setText("0");
                timer.onFinish();

            }
        });
detailsButton.setOnClickListener(new View.OnClickListener() {
    @Override//n
    public void onClick(View v) {
if (!textView.getText().toString().matches("0") || !time.getText().toString().matches("0") ) {
    //intent for dATE
    Intent i = new Intent(MainActivity.this, DetailsActivity.class);
    i.putExtra("key", String.valueOf(stepCount));
    MainActivity.this.startActivity(i);
    //meters ran
    i.putExtra("meters", String.valueOf(stepCount * 0.8));
    MainActivity.this.startActivity(i);
    //calories burned
   i.putExtra("calories", String.valueOf(stepCount * 0.04));
    MainActivity.this.startActivity(i);
i.putExtra("result", time.getText().toString());
    MainActivity.this.startActivity(i);
}
else {
    Toast msg = Toast.makeText(MainActivity.this, "You must START run!", Toast.LENGTH_LONG);
    msg.setGravity(Gravity.CENTER, msg.getXOffset() / 2, msg.getYOffset() / 2);
    msg.show();
}
    }
});

    }

}






