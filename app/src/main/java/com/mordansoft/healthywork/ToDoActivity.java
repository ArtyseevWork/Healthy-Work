package com.mordansoft.healthywork;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.mordansoft.healthywork.databinding.ActivityToDoBinding;

public class ToDoActivity extends AppCompatActivity {

    private ActivityToDoBinding binding;
    CurrentStatus currentStatus;
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
        currentStatus = CurrentStatus.getCurrentStatusFromFile(this);
        binding.btnTodoPositive.setOnClickListener(btnPositiveListener);
        binding.btnTodoNegative.setOnClickListener(btnNegativeListener);
        binding.btnTodoExit.setOnClickListener(btnExitListener);
        exercise = Exercise.getExerciseById(this, currentStatus.getExerciseId());
    }

    private void updateUi(){
        binding.tvTodoExerciseName.setText(exercise.getName());
        binding.tvTodoExerciseCount.setText(String.valueOf(exercise.getCount()));
        binding.tvTodoAlreadyDone.setText(String.valueOf(currentStatus.getCountOfExerciseDone()));
    }

    /********** Listeners **********/

    View.OnClickListener btnPositiveListener = v -> {
        currentStatus.exerciseDonePlus(this,exercise.getCount());
        currentStatus.run(this);
        goBack();

    };

    View.OnClickListener btnNegativeListener = v -> {
        currentStatus.exerciseSkipPlus(this,exercise.getCount());
        currentStatus.run(this);
        goBack();
    };

    View.OnClickListener btnExitListener = v -> {
        currentStatus.stop(this);
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

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