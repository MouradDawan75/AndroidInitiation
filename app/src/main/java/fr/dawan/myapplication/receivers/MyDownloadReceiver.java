package fr.dawan.myapplication.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class MyDownloadReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Téléchargement terminé....", Toast.LENGTH_LONG).show();
        Log.i(">>> DownloadManager","Téléchargement terminé....");
    }
}
