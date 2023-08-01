package com.mordansoft.healthywork.domain.usecase.status;

import android.content.Context;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.VibrationEffect;
import android.os.Vibrator;

import com.mordansoft.healthywork.domain.AlarmCreator;
import com.mordansoft.healthywork.domain.MordanSoftLogger;
import com.mordansoft.healthywork.domain.model.AppStatus;
import com.mordansoft.healthywork.domain.model.Exercise;
import com.mordansoft.healthywork.domain.model.Schedule;
import com.mordansoft.healthywork.domain.repository.AppStatusRepository;
import com.mordansoft.healthywork.domain.repository.ExerciseRepository;
import com.mordansoft.healthywork.domain.repository.ScheduleRepository;
import com.mordansoft.healthywork.domain.repository.StatisticRepository;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Calendar;

public class StartUc {

    AppStatusRepository appStatusRepository;
    StatisticRepository statisticRepository;
    ExerciseRepository exerciseRepository;
    ScheduleRepository scheduleRepository;
    AlarmCreator alarmCreator;
    private Schedule schedule;

    public StartUc(AppStatusRepository appStatusRepository,
                   StatisticRepository statisticRepository,
                   ExerciseRepository exerciseRepository,
                   ScheduleRepository scheduleRepository,
                   AlarmCreator alarmCreator ) {
        this.appStatusRepository = appStatusRepository;
        this.statisticRepository = statisticRepository;
        this.exerciseRepository = exerciseRepository;
        this.scheduleRepository = scheduleRepository;
        this.alarmCreator = alarmCreator;
        schedule  = scheduleRepository.getSchedule();
    }


    public AppStatus execute(){
        AppStatus appStatus     = appStatusRepository.getAppStatus();
        ArrayList<Exercise> exercises = exerciseRepository.getExercisesByQuery("enable = 1");
        int nextExerciseId = exercises.get(0).getId();
        appStatus.setNextExerciseId(nextExerciseId);
        // updateTimestamp
        Exercise exercise = exerciseRepository.getExerciseById(nextExerciseId);
        exerciseRepository.updateExercise(exercise); // updateTimestamp

        //schedule next alarm
        Calendar nextAlarmTime =  getNextAlarmTime(null);
        alarmCreator.createAlarm(nextAlarmTime.getTimeInMillis());
        appStatus.setNextAlarmTime(nextAlarmTime.getTimeInMillis());

        //update AppStatus
        appStatus.setApplicationStatus(AppStatus.applicationStatusActive);

        appStatusRepository.updateAppStatus(appStatus);
        return  appStatus;
    }

    public boolean[] getWorkDaysArray(){
        return new boolean[]{ schedule.isSu(),
                schedule.isMo(),
                schedule.isTu(),
                schedule.isWe(),
                schedule.isTh(),
                schedule.isFr(),
                schedule.isSa() };
    }


