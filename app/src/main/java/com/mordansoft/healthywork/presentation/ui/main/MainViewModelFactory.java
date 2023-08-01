package com.mordansoft.healthywork.presentation.ui.main;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.mordansoft.healthywork.data.repository.AppStatusRepositoryImpl;
import com.mordansoft.healthywork.data.repository.ExerciseRepositoryImpl;
import com.mordansoft.healthywork.data.repository.ScheduleRepositoryImpl;
import com.mordansoft.healthywork.data.repository.StatisticRepositoryImpl;
import com.mordansoft.healthywork.data.storage.AppStatusStorageImpShPr;
import com.mordansoft.healthywork.data.storage.ExerciseStorageImplSqlite;
import com.mordansoft.healthywork.data.storage.ScheduleStorageImplShPr;
import com.mordansoft.healthywork.data.storage.StatisticStorageImplShPr;
import com.mordansoft.healthywork.domain.AlarmCreator;
import com.mordansoft.healthywork.domain.repository.AppStatusRepository;
import com.mordansoft.healthywork.domain.repository.ExerciseRepository;
import com.mordansoft.healthywork.domain.repository.ScheduleRepository;
import com.mordansoft.healthywork.domain.repository.StatisticRepository;
import com.mordansoft.healthywork.domain.usecase.exercise.GetExerciseByIdUc;
import com.mordansoft.healthywork.domain.usecase.statistic.GetStatisticUc;
import com.mordansoft.healthywork.domain.usecase.status.ChangeNextExerciseUc;
import com.mordansoft.healthywork.domain.usecase.status.GetAppStatusUc;
import com.mordansoft.healthywork.domain.usecase.status.StartUc;
import com.mordansoft.healthywork.domain.usecase.status.StopUc;
import com.mordansoft.healthywork.worker.AlarmCreatorImpl;

public class MainViewModelFactory implements ViewModelProvider.Factory {
    Context context;
    private final GetAppStatusUc       getAppStatusUc;
    private final StartUc              startUc;
    private final StopUc               stopUc;
    private final ChangeNextExerciseUc changeNextExerciseUc;
    private final GetStatisticUc getStatisticUc;
    private final GetExerciseByIdUc getExerciseByIdUc;

    public MainViewModelFactory(Context context) {
        this.context = context;

        AppStatusRepository appStatusRepository = new AppStatusRepositoryImpl(new AppStatusStorageImpShPr(context));
        StatisticRepository statisticRepository = new StatisticRepositoryImpl(new StatisticStorageImplShPr(context));
        ExerciseRepository exerciseRepository = new ExerciseRepositoryImpl(new ExerciseStorageImplSqlite(context));
        ScheduleRepository scheduleRepository = new ScheduleRepositoryImpl(new ScheduleStorageImplShPr(context));
        AlarmCreator alarmCreator = new AlarmCreatorImpl(context);

        getAppStatusUc = new GetAppStatusUc(appStatusRepository);
        startUc = new StartUc(appStatusRepository,
                statisticRepository,
                exerciseRepository,
                scheduleRepository,
                alarmCreator);
        stopUc = new StopUc(appStatusRepository);
        changeNextExerciseUc = new ChangeNextExerciseUc(appStatusRepository, exerciseRepository);
        getStatisticUc = new GetStatisticUc(statisticRepository);
        getExerciseByIdUc = new GetExerciseByIdUc(exerciseRepository);

    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MainViewModel(getAppStatusUc,
                                     startUc,
                                     stopUc,
                                     changeNextExerciseUc,
                                     getStatisticUc,
                                     getExerciseByIdUc
                                     );
    }

}

