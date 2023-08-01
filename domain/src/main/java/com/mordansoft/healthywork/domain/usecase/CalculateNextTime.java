package com.mordansoft.healthywork.domain.usecase;

import com.mordansoft.healthywork.domain.repository.ScheduleRepository;

public class CalculateNextTime {

    ScheduleRepository scheduleRepository;

    public CalculateNextTime(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }
}
