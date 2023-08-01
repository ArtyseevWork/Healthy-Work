package com.mordansoft.healthywork.domain.usecase.exercise;

import com.mordansoft.healthywork.domain.model.Exercise;
import com.mordansoft.healthywork.domain.repository.ExerciseRepository;

public class GetExerciseByIdUc {

    ExerciseRepository exerciseRepository;

    public GetExerciseByIdUc(ExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }

    public Exercise execute(int id ){
        return exerciseRepository.getExerciseById(id);
    }
}
