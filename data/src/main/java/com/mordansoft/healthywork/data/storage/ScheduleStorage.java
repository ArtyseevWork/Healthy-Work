package com.mordansoft.healthywork.data.storage;

import com.mordansoft.healthywork.data.model.ScheduleD;

public interface ScheduleStorage {

    public boolean updateSchedule(ScheduleD exercise);
    public ScheduleD  getSchedule();

}
