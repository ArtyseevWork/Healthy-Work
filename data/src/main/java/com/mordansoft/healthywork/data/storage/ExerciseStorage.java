package com.mordansoft.healthywork.data.storage;

import com.mordansoft.healthywork.data.model.ExerciseD;
import com.mordansoft.healthywork.domain.model.Exercise;

import java.util.ArrayList;

public interface ExerciseStorage {

    public boolean   deleteExercise  (ExerciseD exercise);
    public boolean   updateExercise  (ExerciseD exercise);
    public ExerciseD  getExerciseById (int exerciseId);
    public ArrayList<ExerciseD>  getExercisesByQuery (String query);

    public ExerciseD getDefaultExercise();


}
