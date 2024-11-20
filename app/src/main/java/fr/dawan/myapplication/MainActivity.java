package fr.dawan.myapplication;

import static android.content.Intent.CATEGORY_BROWSABLE;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;
import java.io.FileOutputStream;

/*
Gestion des layout pour les différentes tailles d'écran:
lien doc: https://android-developers.googleblog.com/2011/07/new-tools-for-managing-screen-sizes.html
Typical numbers for screen width dp are:

320: a phone screen (240x320 ldpi, 320x480 mdpi, 480x800 hdpi, etc).
480: a tweener tablet like the Streak (480x800 mdpi).
600: a 7” tablet (600x1024).
720: a 10” tablet (720x1280, 800x1280, etc).

 */

public class MainActivity extends BaseActivity {

    //Déclaration de tous les composants graphiques

    Button btnCycleVieActivity;

    //Déclaration de l'ActivityForResult
    ActivityResultLauncher<Intent> imageResultat;



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

        //Attacher le bouton quitter
        attacherBoutonQuitter();

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


                Toast.makeText(MainActivity.this, "Données transmises", Toast.LENGTH_LONG).show();

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

        //Enregistrement de l'ActivityForResult implicte: action exécutée par Android
        imageResultat = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if(result.getResultCode() == RESULT_OK){
                            Intent data = result.getData();

                            //getExtras() car il s'agit d'un type complèxe
                            //Le système Android utilise par défaut a clé data
                            Bitmap image = (Bitmap) data.getExtras().get("data");
                            ImageView iv = findViewById(R.id.iv_cature);
                            iv.setImageBitmap(image);

                            //Sauvegarde de l'image
                            try {
                                //getFilesDirectiry: renvoie la racine du projet (nom du package)
                                Log.i(">>>chemin", ""+getFilesDir());
                                File f = new File(getFilesDir()+"/test.png");
                                FileOutputStream fis = new FileOutputStream(f);
                                image.compress(Bitmap.CompressFormat.PNG, 90,fis);
                                fis.close();
                                Toast.makeText(MainActivity.this,"Image sauvegardée", Toast.LENGTH_LONG).show();

                            }catch (Exception e){
                                Log.i(">>>Erreur", e.getMessage());
                                Toast.makeText(MainActivity.this,"Erreur", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });

    }


    //IMC Calcul: Intent Explicite
    public void btnImcCalcul(View view) {
        Intent intent = new Intent(MainActivity.this, ImcActivity.class);
        startActivity(intent);
    }

   // Intent Implicite: Ouverture d'une URL
    // Si aucun résultat: ajoutez la permission <uses-permission ...Internet/> dans le manifest.xml
    public void btnOpenUrlClick(View view) {
        /*
        Plusieurs bugs possibes sur un émulateur.
        A tester de préference sur un téléphone physique
         */
        /*
        String url = "https://www.google.fr";
        Intent urlIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(urlIntent);

         */
        Toast.makeText(MainActivity.this,"A tester sur tél. physique", Toast.LENGTH_LONG).show();
    }

    //Intent implicite: appel téléphonique
    // Permission dans manifest.xml: CALL_PHONE
    public void btnCallClick(View view) {
        //Dans uri.parse, il faut respecter la syntaxe: utilisation du mot clé tel:
        Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:0606060606"));
        startActivity(callIntent);
    }

    //Intent implicite: Send SMS
    //Permission: SEND_SMS
    public void btnSmsClick(View view) {
        //Dans uri.parse, il faut respecter la syntaxe: utilisation du mot clé smsto:
        Intent smsIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:0606060606"));
        smsIntent.putExtra("sms_body","hello, blabla......");
        startActivity(smsIntent);
    }

    //Intent implicite:
    //Permission: INTERNET
    public void btnEmailClick(View view) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO,Uri.parse("mailto:mmahrane@dawan.fr"));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT,"test email....");
        emailIntent.putExtra(Intent.EXTRA_TEXT,"contenu de l'email....");
        //startActivity(emailIntent);

        //Syntaxe à utiliser pour compte gmail configuré sur le tél (emulateur ou physique)
        Intent intent2 = new Intent(Intent.ACTION_SENDTO,
                Uri.parse("mailto:?subject="+"sujet du mail"+"&to="+"mmahrane@dawan.fr"+"&body= contenu du mail.."));
        startActivity(intent2);
    }

    //Intent de type ActivityForResult
    //Permt de déleguer certaines tâches à 1 Activity intermédiaire et la fin de la tâche
    //cette dernière est censée renvoyer un résultat à l'Activty appelante
    public void btnIntentClick(View view) {
        Intent i = new Intent(MainActivity.this, IntentForResult.class);
        startActivity(i);
    }

    //ActivityForResult implicite
    //Permission: CAMERA
    public void btnCaptureClick(View view) {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        imageResultat.launch(intent);
    }

    //Manipulation de ListView
    public void btnListClick(View view) {
        Intent listIntent = new Intent(MainActivity.this, MyListActivity.class);
        startActivity(listIntent);

    }

    //Manipulation d'un Spinner
    public void btnSpinnerClick(View view) {
        Intent intent = new Intent(MainActivity.this, SpinnerActivity.class);
        startActivity(intent);
    }

    //Exo Panier
    public void btnCartClick(View view) {
        Intent cartIntent = new Intent(MainActivity.this, OrderActivity.class);
        startActivity(cartIntent);
    }
}