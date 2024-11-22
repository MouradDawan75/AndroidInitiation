package fr.dawan.myapplication;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import fr.dawan.myapplication.receivers.AlarmReceiver;

public class AlarmManagerActivity extends BaseActivity {

    EditText edTemps;
    Button btnStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_alarm_manager);

        /*
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });*/

        edTemps = findViewById(R.id.ed_alarm_temps);
        btnStart = findViewById(R.id.btn_alarm_start);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = Integer.parseInt(edTemps.getText().toString());
                activateAlarm(i);
            }
        });
    }

    private void activateAlarm(int i) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        /*
        Lien doc: https://developer.android.com/reference/android/app/AlarmManager
        RTC: temps exacte en millisecondes. N'allume pas le tél. s'il est éteind
        RTC_WAKEUP: temps exacte en millisecondes. Allume pas le tél. s'il est éteind
        Flag_One_Shot: tâche exécutée qu'1 seule fois
        Flag_MUABLE - Flag_IMMUABLE : tâche exéctuée à chaque appel de l'intent
         */

        Intent intent = new Intent(this, AlarmReceiver.class);

        /*
        PendingIntent: tâche en attente d'exécution.
        A exécuter via un Broadcast Receiver
         */
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);


        alarmManager.set(AlarmManager.RTC, System.currentTimeMillis()+ (i * 1000), pendingIntent);
        Toast.makeText(this, "Tâche planifiée...", Toast.LENGTH_LONG).show();

    }
}