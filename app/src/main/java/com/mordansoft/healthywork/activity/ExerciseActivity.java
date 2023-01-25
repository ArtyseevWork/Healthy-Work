package com.mordansoft.healthywork.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;

import com.mordansoft.healthywork.models.Exercise;
import com.mordansoft.healthywork.helpers.MordanSoftLogger;
import com.mordansoft.healthywork.R;
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
        binding.btnExerciseSave.setOnClickListener(btnBackListener);
        binding.btnExerciseBack.setOnClickListener(btnBackListener);
        binding.btnExerciseDelete.setOnClickListener(btnDeleteListener);
        binding.btnExerciseMinusCount.setOnClickListener(btnMinusListener);
        binding.btnExercisePlusCount.setOnClickListener(btnPlusListener);
    }

    private void updateUi() {
        try {
            binding.etExerciseName.setText(exercise.getName());
            binding.etExerciseUnits.setSelection(getIndex(binding.etExerciseUnits, exercise.getUnit()));
            binding.etExerciseCounts.setText(String.valueOf(exercise.getCount()));
            binding.chbExerciseAvailable.setChecked(exercise.isEnable());
            binding.etExerciseDescription.setText(exercise.getDescription());
        } catch (Exception e){
            MordanSoftLogger.addLog("ExerciseActivity updateUi() - " + e, 'e');
        }
    }


    /********** Listeners **********/
    View.OnClickListener btnBackListener = v -> goBack();

    View.OnClickListener btnDeleteListener = v -> {
        Exercise.deleteExercise(v.getContext(), exercise);
        Intent intent = new Intent(this, ExercisesMenuActivity.class);
        startActivity(intent);
    };

    View.OnClickListener btnPlusListener = v -> changeCount(1);

    View.OnClickListener btnMinusListener = v -> changeCount(-1);

    @Override
    public void onBackPressed() {
        goBack();
    }
    /* ****** ! Listeners **********/

    private void changeCount(int delta){
        int count = Integer.parseInt(String.valueOf(binding.etExerciseCounts.getText())) + delta;
        binding.etExerciseCounts.setText(String.valueOf(count) );
    }

    private void saveState(){
        exercise.setName(String.valueOf(binding.etExerciseName.getText()));
        exercise.setUnit(binding.etExerciseUnits.getSelectedItem().toString());
        exercise.setCount(Integer.parseInt(String.valueOf(binding.etExerciseCounts.getText())));
        exercise.setEnable(binding.chbExerciseAvailable.isChecked());
        exercise.setDescription(String.valueOf(binding.etExerciseDescription.getText()));
        exercise.saveExercise(this);
    }

    private int getIndex(Spinner spinner, String myString){
        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                return i;
            }
        }
        return 0;
    }

    public void goBack() {
        saveState();
        Intent intent = new Intent(this, ExercisesMenuActivity.class);
        startActivity(intent);
    }






}