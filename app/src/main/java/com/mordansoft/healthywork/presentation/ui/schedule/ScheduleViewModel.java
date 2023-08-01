package com.mordansoft.healthywork.presentation.ui.schedule;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mordansoft.healthywork.domain.model.Schedule;
import com.mordansoft.healthywork.domain.usecase.schedule.GetScheduleUc;
import com.mordansoft.healthywork.domain.usecase.schedule.UpdateScheduleUc;

public class ScheduleViewModel  extends ViewModel {

    private final GetScheduleUc getScheduleUc;
    private final UpdateScheduleUc updateScheduleUc;
    private MutableLiveData<Schedule> scheduleMutableLiveData;

    ScheduleViewModel(GetScheduleUc getScheduleUc,
                      UpdateScheduleUc updateScheduleUc){

        this.getScheduleUc = getScheduleUc;
        this.updateScheduleUc = updateScheduleUc;
    }

    public MutableLiveData<Schedule> getScheduleMutableLiveData() {
        if (scheduleMutableLiveData == null) {
            scheduleMutableLiveData = new MutableLiveData<Schedule>();
            getSchedule();
        }
        return scheduleMutableLiveData;
    }

    public void getSchedule(){
        scheduleMutableLiveData.setValue(getScheduleUc.execute());
    }

    public void updateSchedule(Schedule schedule){
        updateScheduleUc.execute(schedule);
    }
}
