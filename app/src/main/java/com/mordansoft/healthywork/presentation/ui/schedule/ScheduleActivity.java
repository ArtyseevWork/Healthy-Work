package com.mordansoft.healthywork.presentation.ui.schedule;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.mordansoft.healthywork.databinding.ActivityScheduleBinding;
import com.mordansoft.healthywork.helpers.MordanSoftLogger;
import com.mordansoft.healthywork.R;
import com.mordansoft.healthywork.domain.model.Schedule;
import com.mordansoft.healthywork.presentation.ui.main.MainActivity;

import java.text.DecimalFormat;

public class ScheduleActivity extends AppCompatActivity implements SetTime {

    private ActivityScheduleBinding binding;
    private Schedule schedule;
    private ScheduleViewModel vm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        init();
        //updateUi();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    public void init(){
        binding = ActivityScheduleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.btnScheduleSave.setOnClickListener(btnBackListener);
        binding.btnScheduleBack.setOnClickListener(btnBackListener);
        //schedule = Schedule.getScheduleFromFile(this);
        binding.etScheduleStartDay.setOnClickListener(setTimeListener);
        binding.etScheduleEndDay.setOnClickListener(setTimeListener);
        binding.etScheduleStartRecess.setOnClickListener(setTimeListener);
        binding.etScheduleEndRecess.setOnClickListener(setTimeListener);

        binding.btnScheduleMinusCount.setOnClickListener(btnMinusListener);
        binding.btnSchedulePlusCount.setOnClickListener(btnPlusListener);

        vm = new ViewModelProvider(this,
                new ScheduleViewModelFactory(this))
                .get(ScheduleViewModel.class);

