package com.mordansoft.healthywork.data.storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.mordansoft.healthywork.data.model.ScheduleD;

public class ScheduleStorageImplShPr implements ScheduleStorage {
    private static final String fileName = "schedule";
    private static final boolean scheduleEnableDefault = false;
    private static final String scheduleEnableKey = "SCHEDULE_ENABLE";
    private static final int startDayHoursDefault = 9;
    private static final String startDayHoursKey = "START_DAY_HOURS";
    private static final int endDayHoursDefault = 18;
    private static final String endDayHoursKey = "END_DAY_HOURS";
    private static final int startDayMinutesDefault = 0;
    private static final String startDayMinutesKey = "START_DAY_MINUTES";
    private static final int endDayMinutesDefault = 0;
    private static final String endDayMinutesKey = "END_DAY_MINUTES";
    private static final boolean recessEnableDefault = true;
    private static final String recessEnableKey = "RECESS_ENABLE";
    private static final int startRecessHoursDefault = 13;
    private static final String startRecessHoursKey = "START_RECESS_HOURS";
    private static final int endRecessHoursDefault = 14;
    private static final String endRecessHoursKey = "END_RECESS_HOURS";
    private static final int startRecessMinutesDefault = 0;
    private static final String startRecessMinutesKey = "START_RECESS_MINUTES";
    private static final int endRecessMinutesDefault = 0;
    private static final String endRecessMinutesKey = "END_RECESS_MINUTES";
    private static final boolean suDefault = false;
    private static final String suKey = "SU";
    private static final boolean moDefault = true;
    private static final String moKey = "MO";
    private static final boolean tuDefault = true;
    private static final String tuKey = "TU";
    private static final boolean weDefault = true;
    private static final String weKey = "WE";
    private static final boolean thDefault = true;
    private static final String thKey = "TH";
    private static final boolean frDefault = true;
    private static final String frKey = "FR";
    private static final boolean saDefault = false;
    private static final String saKey = "SA";
    private int period;
    private static final int periodDefault = 60;
    private static final String periodKey = "PERIOD";
    private int countdown;
    private static final int countdownDefault = 0;
    private static final String countdownKey = "COUNTDOWN";

    Context context;

    public ScheduleStorageImplShPr(Context context) {
        this.context = context;
    }

    @Override
    public boolean updateSchedule(ScheduleD schedule) {
        SharedPreferences scheduleFile = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = scheduleFile.edit();
        editor.putBoolean(scheduleEnableKey, schedule.isScheduleEnable());
        editor.putInt(startDayHoursKey, schedule.getStartDayHours());
        editor.putInt(startDayMinutesKey, schedule.getStartDayMinutes());
        editor.putInt(endDayHoursKey, schedule.getEndDayHours());
        editor.putInt(endDayMinutesKey, schedule.getEndDayMinutes());
        editor.putBoolean(recessEnableKey, schedule.isRecessEnable());
        editor.putInt(startRecessHoursKey, schedule.getStartRecessHours());
        editor.putInt(startRecessMinutesKey, schedule.getStartRecessMinutes());
        editor.putInt(endRecessHoursKey, schedule.getEndRecessHours());
        editor.putInt(endRecessMinutesKey, schedule.getEndRecessMinutes());
        editor.putBoolean(suKey, schedule.isSu());
        editor.putBoolean(moKey, schedule.isMo());
        editor.putBoolean(tuKey, schedule.isTu());
        editor.putBoolean(weKey, schedule.isWe());
        editor.putBoolean(thKey, schedule.isTh());
        editor.putBoolean(frKey, schedule.isFr());
        editor.putBoolean(saKey, schedule.isSa());
        editor.putInt(periodKey, schedule.getPeriod());
        editor.putInt(countdownKey, schedule.getCountdown());
        editor.apply();
        return true;
    }

    @Override
    public ScheduleD getSchedule() {

        boolean scheduleEnable;
        int     startDayHours;
        int     startDayMinutes;
        int     endDayHours;
        int     endDayMinutes;
        boolean recessEnable;
        int     startRecessHours;
        int     startRecessMinutes;
        int     endRecessHours;
        int     endRecessMinutes;
        boolean su;
        boolean mo;
        boolean tu;
        boolean we;
        boolean th;
        boolean fr;
        boolean sa;
        int period;
        int countdown;

        SharedPreferences scheduleFile = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);

        scheduleEnable = scheduleFile.getBoolean(scheduleEnableKey,scheduleEnableDefault);
        startDayHours = scheduleFile.getInt(startDayHoursKey,startDayHoursDefault);
        startDayMinutes = scheduleFile.getInt(startDayMinutesKey,startDayMinutesDefault);
        endDayHours = scheduleFile.getInt(endDayHoursKey,endDayHoursDefault);
        endDayMinutes = scheduleFile.getInt(endDayMinutesKey,endDayMinutesDefault);
        recessEnable = scheduleFile.getBoolean(recessEnableKey,recessEnableDefault);
        startRecessHours = scheduleFile.getInt(startRecessHoursKey,startRecessHoursDefault);
        startRecessMinutes = scheduleFile.getInt(startRecessMinutesKey,startRecessMinutesDefault);
        endRecessHours = scheduleFile.getInt(endRecessHoursKey,endRecessHoursDefault);
        endRecessMinutes = scheduleFile.getInt(endRecessMinutesKey,endRecessMinutesDefault);
        su = scheduleFile.getBoolean(suKey,suDefault);
        mo = scheduleFile.getBoolean(moKey,moDefault);
        tu = scheduleFile.getBoolean(tuKey,tuDefault);
        we = scheduleFile.getBoolean(weKey,weDefault);
        th = scheduleFile.getBoolean(thKey,thDefault);
        fr = scheduleFile.getBoolean(frKey,frDefault);
        sa = scheduleFile.getBoolean(saKey,saDefault);
        period =  scheduleFile.getInt(periodKey,periodDefault);
        countdown =  scheduleFile.getInt(countdownKey,countdownDefault);

        return new ScheduleD(scheduleEnable,
                startDayHours, startDayMinutes,
                endDayHours,    endDayMinutes,
                recessEnable,
                startRecessHours, startRecessMinutes,
                endRecessHours, endRecessMinutes,
                su, mo, tu, we, th, fr, sa,
                period,  countdown);
    }
}
