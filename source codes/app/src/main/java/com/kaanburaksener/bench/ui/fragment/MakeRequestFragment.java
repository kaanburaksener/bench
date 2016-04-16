package com.kaanburaksener.bench.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View.OnClickListener;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.kaanburaksener.bench.db.DBHandler;
import com.kaanburaksener.bench.handler.RequestHandler;
import com.kaanburaksener.bench.R;

/**
 * Created by kaanburaksener on 31/03/16.
 */

public class MakeRequestFragment extends BaseFragment {
    private EditText titleET;
    private EditText descriptionET;
    private EditText locationET;
    private TextView dateTV;
    private TextView timeTV;
    private Button makeRequestButton;
    private Spinner playerPositionSpinner;
    private DBHandler dbHandler;

    private String title;
    private String description;
    private String location;
    private String date = "";
    private String time = "";
    private String playerPosition;
    private String dt = ""; //DateTime Format

    private int ownerID;
    private int statusID = 1; //Opened as default
    private int playerPositionID;

    private List<String> playerPositions;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_make_request,container,false);
        initializer(v);
        return v;
    }

    /**
     * This function is used to initialize the layout elements and the attributes of the class
     */

    private void initializer(View v) {
        makeRequestButton = (Button) v.findViewById(R.id.makeRequestButton);
        titleET = (EditText) v.findViewById(R.id.title);
        descriptionET = (EditText) v.findViewById(R.id.description);
        locationET = (EditText) v.findViewById(R.id.location);
        dateTV = (TextView) v.findViewById(R.id.date);
        timeTV = (TextView) v.findViewById(R.id.time);
        playerPositionSpinner = (Spinner) v.findViewById(R.id.playerPosition);

        dbHandler = new DBHandler(mainActivity);
        playerPositions = new ArrayList<String>();

        addItemsOnSpinner();
        addListenerForCreateButton();
        addListenerForDatePicker();
        addListenerForTimePicker();
    }

    /**
     * This function is used to initialize the spinner - drop down list dynamically from database
     */

    public void addItemsOnSpinner() {
        playerPositions = dbHandler.getPlayerPositions();
        ArrayAdapter dataAdapter = new ArrayAdapter(this.getActivity(),android.R.layout.simple_spinner_item, playerPositions);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        playerPositionSpinner.setAdapter(dataAdapter);
    }

    /**
     * This function is used to trigger creating a new request process
     */

    public void addListenerForCreateButton() {
        makeRequestButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                title = titleET.getText().toString();
                description = descriptionET.getText().toString();
                location = locationET.getText().toString();
                playerPosition = String.valueOf(playerPositionSpinner.getSelectedItem());
                ownerID = dbHandler.getUserId();
                playerPositionID = dbHandler.getPlayerPositionID(playerPosition);
                dt = date + " " + time;

                if (checkFormData()) {
                    RequestHandler.performCreateNewRequest(title, description, location, playerPositionID, dt, statusID, ownerID, mainActivity, mainActivity.getApplicationContext(), mainActivity.getWindow().getContext());
                }
            }
        });
    }

    /**
     * This function is used to check the validity of form data
     */

    public boolean checkFormData() {
        boolean result = true;

        if (title.isEmpty()) {
            result = false;
            Toast.makeText(getActivity().getApplicationContext(), "Title is empty", Toast.LENGTH_SHORT).show();
        } else if (description.isEmpty() || description.length() > 320) {
            result = false;
            Toast.makeText(getActivity().getApplicationContext(),"Description is empty", Toast.LENGTH_SHORT).show();
        } else if (location.isEmpty()) {
            result = false;
            Toast.makeText(getActivity().getApplicationContext(), "Location is empty", Toast.LENGTH_SHORT).show();
        } else if (dt.isEmpty() || dt == null) {
            result = false;
            Toast.makeText(getActivity().getApplicationContext(), "Date is empty", Toast.LENGTH_SHORT).show();
        }

        return result;
    }

    /**
     * This function is used to pick up the date
     */

    public void addListenerForDatePicker() {
        dateTV.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });
    }

    /**
     * This function is used to pick up the time
     */

    public void addListenerForTimePicker() {
        timeTV.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker();
            }
        });
    }

    /**
     * This function is used to show the calender as a fragment
     */

    private void showDatePicker() {
        DatePickerFragment date = new DatePickerFragment();

        Calendar calender = Calendar.getInstance();
        Bundle args = new Bundle();
        args.putInt("year", calender.get(Calendar.YEAR));
        args.putInt("month", calender.get(Calendar.MONTH));
        args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));
        date.setArguments(args);

        date.setCallBack(ondate);
        date.show(getFragmentManager(), "Date Picker");
    }

    /**
     * This function is used to show the calender as a fragment
     */

    private void showTimePicker() {
        TimePickerFragment time = new TimePickerFragment();

        Calendar calender = Calendar.getInstance();
        Bundle args = new Bundle();
        args.putInt("hour", calender.get(Calendar.HOUR_OF_DAY));
        args.putInt("minute", calender.get(Calendar.MINUTE));
        time.setArguments(args);

        time.setCallBack(ontime);
        time.show(getFragmentManager(), "Time Picker");
    }

    /**
     * This function is used to set picked date to the textview
     */

    OnDateSetListener ondate = new OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            monthOfYear++; //Months are indexed from 0 to 11
            String month = String.valueOf(monthOfYear);
            String day = String.valueOf(dayOfMonth);

            if(monthOfYear < 10) {
                month = "0" + String.valueOf(monthOfYear);
            }

            if(dayOfMonth < 10) {
                day = "0" + String.valueOf(dayOfMonth);
            }

            date = String.valueOf(year)+ "-" + month + "-" + day;
            dateTV.setText(date);
        }
    };

    /**
     * This function is used to set picked time to the textview
     */

    OnTimeSetListener ontime = new OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute){
            String hour = String.valueOf(hourOfDay);
            String min = String.valueOf(minute);

            if(hourOfDay < 10) {
                hour = "0" + String.valueOf(hourOfDay);
            }

            if(minute < 10) {
                min = "0" + String.valueOf(minute);
            }

            time = hour + ":" + min + ":00";
            timeTV.setText(time);
        }
    };


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
