package com.mordansoft.healthywork;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

public class ExercisesMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises_menu);
        createRecyclerView(R.id.rv_exercises_menu);
    }

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

    public void goBack() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        goBack();
    }
}