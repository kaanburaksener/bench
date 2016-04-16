package com.kaanburaksener.bench.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Build;
import android.support.v7.app.ActionBar;

import android.widget.Button;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.EditText;
import android.view.View;
import android.widget.Toast;

import com.kaanburaksener.bench.MainActivity;
import com.kaanburaksener.bench.db.DBHandler;
import com.kaanburaksener.bench.helper.Helper;
import com.kaanburaksener.bench.R;
import com.kaanburaksener.bench.handler.AccountHandler;

import com.twilio.twiliochat.application.TwilioChatApplication;
import com.twilio.twiliochat.interfaces.LoginListener;
import com.twilio.twiliochat.ipmessaging.IPMessagingClientManager;
import com.twilio.twiliochat.util.SessionManager;

/**
 * Created by kaanburaksener on 24/03/16.
 */
public class SigninActivity extends AppCompatActivity {
    final Context context = this;
    private ActionBar actionBar;
    private Helper helper;
    private Button signinButton;
    private AccountHandler accountHandler;
    private TextView appName;
    private EditText emailET;
    private EditText passwordET;
    private String email;
    private String password;
    private IPMessagingClientManager messagingClient;
    private DBHandler dbHandler;

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
        setStatusBarColor();
        dbHandler = new DBHandler(context);
        actionBar = getSupportActionBar();
        actionBar.hide();
        helper = new Helper();
        accountHandler = new AccountHandler(this, this.getApplicationContext(), this.getWindow().getContext());
        appName = (TextView) findViewById(R.id.appName);
        emailET = (EditText) findViewById(R.id.email);
        passwordET = (EditText) findViewById(R.id.password);
        signinButton = (Button) findViewById(R.id.signinButton);
        signinButton.setOnClickListener(signinHandler);
        setFont();
        //messagingClient = TwilioChatApplication.get().getIPMessagingClient();
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
     * This function is used to set font to the text of the logo
     */

    private void setFont() {
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/Ubuntu-M.ttf");
        appName.setTypeface(typeface);
    }

    /**
     * This function is used to trigger sign in request
     */

    View.OnClickListener signinHandler = new View.OnClickListener() {
        public void onClick(View v) {
            if(checkFormData()) {
                accountHandler.receiveData(email, password);
                accountHandler.performSignin();
                //SessionManager.getInstance().createLoginSession(dbHandler.getUserName());
                //initializeMessagingClient();
            }
        }
    };

    /**
     * This function is used to connect Twilio IP Messaging API's channel
     */

    /*
    private void initializeMessagingClient() {
        messagingClient.connectClient(new LoginListener() {
            @Override
            public void onLoginFinished() {
                goMainActivity();
            }

            @Override
            public void onLoginError(String errorMessage) {}
        });
    }
    */

    /**
     * This function is used to redirect the user to main page
     */

    /*private void goMainActivity() {
        Intent launchIntent = new Intent();
        launchIntent.setClass(getApplicationContext(), MainActivity.class);
        startActivity(launchIntent);
        finish();
    }*/

    /**
     * This function is used to redirect the user to sign up page
     */

    public void goSignupActivity(View view) {
        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);
    }

    /**
     * This function is used to check validity of form data
     */

    public boolean checkFormData() {
        boolean result = true;

        email = emailET.getText().toString();
        password = passwordET.getText().toString();

        if (email.isEmpty() || !helper.isEmailValid(email)) {
            result = false;
            Toast.makeText(getApplicationContext(), R.string.email_error, Toast.LENGTH_LONG).show();
        } else if (password.isEmpty() || password.length() < 6 || password.length() > 32) {
            result = false;
            Toast.makeText(getApplicationContext(), R.string.password_error, Toast.LENGTH_LONG).show();
        }

        return result;
    }

    @Override
    protected void onDestroy() {
        Log.d("Destroy", "Activity Gone");
        super.onDestroy();
    }

}