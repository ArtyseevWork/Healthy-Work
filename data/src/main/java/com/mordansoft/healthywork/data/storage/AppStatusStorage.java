package com.mordansoft.healthywork.data.storage;

import com.mordansoft.healthywork.data.model.AppStatusD;

public interface AppStatusStorage {

    public boolean updateAppStatus(AppStatusD appStatusD);
    public AppStatusD getAppStatus();

}
