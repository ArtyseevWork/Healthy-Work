package com.mordansoft.healthywork.domain.usecase.schedule;

import com.mordansoft.healthywork.domain.model.Schedule;
import com.mordansoft.healthywork.domain.repository.ScheduleRepository;

public class UpdateScheduleUc {

    ScheduleRepository scheduleRepository;

    public UpdateScheduleUc(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    public boolean execute(Schedule schedule){
        return scheduleRepository.updateSchedule(schedule);
    }
}
