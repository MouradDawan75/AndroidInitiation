package fr.dawan.myapplication.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(">>>MyReceiver", "Air plane mode....");
        Toast.makeText(context, "Air plane mode", Toast.LENGTH_LONG).show();
    }
}
