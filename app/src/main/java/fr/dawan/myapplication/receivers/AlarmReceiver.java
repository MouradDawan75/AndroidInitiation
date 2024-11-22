package fr.dawan.myapplication.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import fr.dawan.myapplication.services.MusicService;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(">>>AlarmReceiver","Exécution de la tâche planifiée...");
        /*
        Activity, service, thread, ExecutorService
         */

        //Appeler une t^che gérée par un Service
        Intent i = new Intent(context, MusicService.class);
        context.startService(i);

        //Exécutée la tâche dans un Thread
        /*
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                //tâche à exécuté
            }
        });*/

        //t.start();
    }
}
