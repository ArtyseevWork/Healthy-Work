package com.mordansoft.healthywork;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.mordansoft.healthywork.databinding.ActivityScheduleBinding;

public class ScheduleActivity extends AppCompatActivity implements SetTime {

    private ActivityScheduleBinding binding;
    private Schedule schedule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        init();
        updateUi();
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
        schedule = Schedule.getScheduleFromFile(this);
        binding.etScheduleStartDay.setOnClickListener(setTimeListener);
        binding.etScheduleEndDay.setOnClickListener(setTimeListener);
        binding.etScheduleStartRecess.setOnClickListener(setTimeListener);
        binding.etScheduleEndRecess.setOnClickListener(setTimeListener);
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
        binding.etScheduleStartDay.setText(Schedule.getStringTime(schedule.getStartDayHours(),
                schedule.getStartDayMinutes()));
        binding.etScheduleEndDay.setText(Schedule.getStringTime(schedule.getEndDayHours(),
                schedule.getEndDayMinutes()));
        binding.etScheduleStartRecess.setText(Schedule.getStringTime(schedule.getStartRecessHours(),
                schedule.getStartRecessMinutes()));
        binding.etScheduleEndRecess.setText(Schedule.getStringTime(schedule.getEndRecessHours(),
                schedule.getEndRecessMinutes()));
        MordanSoftLogger.addLog("ScheduleActivity.updateUi END");
    }

    View.OnClickListener btnBackListener = v -> goBack();

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
            args.putInt("VIEWID", viewId); //todo "_" in words
            newFragment.setArguments(args);

            newFragment.show(getSupportFragmentManager(), "ScheduleActivity");

        } catch(Exception e) {
            MordanSoftLogger.addLog("showTimePickerDialog - " + e, 'e');
        }
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

    private void saveState(){
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

            schedule.saveScheduleToFile(this);
            Toast.makeText(this, getString(R.string.activity_preferences_save_message), Toast.LENGTH_LONG).show();
        } catch(Exception e) {
            MordanSoftLogger.addLog("Schedule Activity saveState - " + e, 'e');
        }
    }

    public void goBack() {
        if (schedule.check(this)) {
            saveState();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        goBack();
    }
}