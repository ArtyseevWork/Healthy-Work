package com.mordansoft.healthywork.presentation.ui.exercises;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.mordansoft.healthywork.R;
import com.mordansoft.healthywork.domain.model.Exercise;
import com.mordansoft.healthywork.presentation.ui.exercise.ExerciseActivity;
import com.mordansoft.healthywork.presentation.ui.main.MainActivity;

import java.util.ArrayList;

public class ExercisesActivity extends AppCompatActivity {
    Button newExerciseBtn;
    ImageButton backButton;
    ArrayList<Exercise> exercisesList;
    private ExercisesViewModel vm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        updateUi();
    }

    protected void init(){
        setContentView(R.layout.activity_exercises_menu);
        newExerciseBtn = findViewById(R.id.btn_exercise_save);
        newExerciseBtn.setOnClickListener(newExerciseListener);
        backButton = findViewById(R.id.btn_exercises_back);
        backButton.setOnClickListener(btnBackListener);
        vm = new ViewModelProvider(this,
                new ExercisesViewModelFactory(getApplicationContext()))
                .get(ExercisesViewModel.class);
        vm.getExercises().observe(this, exercisesObserver);
    }

    protected void updateUi(){
        createRecyclerView(R.id.rv_exercises_menu);
    }

    View.OnClickListener newExerciseListener = v -> {
        Intent intent = new Intent(this, ExerciseActivity.class);
        intent.putExtra("EXTRA_EXERCISE_ID", 0);
        startActivity(intent);
    };

    final Observer<ArrayList<Exercise>> exercisesObserver = new Observer<ArrayList<Exercise>>() {
        @Override
        public void onChanged(ArrayList<Exercise> newExercises) {
            exercisesList = newExercises;
            updateUi();
        }
    };


    public void createRecyclerView(int view){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true);
        linearLayoutManager.setReverseLayout(false);
        linearLayoutManager.setStackFromEnd(false);
        RecyclerView recyclerView = findViewById(view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        ExerciseAdapter adapter = new ExerciseAdapter(exercisesList);
        recyclerView.setAdapter(adapter);
        adapter.setListener(exerciseId -> {
            Intent intent = new Intent(this, ExerciseActivity.class);
            intent.putExtra("EXTRA_EXERCISE_ID", exerciseId);
            startActivity(intent);
        });
    }

    View.OnClickListener btnBackListener = v -> goBack();

    public void goBack() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        goBack();
    }
}