    public Calendar getNextAlarmTime( @Nullable Calendar inCalendar){

        MordanSoftLogger.addLog("getNextAlarmTime START");
        int period = schedule.getPeriod();
        if (inCalendar == null){
            inCalendar = Calendar.getInstance();
        }

        if (false) {//debug
            inCalendar.setTimeInMillis(inCalendar.getTimeInMillis() + (30*1000));
            return inCalendar;
        }

        Calendar outCalendar = (Calendar) inCalendar.clone();

        int dayOfWeekIn = inCalendar.get(Calendar.DAY_OF_WEEK);

        if (schedule.isScheduleEnable()){                                            //schedule turn on
            boolean[] workDays = getWorkDaysArray();
            if (!workDays[dayOfWeekIn - 1]){                                         //today is not working day - processing next day
                outCalendar.add(Calendar.DAY_OF_YEAR,1);
                outCalendar = getNextAlarmTime(outCalendar);
            } else {                                                                 //today is working day
                Calendar workDayStartCalendar = (Calendar) inCalendar.clone();
                workDayStartCalendar.set(Calendar.HOUR_OF_DAY, schedule.getStartDayHours());
                workDayStartCalendar.set(Calendar.MINUTE, schedule.getStartDayMinutes());
                long startWorkDayMillis = workDayStartCalendar.getTimeInMillis();
                MordanSoftLogger.addLog("getNextAlarmTime  workDayStartCalendar = " + workDayStartCalendar.getTime());

                Calendar workDayEndCalendar = (Calendar) inCalendar.clone();
                workDayEndCalendar.set(Calendar.HOUR_OF_DAY, schedule.getEndDayHours());
                workDayEndCalendar.set(Calendar.MINUTE, schedule.getEndDayMinutes());
                long endWorkDayMillis = workDayEndCalendar.getTimeInMillis();
                MordanSoftLogger.addLog("getNextAlarmTime  workDayEndCalendar = " + workDayEndCalendar.getTime());

                Calendar recessStartCalendar = (Calendar) inCalendar.clone();
                recessStartCalendar.set(Calendar.HOUR_OF_DAY, schedule.getStartRecessHours());
                recessStartCalendar.set(Calendar.MINUTE, schedule.getStartRecessMinutes());
                MordanSoftLogger.addLog("getNextAlarmTime  recessStartCalendar = " + recessStartCalendar.getTime());long startRecessMillis = recessStartCalendar.getTimeInMillis();

                Calendar recessEndCalendar = (Calendar) inCalendar.clone();
                recessEndCalendar.set(Calendar.HOUR_OF_DAY, schedule.getEndRecessHours());
                recessEndCalendar.set(Calendar.MINUTE, schedule.getEndRecessMinutes());
                long endRecessMillis = recessEndCalendar.getTimeInMillis();
                MordanSoftLogger.addLog("getNextAlarmTime  recessEndCalendar = " + recessEndCalendar.getTime());

                if (inCalendar.getTimeInMillis() <= startWorkDayMillis) {                       // before work day
                    outCalendar = getNextAlarmTimeSimple(workDayStartCalendar);
                } else if (inCalendar.getTimeInMillis() >= endWorkDayMillis - period) {         // after work day
                    outCalendar.add(Calendar.DAY_OF_YEAR,1);
                    outCalendar.set(Calendar.HOUR_OF_DAY, workDayStartCalendar.get(Calendar.HOUR_OF_DAY));
                    outCalendar.set(Calendar.MINUTE, workDayStartCalendar.get(Calendar.MINUTE));
                    outCalendar = getNextAlarmTime(outCalendar);
                } else if (inCalendar.getTimeInMillis() > startWorkDayMillis  // in work day
                        && inCalendar.getTimeInMillis() < endWorkDayMillis - period) {
                    if (false && schedule.isRecessEnable() &&            //in recess
                            inCalendar.getTimeInMillis() > startRecessMillis - period //
                            && inCalendar.getTimeInMillis() < endRecessMillis - period) {
                        outCalendar = getNextAlarmTimeSimple(recessEndCalendar);
                    } else {
                        outCalendar = getNextAlarmTimeSimple(inCalendar);
                    }
                }
            }
        } else { //schedule turn off
            outCalendar = getNextAlarmTimeSimple(inCalendar);
        }
        MordanSoftLogger.addLog("getNextAlarmTime END: " + outCalendar.getTime());
        return outCalendar;
    }

    public Calendar getNextAlarmTimeSimple(Calendar calendar){

        int minutesAlarm;
        int hoursAlarm;
        int hoursNow;
        int minutesNow;
        int countdown = schedule.getCountdown();
        int period = schedule.getPeriod();

        hoursNow = calendar.get(Calendar.HOUR_OF_DAY);
        minutesNow = calendar.get(Calendar.MINUTE);
        minutesAlarm = countdown;
        hoursAlarm = hoursNow;

        if (period == 30){
            if (minutesNow >= (countdown+period)){
                hoursAlarm = hoursNow +1;
            }
            else if (minutesNow >= (countdown)){
                minutesAlarm = countdown+period;
            }
        } else if (period == 60){
            if (minutesNow >= countdown){
                hoursAlarm = hoursNow + 1;
            }
        }

        calendar.set(Calendar.HOUR_OF_DAY, hoursAlarm);
        calendar.set(Calendar.MINUTE, minutesAlarm);
        calendar.set(Calendar.SECOND, 0);

        return calendar;
    }
}
