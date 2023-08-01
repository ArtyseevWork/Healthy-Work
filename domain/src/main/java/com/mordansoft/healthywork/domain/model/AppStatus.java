package com.mordansoft.healthywork.domain.model;

public class AppStatus {

    public static final int applicationStatusNew = 0;
    public static final int applicationStatusStopped = 1;
    public static final int applicationStatusPending = 100;
    public static final int applicationStatusActive = 200;
    private int applicationStatus;
    private long nextAlarmTime;
    private int nextExerciseId;
    private int currentExerciseId;

    public AppStatus(int applicationStatus, long nextAlarmTime, int nextExerciseId, int currentExerciseId) {
        this.applicationStatus = applicationStatus;
        this.nextAlarmTime = nextAlarmTime;
        this.nextExerciseId = nextExerciseId;
        this.currentExerciseId = currentExerciseId;
    }

    public int getApplicationStatus() {
        return applicationStatus;
    }

    public void setApplicationStatus(int applicationStatus) {
        this.applicationStatus = applicationStatus;
    }

    public long getNextAlarmTime() {
        return nextAlarmTime;
    }

    public void setNextAlarmTime(long nextAlarmTime) {
        this.nextAlarmTime = nextAlarmTime;
    }

    public int getNextExerciseId() {
        return nextExerciseId;
    }

    public void setNextExerciseId(int nextExerciseId) {
        this.nextExerciseId = nextExerciseId;
    }

    public int getCurrentExerciseId() {
        return currentExerciseId;
    }

    public void setCurrentExerciseId(int currentExerciseId) {
        this.currentExerciseId = currentExerciseId;
    }
}
