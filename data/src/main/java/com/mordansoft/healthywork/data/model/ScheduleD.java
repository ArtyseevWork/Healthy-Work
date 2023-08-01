package com.mordansoft.healthywork.data.model;



public class ScheduleD {

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
    private int period;
    private int countdown;

    public ScheduleD(boolean scheduleEnable,
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

    public void setPeriod(int period) {
        this.period = period;
    }

    public int getCountdown() {
        return countdown;
    }

    public void setCountdown(int countdown) {
        this.countdown = countdown;
    }



}
