package com.kaanburaksener.bench.ui.fragment;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

/**
 * Created by kaanburaksener on 05/04/16.
 */
public class DatePickerFragment extends DialogFragment {
    OnDateSetListener onDateSet;
    private int year, month, day;

    public DatePickerFragment() {}

    public void setCallBack(OnDateSetListener ondate) {
        onDateSet = ondate;
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        year = args.getInt("year");
        month = args.getInt("month");
        day = args.getInt("day");
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new DatePickerDialog(getActivity(),  onDateSet, year, month, day);
    }
}
