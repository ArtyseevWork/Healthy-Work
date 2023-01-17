package com.mordansoft.healthywork;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.mordansoft.healthywork.databinding.ActivityExerciseBinding;

public class ExerciseActivity extends AppCompatActivity {

    private ActivityExerciseBinding binding;
    private Exercise exercise;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        updateUi();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    public void init(){
        setContentView(R.layout.activity_exercise);
        binding = ActivityExerciseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Bundle arguments = getIntent().getExtras();
        int exerciseId = arguments.getInt("EXTRA_EXERCISE_ID");
        this.exercise = Exercise.getExerciseById(this, exerciseId);
        binding.btnNewExercise.setOnClickListener(btnSaveListener);
    }

    private void updateUi() {
        try {
            binding.etExerciseName.setText(exercise.getName());
            binding.etExerciseUnits.setText(exercise.getUnit());
            binding.etExerciseCounts.setText(String.valueOf(exercise.getCount()));
            binding.chbExerciseAvailable.setChecked(exercise.isEnable());
            binding.etExerciseDescription.setText(exercise.getDescription());
        } catch (Exception e){
            MordanSoftLogger.addLog("ExerciseActivity updateUi() - " + e, 'e');
        }
    }

    View.OnClickListener btnSaveListener = this::saveState;

    private void saveState(View v){
        String y = String.valueOf(binding.etExerciseCounts.getText());
        int x = Integer.parseInt(y);


        exercise.setName(String.valueOf(binding.etExerciseName.getText()));
        exercise.setUnit(String.valueOf(binding.etExerciseUnits.getText()));
        exercise.setCount(x);
        exercise.setEnable(binding.chbExerciseAvailable.isChecked());
        exercise.setDescription(String.valueOf(binding.etExerciseDescription.getText()));
        exercise.saveExercise(this);
    }

    public void goBack() {
        Intent intent = new Intent(this, ExercisesMenuActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        goBack();
    }




}