package com.kaanburaksener.bench.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.os.Handler;
import android.os.IBinder;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.kaanburaksener.bench.R;
import com.kaanburaksener.bench.db.DBHandler;
import com.kaanburaksener.bench.ui.activity.ApplicationHistoryActivity;
import com.twilio.twiliochat.activities.MainChatActivity;
import com.twilio.twiliochat.application.TwilioChatApplication;
import com.twilio.twiliochat.interfaces.LoginListener;
import com.twilio.twiliochat.ipmessaging.IPMessagingClientManager;
import com.twilio.twiliochat.util.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by kaanburaksener on 13/04/16.
 */
public class ApplicationResultService extends Service {
    public static final long CHECK_INTERVAL = 1 * 60 * 1000; // 1 Minute
    private Handler mHandler = new Handler();
    private Timer mTimer = null;
    private DBHandler dbHandler;
    private int userID;
    NotificationManager notificationManager;
    Intent notificationIntent;
    PendingIntent pendingIntent;
    NotificationCompat.Builder notification;
    public Context context;
    private IPMessagingClientManager messagingClient;

    /** indicates how to behave if the service is killed */
    int mStartMode;

    /** interface for clients that bind */
    IBinder mBinder;

    /** indicates whether onRebind should be used */
    boolean mAllowRebind;

    /** Called when the service is being created. */

    @Override
    public void onCreate() {
        dbHandler = new DBHandler(this);
        context = this;

        if(dbHandler.hasUser()) {
            userID = dbHandler.getUserId();

            notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

            if(mTimer != null) {
                mTimer.cancel();
            } else {
                mTimer = new Timer();
            }

            mTimer.scheduleAtFixedRate(new CheckResultTimerTask(), 0, CHECK_INTERVAL);
        }
    }

    /** The service is starting, due to a call to startService() */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    /** A client is binding to the service with bindService() */
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    /** Called when all clients have unbound with unbindService() */
    @Override
    public boolean onUnbind(Intent intent) {
        return mAllowRebind;
    }

    /** Called when a client is binding to the service with bindService()*/
    @Override
    public void onRebind(Intent intent) {}

    /** Called when The service is no longer used and is being destroyed */
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * This function is used to check is there any accepted application or not, and if there is, the function creates a notification
     */

    public void performCheckApplicationResult(final Context context){
        String url = context.getResources().getString(R.string.check_application_results_url);

        HashMap<String, String> param = new HashMap<String, String>();
        param.put("user_id", new Integer(userID).toString());

        JsonArrayRequest postRequest = new JsonArrayRequest(Request.Method.POST, url, new JSONObject(param), new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                try {
                    JSONObject response = (JSONObject) jsonArray.get(0);
                    int id = Integer.valueOf(response.getString("id"));

                    if(id != -1) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject request = (JSONObject) jsonArray.get(i);

                            int requestID = Integer.valueOf(request.getString("id"));
                            String title = request.getString("title");

                            if(!dbHandler.isNotificationCreatedBefore(requestID)) {
                                buildNotification(requestID, title);
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        Volley.newRequestQueue(context).add(postRequest);
    }

    /**
     * This function is used to create a notification for accepted application
     */

    private void buildNotification(int requestID, String requestInfo){
        notification = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.notification_icon)
                .setContentTitle(requestInfo)
                .setAutoCancel(true)
                .setContentText("Your application is accepted!");

        notification.setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});

        notificationIntent = new Intent(this, ApplicationHistoryActivity.class);
        notificationIntent.putExtra("request id", requestID);
        pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent,0);
        notification.setContentIntent(pendingIntent);

        int notificationId = requestID;
        notificationManager.notify(notificationId, notification.build());
    }

    /**
     * This function is used to check is there any chat request or not, and if there is, the function creates a notification
     */

    public void performCheckChatRequest(final Context context){
        String url = context.getResources().getString(R.string.check_chat_requests_url);

        HashMap<String, String> param = new HashMap<String, String>();
        param.put("user_id", new Integer(userID).toString());

        JsonArrayRequest postRequest = new JsonArrayRequest(Request.Method.POST, url, new JSONObject(param), new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                try {
                    JSONObject response = (JSONObject) jsonArray.get(0);
                    int chat_request_id = Integer.valueOf(response.getString("chat_request_id"));

                    if(chat_request_id != -1) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject request = (JSONObject) jsonArray.get(i);
                            int chatRequestID = Integer.parseInt(request.getString("chat_request_id"));
                            String starterName = request.getString("starter_name");
                            String requestTitle = request.getString("request_title");

                            buildChatNotification(chatRequestID, starterName, requestTitle);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        Volley.newRequestQueue(context).add(postRequest);
    }

    /**
     * This function is used to create a notification for chat request
     */

    private void buildChatNotification(final int chatRequestID, String starterName, String requestTitle){
        notification = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.notification_icon)
                .setContentTitle(requestTitle)
                .setAutoCancel(true)
                .setContentText(starterName + " wants to start a chat with you!");

        notification.setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});

        messagingClient = TwilioChatApplication.get().getIPMessagingClient();
        SessionManager.getInstance().createLoginSession(dbHandler.getUserName());
        messagingClient.connectClient(new LoginListener() {
            @Override
            public void onLoginFinished() {
                notificationIntent = new Intent(context, MainChatActivity.class);
                notificationIntent.putExtra("incoming class", 1);
                notificationIntent.putExtra("chat_request_id", chatRequestID);
                notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);
                notification.setContentIntent(pendingIntent);
                int notificationId = chatRequestID;
                notificationManager.notify(notificationId, notification.build());
            }

            @Override
            public void onLoginError(String errorMessage) {
            }
        });
    }

    /**
     * This function is used to repeat the functions inside for a specific time interval
     */

    class CheckResultTimerTask extends TimerTask {
        @Override
        public void run() {
            mHandler.post(new Runnable() {

                @Override
                public void run() {
                    performCheckApplicationResult(context);
                    performCheckChatRequest(context);
                }
            });
        }
    }
}
