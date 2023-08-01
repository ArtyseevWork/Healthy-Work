package com.mordansoft.healthywork.domain.usecase.exercise;

import com.mordansoft.healthywork.domain.model.Exercise;
import com.mordansoft.healthywork.domain.repository.ExerciseRepository;

import java.util.ArrayList;

public class GetAllExercisesUc {

    ExerciseRepository exerciseRepository;

    public GetAllExercisesUc(ExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }

    public ArrayList<Exercise> execute(String query ){
        return exerciseRepository.getExercisesByQuery(query);
    }
}
