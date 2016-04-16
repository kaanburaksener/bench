package com.kaanburaksener.bench.ui.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.kaanburaksener.bench.R;
import com.kaanburaksener.bench.core.User;
import com.kaanburaksener.bench.db.DBHandler;
import com.kaanburaksener.bench.ui.activity.EditProfileActivity;

/**
 * Created by kaanburaksener on 09/04/16.
 */
public class MyProfileActivity extends AppCompatActivity {
    private User user;
    private DBHandler dbHandler;
    private Button editButton;
    private TextView userName;
    private TextView location;
    private TextView birthday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        initializer();
    }

    /**
     * This function is used to initialize the layout elements and the attributes of the class
     */

    private void initializer() {
        dbHandler = new DBHandler(this);

        editButton = (Button) findViewById(R.id.editButton);
        userName = (TextView) findViewById(R.id.userName);
        location = (TextView) findViewById(R.id.location);
        birthday = (TextView) findViewById(R.id.birthday);

        user = dbHandler.getUser(dbHandler.getUserId());

        userName.setText(user.getName());
        location.setText(user.getLocation());
        birthday.setText(user.getBirthday());

        setStatusBarColor();
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
     * This function is used to redirect the user to edit profile page
     */

    public void goEditProfileActivity(View view) {
        Intent intent = new Intent(this, EditProfileActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        Log.d("Destroy", "Activity Gone");
        super.onDestroy();
    }
}
