package com.kaanburaksener.bench.handler;

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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.kaanburaksener.bench.MainActivity;
import com.kaanburaksener.bench.R;
import com.kaanburaksener.bench.callback.VolleyCallback;
import com.kaanburaksener.bench.ui.ApplicationHistoryActivity;
import com.kaanburaksener.bench.ui.RequestHistoryActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kaanburaksener on 11/04/16.
 */
public class RequestApplicationHandler {
    public static void performCreateRequestApplication (final int requestID,
                                                        final int applicantUserID,
                                                        final Activity activity,
                                                        final Context context,
                                                        final Context windowContext){

        final ProgressDialog progressDialog =  new ProgressDialog(windowContext);
        progressDialog.setMessage("Your application is being processed...");
        progressDialog.show();

        String url = context.getResources().getString(R.string.create_new_request_application_url);

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("New App Response:", response);
                            JSONObject res = new JSONObject(response);

                            if (res.getString(context.getResources().getString(R.string.key_success)) != null) {
                                int success = Integer.parseInt(res.getString(context.getResources().getString(R.string.key_success)));
                                String message = res.getString(context.getResources().getString(R.string.key_message));

                                if (success == 1) {
                                    progressDialog.dismiss();
                                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(context, MainActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    context.startActivity(intent);
                                    activity.finish();
                                } else if (success == 2) {
                                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                } else if (success == 3) {
                                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                } else {
                                    Toast.makeText(context, R.string.invalid_post, Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String>  params = new HashMap<>();

                // the POST parameters:
                params.put("request_id", new Integer(requestID).toString());
                params.put("applicant_user_id", new Integer(applicantUserID).toString());

                return params;
            }
        };
        Volley.newRequestQueue(context).add(postRequest);
    }

    public static void performCancelRequestApplication (final int requestID,
                                                        final int applicantUserID,
                                                        final Activity activity,
                                                        final Context context,
                                                        final Context windowContext){

        final ProgressDialog progressDialog =  new ProgressDialog(windowContext);
        progressDialog.setMessage("Cancellation request is being processed...");
        progressDialog.show();

        String url = context.getResources().getString(R.string.delete_application_url);

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("Cancellation Response:", response);
                            JSONObject res = new JSONObject(response);

                            if (res.getString(context.getResources().getString(R.string.key_success)) != null) {
                                int success = Integer.parseInt(res.getString(context.getResources().getString(R.string.key_success)));
                                String message = res.getString(context.getResources().getString(R.string.key_message));

                                if (success == 1) {
                                    progressDialog.dismiss();
                                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(context, ApplicationHistoryActivity.class);
                                    intent.putExtra("request id", -1); //-1 shows that ApplicationHistoryActivity is approached not by notification
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    context.startActivity(intent);
                                    activity.finish();
                                } else if (success == 2) {
                                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                } else if (success == 3) {
                                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                } else {
                                    Toast.makeText(context, R.string.invalid_post, Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String>  params = new HashMap<>();

                // the POST parameters:
                params.put("request_id", new Integer(requestID).toString());
                params.put("applicant_user_id", new Integer(applicantUserID).toString());

                return params;
            }
        };
        Volley.newRequestQueue(context).add(postRequest);
    }

    public static void performGetApplicants (final int requestID,
                                             final Context context,
                                             final VolleyCallback callback){

        String url = context.getResources().getString(R.string.get_applicants_url);

        HashMap<String, String> param = new HashMap<String, String>();
        param.put("request_id", new Integer(requestID).toString());

        JsonArrayRequest postRequest = new JsonArrayRequest(Request.Method.POST, url, new JSONObject(param), new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                callback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                callback.onError(error.toString());
            }
        });
        Volley.newRequestQueue(context).add(postRequest);
    }

    public static void performFinalizeRequestApplication (final int requestID,
                                                          final int applicantUserID,
                                                          final int applicationStatusID,
                                                          final Activity activity,
                                                          final Context context,
                                                          final Context windowContext){

        final ProgressDialog progressDialog =  new ProgressDialog(windowContext);
        progressDialog.setMessage("Finalization is being processed...");
        progressDialog.show();

        String url = context.getResources().getString(R.string.finalize_application_url);

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("Final Response:", response);
                            JSONObject res = new JSONObject(response);

                            if (res.getString(context.getResources().getString(R.string.key_success)) != null) {
                                int success = Integer.parseInt(res.getString(context.getResources().getString(R.string.key_success)));
                                String message = res.getString(context.getResources().getString(R.string.key_message));

                                if (success == 1) {
                                    progressDialog.dismiss();
                                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(context, RequestHistoryActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    context.startActivity(intent);
                                    activity.finish();
                                } else if (success == 2) {
                                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                } else if (success == 3) {
                                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                } else {
                                    Toast.makeText(context, R.string.invalid_post, Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String>  params = new HashMap<>();

                // the POST parameters:
                params.put("request_id", new Integer(requestID).toString());
                params.put("applicant_user_id", new Integer(applicantUserID).toString());
                params.put("application_status_id", new Integer(applicationStatusID).toString());

                return params;
            }
        };
        Volley.newRequestQueue(context).add(postRequest);
    }

    public static void performGetUserApplications (final int userID,
                                                   final Context context,
                                                   final VolleyCallback callback){

        String url = context.getResources().getString(R.string.get_user_applications_url);

        HashMap<String, String> param = new HashMap<String, String>();
        param.put("user_id", new Integer(userID).toString());

        JsonArrayRequest postRequest = new JsonArrayRequest(Request.Method.POST, url, new JSONObject(param), new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                callback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                callback.onError(error.toString());
            }
        });
        Volley.newRequestQueue(context).add(postRequest);
    }
}
