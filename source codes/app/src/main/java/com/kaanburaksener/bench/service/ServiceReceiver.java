package com.kaanburaksener.bench.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by kaanburaksener on 13/04/16.
 */
public class ServiceReceiver extends BroadcastReceiver {
    public ServiceReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent mIntent = new Intent(context, ApplicationResultService.class);
        context.startService(mIntent);
    }
}