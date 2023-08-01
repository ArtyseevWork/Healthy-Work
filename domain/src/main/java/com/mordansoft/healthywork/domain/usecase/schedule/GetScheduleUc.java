package com.mordansoft.healthywork.domain.usecase.schedule;

import com.mordansoft.healthywork.domain.model.Schedule;
import com.mordansoft.healthywork.domain.repository.ExerciseRepository;
import com.mordansoft.healthywork.domain.repository.ScheduleRepository;

public class GetScheduleUc {

    ScheduleRepository scheduleRepository;

    public GetScheduleUc(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    public Schedule execute (){
        return scheduleRepository.getSchedule();
    }
}
