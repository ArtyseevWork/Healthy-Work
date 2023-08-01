package com.mordansoft.healthywork.presentation.ui.main;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.mordansoft.healthywork.domain.model.AppStatus;
import com.mordansoft.healthywork.domain.model.Exercise;
import com.mordansoft.healthywork.domain.model.Statistic;
import com.mordansoft.healthywork.domain.usecase.exercise.GetExerciseByIdUc;
import com.mordansoft.healthywork.domain.usecase.statistic.GetStatisticUc;
import com.mordansoft.healthywork.domain.usecase.status.ChangeNextExerciseUc;
import com.mordansoft.healthywork.domain.usecase.status.GetAppStatusUc;
import com.mordansoft.healthywork.domain.usecase.status.StartUc;
import com.mordansoft.healthywork.domain.usecase.status.StopUc;
import com.mordansoft.healthywork.helpers.MordanSoftLogger;

public class MainViewModel extends ViewModel {
    private final GetAppStatusUc getAppStatusUc;
    private final StartUc startUc;
    private final StopUc stopUc;
    private final ChangeNextExerciseUc changeNextExerciseUc;
    private final GetStatisticUc getStatisticUc;
    private final GetExerciseByIdUc getExerciseByIdUc;
    private MutableLiveData<AppStatus> appStatusMutableLiveData;
    private MutableLiveData<Exercise> exerciseMutableLiveData;

    private MutableLiveData<Statistic> statisticMutableLiveData;

    public MainViewModel(GetAppStatusUc getAppStatusUc,
                         StartUc startUc,
                         StopUc stopUc,
                         ChangeNextExerciseUc changeNextExerciseUc,
                         GetStatisticUc getStatisticUc,
                         GetExerciseByIdUc getExerciseByIdUc) {
        this.getAppStatusUc = getAppStatusUc;
        this.startUc = startUc;
        this.stopUc = stopUc;
        this.changeNextExerciseUc = changeNextExerciseUc;
        this.getStatisticUc = getStatisticUc;
        this.getExerciseByIdUc = getExerciseByIdUc;
    }

    public MutableLiveData<AppStatus> getAppStatusMutableLiveData() {
        MordanSoftLogger.addLog("getAppStatusMutableLiveData() Start");
        if (appStatusMutableLiveData == null) {
            appStatusMutableLiveData = new MutableLiveData<>();
        }
        getAppStatus();
        MordanSoftLogger.addLog("getAppStatusMutableLiveData() End");
        return appStatusMutableLiveData;
    }


    public void getAppStatus(){
        appStatusMutableLiveData.setValue(getAppStatusUc.execute());
    }

    public MutableLiveData<Exercise> getExerciseMutableLiveData(){
        if (exerciseMutableLiveData == null) {
            exerciseMutableLiveData = new MutableLiveData<>();
        }
        getExercise();

        return exerciseMutableLiveData;
    }

    public void getExercise(){
        int exerciseId = getAppStatusUc.execute().getNextExerciseId();
        exerciseMutableLiveData.setValue(getExerciseByIdUc.execute(exerciseId));
    }

    public MutableLiveData<Statistic> getStatisticMutableLiveData() {
        if (statisticMutableLiveData == null) {
            statisticMutableLiveData = new MutableLiveData<>();

        }
        getStatistic();
        return statisticMutableLiveData;
    }
    public void getStatistic(){
        statisticMutableLiveData.setValue(getStatisticUc.execute());
    }



    public void start() {
        getAppStatusMutableLiveData().setValue( startUc.execute());
    }

    public void  stop() {
        getAppStatusMutableLiveData().setValue( stopUc.execute());
    }

    public void changeNextExercise() {
        getAppStatusMutableLiveData().setValue(changeNextExerciseUc.execute());
        getExercise();
    }



}



