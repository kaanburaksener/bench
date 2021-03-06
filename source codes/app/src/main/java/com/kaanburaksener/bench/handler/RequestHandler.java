package com.kaanburaksener.bench.handler;

import android.app.Activity;
import android.app.ProgressDialog;

import android.content.Context;
import android.content.Intent;

import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.android.volley.toolbox.StringRequest;

import com.kaanburaksener.bench.callback.VolleyCallback;
import com.kaanburaksener.bench.MainActivity;
import com.kaanburaksener.bench.R;
import com.kaanburaksener.bench.ui.activity.RequestHistoryActivity;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kaanburaksener on 06/04/16.
 */

public class RequestHandler {
    public static void performCreateNewRequest (final String title,
                                                final String description,
                                                final String location,
                                                final int playerPositionID,
                                                final String time,
                                                final int statusID,
                                                final int ownerID,
                                                final Activity activity,
                                                final Context context,
                                                final Context windowContext){

        final ProgressDialog progressDialog =  new ProgressDialog(windowContext);
        progressDialog.setMessage("Your request is being processed...");
        progressDialog.show();

        String url = context.getResources().getString(R.string.create_new_request_url);

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject res = new JSONObject(response);

                            if (res.getString(context.getResources().getString(R.string.key_success)) != null) {
                                int success = Integer.parseInt(res.getString(context.getResources().getString(R.string.key_success)));
                                String message = res.getString(context.getResources().getString(R.string.key_message));

                                if (success == 1) {
                                    progressDialog.dismiss();
                                    Intent intent = new Intent(context, MainActivity.class);
                                    intent.putExtra("user_id", Integer.parseInt(res.getString("id")));
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    context.startActivity(intent);
                                    activity.finish();
                                } else if (success == 0) {
                                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
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

                params.put("title", title);
                params.put("description", description);
                params.put("location", location);
                params.put("player_position_id", new Integer(playerPositionID).toString());
                params.put("time", time);
                params.put("status_id", new Integer(statusID).toString());
                params.put("request_owner_id", new Integer(ownerID).toString());

                return params;
            }
        };
        Volley.newRequestQueue(context).add(postRequest);
    }

    public static void performCloseRequest (final int requestID,
                                            final Activity activity,
                                            final Context context,
                                            final Context windowContext){

        final ProgressDialog progressDialog =  new ProgressDialog(windowContext);
        progressDialog.setMessage("Request is being processed...");
        progressDialog.show();

        String url = context.getResources().getString(R.string.close_request_url);

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
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

                params.put("request_id", new Integer(requestID).toString());

                return params;
            }
        };
        Volley.newRequestQueue(context).add(postRequest);
    }


    public static void performGetOpenedRequests (final int userID,
                                                 final Context context,
                                                 final VolleyCallback callback){

        String url = context.getResources().getString(R.string.get_opened_requests_url);

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

    public static void performGetUserRequests (final int userID,
                                               final Context context,
                                               final VolleyCallback callback){

        String url = context.getResources().getString(R.string.get_user_requests_url);

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