package com.raintail.demonotification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by licong10 on 17-8-28.
 */
public class MyBroadCast extends BroadcastReceiver {

    int count = 100;

    @Override
    public void onReceive(Context context, Intent intent) {
        count++;
        Log.i("aa", count + "");
    }



}
