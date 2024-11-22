package fr.dawan.myapplication.receivers;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationManagerCompat;

public class CancelReceiver  extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManagerCompat manager = NotificationManagerCompat.from(context);
        Log.i(">>>CancelReceiver", "fermer la notification....");
        manager.cancel(100);
    }
}
