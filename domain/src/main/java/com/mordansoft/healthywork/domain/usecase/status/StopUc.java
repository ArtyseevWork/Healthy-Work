package com.mordansoft.healthywork.domain.usecase.status;

import com.mordansoft.healthywork.domain.model.AppStatus;
import com.mordansoft.healthywork.domain.repository.AppStatusRepository;

public class StopUc {
    AppStatusRepository appStatusRepository;

    public StopUc(AppStatusRepository appStatusRepository) {
        this.appStatusRepository = appStatusRepository;
    }

    public AppStatus execute(){
        AppStatus appStatus = appStatusRepository.getAppStatus();
        appStatus.setApplicationStatus(AppStatus.applicationStatusStopped);
        appStatusRepository.updateAppStatus(appStatus);
        return appStatus;
    }

}
