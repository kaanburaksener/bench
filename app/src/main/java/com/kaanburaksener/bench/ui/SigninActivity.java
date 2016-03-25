package com.kaanburaksener.bench.ui;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.EditText;
import android.view.View;
import android.widget.Toast;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

import com.kaanburaksener.bench.R;

/**
 * Created by kaanburaksener on 24/03/16.
 */
public class SigninActivity extends AppCompatActivity {
    private TextView appName;
    private EditText email;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        initializer();
    }

    /**
     * This function is used to initialize the layout elements and the attributes of the class
     */
    private void initializer() {
        appName = (TextView)findViewById(R.id.appName);
        email = (EditText)findViewById(R.id.email);
        password = (EditText)findViewById(R.id.password);
        setFont();
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
     * This function is used to set font to the layout elements
     */
    private void setFont() {
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/Ubuntu-M.ttf");
        appName.setTypeface(typeface);
    }

    /**
     * This function is used to check the validity of entered data from sign in form
     */
    private boolean checkSigninForm() {
        boolean result = true;

        if (TextUtils.isEmpty(email.getText()) || !isEmailValid(email.getText().toString())) {
            result = false;
            Toast.makeText(getApplicationContext(), R.string.email_error, Toast.LENGTH_LONG).show();

        } else if (TextUtils.isEmpty(password.getText()) || password.getText().length() < 8 || password.getText().length() > 32) {
            result = false;
            Toast.makeText(getApplicationContext(), R.string.password_error, Toast.LENGTH_LONG).show();
        }

        return result;
    }

    /**
     * This function is used to sends the register forms
     */
    public void sendForm(View view) {

    }

    /**
     * This function is used to redirect the user to sign up page
     */
    public void goSignupActivity(View view) {
        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * This function is used to check the validity of the input entered as email
     */
    public static boolean isEmailValid(String email) {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    @Override
    protected void onDestroy() {
        Log.d("Destroy", "Activity Gone");
        super.onDestroy();
    }

}
