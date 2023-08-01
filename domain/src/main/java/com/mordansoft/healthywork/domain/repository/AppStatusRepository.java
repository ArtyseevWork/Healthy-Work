package com.mordansoft.healthywork.domain.repository;

import com.mordansoft.healthywork.domain.model.AppStatus;

public interface AppStatusRepository {
    public boolean   updateAppStatus(AppStatus appStatus);
    public AppStatus   getAppStatus();
}
