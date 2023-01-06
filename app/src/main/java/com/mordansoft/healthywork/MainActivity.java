package com.mordansoft.healthywork;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.mordansoft.healthywork.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private static long back_pressed;
    private ActivityMainBinding binding;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        updateUi();
        MordanSoftLogger.addLog("Start MainActivity");

    }

    public void init(){
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.btnMainStart.setOnClickListener(btnMainStartListener);
        binding.btnMainProperties.setOnClickListener(btnSettingsListener);
        binding.btnMainExercises.setOnClickListener(btnExercisesListener);
        binding.btnMainSchedule.setOnClickListener(btnScheduleListener);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }


    public void updateUi(){

        //binding.mainCounter.setText(String.valueOf(Exercise.getCntExercise(this)));
    }

    /********** Listeners **********/

    View.OnClickListener btnMainStartListener = v -> {
        Toast.makeText(getApplicationContext(), "someText",Toast.LENGTH_LONG).show();
        updateUi();
    };

    View.OnClickListener btnSettingsListener = v -> {
        Intent intent = new Intent(getApplicationContext(), PreferencesActivity.class);
        startActivity(intent);
    };

    View.OnClickListener btnExercisesListener = v -> {
        Intent intent = new Intent(getApplicationContext(), ExercisesMenuActivity.class);
        startActivity(intent);
    };

    View.OnClickListener btnScheduleListener = v -> {
        Intent intent = new Intent(getApplicationContext(), ScheduleActivity.class);
        startActivity(intent);
    };

    /* ****** ! Listeners **********/

    @Override
    public void onBackPressed() {   //exit from the app
        if (back_pressed + 2000 > System.currentTimeMillis()) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else {
            Toast.makeText(getBaseContext(), getString(R.string.activity_main_exit_message),
                    Toast.LENGTH_SHORT).show();
            back_pressed = System.currentTimeMillis();
        }
    }


}