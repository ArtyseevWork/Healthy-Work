package com.mordansoft.healthywork.domain.usecase.exercise;

import com.mordansoft.healthywork.domain.model.Exercise;
import com.mordansoft.healthywork.domain.repository.ExerciseRepository;

public class UpdateExerciseUc {

    ExerciseRepository exerciseRepository;

    public UpdateExerciseUc(ExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }

    public boolean execute(Exercise exercise){
        return exerciseRepository.updateExercise(exercise);
    }
}
