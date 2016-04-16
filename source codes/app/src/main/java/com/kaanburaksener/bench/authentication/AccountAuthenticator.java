package com.kaanburaksener.bench.authentication;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import com.kaanburaksener.bench.MainActivity;
import com.kaanburaksener.bench.R;
import com.kaanburaksener.bench.core.User;
import com.kaanburaksener.bench.db.DBHandler;
import com.kaanburaksener.bench.handler.VolleyController;
import com.kaanburaksener.bench.ui.activity.MyProfileActivity;
import com.kaanburaksener.bench.ui.activity.SigninActivity;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

/**
 * Created by kaanburaksener on 01/04/16.
 */
public class AccountAuthenticator {

    /**
     * This function is used to send sign up request to the server
     */

    public static void signupUser(final String name, final String email, final String password, final Activity activity, final Context context, final Context windowContext) {
        StringRequest request = new StringRequest(Request.Method.POST, context.getResources().getString(R.string.signup_url), new Response.Listener<String>() {

        @Override
        public void onResponse(String response) {
            try {
                final ProgressDialog progressDialog = new ProgressDialog(windowContext);
                progressDialog.setMessage("Sign Up...");
                progressDialog.show();

                JSONObject res = new JSONObject(response);

                if (res.getString(context.getResources().getString(R.string.key_success)) != null) {
                    int success = Integer.parseInt(res.getString(context.getResources().getString(R.string.key_success)));

                    if (success == 1) {
                        Toast.makeText(context, R.string.signup_success, Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        Intent intent = new Intent(context, SigninActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                        activity.finish();
                    } else if (success == 0) {
                        Toast.makeText(context, R.string.email_exists, Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(context, R.string.invalid_post, Toast.LENGTH_SHORT).show();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Response Error", error.toString());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put("name", name);
                params.put("email", email);
                params.put("password", password);

                return params;
            }
        };
        VolleyController.getInstance(context).getRequestQueue().add(request);
    }

    /**
     * This function is used to send sign in request to the server
     */

    public static final void signinUser(final String email, final String password, final Activity activity, final Context context, final Context windowContext) {
        StringRequest request = new StringRequest(Request.Method.POST, context.getResources().getString(R.string.signin_url), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    final ProgressDialog progressDialog = new ProgressDialog(windowContext);
                    progressDialog.setMessage("Sign In...");
                    progressDialog.show();

                    JSONObject res = new JSONObject(response);
                    if (res.getString(context.getResources().getString(R.string.key_success)) != null) {
                        int success = Integer.parseInt(res.getString(context.getResources().getString(R.string.key_success)));
                        String message = res.getString(context.getResources().getString(R.string.key_message));

                        if (success == 1) {
                            DBHandler dbHandler = new DBHandler(context);
                            dbHandler.addUser(res.getString("id"), res.getString("name"), res.getString("email"), res.getString("password"), res.getString("location"), res.getString("birthday"), res.getString("created_at"));
                            Intent intent = new Intent();
                            intent.setClass(context, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                            activity.finish();
                            progressDialog.dismiss();
                        } else if (success == 0) {
                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Response Error", error.toString());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put("email", email);
                params.put("password", password);

                return params;
            }
        };
        VolleyController.getInstance(context).getRequestQueue().add(request);
    }

    /**
     * This function is used to update the user information
     */

    public static final void updateUser(final int id, final String location, final String birthday, final Activity activity, final Context context, final Context windowContext) {
        StringRequest request = new StringRequest(Request.Method.POST, context.getResources().getString(R.string.update_user_url), new Response.Listener<String>() {

        @Override
        public void onResponse(String response) {
            try {
                final ProgressDialog progressDialog = new ProgressDialog(windowContext);
                progressDialog.setMessage("Update...");
                progressDialog.show();

                JSONObject res = new JSONObject(response);

                if (res.getString(context.getResources().getString(R.string.key_success)) != null) {
                    int success = Integer.parseInt(res.getString(context.getResources().getString(R.string.key_success)));
                    String message = res.getString(context.getResources().getString(R.string.key_message));

                    if (success == 1) {
                        DBHandler dbHandler = new DBHandler(context);
                        User user = dbHandler.getUser(id);
                        user.setLocation(location);
                        user.setBirthday(birthday);
                        dbHandler.updateUser(user);
                        progressDialog.dismiss();
                        Intent intent = new Intent(context, MyProfileActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                        activity.finish();
                    } else if (success == 0) {
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                    } else if (success == 2) {
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Response Error", error.toString());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put("id", String.valueOf(id));
                params.put("location", location);
                params.put("birthday", birthday);

                return params;
            }
        };
        VolleyController.getInstance(context).getRequestQueue().add(request);
    }
}
