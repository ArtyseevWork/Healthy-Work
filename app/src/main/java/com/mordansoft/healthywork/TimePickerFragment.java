package com.mordansoft.healthywork;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public class TimePickerFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {

    SetTime setTime;
    int hour;
    int minute;
    int viewId;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        assert getArguments() != null;
        this.minute = getArguments().getInt("MINUTE");
        this.hour = getArguments().getInt("HOUR");
        this.viewId = getArguments().getInt("VIEWID");

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, this.hour, this.minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    @Override
    public void onAttach(@NonNull Context context){
        super.onAttach(context);
        setTime = (SetTime) context;
    }

    public void onTimeSet(TimePicker view, int hours, int minutes) {
        setTime.setTime(this.viewId, hours, minutes);
        // Do something with the time chosen by the user

    }
}
