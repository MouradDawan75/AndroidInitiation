package fr.dawan.myapplication.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class ToastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(">>> ToastReceiver", "Données reçues");
        String contenu = intent.getStringExtra("data");
        Toast.makeText(context, contenu, Toast.LENGTH_LONG).show();
    }
}
