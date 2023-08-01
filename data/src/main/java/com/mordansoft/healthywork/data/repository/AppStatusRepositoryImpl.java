package com.mordansoft.healthywork.data.repository;

import com.mordansoft.healthywork.data.model.AppStatusD;
import com.mordansoft.healthywork.data.storage.AppStatusStorage;
import com.mordansoft.healthywork.domain.model.AppStatus;

public class AppStatusRepositoryImpl implements com.mordansoft.healthywork.domain.repository.AppStatusRepository {

    AppStatusStorage appStatusStorage;

    public AppStatusRepositoryImpl(AppStatusStorage appStatusStorage) {
        this.appStatusStorage = appStatusStorage;
    }

    @Override
    public boolean updateAppStatus(AppStatus appStatus) {
        return appStatusStorage.updateAppStatus(appStatusToAppStatusD(appStatus));
    }

    @Override
    public AppStatus getAppStatus() {
        return appStatusDToAppStatus(appStatusStorage.getAppStatus());
    }

    /* *** mapper *****/

    private AppStatusD appStatusToAppStatusD(AppStatus appStatus){
        AppStatusD appStatusD = new AppStatusD(appStatus.getApplicationStatus(),
                                               appStatus.getNextAlarmTime(),
                                               appStatus.getCurrentExerciseId(),
                                               appStatus.getNextExerciseId());
        return  appStatusD;
    }


    private AppStatus appStatusDToAppStatus(AppStatusD appStatusD){
        AppStatus appStatus = new AppStatus(appStatusD.getApplicationStatus(),
                                            appStatusD.getNextAlarmTime(),
                                            appStatusD.getCurrentExerciseId(),
                                            appStatusD.getNextExerciseId());
        return  appStatus;
    }


    /* * ! mapper *****/


}