        vm.getScheduleMutableLiveData().observe(this, scheduleObserver);
    }

    private void updateUi() {
        MordanSoftLogger.addLog("ScheduleActivity.updateUi START");
        binding.chbScheduleAvailable.setChecked(schedule.isScheduleEnable());
        binding.chbScheduleSu.setChecked(schedule.isSu());
        binding.chbScheduleMo.setChecked(schedule.isMo());
        binding.chbScheduleTu.setChecked(schedule.isTu());
        binding.chbScheduleWe.setChecked(schedule.isWe());
        binding.chbScheduleTh.setChecked(schedule.isTh());
        binding.chbScheduleFr.setChecked(schedule.isFr());
        binding.chbScheduleSa.setChecked(schedule.isSa());
        binding.chbScheduleRecessEnable.setChecked(schedule.isRecessEnable());
        binding.etScheduleStartDay.setText(getStringTime(schedule.getStartDayHours(),
                schedule.getStartDayMinutes()));
        binding.etScheduleEndDay.setText(getStringTime(schedule.getEndDayHours(),
                schedule.getEndDayMinutes()));
        binding.etScheduleStartRecess.setText(getStringTime(schedule.getStartRecessHours(),
                schedule.getStartRecessMinutes()));
        binding.etScheduleEndRecess.setText(getStringTime(schedule.getEndRecessHours(),
                schedule.getEndRecessMinutes()));

        binding.tvScheduleCountdown.setText(String.valueOf(schedule.getCountdown()));

        int period = schedule.getPeriod();
        if (String.valueOf(period).equals(getString(R.string.activity_preferences_half_hour))){
            binding.swSchedulePeriod.setChecked(false);
        } else if (String.valueOf(period).equals(getString(R.string.activity_preferences_hour))){
            binding.swSchedulePeriod.setChecked(true);
        }
        MordanSoftLogger.addLog("ScheduleActivity.updateUi END");
    }

    final private Observer<Schedule> scheduleObserver = new Observer<Schedule>() {
        @Override
        public void onChanged(@Nullable final Schedule newSchedule) {
            // Update the UI, in this case, a TextView.
            schedule = newSchedule;
            updateUi();
        }
    };

    View.OnClickListener btnBackListener = v -> goBack();

    View.OnClickListener btnPlusListener = v -> changeCount(5);
    View.OnClickListener btnMinusListener = v -> changeCount(-5);

    View.OnClickListener setTimeListener = v -> {
        switch (v.getId()){
            case(R.id.et_schedule_start_day):
                this.showTimePickerDialog(R.id.et_schedule_start_day,
                        this.schedule.getStartDayHours(),
                        this.schedule.getStartDayMinutes());
                break;
            case(R.id.et_schedule_end_day):
                this.showTimePickerDialog(R.id.et_schedule_end_day,
                        this.schedule.getEndDayHours(),
                        this.schedule.getEndDayMinutes());
                break;
            case(R.id.et_schedule_start_recess):
                this.showTimePickerDialog(R.id.et_schedule_start_recess,
                        this.schedule.getStartRecessHours(),
                        this.schedule.getStartRecessMinutes());
                break;
            case(R.id.et_schedule_end_recess):
                this.showTimePickerDialog(R.id.et_schedule_end_recess,
                        this.schedule.getEndRecessHours(),
                        this.schedule.getEndRecessMinutes());
                break;
        }

    };

    private void showTimePickerDialog(int viewId, int hours, int minutes){
        try {
            DialogFragment newFragment = new TimePickerFragment();
            Bundle args = new Bundle();
            args.putInt("MINUTE", minutes);
            args.putInt("HOUR", hours);
            args.putInt("VIEW_ID", viewId); //todo "_" in words
            newFragment.setArguments(args);

            newFragment.show(getSupportFragmentManager(), "ScheduleActivity");

        } catch(Exception e) {
            MordanSoftLogger.addLog("showTimePickerDialog - " + e, 'e');
        }
    }

    private void changeCount(int delta){
        int count = Integer.parseInt(String.valueOf(binding.tvScheduleCountdown.getText())) + delta;
        if (count > 59){
            count = 0;
        } else if (count < 0){
            count = 55;
        }
        binding.tvScheduleCountdown.setText(String.valueOf(count) );
    }

    @Override
    public void setTime(int id, int hours, int minutes) {
        switch (id){
            case (R.id.et_schedule_start_day):
                 schedule.setStartDayHours(hours);
                 schedule.setStartDayMinutes(minutes);
                 break;
            case (R.id.et_schedule_end_day):
                 schedule.setEndDayHours(hours);
                 schedule.setEndDayMinutes(minutes);
                 break;
            case (R.id.et_schedule_start_recess):
                 schedule.setStartRecessHours(hours);
                 schedule.setStartRecessMinutes(minutes);
                 break;
            case (R.id.et_schedule_end_recess):
                 schedule.setEndRecessHours(hours);
                 schedule.setEndRecessMinutes(minutes);
                 break;
        }
        updateUi();
    }

    private String getStringTime(int hours, int minutes){
        String hh =new DecimalFormat( "00" ).format(hours);
        String mm =new DecimalFormat( "00" ).format(minutes);
        return (hh + ":" + mm);
    }

    private boolean saveState(){
        boolean result = false;
        try {
            this.schedule.setScheduleEnable(binding.chbScheduleAvailable.isChecked());
            this.schedule.setSu(binding.chbScheduleSu.isChecked());
            this.schedule.setMo(binding.chbScheduleMo.isChecked());
            this.schedule.setTu(binding.chbScheduleTu.isChecked());
            this.schedule.setWe(binding.chbScheduleWe.isChecked());
            this.schedule.setTh(binding.chbScheduleTh.isChecked());
            this.schedule.setFr(binding.chbScheduleFr.isChecked());
            this.schedule.setSa(binding.chbScheduleSa.isChecked());
            this.schedule.setRecessEnable(binding.chbScheduleRecessEnable.isChecked());

            String countdownStr = String.valueOf(binding.tvScheduleCountdown.getText());
            int countdownInt = Integer.parseInt(countdownStr);
            schedule.setCountdown(countdownInt);
            if (binding.swSchedulePeriod.isChecked()){
                schedule.setPeriod(
                        Integer.parseInt(getString(R.string.activity_preferences_hour)));
            } else {
                schedule.setPeriod(
                        Integer.parseInt(getString(R.string.activity_preferences_half_hour)));
            }
            vm.updateSchedule(schedule);
            Toast.makeText(this, getString(R.string.activity_preferences_save_message), Toast.LENGTH_LONG).show();
            result = true;


        } catch(Exception e) {
            MordanSoftLogger.addLog("Schedule Activity saveState - " + e, 'e');
        }
        return result;
    }

    public void goBack() {
        if (saveState()) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        goBack();
    }
}