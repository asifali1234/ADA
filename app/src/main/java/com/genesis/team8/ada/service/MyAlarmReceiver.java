package com.genesis.team8.ada.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.genesis.team8.ada.service.AccidentService;

/**
 * Created by asif ali on 14/01/17.
 */

public class MyAlarmReceiver extends BroadcastReceiver {
    public static final int REQUEST_CODE = 12345;

    // Triggered by the Alarm periodically (starts the service to run task)
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, AccidentService.class);
       // i.putExtra("foo", "bar");
        context.startService(i);
    }
}
