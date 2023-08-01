package com.mordansoft.healthywork.presentation.ui.exercises;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.mordansoft.healthywork.data.repository.ExerciseRepositoryImpl;
import com.mordansoft.healthywork.data.storage.ExerciseStorageImplSqlite;
import com.mordansoft.healthywork.domain.repository.ExerciseRepository;
import com.mordansoft.healthywork.domain.usecase.exercise.GetAllExercisesUc;

public class ExercisesViewModelFactory implements ViewModelProvider.Factory {
    Context context;
    private final ExerciseRepository exerciseRepository;
    private final GetAllExercisesUc getAllExercisesUc;
    public ExercisesViewModelFactory(Context context) {
        this.context = context;
        exerciseRepository = new ExerciseRepositoryImpl(
                new ExerciseStorageImplSqlite(this.context));
        getAllExercisesUc = new GetAllExercisesUc(exerciseRepository);
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ExercisesViewModel(getAllExercisesUc);
    }
}
