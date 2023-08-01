package com.mordansoft.healthywork.presentation.ui.exercise;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.mordansoft.healthywork.domain.usecase.exercise.DeleteExerciseUc;
import com.mordansoft.healthywork.domain.usecase.exercise.GetExerciseByIdUc;
import com.mordansoft.healthywork.domain.usecase.exercise.UpdateExerciseUc;
import com.mordansoft.healthywork.domain.model.Exercise;

public class ExerciseViewModel extends ViewModel {

   private final DeleteExerciseUc deleteExerciseUc;
   private final UpdateExerciseUc updateExerciseUc;
   private final GetExerciseByIdUc getExerciseByIdUc;

   private MutableLiveData<Exercise> exercise;

    public ExerciseViewModel(GetExerciseByIdUc getExerciseByIdUc,
                             UpdateExerciseUc updateExerciseUc,
                             DeleteExerciseUc deleteExerciseUc ) {
        this.getExerciseByIdUc = getExerciseByIdUc;
        this.updateExerciseUc = updateExerciseUc;
        this.deleteExerciseUc = deleteExerciseUc;
    }

    public MutableLiveData<Exercise> getExercise(int exerciseId) {
        if (exercise == null) {
            exercise = new MutableLiveData<Exercise>();
            getExerciseById(exerciseId);
        }
        return exercise;
    }

    public void getExerciseById(int exerciseId){
        exercise.setValue(getExerciseByIdUc.execute(exerciseId));
    }
    public void updateExercise(Exercise exercise){
        updateExerciseUc.execute(exercise);
    }
    public void deleteExercise(Exercise exercise){
         deleteExerciseUc.execute(exercise);
    }

}
