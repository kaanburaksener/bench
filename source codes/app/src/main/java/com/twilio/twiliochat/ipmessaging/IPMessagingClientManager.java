package com.twilio.twiliochat.ipmessaging;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.content.Context;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.kaanburaksener.bench.db.DBHandler;
import com.kaanburaksener.bench.handler.VolleyController;
import com.twilio.common.TwilioAccessManager;
import com.twilio.common.TwilioAccessManagerFactory;
import com.twilio.common.TwilioAccessManagerListener;
import com.twilio.ipmessaging.Channel;
import com.twilio.ipmessaging.Constants;
import com.twilio.ipmessaging.IPMessagingClientListener;
import com.twilio.ipmessaging.TwilioIPMessagingClient;
import com.twilio.ipmessaging.TwilioIPMessagingSDK;

import com.kaanburaksener.bench.R;

import com.twilio.twiliochat.interfaces.FetchTokenListener;
import com.twilio.twiliochat.interfaces.LoginListener;
import com.twilio.twiliochat.util.SessionManager;

public class IPMessagingClientManager implements IPMessagingClientListener {
  private final Handler handler = new Handler();
  private String capabilityToken;
  private TwilioIPMessagingClient ipMessagingClient;
  private Context context;
  private TwilioAccessManager accessManager;
  private DBHandler dbHandler;

  public IPMessagingClientManager(Context context) {
    this.context = context;
  }

  public IPMessagingClientManager() {}

  public String getCapabilityToken() {
    return capabilityToken;
  }

  public void setCapabilityToken(String capabilityToken) {
    this.capabilityToken = capabilityToken;
    if (this.accessManager != null) {
      this.accessManager.updateToken(capabilityToken);
    }
  }

  public void setClientListener(IPMessagingClientListener listener) {
    if (this.ipMessagingClient != null) {
      this.ipMessagingClient.setListener(listener);
    }
  }

  public TwilioIPMessagingClient getIpMessagingClient() {
    return this.ipMessagingClient;
  }

  public void setIpMessagingClient(TwilioIPMessagingClient client) {
    this.ipMessagingClient = client;
  }

  public void connectClient(final LoginListener listener) {
    TwilioIPMessagingSDK.setLogLevel(android.util.Log.DEBUG);
    if (!TwilioIPMessagingSDK.isInitialized()) {
      TwilioIPMessagingSDK.initializeSDK(context, new Constants.InitListener() {
        @Override
        public void onInitialized() {
          createClientWithAccessManager(listener);
        }

        @Override
        public void onError(Exception error) {
          System.out.println("Error initializing the SDK :" + error.getMessage());
        }
      });
    } else {
      createClientWithAccessManager(listener);
    }
  }

  private void createClientWithAccessManager(final LoginListener listener) {
    fetchAccessToken(new FetchTokenListener() {
      @Override
      public void fetchTokenSuccess(String token) {
        initializeClientWithToken(token, listener);
      }

      @Override
      public void fetchTokenFailure(Exception e) {
        if (listener != null) {
          listener.onLoginError(e.getLocalizedMessage());
        }
      }
    });
  }

  private void initializeClientWithToken(String token, final LoginListener listener) {
    this.accessManager =
        TwilioAccessManagerFactory.createAccessManager(token, new TwilioAccessManagerListener() {
          @Override
          public void onAccessManagerTokenExpire(TwilioAccessManager twilioAccessManager) {
            System.out.println("token expired.");
            fetchAccessToken(new FetchTokenListener() {
              @Override
              public void fetchTokenSuccess(String token) {
                IPMessagingClientManager.this.accessManager.updateToken(token);
              }

              @Override
              public void fetchTokenFailure(Exception e) {
                System.out.println("Error trying to fetch token: " + e.getLocalizedMessage());
              }
            });
          }

          @Override
          public void onTokenUpdated(TwilioAccessManager twilioAccessManager) {
            System.out.println("token updated.");
          }

          @Override
          public void onError(TwilioAccessManager twilioAccessManager, String s) {
            System.out.println("token error: " + s);
          }
        });

    ipMessagingClient =
        TwilioIPMessagingSDK.createIPMessagingClientWithAccessManager(this.accessManager, this);
    if (listener != null) {
      listener.onLoginFinished();
    }
  }

  private void fetchAccessToken(final FetchTokenListener listener) {
    StringRequest request = new StringRequest(Request.Method.POST, context.getResources().getString(R.string.token_url), new Response.Listener<String>() {

      @Override
      public void onResponse(String response) {
        String token = null;

        try {
          Log.d("Token Response: ", response);
          JSONObject res = new JSONObject(response);

          if (res.getString(context.getResources().getString(R.string.key_token)) != null) {
              token = res.getString("token");
          } else {
              listener.fetchTokenFailure(new Exception("Failed to fetch token"));
          }
        } catch (Exception e) {
          e.printStackTrace();
          listener.fetchTokenFailure(new Exception("Failed to parse token JSON response"));
        }

        listener.fetchTokenSuccess(token);
      }
    }, new Response.ErrorListener() {
      @Override
      public void onErrorResponse(VolleyError error) {
        listener.fetchTokenFailure(new Exception("Failed to fetch token"));
      }
    }){
      @Override
      protected Map<String, String> getParams() throws AuthFailureError {
        dbHandler = new DBHandler(context);

        Map<String, String> params = new HashMap<String, String>();

        params.put("identity", dbHandler.getUserName());
        params.put("device_id", Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID));

        return params;
      }
    };
    VolleyController.getInstance(context).getRequestQueue().add(request);
  }

  @Override
  public void onChannelAdd(Channel channel) {}

  @Override
  public void onChannelChange(Channel channel) {}

  @Override
  public void onChannelDelete(Channel channel) {}

  @Override
  public void onError(int i, String s) {}

  @Override
  public void onAttributesChange(String s) {}

  @Override
  public void onChannelHistoryLoaded(Channel channel) {}
}
