package com.mordansoft.healthywork;

import android.content.Context;
import android.content.SharedPreferences;

public class Schedule {

    private boolean scheduleEnable;
    private int     startDayHours;
    private int     startDayMinutes;
    private int     endDayHours;
    private int     endDayMinutes;
    private boolean recessEnable;
    private int     startRecessHours;
    private int     startRecessMinutes;
    private int     endRecessHours;
    private int     endRecessMinutes;
    private boolean su;
    private boolean mo;
    private boolean tu;
    private boolean we;
    private boolean th;
    private boolean fr;
    private boolean sa;

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

    private Schedule(boolean scheduleEnable,
                    int startDayHours,
                    int startDayMinutes,
                    int endDayHours,
                    int endDayMinutes,
                    boolean recessEnable,
                    int startRecessHours,
                    int startRecessMinutes,
                    int endRecessHours,
                    int endRecessMinutes,
                    boolean su, boolean mo,
                    boolean tu, boolean we,
                    boolean th, boolean fr,
                    boolean sa) {
        this.scheduleEnable = scheduleEnable;
        this.startDayHours = startDayHours;
        this.startDayMinutes = startDayMinutes;
        this.endDayHours = endDayHours;
        this.endDayMinutes = endDayMinutes;
        this.recessEnable = recessEnable;
        this.startRecessHours = startRecessHours;
        this.startRecessMinutes = startRecessMinutes;
        this.endRecessHours = endRecessHours;
        this.endRecessMinutes = endRecessMinutes;
        this.su = su;
        this.mo = mo;
        this.tu = tu;
        this.we = we;
        this.th = th;
        this.fr = fr;
        this.sa = sa;
    }

    public boolean isScheduleEnable() {
        return scheduleEnable;
    }

    public int getStartDayHours() {
        return startDayHours;
    }

    public int getStartDayMinutes() {
        return startDayMinutes;
    }

    public int getEndDayHours() {
        return endDayHours;
    }

    public int getEndDayMinutes() {
        return endDayMinutes;
    }

    public boolean isRecessEnable() {
        return recessEnable;
    }

    public int getStartRecessHours() {
        return startRecessHours;
    }

    public int getStartRecessMinutes() {
        return startRecessMinutes;
    }

    public int getEndRecessHours() {
        return endRecessHours;
    }

    public int getEndRecessMinutes() {
        return endRecessMinutes;
    }

    public boolean isSu() {
        return su;
    }

    public boolean isMo() {
        return mo;
    }

    public boolean isTu() {
        return tu;
    }

    public boolean isWe() {
        return we;
    }

    public boolean isTh() {
        return th;
    }

    public boolean isFr() {
        return fr;
    }

    public boolean isSa() {
        return sa;
    }

    public void setScheduleEnable(boolean scheduleEnable) {
        this.scheduleEnable = scheduleEnable;
    }

    public void setStartDayHours(int startDayHours) {
        this.startDayHours = startDayHours;
    }

    public void setStartDayMinutes(int startDayMinutes) {
        this.startDayMinutes = startDayMinutes;
    }

    public void setEndDayHours(int endDayHours) {
        this.endDayHours = endDayHours;
    }

    public void setEndDayMinutes(int endDayMinutes) {
        this.endDayMinutes = endDayMinutes;
    }

    public void setRecessEnable(boolean recessEnable) {
        this.recessEnable = recessEnable;
    }

    public void setStartRecessHours(int startRecessHours) {
        this.startRecessHours = startRecessHours;
    }

    public void setStartRecessMinutes(int startRecessMinutes) {
        this.startRecessMinutes = startRecessMinutes;
    }

    public void setEndRecessHours(int endRecessHours) {
        this.endRecessHours = endRecessHours;
    }

    public void setEndRecessMinutes(int endRecessMinutes) {
        this.endRecessMinutes = endRecessMinutes;
    }

    public void setSu(boolean su) {
        this.su = su;
    }

    public void setMo(boolean mo) {
        this.mo = mo;
    }

    public void setTu(boolean tu) {
        this.tu = tu;
    }

    public void setWe(boolean we) {
        this.we = we;
    }

    public void setTh(boolean th) {
        this.th = th;
    }

    public void setFr(boolean fr) {
        this.fr = fr;
    }

    public void setSa(boolean sa) {
        this.sa = sa;
    }

    public static Schedule getScheduleFromFile(Context context) {
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

        String filename = context.getString(R.string.activity_schedule_filename);
        SharedPreferences scheduleFile = context.getSharedPreferences(filename, Context.MODE_PRIVATE);

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


        return new Schedule(scheduleEnable,
                            startDayHours,
                            startDayMinutes,
                            endDayHours,
                            endDayMinutes,
                            recessEnable,
                            startRecessHours,
                            startRecessMinutes,
                            endRecessHours,
                            endRecessMinutes,
                            su,
                            mo,
                            tu,
                            we,
                            th,
                            fr,
                            sa);
    }

    public static void saveScheduleToFile(Context context, Schedule schedule){
        String filename = context.getString(R.string.activity_schedule_filename);
        SharedPreferences scheduleFile = context.getSharedPreferences(filename, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = scheduleFile.edit();
        editor.putBoolean(scheduleEnableKey, schedule.scheduleEnable);
        editor.putInt(startDayHoursKey, schedule.startDayHours);
        editor.putInt(startDayMinutesKey, schedule.startDayMinutes);
        editor.putInt(endDayHoursKey, schedule.endDayHours);
        editor.putInt(endDayMinutesKey, schedule.endDayMinutes);
        editor.putBoolean(recessEnableKey, schedule.recessEnable);
        editor.putInt(startRecessHoursKey, schedule.startRecessHours);
        editor.putInt(startRecessMinutesKey, schedule.startRecessMinutes);
        editor.putInt(endRecessHoursKey, schedule.endRecessHours);
        editor.putInt(endRecessMinutesKey, schedule.endRecessMinutes);
        editor.putBoolean(suKey, schedule.su);
        editor.putBoolean(moKey, schedule.mo);
        editor.putBoolean(tuKey, schedule.tu);
        editor.putBoolean(weKey, schedule.we);
        editor.putBoolean(thKey, schedule.th);
        editor.putBoolean(frKey, schedule.fr);
        editor.putBoolean(saKey, schedule.sa);
        editor.apply();
    }

    public static String getStringTime(int hours, int minutes){
        return (String.valueOf(hours)+ ":" +String.valueOf(minutes));
    }
}
