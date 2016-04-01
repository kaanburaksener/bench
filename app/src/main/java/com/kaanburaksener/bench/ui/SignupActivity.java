package com.kaanburaksener.bench.ui;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Build;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.EditText;
import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

import java.util.ArrayList;
import java.util.List;

import com.kaanburaksener.bench.MainActivity;
import com.kaanburaksener.bench.R;
import com.kaanburaksener.bench.json.JSONParser;

/**
 * Created by kaanburaksener on 24/03/16.
 */
public class SignupActivity extends AppCompatActivity {
    private TextView appName;
    private EditText nameET;
    private EditText emailET;
    private EditText passwordET;
    private String name;
    private String email;
    private String password;
    private ProgressDialog pDialog;
    private JSONParser jsonParser;
    private static String url_register_user = "http://bench-kaanburaksener.rhcloud.com/register_user.php";
    private static final String TAG_SUCCESS = "success";// JSON Node names

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
        jsonParser = new JSONParser();
        appName = (TextView) findViewById(R.id.appName);
        nameET = (EditText) findViewById(R.id.name);
        emailET = (EditText) findViewById(R.id.email);
        passwordET = (EditText) findViewById(R.id.password);
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
     * This function is used to redirect the user to sign up page
     */
    public void goSigninActivity(View view) {
        Intent intent = new Intent(this, SigninActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * This function is used to set font to the layout elements
     */
    public void sendForm(View view) {
        this.name = nameET.getText().toString();
        this.email = emailET.getText().toString();
        this.password = passwordET.getText().toString();

        if(isEmailValid(email)) {
            new RegisterNewUser().execute();
        }
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

    /**
     * Background Async Task to Create new product
     */
    class RegisterNewUser extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(SignupActivity.this);
            pDialog.setMessage("Registering New User..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Creating product
         */
        protected String doInBackground(String... args) {
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("name", name));
            params.add(new BasicNameValuePair("email", email));
            params.add(new BasicNameValuePair("password", password));

            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(url_register_user, "POST", params);

            // check log cat fro response
            Log.d("Create Response", json.toString());

            // check for success tag
            try {
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // successfully created product
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);

                    // closing this screen
                    finish();
                } else {
                    // failed to create product
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
            pDialog.dismiss();
        }

    }

}