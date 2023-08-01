package com.mordansoft.healthywork.data.repository;

import com.mordansoft.healthywork.data.model.ExerciseD;
import com.mordansoft.healthywork.data.storage.ExerciseStorage;
import com.mordansoft.healthywork.domain.model.Exercise;
import com.mordansoft.healthywork.domain.repository.ExerciseRepository;

import java.util.ArrayList;


public class ExerciseRepositoryImpl  implements ExerciseRepository {

    private final ExerciseStorage exerciseStorage;

    public ExerciseRepositoryImpl(ExerciseStorage exerciseStorage) {
        this.exerciseStorage = exerciseStorage;
    }

    public boolean  deleteExercise (Exercise exercise){
        return exerciseStorage.deleteExercise(exerciseToExerciseD(exercise));
    }

    public boolean  updateExercise (Exercise exercise){
        exerciseStorage.updateExercise(exerciseToExerciseD(exercise));
        return true;
    }
    public Exercise getExerciseById(int exerciseId){

       if (exerciseId == 0 ){
           return exerciseDToExercise(exerciseStorage.getDefaultExercise());
       }
       return exerciseDToExercise(exerciseStorage.getExerciseById(exerciseId));
    }

    @Override
    public ArrayList<Exercise> getExercisesByQuery(String query) {
        ArrayList<ExerciseD> result = exerciseStorage.getExercisesByQuery(query);
        return arrayExerciseDToExercise(result);
    }

    /*********** mappers *************/

    private ExerciseD exerciseToExerciseD(Exercise exercise){
        ExerciseD exerciseD = new ExerciseD(
                exercise.getId(),
                exercise.getName(),
                exercise.getDescription(),
                exercise.getUnit(),
                exercise.getCount(),
                exercise.isEnable()
        );
        return exerciseD;
    }

    private Exercise exerciseDToExercise(ExerciseD exerciseD){
        Exercise exercise = new Exercise(
                exerciseD.getId(),
                exerciseD.getName(),
                exerciseD.getDescription(),
                exerciseD.getUnit(),
                exerciseD.getCount(),
                exerciseD.isEnable()
        );
        return exercise;
    }

    private ArrayList<Exercise> arrayExerciseDToExercise(ArrayList<ExerciseD> arrayExerciseD){

        ArrayList<Exercise> arrayExercise = new ArrayList<>();
        for (ExerciseD e: arrayExerciseD ){
           arrayExercise.add(exerciseDToExercise(e));
        }

        return arrayExercise;
    }

    private ArrayList<ExerciseD> arrayExerciseToExerciseD(ArrayList<Exercise> arrayExercise){

        ArrayList<ExerciseD> arrayExerciseD = new ArrayList<>();
        for (Exercise e: arrayExercise ){
            arrayExerciseD.add(exerciseToExerciseD(e));
        }

        return arrayExerciseD;
    }


    /* ******* ! mappers *************/

}
