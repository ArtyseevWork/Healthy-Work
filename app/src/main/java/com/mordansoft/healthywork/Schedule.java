package com.mordansoft.healthywork;

import static android.content.Context.ALARM_SERVICE;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.annotation.Nullable;
import java.util.Calendar;

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

    public void saveScheduleToFile(Context context){
        String filename = context.getString(R.string.activity_schedule_filename);
        SharedPreferences scheduleFile = context.getSharedPreferences(filename, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = scheduleFile.edit();
        editor.putBoolean(scheduleEnableKey, this.scheduleEnable);
        editor.putInt(startDayHoursKey, this.startDayHours);
        editor.putInt(startDayMinutesKey, this.startDayMinutes);
        editor.putInt(endDayHoursKey, this.endDayHours);
        editor.putInt(endDayMinutesKey, this.endDayMinutes);
        editor.putBoolean(recessEnableKey, this.recessEnable);
        editor.putInt(startRecessHoursKey, this.startRecessHours);
        editor.putInt(startRecessMinutesKey, this.startRecessMinutes);
        editor.putInt(endRecessHoursKey, this.endRecessHours);
        editor.putInt(endRecessMinutesKey, this.endRecessMinutes);
        editor.putBoolean(suKey, this.su);
        editor.putBoolean(moKey, this.mo);
        editor.putBoolean(tuKey, this.tu);
        editor.putBoolean(weKey, this.we);
        editor.putBoolean(thKey, this.th);
        editor.putBoolean(frKey, this.fr);
        editor.putBoolean(saKey, this.sa);
        editor.apply();
    }

    public static String getStringTime(int hours, int minutes){
        return (hours + ":" + minutes);
    }

    public boolean[] getWorkDaysArray(){
        boolean[] days = { this.su, this.mo, this.tu, this.we, this.th, this.fr, this.sa };
        return days;
    }

    public static Calendar getNextAlarmTime(Context context, @Nullable Calendar inCalendar){
        MordanSoftLogger.addLog("getNextAlarmTime START");
        if (inCalendar == null){
            inCalendar = Calendar.getInstance();
        }
        MordanSoftLogger.addLog("getNextAlarmTime  inCalendar = " + inCalendar.getTime());

        Calendar outCalendar = (Calendar) inCalendar.clone();
        Preferences preferences = Preferences.getPreferencesFromFile(context);
        Schedule schedule = Schedule.getScheduleFromFile(context);

        int countdown = preferences.getCountdown();
        int period = preferences.getPeriod();
        int dayOfWeakIn = inCalendar.get(Calendar.DAY_OF_WEEK);
        int dayOfYearIn= inCalendar.get(Calendar.DAY_OF_YEAR);

        if (schedule.isScheduleEnable()){ //schedule turn on
            boolean[] workDays = schedule.getWorkDaysArray();
            if (!workDays[dayOfWeakIn + 1]){ //today is not working day - processing next day
                //dayOfYear++;
                outCalendar.add(Calendar.DAY_OF_YEAR,1);
                //outCalendar.set(Calendar.DAY_OF_YEAR, dayOfYear);
                outCalendar = getNextAlarmTime(context, outCalendar);
            } else { //today is working day

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

                if (inCalendar.getTimeInMillis() <= startWorkDayMillis) { // before work day
                    outCalendar = getNextAlarmTimeSimple(context, workDayStartCalendar);
                } else if (inCalendar.getTimeInMillis() >= endWorkDayMillis - period) { // after work day
                    outCalendar.add(Calendar.DAY_OF_YEAR,1);
                    outCalendar.set(Calendar.HOUR_OF_DAY, workDayStartCalendar.get(Calendar.HOUR_OF_DAY));
                    outCalendar.set(Calendar.MINUTE, workDayStartCalendar.get(Calendar.MINUTE));
                    outCalendar = getNextAlarmTime(context, outCalendar);
                } else if (inCalendar.getTimeInMillis() > startWorkDayMillis  // in work day
                        && inCalendar.getTimeInMillis() < endWorkDayMillis - period) {
                    if (schedule.isRecessEnable() &&            //in recess
                            inCalendar.getTimeInMillis() > startRecessMillis - period //
                            && inCalendar.getTimeInMillis() < endRecessMillis - period) {
                        outCalendar = getNextAlarmTimeSimple(context, recessEndCalendar);
                    } else {
                        outCalendar = getNextAlarmTimeSimple(context, inCalendar);
                    }
                }
            }
        } else { //schedule turn off
            outCalendar = getNextAlarmTimeSimple(context, inCalendar);
        }
        MordanSoftLogger.addLog("getNextAlarmTime END: " + outCalendar.getTime());
        return outCalendar;
    }

    public static Calendar getNextAlarmTimeSimple(Context context, Calendar calendar){

        Preferences preferences = Preferences.getPreferencesFromFile(context);
        //Schedule schedule = Schedule.getScheduleFromFile(context);

        int minutesAlarm;
        int hoursAlarm;
        int hoursNow;
        int minutesNow;
        int countdown = preferences.getCountdown();
        int period = preferences.getPeriod();

        hoursNow = calendar.get(Calendar.HOUR_OF_DAY);
        minutesNow = calendar.get(Calendar.MINUTE);
        minutesAlarm = countdown;
        hoursAlarm = hoursNow;

        if (period == 30){
            if (minutesNow > (countdown+period)){
                hoursAlarm= hoursNow +1;
            }
            else if (minutesNow > (countdown)){
                minutesAlarm = countdown+period;
            }
        } else if (period == 60){
            if (minutesNow > countdown){
                hoursAlarm = hoursNow + 1;
            }
        }

        calendar.set(Calendar.HOUR_OF_DAY, hoursAlarm);
        calendar.set(Calendar.MINUTE, minutesAlarm);
        calendar.set(Calendar.SECOND, 0);

        return calendar;
    }

    public static Calendar run(Context context){
        MordanSoftLogger.addLog("Alarm run START " );
        Calendar nextAlarmTime = getNextAlarmTime(context, null);
        long intervalMs = (long) Preferences.getPreferencesFromFile(context).getPeriod()*60*1000;
        //intervalMs = 60*1000; // for debugging
        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT|  Intent.FILL_IN_DATA);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, nextAlarmTime.getTimeInMillis(),
                intervalMs,
                pendingIntent);

        //alarmManager.cancel(pendingIntent);
        //Toast.makeText(context, "someText",Toast.LENGTH_LONG).show();

        MordanSoftLogger.addLog("Alarm run END " );
        return nextAlarmTime;
    }

    public static void stop(Context context){
        MordanSoftLogger.addLog("Alarm.stop START " );
        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT|  Intent.FILL_IN_DATA);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);

        MordanSoftLogger.addLog("Alarm.stop END " );
    }
}
