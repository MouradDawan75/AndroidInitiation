package fr.dawan.myapplication.services;


import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.List;
import java.util.Map;

import fr.dawan.myapplication.R;
import fr.dawan.myapplication.receivers.MusicReceiver;

public class MusicService extends Service {

    //Déclaration des différents composants

    private MediaPlayer mediaPlayer;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        /*
        Méthode permettant d'interagir avec l'UI en injectant des données dans l'intent et en le récupérant
        via un Broadcast receiver
        intent.setAction("musicService)
        intent.setPackage("fr.dawan.myapplication")
        intent.putExtra("data", "données à trasmettre")
        sendBroadcast(intent);
        Créer un Brodcast Recevier pour intercepter le message diffusé
         */
        return null;
    }

    //1 ére Méthode appelée lors de l'instantiation
    /*
    Initialiser les différents
     */
    @Override
    public void onCreate() {
        super.onCreate();
       mediaPlayer = MediaPlayer.create(this, R.raw.song);
    }

    //Méthode exécutée lors du démarrage du service
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.i(">>>MusicSevice", "Démarré......");
        mediaPlayer.start();
        /*
        START_STICKY: indique au système de recréer le service après avoir assez de ressources mémoire.
        START_REDELIVER_INTENT: indique au système de recréer le service et de le redilivrer via un Intent pour la méthode onStart
         */

        return START_STICKY;
    }

    //Méthode appelé lors de l'arrêt du service
    @Override
    public void onDestroy() {

        Log.i(">>> MusicService","Stoppé.........");
        mediaPlayer.release(); //stoppe le mediaPlayer

        /*
        Relancer le service en cas d'un arrêt
         */
        Intent i = new Intent(this, MusicReceiver.class);
        sendBroadcast(i);

        super.onDestroy();
    }
}
