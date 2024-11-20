package fr.dawan.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class IntentForCalcul extends BaseActivity {

    TextView tvNombre1, tvNombre2;
    Button btnAdditionner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_intent_for_calcul);
        /*
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

         */
        tvNombre1 = findViewById(R.id.tv_nombre1);
        tvNombre2 = findViewById(R.id.tv_nombre2);
        btnAdditionner = findViewById(R.id.btn_additionner);

        //Récupérer les 2 nombres fournis par IntentForResult et les afficher dans les textView
        double nb1 = getIntent().getDoubleExtra("nb1", 0);
        double nb2 = getIntent().getDoubleExtra("nb2", 0);

        tvNombre1.setText(String.valueOf(nb1));
        tvNombre2.setText(String.valueOf(nb2));

        btnAdditionner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Faire le calcul et renvoyer le résultat
                double addition = nb1 + nb2;
                Intent resultIntent = new Intent();
                resultIntent.putExtra("resultat", addition);
                setResult(RESULT_OK,resultIntent);

                //Fermer cette Activity
                finish();
            }
        });

    }
}