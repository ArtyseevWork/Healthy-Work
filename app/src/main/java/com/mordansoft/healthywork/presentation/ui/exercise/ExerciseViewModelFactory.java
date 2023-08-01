package com.mordansoft.healthywork.presentation.ui.exercise;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.mordansoft.healthywork.data.repository.ExerciseRepositoryImpl;
import com.mordansoft.healthywork.data.storage.ExerciseStorageImplSqlite;
import com.mordansoft.healthywork.domain.repository.ExerciseRepository;
import com.mordansoft.healthywork.domain.usecase.exercise.DeleteExerciseUc;
import com.mordansoft.healthywork.domain.usecase.exercise.GetExerciseByIdUc;
import com.mordansoft.healthywork.domain.usecase.exercise.UpdateExerciseUc;

public class ExerciseViewModelFactory implements ViewModelProvider.Factory {
    Context context;
    private final ExerciseRepository exerciseRepository;
    private final DeleteExerciseUc deleteExerciseUc;
    private final UpdateExerciseUc updateExerciseUc;
    private final GetExerciseByIdUc getExerciseByIdUc;

    public ExerciseViewModelFactory(Context context) {
        this.context = context;
        this.exerciseRepository = new ExerciseRepositoryImpl(
                new ExerciseStorageImplSqlite(context));
        deleteExerciseUc = new DeleteExerciseUc(exerciseRepository);
        updateExerciseUc = new UpdateExerciseUc(exerciseRepository);
        getExerciseByIdUc = new GetExerciseByIdUc(exerciseRepository);
    }


    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ExerciseViewModel(getExerciseByIdUc, updateExerciseUc, deleteExerciseUc);
    }
}
