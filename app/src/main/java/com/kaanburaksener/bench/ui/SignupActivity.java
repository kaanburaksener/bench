package com.kaanburaksener.bench.ui;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import android.view.View;
import android.widget.Toast;

import com.kaanburaksener.bench.helper.Helper;
import com.kaanburaksener.bench.R;
import com.kaanburaksener.bench.handler.AccountHandler;

/**
 * Created by kaanburaksener on 24/03/16.
 */
public class SignupActivity extends AppCompatActivity {
    private ActionBar actionBar;
    private Helper helper;
    private Button signupButton;
    private AccountHandler accountHandler;
    private TextView appName;
    private EditText nameET;
    private EditText emailET;
    private EditText passwordET;
    private String name;
    private String email;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        initializer();
    }

    /**
     * This function is used to initialize the layout elements and the attributes of the class
     */

    private void initializer() {
        setStatusBarColor();
        actionBar = getSupportActionBar();
        actionBar.hide();
        appName = (TextView) findViewById(R.id.appName);
        nameET = (EditText) findViewById(R.id.name);
        emailET = (EditText) findViewById(R.id.email);
        passwordET = (EditText) findViewById(R.id.password);
        signupButton = (Button) findViewById(R.id.signupButton);
        signupButton.setOnClickListener(signupHandler);
        accountHandler = new AccountHandler(this, this.getApplicationContext(), this.getWindow().getContext());
        helper = new Helper();
        setFont();
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
     * This function is used to set font to the layout elements
     */

    private void setFont() {
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/Ubuntu-M.ttf");
        appName.setTypeface(typeface);
    }

    /**
     * This function is used to trigger sign up request
     */

    View.OnClickListener signupHandler = new View.OnClickListener() {
        public void onClick(View v) {
            if(checkFormData()) {
                accountHandler.receiveData(name, email, password);
                accountHandler.performSignup();
            }
        }
    };

    /**
     * This function is used to redirect the user to sign up page
     */

    public void goSigninActivity(View view) {
        Intent intent = new Intent(this, SigninActivity.class);
        startActivity(intent);
    }

    /**
     * This function is used to check validity of form data
     */

    public boolean checkFormData() {
        boolean result = true;

        name = nameET.getText().toString();
        email = emailET.getText().toString();
        password = passwordET.getText().toString();

        if (name.isEmpty() || name.length() < 4 || name.length() > 32) {
            result = false;
            Toast.makeText(getApplicationContext(), R.string.username_error, Toast.LENGTH_SHORT).show();

        } else if (email.isEmpty() || !helper.isEmailValid(email)) {
            result = false;
            Toast.makeText(getApplicationContext(), R.string.email_error, Toast.LENGTH_SHORT).show();

        } else if (password.isEmpty() || password.length() < 6 || password.length() > 32) {
            result = false;
            Toast.makeText(getApplicationContext(), R.string.password_error, Toast.LENGTH_SHORT).show();
        }

        return result;
    }

    @Override
    protected void onDestroy() {
        Log.d("Destroy", "Activity Gone");
        super.onDestroy();
    }
}