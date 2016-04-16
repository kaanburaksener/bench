package com.kaanburaksener.bench.ui.fragment;

import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;

/**
 * Created by kaanburaksener on 05/04/16.
 */
public class TimePickerFragment extends DialogFragment {
    OnTimeSetListener onTimeSet;
    private int hour, minute;

    public TimePickerFragment() {}

    public void setCallBack(TimePickerDialog.OnTimeSetListener ontime) {
        onTimeSet = ontime;
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        hour = args.getInt("hour");
        minute = args.getInt("minute");
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new TimePickerDialog(getActivity(), onTimeSet, hour, minute, DateFormat.is24HourFormat(getActivity()));
    }
}
