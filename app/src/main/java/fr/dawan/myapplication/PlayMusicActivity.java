package fr.dawan.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import fr.dawan.myapplication.services.MusicService;

/*
Pour les fichiers audio et vidéo, générer le fichier ressource raw -> clic droit sur res -> android resource directory -> choisir raw
 */
public class PlayMusicActivity extends BaseActivity {

    Button btnPlay, btnStop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_play_music_activity);
        /*
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });*/

        btnPlay = findViewById(R.id.btn_play_music);
        btnStop = findViewById(R.id.btn_stop_music);

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playMusic();
            }
        });
        
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopMusic();
            }
        });
    }

    private void stopMusic() {
        Intent i = new Intent(this, MusicService.class);
        stopService(i);
    }

    private void playMusic() {
        Intent i = new Intent(this, MusicService.class);
        startService(i);
    }
}