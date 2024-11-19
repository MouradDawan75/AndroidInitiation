package fr.dawan.myapplication;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class BaseActivity extends AppCompatActivity {

    /*
    Classe contenant les traitements (méthodes) communs aux autres Activity
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void attacherBoutonQuitter(){
        findViewById(R.id.btn_quitter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseActivity.this.finishAffinity(); //ferme cette Activity et les Activity filles (mais pas les services)
                //System.exit(0); ferme tous les composants de l'applications, pas uniquement les Activity
            }
        });
    }
}