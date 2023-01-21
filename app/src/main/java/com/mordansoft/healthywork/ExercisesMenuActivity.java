package com.mordansoft.healthywork;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class ExercisesMenuActivity extends AppCompatActivity {
    Button newExerciseBtn;
    ImageButton backButton;

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
    }

    protected void updateUi(){
        createRecyclerView(R.id.rv_exercises_menu);
    }

    View.OnClickListener newExerciseListener = v -> {
        Intent intent = new Intent(this, ExerciseActivity.class);
        intent.putExtra("EXTRA_EXERCISE_ID", 0);
        startActivity(intent);
    };



    public void createRecyclerView(int view){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true);
        linearLayoutManager.setReverseLayout(false);
        linearLayoutManager.setStackFromEnd(false);
        RecyclerView recyclerView = findViewById(view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        ExerciseAdapter adapter = new ExerciseAdapter(Exercise.getExercisesByQuery(this, "1=1"), this);
        recyclerView.setAdapter(adapter);
        adapter.setListener(profitId -> {
            Intent intent = new Intent(this, ExerciseActivity.class);
            intent.putExtra("EXTRA_EXERCISE_ID", profitId);
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