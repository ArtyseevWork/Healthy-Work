package com.mordansoft.healthywork;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class ExerciseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);
        Bundle arguments = getIntent().getExtras();
        String exerciseId = arguments.getString("EXTRA_PROFIT_ID");
    }
}