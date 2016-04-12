package com.kaanburaksener.bench.ui;

import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kaanburaksener.bench.R;
import com.kaanburaksener.bench.db.DBHandler;
import com.kaanburaksener.bench.handler.AccountHandler;
import com.kaanburaksener.bench.ui.fragment.DatePickerFragment;

import java.util.Calendar;

/**
 * Created by kaanburaksener on 10/04/16.
 */
public class EditProfileActivity extends AppCompatActivity {
    private DBHandler dbHandler;
    private Button updateButton;
    private AccountHandler accountHandler;
    private TextView birthdayTV;
    private EditText locationET;
    private int userID;
    private String location;
    private String birthday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        initializer();
    }

    /**
     * This function is used to initialize the layout elements and the attributes of the class
     */

    private void initializer() {
        setStatusBarColor();
        dbHandler = new DBHandler(this);
        userID = dbHandler.getUserId();
        accountHandler = new AccountHandler(this, this.getApplicationContext(), this.getWindow().getContext());
        locationET = (EditText) findViewById(R.id.location);
        birthdayTV = (TextView) findViewById(R.id.birthday);
        updateButton = (Button) findViewById(R.id.updateButton);
        updateButton.setOnClickListener(updateProfileHandler);
        addListenerForDatePicker();
    }

    /**
     * This function is used to change the status bar color
     */

    private void setStatusBarColor() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                getWindow().setStatusBarColor(getResources().getColor(R.color.color_primary_dark));
            }
        } catch (NumberFormatException e) {
            Log.d("Status bar set error: ", e.toString());
        }
    }

    /**
     * This function is used to trigger creating a new request process
     */

    View.OnClickListener updateProfileHandler = new View.OnClickListener() {
        public void onClick(View v) {
            if(checkFormData()) {
                accountHandler.receiveData(userID, location, birthday);
                accountHandler.performUpdate();
            }
        }
    };

    public boolean checkFormData() {
        boolean result = true;

        location = locationET.getText().toString();

        if (location.isEmpty()) {
            result = false;
            Toast.makeText(getApplicationContext(), "Please fill the location field", Toast.LENGTH_LONG).show();
        } else if (birthday.isEmpty()) {
            result = false;
            Toast.makeText(getApplicationContext(), "Please enter your birthday", Toast.LENGTH_LONG).show();
        }

        return result;
    }

    /**
     * This function is used to pick up the date
     */

    public void addListenerForDatePicker() {
        birthdayTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });
    }

    /**
     * This function is used to show the calender as a fragment
     */

    private void showDatePicker() {
        DatePickerFragment date = new DatePickerFragment();
        /**
         * Set Up Current Date Into dialog
         */
        Calendar calender = Calendar.getInstance();
        Bundle args = new Bundle();
        args.putInt("year", calender.get(Calendar.YEAR));
        args.putInt("month", calender.get(Calendar.MONTH));
        args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));
        date.setArguments(args);
        /**
         * Set Call back to capture selected date
         */
        date.setCallBack(ondate);
        date.show(getSupportFragmentManager(), "Date Picker");
    }

    /**
     * This function is used to set picked date to the textview
     */

    DatePickerDialog.OnDateSetListener ondate = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            monthOfYear++;
            String month = String.valueOf(monthOfYear); //Months are indexed from 0 to 11
            String day = String.valueOf(dayOfMonth);

            if(monthOfYear < 10) {
                month = "0" + String.valueOf(monthOfYear);
            }

            if(dayOfMonth < 10) {
                day = "0" + String.valueOf(dayOfMonth);
            }

            birthday = String.valueOf(year)+ "-" + month + "-" + day;
            birthdayTV.setText(birthday);
        }
    };


    @Override
    protected void onDestroy() {
        Log.d("Destroy", "Activity Gone");
        super.onDestroy();
    }
}
