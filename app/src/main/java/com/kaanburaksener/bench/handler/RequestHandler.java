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
import com.android.volley.toolbox.Volley;
import com.android.volley.toolbox.StringRequest;

import com.kaanburaksener.bench.db.DBHandler;
import com.kaanburaksener.bench.MainActivity;
import com.kaanburaksener.bench.R;

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
                            Log.d("New Request Response: ",response);
                            JSONObject res = new JSONObject(response);

                            if (res.getString(context.getResources().getString(R.string.key_success)) != null) {
                                int success = Integer.parseInt(res.getString(context.getResources().getString(R.string.key_success)));
                                String message = res.getString(context.getResources().getString(R.string.key_message));

                                if (success == 1) {
                                    DBHandler dbHandler = new DBHandler(context);
                                    dbHandler.addRequest(res.getString("id"), res.getString("title"), res.getString("description"), res.getString("location"), res.getString("player_position_id"), res.getString("time"), res.getString("status_id"), res.getString("owner_id"), res.getString("created_at"));

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

                // the POST parameters:
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
}