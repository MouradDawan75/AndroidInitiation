package fr.dawan.myapplication.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class MyCustomReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        if(intent.getAction().equalsIgnoreCase("myAction")){
            Bundle b=intent.getExtras();
            String message = b.getString("data");
            Log.i(">>> MyCustom Receiver", "Données reçues");
            Toast.makeText(context, "Données reçues", Toast.LENGTH_LONG).show();
        }
    }
}
