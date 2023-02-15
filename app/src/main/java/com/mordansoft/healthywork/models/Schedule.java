package com.mordansoft.healthywork.models;

import static android.content.Context.ALARM_SERVICE;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import com.mordansoft.healthywork.receivers.AlarmReceiver;
import com.mordansoft.healthywork.helpers.MordanSoftLogger;
import com.mordansoft.healthywork.R;
import com.mordansoft.healthywork.activity.MainActivity;

import java.text.DecimalFormat;
import java.util.Calendar;

public class Schedule {

    private static final String fileName = "schedule";
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
    private int period;
    private static final int periodDefault = 60;
    private static final String periodKey = "PERIOD";
    private int countdown;
    private static final int countdownDefault = 0;
    private static final String countdownKey = "COUNTDOWN";

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
                    boolean sa, int period, int countdown
                     ) {
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
        this.period = period;
        this.countdown = countdown;

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

    public int getPeriod() {
        return period;
    }

    public void setPeriod(Context context, int period) {
        this.period = period;
        SharedPreferences sharedPref = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(periodKey, period);
        editor.apply();
    }

    public int getCountdown() {
        return countdown;
    }

    public void setCountdown(Context context, int countdown) {
        this.countdown = countdown;
        SharedPreferences sharedPref = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(countdownKey, countdown);
        editor.apply();
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



        return new Schedule(scheduleEnable,
                            startDayHours, startDayMinutes,
                            endDayHours,    endDayMinutes,
                            recessEnable,
                            startRecessHours, startRecessMinutes,
                            endRecessHours, endRecessMinutes,
                            su, mo, tu, we, th, fr, sa,
                            period,  countdown);
    }

    public boolean check(Context context){
        boolean result = true;
        String error = "";
        if( !(this.su || this.mo || this.tu || this.we || this.th || this.fr ||this.sa) ){
            result = false;
            error = context.getString(R.string.activity_schedule_save_error_day_of_week);
        }

        if (this.startDayHours > this.endDayHours ||
                (this.startDayHours   == this.endDayHours
              && this.startDayMinutes >= this.endDayMinutes)){
            result = false;
            error += context.getString(R.string.activity_schedule_save_wrong_day_time);
        }

        if (this.startRecessHours > this.endRecessHours ||
                (this.startRecessHours   == this.endRecessHours
                        && this.startRecessMinutes >= this.endRecessMinutes)){
            result = false;
            error += context.getString(R.string.activity_schedule_save_wrong_break_time);
        }

        if (!result){
            checkDialog(context, error);
        }
        return  result;
    }

    public void saveScheduleToFile(Context context){
        SharedPreferences scheduleFile = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);

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
        editor.putInt(periodKey, this.period);
        editor.putInt(countdownKey, this.countdown);
        editor.apply();
    }

    public static String getStringTime(int hours, int minutes){
        String hh =new DecimalFormat( "00" ).format(hours);
        String mm =new DecimalFormat( "00" ).format(minutes);
        return (hh + ":" + mm);
    }

    public boolean[] getWorkDaysArray(){
        return new boolean[]{ this.su, this.mo, this.tu, this.we, this.th, this.fr, this.sa };
    }

    public static Calendar getNextAlarmTime(Context context, @Nullable Calendar inCalendar){
        MordanSoftLogger.addLog("getNextAlarmTime START");

        if (inCalendar == null){
            inCalendar = Calendar.getInstance();
        }

        if (false) {//debug
            inCalendar.setTimeInMillis(inCalendar.getTimeInMillis() + (30*1000));
            return inCalendar;
        }

        Calendar outCalendar = (Calendar) inCalendar.clone();
        Schedule schedule = Schedule.getScheduleFromFile(context);

        int period = schedule.getPeriod();
        int dayOfWeekIn = inCalendar.get(Calendar.DAY_OF_WEEK);

        if (schedule.isScheduleEnable()){                                            //schedule turn on
            boolean[] workDays = schedule.getWorkDaysArray();
            if (!workDays[dayOfWeekIn - 1]){                                         //today is not working day - processing next day
                outCalendar.add(Calendar.DAY_OF_YEAR,1);
                outCalendar = getNextAlarmTime(context, outCalendar);
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
                    outCalendar = getNextAlarmTimeSimple(context, workDayStartCalendar);
                } else if (inCalendar.getTimeInMillis() >= endWorkDayMillis - period) {         // after work day
                    outCalendar.add(Calendar.DAY_OF_YEAR,1);
                    outCalendar.set(Calendar.HOUR_OF_DAY, workDayStartCalendar.get(Calendar.HOUR_OF_DAY));
                    outCalendar.set(Calendar.MINUTE, workDayStartCalendar.get(Calendar.MINUTE));
                    outCalendar = getNextAlarmTime(context, outCalendar);
                } else if (inCalendar.getTimeInMillis() > startWorkDayMillis  // in work day
                        && inCalendar.getTimeInMillis() < endWorkDayMillis - period) {
                    if (false && schedule.isRecessEnable() &&            //in recess
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

        Schedule schedule = Schedule.getScheduleFromFile(context);

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

    public static Calendar run(Context context){
        MordanSoftLogger.addLog("Schedule run START " );
        Calendar nextAlarmTime = getNextAlarmTime(context, null);
        PendingIntent pendingIntent = getPendingIntent(context);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, nextAlarmTime.getTimeInMillis(), pendingIntent);
        } else {
            //alarmManager.setAlarmClock(new AlarmManager.AlarmClockInfo(nextAlarmTime.getTimeInMillis(),pendingIntent),pendingIntent);
            alarmManager.setExact(AlarmManager.RTC_WAKEUP,nextAlarmTime.getTimeInMillis(),pendingIntent);
            /*alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, nextAlarmTime.getTimeInMillis(),
                    intervalMs,
                    pendingIntent);*/
        }
        MordanSoftLogger.addLog("Schedule run END " );
        return nextAlarmTime;
    }

    public static void stop(Context context){
        MordanSoftLogger.addLog("Schedule.stop START " );
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        alarmManager.cancel(getPendingIntent(context));
        MordanSoftLogger.addLog("Schedule.stop END " );
    }


    private static PendingIntent getPendingIntent(Context context){
        int flags;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            flags = PendingIntent.FLAG_UPDATE_CURRENT|  Intent.FILL_IN_DATA | PendingIntent.FLAG_IMMUTABLE;
        } else {
            flags = PendingIntent.FLAG_UPDATE_CURRENT|  Intent.FILL_IN_DATA;
        }
        Intent intent = new Intent(context, AlarmReceiver.class);
        return PendingIntent.getBroadcast(context,
                0,
                intent, flags);
    }

    public static void checkDialog(Context context, String text) {
        text += context.getString(R.string.activity_schedule_save_error_question);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Save Error");
        builder.setMessage(text);
        builder.setPositiveButton(context.getString(R.string.yes), (dialog, id) -> {
            Intent intent = new Intent(context, MainActivity.class);
            context.startActivity(intent);
        });
        builder.setNegativeButton(context.getString(R.string.no), (dialog, id) -> dialog.cancel());
        builder.show();
    }

}
