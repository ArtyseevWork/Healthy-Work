package com.mordansoft.healthywork.presentation.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;
import com.mordansoft.healthywork.databinding.ActivityMainBinding;
import com.mordansoft.healthywork.domain.model.AppStatus;
import com.mordansoft.healthywork.domain.model.Exercise;
import com.mordansoft.healthywork.domain.model.Statistic;
import com.mordansoft.healthywork.helpers.MordanSoftLogger;
import com.mordansoft.healthywork.R;
import com.mordansoft.healthywork.worker.Preferences;
import com.mordansoft.healthywork.presentation.ui.todo.ToDoActivity;
import com.mordansoft.healthywork.presentation.ui.exercises.ExercisesActivity;
import com.mordansoft.healthywork.presentation.ui.schedule.ScheduleActivity;

import org.jetbrains.annotations.Nullable;


public class MainActivity extends AppCompatActivity {

    private static long back_pressed;
    private ActivityMainBinding binding;
    private static MainActivity instance;
    private MainViewModel vm;

    AppStatus appStatus;
    Statistic statistic;
    Exercise exercise;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MordanSoftLogger.addLog("Start MainActivity");
        super.onCreate(savedInstanceState);
    }

    public void init(){
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        vm = new ViewModelProvider(this,
                new MainViewModelFactory(this))
                .get(MainViewModel.class);

        vm.getAppStatusMutableLiveData().observe(this,appStatusObserver);
        vm.getStatisticMutableLiveData().observe(this,statisticObserver);
        vm.getExerciseMutableLiveData().observe(this,exerciseObserver);



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
        if (appStatus.getApplicationStatus() == AppStatus.applicationStatusPending){ //todo
            Intent intent = new Intent(getApplicationContext(), ToDoActivity.class);
            startActivity(intent);
        }
        if (appStatus != null && statistic != null && exercise !=null) {

            if (appStatus.getApplicationStatus() == AppStatus.applicationStatusActive) { //application enable
                binding.tvMainNextAlertTimer.setText(getStringNextAlarmTime());
                binding.btnMainStart.setText(getText(R.string.activity_main_stop));
                binding.tvMainNextAlertTimer.setVisibility(View.VISIBLE);
                binding.spMainExercise.setText(exercise.getName());
                binding.tvMainPerformedCount.setText(String.valueOf(statistic.getCountOfExerciseDone()));
                binding.tvMainMissedCount.setText(String.valueOf(statistic.getCountOfExerciseSkipped()));

                binding.tvMainNextAlert.setVisibility(View.VISIBLE);
                binding.tvMainNextAlertTimer.setVisibility(View.VISIBLE);
                binding.tvMainExercise.setVisibility(View.VISIBLE);
                binding.spMainExercise.setVisibility(View.VISIBLE);
                binding.tvMainComleted.setVisibility(View.VISIBLE);
                binding.tvMainPerformedCount.setVisibility(View.VISIBLE);
                binding.tvMainMissed.setVisibility(View.VISIBLE);
                binding.tvMainMissedCount.setVisibility(View.VISIBLE);

                binding.btnMainSchedule.setVisibility(View.GONE);
                binding.btnMainChangeExercise.setVisibility(View.VISIBLE);
                binding.btnMainExercises.setVisibility(View.GONE);
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

                binding.btnMainSchedule.setVisibility(View.VISIBLE);
                binding.btnMainChangeExercise.setVisibility(View.GONE);
                binding.btnMainExercises.setVisibility(View.VISIBLE);
            }
        }
    }

    public static MainActivity  getInstance(){
        return instance;
    }

    final private Observer<AppStatus> appStatusObserver = new Observer<AppStatus>() {
        @Override
        public void onChanged(@Nullable final AppStatus newAppStatus) {
            appStatus = newAppStatus;

            updateUi();
        }
    };

    final private Observer<Statistic> statisticObserver = new Observer<Statistic>() {
        @Override
        public void onChanged(@Nullable final Statistic newStatistic) {
            statistic = newStatistic;
            updateUi();
        }
    };

    final private Observer<Exercise> exerciseObserver = new Observer<Exercise>() {
        @Override
        public void onChanged(@Nullable final Exercise newExercise) {
            exercise = newExercise;
            updateUi();
        }
    };

    private String getStringNextAlarmTime() {
        long time = appStatus.getNextAlarmTime();
        return String.format("%ta %tT", time,time); //todo localize
    }


    /********** Listeners **********/

    View.OnClickListener btnMainStartListener = v -> {
        if (appStatus.getApplicationStatus() != AppStatus.applicationStatusActive){
            vm.start();
        } else if(appStatus.getApplicationStatus() == AppStatus.applicationStatusActive){
            vm.stop();
        }
        updateUi();
    };

    View.OnClickListener btnChangeExerciseListener = v -> {
        vm.changeNextExercise();
        updateUi();
    };

    View.OnClickListener btnExercisesListener = v -> {
        Intent intent = new Intent(getApplicationContext(), ExercisesActivity.class);
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