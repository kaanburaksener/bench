package com.kaanburaksener.bench.authentication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import com.kaanburaksener.bench.R;
import com.kaanburaksener.bench.MainActivity;
import com.kaanburaksener.bench.db.DBHandler;
import com.kaanburaksener.bench.handler.VolleyController;
import com.kaanburaksener.bench.ui.SigninActivity;

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

    public static void signupUser(final String name, final String email, final String password, final Context context) {
        StringRequest request = new StringRequest(Request.Method.POST, context.getResources().getString(R.string.signup_url), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("Sign Up Response: ", response);
                    JSONObject res = new JSONObject(response);

                    if (res.getString(context.getResources().getString(R.string.key_success)) != null) {
                        int success = Integer.parseInt(res.getString(context.getResources().getString(R.string.key_success)));

                        if (success == 1) {
                            Toast.makeText(context, R.string.signup_success, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(context, SigninActivity.class);
                            context.startActivity(intent);
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
                Toast.makeText(context, R.string.invalid_post, Toast.LENGTH_SHORT).show();
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

    public static final void signinUser(final String email, final String password, final Context context, final Activity activity) {
        StringRequest request = new StringRequest(Request.Method.POST, context.getResources().getString(R.string.signin_url), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try  {
                    Log.d("Sign In Response: ",response);
                    JSONObject res = new JSONObject(response);
                    if (res.getString(context.getResources().getString(R.string.key_success)) != null) {
                        int success = Integer.parseInt(res.getString(context.getResources().getString(R.string.key_success)));
                        if (success == 1) {
                            DBHandler dbHandler = new DBHandler(context);
                            dbHandler.addUser(res.getString("id"), res.getString("name"), res.getString("email"), res.getString("password"), res.getString("location"), res.getString("birthday"), res.getString("created_at"));
                            Intent intent = new Intent(context, MainActivity.class);
                            intent.putExtra("user_id", Integer.parseInt(res.getString("id")));
                            context.startActivity(intent);
                            activity.finish();
                        } else if (success == 0) {
                            Toast.makeText(context, R.string.invalid_signin, Toast.LENGTH_SHORT).show();
                        } else {
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
                Toast.makeText(context, R.string.invalid_post, Toast.LENGTH_SHORT).show();
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
}
