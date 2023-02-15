package com.mordansoft.healthywork.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import com.mordansoft.healthywork.databinding.ActivityToDoBinding;
import com.mordansoft.healthywork.models.CurrentStatus;
import com.mordansoft.healthywork.helpers.MordanSoftLogger;
import com.mordansoft.healthywork.R;
import com.mordansoft.healthywork.models.Exercise;
import com.mordansoft.healthywork.models.TodayStatistics;

public class ToDoActivity extends AppCompatActivity {

    private ActivityToDoBinding binding;
    CurrentStatus currentStatus;
    TodayStatistics todayStatistics;
    Exercise exercise;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do);
        init();
        updateUi();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
        MordanSoftLogger.addLog("Destroy MainActivity");
    }

    private void init(){
        binding = ActivityToDoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        todayStatistics = TodayStatistics.getTodayStatisticsFromFile(this);
        currentStatus = CurrentStatus.getCurrentStatusFromFile(this);
        currentStatus.setApplicationStatus(this, CurrentStatus.applicationStatusActive);
        binding.clButtonMain.setOnClickListener(btnPositiveListener);
        binding.btnTodoNegative.setOnClickListener(btnNegativeListener);
        binding.btnTodoExit.setOnClickListener(btnExitListener);
        binding.clButtonMain.setOnTouchListener(handleTouch);
        exercise = Exercise.getExerciseById(this, currentStatus.getCurrentExerciseId());
    }

    private void updateUi(){
        binding.tvTodoExerciseName.setText(exercise.getName());
        binding.tvTodoExerciseCount.setText(exercise.getCount() + " " +  exercise.getUnit());
    }

    /********** Listeners **********/

    View.OnClickListener btnPositiveListener = v -> {
        todayStatistics.setCountOfExerciseDelta(this,+1);
        exercise.saveExercise(this);
        currentStatus.run(this);
        goBack();
    };

    View.OnClickListener btnNegativeListener = v -> {
        todayStatistics.setCountOfExerciseDelta(this,-1);
        currentStatus.run(this);
        goBack();
    };

    View.OnClickListener btnExitListener = v -> {
        currentStatus.stop(this);

        this.finish();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
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

    /* ****** ! Listeners ********* */

    public void goBack() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        goBack();
    }
}