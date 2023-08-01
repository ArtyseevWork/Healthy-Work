package com.mordansoft.healthywork.presentation.ui.exercises;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mordansoft.healthywork.domain.model.Exercise;
import com.mordansoft.healthywork.domain.usecase.exercise.GetAllExercisesUc;

import java.util.ArrayList;

public class ExercisesViewModel extends ViewModel {

    private GetAllExercisesUc getAllExercisesUc;

    private MutableLiveData<ArrayList<Exercise>> allExercises;

    public ExercisesViewModel(GetAllExercisesUc getAllExercisesUc) {
        this.getAllExercisesUc = getAllExercisesUc;
    }


    public MutableLiveData<ArrayList<Exercise>> getExercises() {
        if (allExercises == null) {
            allExercises = new MutableLiveData<ArrayList<Exercise>>();
            getAllExercises();
        }
        return allExercises;
    }


    public void getAllExercises(){
        allExercises.setValue(getAllExercisesUc.execute("1=1"));
    }



}
