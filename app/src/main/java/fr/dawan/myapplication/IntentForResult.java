package fr.dawan.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class IntentForResult extends AppCompatActivity {

    EditText edNombre1, edNombre2;
    TextView tvResultatRenvoye;
    Button btnCalculer;

    // ActivityForResult à gérer
    //1- Déclarer le résultat attendu
    ActivityResultLauncher<Intent> doubleResultat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_intent_for_result);

        /*
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
         */

        edNombre1 = findViewById(R.id.ed_nombre1);
        edNombre2 = findViewById(R.id.ed_nombre2);
        tvResultatRenvoye = findViewById(R.id.tv_resultat_renvoye);
        btnCalculer = findViewById(R.id.btn_calculer);

        //2- Enregistrement de 'Activity For Result
        doubleResultat = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult resutatRenvoye) {
                        //Gestion du résultat renvoyé par l'Activity qui a exécuté le traitement
                        if(resutatRenvoye.getResultCode() == RESULT_OK){
                            Intent data = resutatRenvoye.getData();
                            double somme = data.getDoubleExtra("resultat", 0);
                            tvResultatRenvoye.setText("Résultat renvoyé = "+somme);
                        }
                    }
                }
        );

        //Faire appel à une autre Activity pour faire le calcul et nous renvoyer le résultat
        btnCalculer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IntentForResult.this, IntentForCalcul.class);
                double nb1 = Double.parseDouble(edNombre1.getText().toString());
                double nb2 = Double.parseDouble(edNombre2.getText().toString());

                //Données de type simple: les injecter dans l'intent
                intent.putExtra("nb1", nb1);
                intent.putExtra("nb2", nb2);
                doubleResultat.launch(intent);
            }
        });
    }
}