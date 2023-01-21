package com.mordansoft.healthywork;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;
import com.mordansoft.healthywork.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {

    private static long back_pressed;
    private ActivityMainBinding binding;
    private static MainActivity instance;
    CurrentStatus currentStatus;
    Exercise exercise;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MordanSoftLogger.addLog("Start MainActivity");
        super.onCreate(savedInstanceState);
        init();
        updateUi();
    }

    public void init(){
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        currentStatus = CurrentStatus.getCurrentStatusFromFile(this);
        instance = this;
        binding.clButtonMain.setOnClickListener(btnMainStartListener);
        binding.clButtonMain.setOnTouchListener(handleTouch);
        binding.btnMainProperties.setOnClickListener(btnSettingsListener);
        binding.btnMainExercises.setOnClickListener(btnExercisesListener);
        binding.btnMainSchedule.setOnClickListener(btnScheduleListener);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
        MordanSoftLogger.addLog("Destroy MainActivity");
    }

    public void updateUi(){
        int visible = View.INVISIBLE;
        String mainButtonText = "Start";

         if (currentStatus.getApplicationStatus() == CurrentStatus.applicationStatusActive){
            visible = View.VISIBLE;
            exercise = Exercise.getExerciseById(this, currentStatus.getExerciseId());
            mainButtonText = "Stop";
            binding.tvMainNextAlertTimer.setText(currentStatus.getStringNextAlarmTime());
            binding.btnMainStart.setText(exercise.getName());
            binding.tvMainNextAlertTimer.setVisibility(visible);
            binding.spMainExercise.setText(exercise.getName());
            binding.tvMainPerformedCount.setText(String.valueOf(currentStatus.getCountOfExerciseDone()));
            binding.tvMainMissedCount.setText(String.valueOf(currentStatus.getCountOfExerciseSkipped()));
        }

        binding.btnMainStart.setText(mainButtonText);
        binding.tvMainNextAlert.setVisibility(visible);
        binding.tvMainNextAlertTimer.setVisibility(visible);
        binding.tvMainExercise.setVisibility(visible);
        binding.spMainExercise.setVisibility(visible);
        binding.tvMainComleted.setVisibility(visible);
        binding.tvMainPerformedCount.setVisibility(visible);
        binding.tvMainMissed.setVisibility(visible);
        binding.tvMainMissedCount.setVisibility(visible);
        //binding.mainCounter.setText(String.valueOf(Exercise.getCntExercise(this)));
    }

    public static MainActivity  getInstance(){
        return instance;
    }

    /********** Listeners **********/

    View.OnClickListener btnMainStartListener = v -> {
        if (currentStatus.getApplicationStatus() == CurrentStatus.applicationStatusDefault){
            currentStatus = currentStatus.run(this);
        } else if(currentStatus.getApplicationStatus() == CurrentStatus.applicationStatusActive){
            currentStatus = currentStatus.stop(this);
        }
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


    private  final View.OnTouchListener handleTouch = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN: {
                    v.setBackground(ContextCompat.getDrawable(v.getContext(), R.drawable.whistle_pressed));
                    v.invalidate();
                    break;
                }
                case MotionEvent.ACTION_UP: {
                    v.setBackground(ContextCompat.getDrawable(v.getContext(), R.drawable.whistle));
                    v.invalidate();
                    break;
                }
            }
            return false;
        }
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