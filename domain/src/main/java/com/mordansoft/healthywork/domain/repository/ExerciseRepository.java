package com.mordansoft.healthywork.domain.repository;

import com.mordansoft.healthywork.domain.model.Exercise;

import java.util.ArrayList;

public interface ExerciseRepository {

 public boolean   deleteExercise  (Exercise exercise);
 public boolean   updateExercise  (Exercise exercise);
 public Exercise  getExerciseById (int exerciseId);
 public ArrayList<Exercise>  getExercisesByQuery (String query);



}
