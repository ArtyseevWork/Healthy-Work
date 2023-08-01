package com.mordansoft.healthywork.domain.model;

public class Statistic {

    public static String exerciseDeltaKey ="EXERCISE_DELTA";

    private int countOfExerciseDone;
    private int countOfExerciseSkipped;

    public Statistic(int countOfExerciseDone, int countOfExerciseSkipped) {
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
