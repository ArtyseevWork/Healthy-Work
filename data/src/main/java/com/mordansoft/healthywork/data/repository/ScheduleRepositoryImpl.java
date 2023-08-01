package com.mordansoft.healthywork.data.repository;

import com.mordansoft.healthywork.data.model.ScheduleD;
import com.mordansoft.healthywork.data.storage.ScheduleStorage;
import com.mordansoft.healthywork.domain.model.Schedule;
import com.mordansoft.healthywork.domain.repository.ScheduleRepository;

public class ScheduleRepositoryImpl implements ScheduleRepository {

    private final ScheduleStorage scheduleStorage;

    public ScheduleRepositoryImpl(ScheduleStorage scheduleStorage) {
        this.scheduleStorage = scheduleStorage;
    }

    @Override
    public boolean updateSchedule(Schedule schedule) {
        return scheduleStorage.updateSchedule(scheduleToScheduleD(schedule));
    }

    @Override
    public Schedule getSchedule() {
        return scheduleDToSchedule(scheduleStorage.getSchedule());
    }

    /*********** mappers *************/
    private ScheduleD scheduleToScheduleD(Schedule schedule){
        ScheduleD scheduleD = new ScheduleD(schedule.isScheduleEnable(),
                                            schedule.getStartDayHours(),
                                            schedule.getStartDayMinutes(),
                                            schedule.getEndDayHours(),
                                            schedule.getEndDayMinutes(),
                                            schedule.isRecessEnable(),
                                            schedule.getStartRecessHours(),
                                            schedule.getStartRecessMinutes(),
                                            schedule.getEndRecessHours(),
                                            schedule.getEndRecessMinutes(),
                                            schedule.isSu(), schedule.isMo(),
                                            schedule.isTu(), schedule.isWe(),
                                            schedule.isTh(), schedule.isFr(),
                                            schedule.isSa(), schedule.getPeriod(),
                                            schedule.getCountdown());
        return scheduleD;
    }

    private Schedule scheduleDToSchedule(ScheduleD scheduleD){
        Schedule schedule = new Schedule(
                scheduleD.isScheduleEnable(),
                scheduleD.getStartDayHours(),
                scheduleD.getStartDayMinutes(),
                scheduleD.getEndDayHours(),
                scheduleD.getEndDayMinutes(),
                scheduleD.isRecessEnable(),
                scheduleD.getStartRecessHours(),
                scheduleD.getStartRecessMinutes(),
                scheduleD.getEndRecessHours(),
                scheduleD.getEndRecessMinutes(),
                scheduleD.isSu(), scheduleD.isMo(),
                scheduleD.isTu(), scheduleD.isWe(),
                scheduleD.isTh(), scheduleD.isFr(),
                scheduleD.isSa(), scheduleD.getPeriod(),
                scheduleD.getCountdown()
        );
        return schedule;
    }
    /* ******* ! mappers *************/

}
