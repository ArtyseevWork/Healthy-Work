package com.mordansoft.healthywork.presentation.ui.todo;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.mordansoft.healthywork.data.repository.AppStatusRepositoryImpl;
import com.mordansoft.healthywork.data.repository.ExerciseRepositoryImpl;
import com.mordansoft.healthywork.data.repository.StatisticRepositoryImpl;
import com.mordansoft.healthywork.data.storage.AppStatusStorageImpShPr;
import com.mordansoft.healthywork.data.storage.ExerciseStorageImplSqlite;
import com.mordansoft.healthywork.data.storage.StatisticStorageImplShPr;
import com.mordansoft.healthywork.domain.NotificationCreator;
import com.mordansoft.healthywork.domain.repository.AppStatusRepository;
import com.mordansoft.healthywork.domain.repository.StatisticRepository;
import com.mordansoft.healthywork.domain.usecase.exercise.GetExerciseByIdUc;
import com.mordansoft.healthywork.domain.usecase.statistic.AddDoneOneUc;
import com.mordansoft.healthywork.domain.usecase.statistic.AddSkippedOneUc;
import com.mordansoft.healthywork.domain.usecase.statistic.GetStatisticUc;
import com.mordansoft.healthywork.domain.usecase.status.GetAppStatusUc;
import com.mordansoft.healthywork.worker.NotificationCreatorImpl;

public class ToDoViewModelFactory implements ViewModelProvider.Factory{
    Context context;
    private final GetStatisticUc getStatisticUc;
    private final AddDoneOneUc addDoneOneUc;
    private final AddSkippedOneUc addSkippedOneUc;
    private final StatisticRepository statisticRepository;
    private  final AppStatusRepository appStatusRepository;
    private final GetAppStatusUc getAppStatusUc;
    private final GetExerciseByIdUc getExerciseByIdUc;
    private final NotificationCreator notificationCreator;

    public ToDoViewModelFactory(Context context) {
        this.context = context;
        notificationCreator = new NotificationCreatorImpl(context);
        statisticRepository = new StatisticRepositoryImpl(new StatisticStorageImplShPr(context));
        appStatusRepository = new AppStatusRepositoryImpl(new AppStatusStorageImpShPr(context));
        getStatisticUc      = new GetStatisticUc(statisticRepository);
        addDoneOneUc        = new AddDoneOneUc(statisticRepository, appStatusRepository, notificationCreator);
        addSkippedOneUc     = new AddSkippedOneUc(statisticRepository, appStatusRepository, notificationCreator);
        getAppStatusUc      = new GetAppStatusUc(appStatusRepository);
        getExerciseByIdUc   = new GetExerciseByIdUc(new ExerciseRepositoryImpl
                                                   (new ExerciseStorageImplSqlite(context)));

    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ToDoViewModel(getStatisticUc,
                                     addDoneOneUc,
                                     addSkippedOneUc,
                                     getAppStatusUc,
                                     getExerciseByIdUc);
    }

}




