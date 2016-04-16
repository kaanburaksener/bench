package com.twilio.twiliochat.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.kaanburaksener.bench.MainActivity;
import com.twilio.twiliochat.util.SessionManager;

public class LaunchActivity extends Activity {

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    Intent launchIntent = new Intent();
    Class<?> launchActivity;

    launchActivity = getLaunchClass();
    launchIntent.setClass(getApplicationContext(), launchActivity);
    startActivity(launchIntent);

    finish();
  }

  private Class<?> getLaunchClass() {
    if (SessionManager.getInstance().isLoggedIn()) {
      return MainChatActivity.class;
    } else {
      return MainActivity.class;
    }
  }
}
