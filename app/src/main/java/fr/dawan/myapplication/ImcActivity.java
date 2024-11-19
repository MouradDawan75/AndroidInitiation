package fr.dawan.myapplication;

import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.Scanner;

public class ImcActivity extends BaseActivity {

    //Pour les ressources statiques de types fichiers: texte, html,.....etc
    //Générez le dossier asset -> clic droit sur app -> new -> folder -> asset

    //Déclaration des composants graphiques
    EditText edPoids, edTaille;
    Button btnCalcul;
    TextView tvResutat;
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_imc);

        attacherBoutonQuitter();

        /*
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
         */

        //Initialisation des composants graphiques
        edPoids = findViewById(R.id.edPoids);
        edTaille = findViewById(R.id.edTaille);
        tvResutat = findViewById(R.id.tv_resultat_imc);
        webView = findViewById(R.id.wb_interpretation);
        btnCalcul = findViewById(R.id.btn_imc);

        //Formule: IMC = poids en kg/taille² (en m)
        btnCalcul.setOnClickListener(new View.OnClickListener() {



            @Override
            public void onClick(View v) {
                //Masquer le clavier virtuel
                final InputMethodManager inputMethod = (InputMethodManager) getSystemService(ImcActivity.INPUT_METHOD_SERVICE);
                inputMethod.hideSoftInputFromWindow(v.getWindowToken(), 0);

                //Vérifer quel RadioButton est coché
                RadioButton rbCM = findViewById(R.id.rb_cm);
                double poids = Double.parseDouble( edPoids.getText().toString());
                double taille = Double.parseDouble(edTaille.getText().toString());

                if(rbCM.isChecked()){
                    //Convertir taille en mètres
                    taille = taille / 100;
                }

                double imc = poids / Math.pow(taille,2);
                tvResutat.setText("Votre imc = "+new DecimalFormat("#,##").format(imc));

                //Extraire le contenu du fichier imc.html et l'afficher dans la webView
                try {
                    InputStream is = getAssets().open("imc.html");
                    Scanner sc = new Scanner(is, "utf-8").useDelimiter("\\A"); //\A: pour lire le contenu en entier
                    String contenu = sc.hasNext()? sc.next():"";

                    webView.loadData(contenu,"text/html", "utf-8");

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(ImcActivity.this,e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}