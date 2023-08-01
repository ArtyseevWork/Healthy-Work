package com.mordansoft.healthywork.presentation.ui.todo;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mordansoft.healthywork.domain.model.AppStatus;
import com.mordansoft.healthywork.domain.model.Exercise;
import com.mordansoft.healthywork.domain.model.Statistic;
import com.mordansoft.healthywork.domain.usecase.exercise.GetExerciseByIdUc;
import com.mordansoft.healthywork.domain.usecase.statistic.AddDoneOneUc;
import com.mordansoft.healthywork.domain.usecase.statistic.AddSkippedOneUc;
import com.mordansoft.healthywork.domain.usecase.statistic.GetStatisticUc;
import com.mordansoft.healthywork.domain.usecase.status.GetAppStatusUc;


public class ToDoViewModel extends ViewModel {
    private final GetAppStatusUc getAppStatusUc;
    private final GetStatisticUc getStatisticUc;
    private final AddDoneOneUc addDoneOneUc;
    private final AddSkippedOneUc addSkippedOneUc;
    private final GetExerciseByIdUc getExerciseByIdUc;
    private MutableLiveData<AppStatus> appStatusMutableLiveData;
    private MutableLiveData<Exercise> exerciseMutableLiveData;
    private MutableLiveData<Statistic> statisticMutableLiveData;

    public ToDoViewModel(GetStatisticUc getStatisticUc,
                         AddDoneOneUc addDoneOneUc,
                         AddSkippedOneUc addSkippedOneUc,
                         GetAppStatusUc getAppStatusUc,
                         GetExerciseByIdUc getExerciseByIdUc) {
        this.getStatisticUc = getStatisticUc;
        this.addDoneOneUc = addDoneOneUc;
        this.addSkippedOneUc = addSkippedOneUc;
        this.getAppStatusUc = getAppStatusUc;
        this.getExerciseByIdUc = getExerciseByIdUc;
    }


    public MutableLiveData<Statistic> getStatisticMutableLiveData() {
        if (statisticMutableLiveData == null) {
            statisticMutableLiveData = new MutableLiveData<>();
            getStatistic();
        }
        return statisticMutableLiveData;
    }

    public void getStatistic(){
        statisticMutableLiveData.setValue(getStatisticUc.execute());
    }

    public MutableLiveData<AppStatus> getAppStatusMutableLiveData() {
        if (appStatusMutableLiveData == null) {
            appStatusMutableLiveData = new MutableLiveData<>();
            getAppStatus();
        }
        return appStatusMutableLiveData;
    }
    public void getAppStatus(){
        getAppStatusMutableLiveData().setValue(getAppStatusUc.execute());
    }


    public MutableLiveData<Exercise> getExerciseMutableLiveData(){
        if (exerciseMutableLiveData == null) {
            exerciseMutableLiveData = new MutableLiveData<>();
            getExercise();
        }
        return exerciseMutableLiveData;
    }

    public void getExercise(){
        int exerciseId = getAppStatusUc.execute().getNextExerciseId();
        exerciseMutableLiveData.setValue(getExerciseByIdUc.execute(exerciseId));}

    public void addDoneOne(){
        addDoneOneUc.execute();
    }

    public void addSkippedOne(){
        addSkippedOneUc.execute();
    }

}
