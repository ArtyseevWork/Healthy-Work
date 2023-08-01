package com.mordansoft.healthywork.domain.repository;

import com.mordansoft.healthywork.domain.model.Exercise;
import com.mordansoft.healthywork.domain.model.Schedule;

public interface ScheduleRepository {

    public boolean   updateSchedule(Schedule schedule);
    public Schedule  getSchedule();

}
