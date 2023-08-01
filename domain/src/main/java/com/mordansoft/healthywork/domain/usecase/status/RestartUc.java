package com.mordansoft.healthywork.domain.usecase.status;

import com.mordansoft.healthywork.domain.AlarmCreator;
import com.mordansoft.healthywork.domain.NotificationCreator;
import com.mordansoft.healthywork.domain.model.AppStatus;
import com.mordansoft.healthywork.domain.model.Exercise;
import com.mordansoft.healthywork.domain.model.Statistic;
import com.mordansoft.healthywork.domain.repository.AppStatusRepository;
import com.mordansoft.healthywork.domain.repository.ExerciseRepository;
import com.mordansoft.healthywork.domain.repository.ScheduleRepository;
import com.mordansoft.healthywork.domain.repository.StatisticRepository;

import java.util.ArrayList;
import java.util.Calendar;

public class RestartUc extends StartUc{

    NotificationCreator notificationCreator;


    public RestartUc(AppStatusRepository appStatusRepository,
                     StatisticRepository statisticRepository,
                     ExerciseRepository exerciseRepository,
                     ScheduleRepository scheduleRepository,
                     AlarmCreator alarmCreator,
                     NotificationCreator notificationCreator) {
        super(appStatusRepository,
              statisticRepository,
              exerciseRepository,
              scheduleRepository,
              alarmCreator);
        this.notificationCreator = notificationCreator;
    }

    @Override
    public AppStatus execute() {
        AppStatus appStatus = appStatusRepository.getAppStatus();
        if (isEnable()) {
            appStatus.setCurrentExerciseId(appStatus.getNextExerciseId());
            notificationCreator.createNotification(
                    exerciseRepository.getExerciseById(appStatus.getCurrentExerciseId()));
            ArrayList<Exercise> exercises = exerciseRepository.getExercisesByQuery("enable = 1");
            int nextExerciseId = exercises.get(0).getId();
            appStatus.setNextExerciseId(nextExerciseId);
            // updateTimestamp
            Exercise exercise = exerciseRepository.getExerciseById(nextExerciseId);
            exerciseRepository.updateExercise(exercise); // updateTimestamp

            //schedule next alarm
            Calendar nextAlarmTime = getNextAlarmTime(null);
            alarmCreator.createAlarm(nextAlarmTime.getTimeInMillis());
            appStatus.setNextAlarmTime(nextAlarmTime.getTimeInMillis());


           if (appStatus.getApplicationStatus() == AppStatus.applicationStatusPending ) {
               Statistic statistic = statisticRepository.getStatistic();
               int cnt = statistic.getCountOfExerciseSkipped();
               cnt++;
               statistic.setCountOfExerciseSkipped(cnt);
               statisticRepository.updateStatistic(statistic);
           }

            //update AppStatus
            appStatus.setApplicationStatus(AppStatus.applicationStatusPending);
            appStatusRepository.updateAppStatus(appStatus);
        } return appStatus;
    }

    public boolean isEnable () {
        AppStatus appStatus = appStatusRepository.getAppStatus();
        if(appStatus.getApplicationStatus() == AppStatus.applicationStatusActive ||
                appStatus.getApplicationStatus() == AppStatus.applicationStatusPending) {
            return true;
        } else {
            return  false;
        }
    }
}
