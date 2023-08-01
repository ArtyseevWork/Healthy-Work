package com.mordansoft.healthywork.data.model;

public class StatisticD {

    private int countOfExerciseDone;
    private int countOfExerciseSkipped;

    public StatisticD(int countOfExerciseDone, int countOfExerciseSkipped) {
        this.countOfExerciseDone = countOfExerciseDone;
        this.countOfExerciseSkipped = countOfExerciseSkipped;
    }

    public int getCountOfExerciseDone() {
        return countOfExerciseDone;
    }

    public void setCountOfExerciseDone(int countOfExerciseDone) {
        this.countOfExerciseDone = countOfExerciseDone;
    }

    public int getCountOfExerciseSkipped() {
        return countOfExerciseSkipped;
    }

    public void setCountOfExerciseSkipped(int countOfExerciseSkipped) {
        this.countOfExerciseSkipped = countOfExerciseSkipped;
    }
}
