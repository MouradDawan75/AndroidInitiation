package fr.dawan.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ThreadActivity extends BaseActivity {

    SeekBar seekBar;
    TextView tvSeekBar;
    int MAX_COUNTER = 100;
    Thread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_thread);
        /*
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });*/

        seekBar = findViewById(R.id.seek_bar);
        tvSeekBar = findViewById(R.id.tv_seek_bar_counter);
        seekBar.setMax(MAX_COUNTER);
    }

    boolean isRunning = false;
    int count = 0;

    public void btnStart(View view) {

        isRunning = true;


        thread = new Thread(new Runnable() {
            @Override
            public void run() {

                while (isRunning){

                    if(count < MAX_COUNTER){

                        //Un Thread ne peut pas manipuler les composants graphiques
                        /*
                        Si besoin d'actualiser des composants graphiques:
                        Utilisez la méthodes runOnUiThread
                         */

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                seekBar.setProgress(count);
                                tvSeekBar.setText("Counter = "+count);
                            }
                        });
                        count++;

                        //Pour simuler une tâche lente
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }
        });

        thread.start();

    }

    public void btnStop(View view) {
        isRunning = false;
    }
}