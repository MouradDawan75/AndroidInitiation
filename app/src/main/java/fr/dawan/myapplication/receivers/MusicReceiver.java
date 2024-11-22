package fr.dawan.myapplication.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import fr.dawan.myapplication.services.MusicService;

public class MusicReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(">>>MusicReceiver", "MusicService stoppé.....");

        //Redémarrer MUsicService
        Intent i = new Intent(context, MusicService.class);
        context.startService(i);

        Log.i(">>>MusicReceiver", "MusicService relancé.....");

    }
}
