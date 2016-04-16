package com.kaanburaksener.bench.handler;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.kaanburaksener.bench.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kaanburaksener on 15/04/16.
 */

/**
 * This class is created to handle the chat requests
 */

public class ChatRequestHandler {
    public static void performMakeChatRequest (final int starterID,
                                               final int receiverID,
                                               final int requestID,
                                               final Context context){

        String url = context.getResources().getString(R.string.make_chat_request_url);

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

                                }  else if (success == 2) {
                                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                                } else if (success == 3) {
                                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(context, R.string.invalid_post, Toast.LENGTH_SHORT).show();
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

                params.put("starter_id", new Integer(starterID).toString());
                params.put("receiver_id", new Integer(receiverID).toString());
                params.put("request_id", new Integer(requestID).toString());

                return params;
            }
        };
        Volley.newRequestQueue(context).add(postRequest);
    }

    public static void performUpdateChatStatus (final int chatRequestID,
                                                final Context context){

        String url = context.getResources().getString(R.string.update_chat_status_url);

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject res = new JSONObject(response);
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

                params.put("chat_request_id", new Integer(chatRequestID).toString());

                return params;
            }
        };
        Volley.newRequestQueue(context).add(postRequest);
    }
}
