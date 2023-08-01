package com.mordansoft.healthywork.presentation.ui.todo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import com.mordansoft.healthywork.databinding.ActivityToDoBinding;
import com.mordansoft.healthywork.domain.model.AppStatus;
import com.mordansoft.healthywork.domain.model.Exercise;
import com.mordansoft.healthywork.domain.model.Statistic;
import com.mordansoft.healthywork.helpers.MordanSoftLogger;
import com.mordansoft.healthywork.R;
import com.mordansoft.healthywork.presentation.ui.main.MainActivity;

import org.jetbrains.annotations.Nullable;

public class ToDoActivity extends AppCompatActivity {

    private ActivityToDoBinding binding;
    private ToDoViewModel vm;

    Statistic statistic;
    AppStatus appStatus;
    Exercise exercise;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do);
        init();
        //updateUi();
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

        vm = new ViewModelProvider(this, //иннициализируем
                new ToDoViewModelFactory(this))
                .get(ToDoViewModel.class);


        vm.getAppStatusMutableLiveData().observe(this, appStatusObserver);
        vm.getExerciseMutableLiveData().observe(this, exerciseObserver);
        vm.getStatisticMutableLiveData().observe(this, statisticObserver);

        binding.clButtonMain.setOnClickListener(btnPositiveListener);
        binding.btnTodoNegative.setOnClickListener(btnNegativeListener);
        binding.btnTodoExit.setOnClickListener(btnExitListener);
        binding.clButtonMain.setOnTouchListener(handleTouch);
    }

    private void updateUi(){
        binding.tvTodoExerciseName.setText(exercise.getName());
        binding.tvTodoExerciseCount.setText(exercise.getCount() + " " +  exercise.getUnit());
    }

    final private Observer<Statistic> statisticObserver = new Observer<Statistic>() {
        @Override
        public void onChanged(final Statistic newStatistic) {
            statistic = newStatistic;
            updateUi();
        }
    };

    final private Observer<AppStatus> appStatusObserver = new Observer<AppStatus>() {
        @Override
        public void onChanged(@Nullable final AppStatus newAppStatus) {
            appStatus = newAppStatus;
            //updateUi();
        }
    };

    final private Observer<Exercise> exerciseObserver = new Observer<Exercise>() {
        @Override
        public void onChanged(@Nullable final Exercise newExercise) {
            exercise = newExercise;
            //updateUi();
        }
    };


    /********** Listeners **********/

    View.OnClickListener btnPositiveListener = v -> {
        vm.addDoneOne();
        goBack();
    };

    View.OnClickListener btnNegativeListener = v -> {
        vm.addSkippedOne();
        goBack();
    };

    View.OnClickListener btnExitListener = v -> {
        this.finish(); // todo проверить работает ли аларм мэнеджер после выхода
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