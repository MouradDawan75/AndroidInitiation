package fr.dawan.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    //Déclaration de tous es composants graphiques

    Button btnCycleVieActivity;



    //onCreate: méthode exécutée lors de l'affichage de cette Activity
    //Permet d'initialiser tous les composants graphiques + affecter le layout à l'Activity


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);

        //R (ressources - pointe vers le dossier res): classe générée par l'IDE qui pointe vers
        // toutes les ressources du projet.
        //Pour chaque ressource, l'IDE génère un ID (un entier)

        setContentView(R.layout.activity_main);

        /*
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

         */

        //Initialisation de tous les composants graphiques via leur id

        btnCycleVieActivity = findViewById(R.id.btn_Activity_lifecycle);

        btnCycleVieActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Afficher une autre Activity: CycleVieActivity
                //Pour passer d'une Activity à une autre, on utilise un objet de type Intent

                //this: mot clé de Java qui pointe vers l'objet (classe) en cours
                Intent intent = new Intent(MainActivity.this, CycleVieActivity.class);

                //Transmettre des données à CycleVieActivity
                //Données simples: int, double, boolean, String..
                //il suffit de les injecter dans l'intent via la méthode putExtra(clé, valeurAssociée)

                intent.putExtra("user", "admin");
                intent.putExtra("password", "@admin@");


                Toast.makeText(MainActivity.this, "Données tansmises", Toast.LENGTH_LONG).show();

                //Données complèxes: objets - l'utilisation d'un Bundle est nécessaire

                //1 - Insérer les données complèxes dans un Bundle
                Bundle b = new Bundle();
                b.putString("user_bundle", "admin_bundle");
                b.putString("password_bundle", "password_bundle");

                //2- Insérer le Bundle dans l'intent
                intent.putExtras(b);

                startActivity(intent);
            }
        });
    }


    //IMC Calcul: Intent Explicite
    public void btnImcCalcul(View view) {
        Intent intent = new Intent(MainActivity.this, ImcActivity.class);
        startActivity(intent);
    }


}