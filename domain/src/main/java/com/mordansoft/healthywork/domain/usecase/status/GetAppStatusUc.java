package com.mordansoft.healthywork.domain.usecase.status;

import com.mordansoft.healthywork.domain.model.AppStatus;
import com.mordansoft.healthywork.domain.repository.AppStatusRepository;

public class GetAppStatusUc {
    AppStatusRepository appStatusRepository;

    public GetAppStatusUc(AppStatusRepository appStatusRepository) {
        this.appStatusRepository = appStatusRepository;
    }

    public AppStatus execute(){
        return appStatusRepository.getAppStatus();
    }
}
