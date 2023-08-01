package com.mordansoft.healthywork.domain.usecase.statistic;

import com.mordansoft.healthywork.domain.NotificationCreator;
import com.mordansoft.healthywork.domain.model.AppStatus;
import com.mordansoft.healthywork.domain.model.Statistic;
import com.mordansoft.healthywork.domain.repository.AppStatusRepository;
import com.mordansoft.healthywork.domain.repository.StatisticRepository;

public class AddSkippedOneUc {

    StatisticRepository statisticRepository;
    AppStatusRepository appStatusRepository;
    NotificationCreator notificationCreator;
    public AddSkippedOneUc(StatisticRepository statisticRepository,
                           AppStatusRepository appStatusRepository,
                           NotificationCreator notificationCreator) {
        this.statisticRepository = statisticRepository;
        this.appStatusRepository = appStatusRepository;
        this.notificationCreator = notificationCreator;
    }

    public boolean execute (){
        Statistic statistic = statisticRepository.getStatistic();
        int cnt = statistic.getCountOfExerciseSkipped();
        cnt++;
        statistic.setCountOfExerciseSkipped(cnt);
        AppStatus appStatus = appStatusRepository.getAppStatus();
        if(appStatus.getApplicationStatus() == AppStatus.applicationStatusPending){
            appStatus.setApplicationStatus(AppStatus.applicationStatusActive);
            appStatusRepository.updateAppStatus(appStatus);
        }
        notificationCreator.deleteNotification();
        return statisticRepository.updateStatistic(statistic);
    }
}
