package com.mordansoft.healthywork.domain;

import com.mordansoft.healthywork.domain.model.Exercise;

public interface AlarmCreator {
    public boolean createAlarm(long time);
    public boolean stopAlarm();
}
