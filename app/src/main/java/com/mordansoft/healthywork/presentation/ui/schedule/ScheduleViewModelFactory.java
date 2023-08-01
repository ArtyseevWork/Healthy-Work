package com.mordansoft.healthywork.presentation.ui.schedule;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.mordansoft.healthywork.data.repository.ScheduleRepositoryImpl;
import com.mordansoft.healthywork.data.storage.ScheduleStorageImplShPr;
import com.mordansoft.healthywork.domain.repository.ScheduleRepository;
import com.mordansoft.healthywork.domain.usecase.schedule.GetScheduleUc;
import com.mordansoft.healthywork.domain.usecase.schedule.UpdateScheduleUc;

public class ScheduleViewModelFactory implements ViewModelProvider.Factory{

    Context context;
    private final GetScheduleUc       getScheduleUc;
    private final UpdateScheduleUc    updateScheduleUc;

    public ScheduleViewModelFactory(Context context) {
        this.context = context;

        ScheduleRepository scheduleRepository = new ScheduleRepositoryImpl(
                new ScheduleStorageImplShPr(this.context));

        getScheduleUc = new GetScheduleUc(scheduleRepository);
        updateScheduleUc = new UpdateScheduleUc(scheduleRepository);

    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ScheduleViewModel(getScheduleUc,
        updateScheduleUc);
    }
}
