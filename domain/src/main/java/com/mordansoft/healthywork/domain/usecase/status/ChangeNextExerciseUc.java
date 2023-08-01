package com.mordansoft.healthywork.domain.usecase.status;

import com.mordansoft.healthywork.domain.MordanSoftLogger;
import com.mordansoft.healthywork.domain.model.AppStatus;
import com.mordansoft.healthywork.domain.model.Exercise;
import com.mordansoft.healthywork.domain.repository.AppStatusRepository;
import com.mordansoft.healthywork.domain.repository.ExerciseRepository;

import java.util.ArrayList;

public class ChangeNextExerciseUc {

    AppStatusRepository appStatusRepository;
    ExerciseRepository exerciseRepository;

    public ChangeNextExerciseUc(AppStatusRepository appStatusRepository, ExerciseRepository exerciseRepository) {
        this.appStatusRepository = appStatusRepository;
        this.exerciseRepository = exerciseRepository;
    }

    public AppStatus execute(){
        MordanSoftLogger.addLog("ChangeNextExerciseUc Start");
        ArrayList<Exercise> exercises = exerciseRepository.getExercisesByQuery("enable = 1");
        int size = exercises.size();
        double x = Math.random(); // 0-1
        int num = (int) (x * size);
        MordanSoftLogger.addLog("ChangeNextExerciseUc  num = " + num);

        int nextExerciseId = exercises.get(num).getId();

        MordanSoftLogger.addLog("ChangeNextExerciseUc  nextExerciseId = " + nextExerciseId);

        AppStatus appStatus = appStatusRepository.getAppStatus();
        appStatus.setNextExerciseId(nextExerciseId);
        appStatusRepository.updateAppStatus(appStatus);

        MordanSoftLogger.addLog("ChangeNextExerciseUc End");
        return appStatus;
    }
}
