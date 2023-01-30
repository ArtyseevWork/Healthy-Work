package com.mordansoft.healthywork.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.mordansoft.healthywork.databinding.ActivityMainBinding;
import com.mordansoft.healthywork.models.CurrentStatus;
import com.mordansoft.healthywork.models.Exercise;
import com.mordansoft.healthywork.helpers.MordanSoftLogger;
import com.mordansoft.healthywork.models.Preferences;
import com.mordansoft.healthywork.R;
import com.mordansoft.healthywork.models.TodayStatistics;


public class MainActivity extends AppCompatActivity {

    private static long back_pressed;
    private ActivityMainBinding binding;
    private static MainActivity instance;
    CurrentStatus currentStatus;
    TodayStatistics todayStatistics;
    Exercise exercise;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MordanSoftLogger.addLog("Start MainActivity");
        super.onCreate(savedInstanceState);
        //init();
        //updateUi();
    }

    public void init(){
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        currentStatus = CurrentStatus.getCurrentStatusFromFile(this);
        if (currentStatus.getApplicationStatus() == CurrentStatus.applicationStatusPending){
            Intent intent = new Intent(getApplicationContext(), ToDoActivity.class);
            startActivity(intent);
        }
        todayStatistics = TodayStatistics.getTodayStatisticsFromFile(this);
        instance = this;
        Preferences.getPreferencesFromFile(this);
        binding.clButtonMain.setOnClickListener(btnMainStartListener);
        binding.clButtonMain.setOnTouchListener(handleTouch);
        binding.btnMainChangeExercise.setOnClickListener(btnChangeExerciseListener);
        binding.btnMainExercises.setOnClickListener(btnExercisesListener);
        binding.btnMainSchedule.setOnClickListener(btnScheduleListener);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
        MordanSoftLogger.addLog("Destroy MainActivity");
    }

    @Override
    protected void onResume() {
        super.onResume();
        init();
        updateUi();
    }

    public void updateUi(){

         if (currentStatus.getApplicationStatus() == CurrentStatus.applicationStatusActive){ //application enable
            exercise = Exercise.getExerciseById(this, currentStatus.getNextExerciseId());
            binding.tvMainNextAlertTimer.setText(currentStatus.getStringNextAlarmTime());
            binding.btnMainStart.setText(getText(R.string.activity_main_stop));
            binding.tvMainNextAlertTimer.setVisibility(View.VISIBLE);
            binding.spMainExercise.setText(exercise.getName());
            binding.tvMainPerformedCount.setText(String.valueOf(todayStatistics.getCountOfExerciseDone()));
            binding.tvMainMissedCount.setText(String.valueOf(todayStatistics.getCountOfExerciseSkipped()));

            binding.tvMainNextAlert.setVisibility     (View.VISIBLE);
            binding.tvMainNextAlertTimer.setVisibility(View.VISIBLE);
            binding.tvMainExercise.setVisibility      (View.VISIBLE);
            binding.spMainExercise.setVisibility      (View.VISIBLE);
            binding.tvMainComleted.setVisibility      (View.VISIBLE);
            binding.tvMainPerformedCount.setVisibility(View.VISIBLE);
            binding.tvMainMissed.setVisibility        (View.VISIBLE);
            binding.tvMainMissedCount.setVisibility   (View.VISIBLE);

            binding.btnMainSchedule      .setVisibility(View.GONE);
            binding.btnMainChangeExercise.setVisibility(View.VISIBLE);
            binding.btnMainExercises     .setVisibility(View.GONE);
         } else {                                                                           //application disable
            binding.btnMainStart.setText(getText(R.string.activity_main_start));
            binding.tvMainNextAlert.setVisibility(View.INVISIBLE);
            binding.tvMainNextAlertTimer.setVisibility(View.INVISIBLE);
            binding.tvMainExercise.setVisibility(View.INVISIBLE);
            binding.spMainExercise.setVisibility(View.INVISIBLE);
            binding.tvMainComleted.setVisibility(View.INVISIBLE);
            binding.tvMainPerformedCount.setVisibility(View.INVISIBLE);
            binding.tvMainMissed.setVisibility(View.INVISIBLE);
            binding.tvMainMissedCount.setVisibility(View.INVISIBLE);

            binding.btnMainSchedule      .setVisibility(View.VISIBLE);
            binding.btnMainChangeExercise.setVisibility(View.GONE);
            binding.btnMainExercises     .setVisibility(View.VISIBLE);
         }

         CurrentStatus currentStatus1 =CurrentStatus.getCurrentStatusFromFile(this);
         int x = 0;

    }

    public static MainActivity  getInstance(){
        return instance;
    }

    /********** Listeners **********/

    View.OnClickListener btnMainStartListener = v -> {
        if (currentStatus.getApplicationStatus() == CurrentStatus.applicationStatusDefault){
            currentStatus = currentStatus.run(this);
            todayStatistics = todayStatistics.recreate(this);
        } else if(currentStatus.getApplicationStatus() == CurrentStatus.applicationStatusActive){
            currentStatus = currentStatus.stop(this);
        }
        updateUi();
    };

    View.OnClickListener btnChangeExerciseListener = v -> {
        currentStatus.changeExercise(this);
        updateUi();
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