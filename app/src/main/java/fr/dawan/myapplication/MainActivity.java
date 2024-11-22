package fr.dawan.myapplication;

import android.Manifest;
import android.app.DownloadManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.app.NotificationCompat;
import androidx.core.graphics.drawable.IconCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import fr.dawan.myapplication.receivers.CancelReceiver;
import fr.dawan.myapplication.receivers.MyDownloadReceiver;
import fr.dawan.myapplication.receivers.MyReceiver;
import fr.dawan.myapplication.receivers.ToastReceiver;
import fr.dawan.myapplication.tools.FileTool;
import fr.dawan.myapplication.tools.PermissionTool;

/*
Gestion des layout pour les différentes tailles d'écran:
lien doc: https://android-developers.googleblog.com/2011/07/new-tools-for-managing-screen-sizes.html
Typical numbers for screen width dp are:

320: a phone screen (240x320 ldpi, 320x480 mdpi, 480x800 hdpi, etc).
480: a tweener tablet like the Streak (480x800 mdpi).
600: a 7” tablet (600x1024).
720: a 10” tablet (720x1280, 800x1280, etc).

Site pour générer des images avec différentes densités:
https://www.appicon.co/

Copier/coller les dossiers générés à la racine du dossier res

 */

public class MainActivity extends BaseActivity {

    //Déclaration de tous les composants graphiques

    Button btnCycleVieActivity;

    //Déclaration de l'ActivityForResult
    ActivityResultLauncher<Intent> imageResultat;

    DownloadManager downloadManager;



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

        //Déclaration des Broadcast Receiver:
        /*
        Soit dans cette méthode, soit dans le manifest.xml
        Dans cette méthode, il est recommandé de déclarer les Receiver pour les actions du système Android
         */
        registerReceiver(new MyReceiver(), new IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            registerReceiver(new MyDownloadReceiver(), new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE), Context.RECEIVER_EXPORTED);
        }

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

    //Exo Quiz
    public void btnQuizClick(View view) {
        startActivity(new Intent(MainActivity.this, QuizActivity.class));
    }


    /*
    SharedPreferences:
    c'est un fichier généré par Android et est accésible dans toute l'application en lecture/écriture
    On l'utiise pour stocker les types simples de données.
    La désinstallation de l'appli. implique la suppression de tous les fichiers sharedpreferences générés par
    l'appli.
     */

    public void btnSharedClick(View view) {
        startActivity(new Intent(this, SharedPreferenceActivity.class));

        //Pour modifier le thème du layout:
        /*
        Créer un thème perso dans le fichier res: thème
        L'appliquer dans le manifest.xml
        Si utilisation d'une image aved différentes densitées:
        Créer un thème sans la barre d'action
         */
    }


    /*
    Stockage interne:
    - Aucune permission nécessaire dans le manifest
    - Idéal pour stocker des fichiers propres à 'application (non visibles par les autres appli.)
    - La désinstallation de l'application implique la suppression de tous les fichiers crées par cette appli.

    2 types de stockages:
    - getFileDir(): stockage permanent (fichiers conservés tant que l'apllication n'est pas supprimée)
    - getCacheDir(): fichier supprimés aléatoirement par Android pour libérer de l'espace

    Stockage externe:
    Nécessite des permissions dans le manifest.
    - Idéal pour partager des fichiers avec les autres applications
    - context.getExternalFileDir(): fichiers supprimés si application supprimée
    - Environement.getExternalPublicDirectory(): fichiers conservés si application supprimée

    Pour utiliser le stockage externe, on doit vérifier son état (disponible ou pas)

    --------- Ecriture -----------
    *** Pour Android < 12 ( < api 33) - Api >= 33 non nécessaire
    <uses-permission WRITE_EXTERNAL_STORAGE/>

    --------- Lecture ------------
    *** Pour Android api >= 33 - aucune permission

    <uses-permission WRITE_EXTERNAL_STORAGE/> api < 33

    api >= 33: on doit préciser le type de fichiers en lecture: audio, video, image
    Pas mal de permission à gérer:
    <uses-permission --------- READ_MEDIA_IMAGE
    <uses-permission --------- READ_MEDIA_VIDEO
    <uses-permission --------- READ_MEDIA_AUDIO

    Pour Android 10: ajouter dans le manifest.xml:
    <application
        android:requestLegacyExternalStorage="true"

     */

    public void btnStorageClick(View view) {

        //Stockage interne
        Log.i(">>> Sockage interne", getFilesDir().getAbsolutePath());
        String fileName = "test.txt";

        try {
            //Ecriture interne
            FileTool.writeInternalStorage(getFilesDir().getAbsolutePath()+"/"+fileName,"toto \n tata \n titi \n test \n toto \n dawan \n toto");
            Toast.makeText(this, "Ecriture interne OK", Toast.LENGTH_LONG).show();

            //Lecture interne
            String contenu = FileTool.readInternaStorage(getFilesDir().getAbsolutePath()+"/"+fileName);
            Toast.makeText(this, contenu, Toast.LENGTH_LONG).show();

        }catch (Exception e){
            Log.i(">>>>Exception", e.getMessage());
            e.printStackTrace();
        }

        //Stockage externe
        try {
            //Ecriture externe
            FileTool.writeExternalStorage(this, fileName, "External content.......");

            //Lecture externe
            String content = FileTool.readExternalStorage(this, fileName);
            Toast.makeText(this, content, Toast.LENGTH_LONG).show();

        }catch (Exception ex){
            Log.i(">>> Exception", ex.getMessage());
            ex.printStackTrace();
        }
    }
    /*
    Gestion des permissions
    2 types de permissions:
    - Install-time-permission: jusqu'à version (api 22 - android 5), les demandes de permissions s'effectuent
    au moment de l'installation de l'application. Si le user refuse une des permissions, l'appli. ne sera pas installées

    - runtime-permission: s'exécutent au moment de l'appel de la fonctionnalité

    Depuis Android api >= 23, les autorisations ne sont pas demandées a l'instalation de l'application.

    - permissions normales: s'installent implicitement au moment d'installer l'application (network, data mobile, internet..)
    - permissions dangereuses: tout ce qui concerne l'accès aux données privées (sms, appels, contacts, gps....)

    - Gestion des permissions dangereuses:
    * Les déclarer dans le manifest.xml
    * Dans le code définir une méthode qui demande la permission au user
    * Vérifier si l'autorisation est déjà accordée. Si elle ne l'ai pas, on demande au user de l'accèpter ou pas
    via une boite dialogue

     */

    public void btnPermissionClick(View view) {
        startActivity(new Intent(this, PermissionActivity.class));
    }

    //Exemple Base de données
    /*
    Android = Noyau (linux) + moteur Sqlite
     */
    public void btnDatabaseClick(View view) {
        startActivity(new Intent(this, DatabaseActivity.class));
    }

    /*
    Volley est une Api qui permet d'exécuter des requêtes HTTP (appeler des API)
    https://google.github.io/volley/
    1- ajouter la dépendence dans build.gradle via le menu file -> project settings -> dependencies -> clic sur + et faire une recherche
    2-
    Trois types de contenu possibles  à récupérer:
     - String
     - JsonObject
     - JsonArrayObject
     */
    public void btnVolleyClick(View view) {
        startActivity(new Intent(this, VolleyActivity.class));
    }


    /*
    Une appli. Android peut reçevoir des messages diffusés soit par Android, soit par d'autres applications.
    Ces messages sont diffusés lorsque des évenements se produisent. Ex: activation du wifi, batterie faible, boot système....etc
    Une application doit s'abonner à ce type d'évenement pour recçevoir le message diffusé par l'évent qu'elle surveille.

    Une appli. peut aussi diffuser un message qui sera intércepter par toutes les appli. qui sont à l'écoute.

    Pour s'abonner il suffit de créer une classe qui hérite de la classe BoradcastRecevier.
    Le Broadcast receiver doit être déclarer soit dans le manifest.xml, soit dans la méthode onCreate

     */
    public void btnBroadcastClick(View view) {
        /*
        Receiver déclaré dans la méthode onCreate
         */
        Toast.makeText(this,"Voir receivers/MyReceiver", Toast.LENGTH_LONG).show();
        Log.i(">>> Broadcast", "MyReceiver.....");
    }

    /*
    Exemple d'un méssage diffusé par une application.
    Toutes les appli. à l'écoute reçoivent ce méssage y compris l'application en question
    Receiver déclaré dans le manifest.xml
     */
    public void btnCustomtClick(View view) {
        Intent intent = new Intent();

        //Fournir le nom de l'action à utiliser dans le manifest.xml
        intent.setAction("myAction");

        //Fournir le nom de l'application qui a diffusé le méssage (package name)
        intent.setPackage("fr.dawan.myapplication");

        //Possibilité d'insérer des données dans l'intent
        intent.putExtra("data", "données fournies par l'application");

        sendBroadcast(intent);
        Log.i(">>> Custom broadcast", "méssage diffusé......");

    }

    /*
    Exemple d'utilisation d service de téléchargment d'Android
     */
    public void btnDownloadClick(View view) {
       downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
       Uri uri = Uri.parse("https://code.visualstudio.com/sha/download?build=stable&os=win32-x64-user");

       //DownloadManager gère une pile de requêtes
        DownloadManager.Request req = new DownloadManager.Request(uri);
        req.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,"vscode");
        req.setAllowedOverRoaming(false); //param qui permet de basculer du wifi vers data mobile
        downloadManager.enqueue(req);
        Toast.makeText(this,"Début du téléchargement", Toast.LENGTH_LONG).show();
    }

    public void btnDownloadFolderClick(View view) {
        Intent intent = new Intent(DownloadManager.ACTION_VIEW_DOWNLOADS);
        startActivity(intent);
    }

    /*
    Android mets fin à une tâche en cours d'exécution si la durée dépasse une limite (entre 20 et 30 secondes)
    Pour gérer ce type de tâches (tâches dites lentes), il faut les exécuter en arrière plan en utilisant soit
    un Thread, soit un Thread Pool + un Handler
     */
    public void btnThreadClick(View view) {
        startActivity(new Intent(this, ThreadActivity.class));
    }

    public void btnExecutorServiceClick(View view) {
        startActivity(new Intent(this, ExecutorServiceActivity.class));
    }
    /*
    Un Service permet d'exécuter des tâches en arrière plan sans interaction avec UI. Un composant de l'application
    démarre le service startService() et ce dernier continu son exécution en background même si le composant appelant est détruit.
    On parle de service illimité qui doit être stoppé explicitement.
    Ex: jouer de la musique en arrière plan.
    Un service doit être déclaré dans le manifest.xml

     */

    public void btnServiceClick(View view) {
        startActivity(new Intent(this, PlayMusicActivity.class));
    }
    /*
    AlaramManager: service système qui fournit un accès au système d'alarme d'Android permettant de
    planifier l'exécution de tâches.
     */

    public void btnAlarmClick(View view) {
        startActivity(new Intent(this, AlarmManagerActivity.class));
    }

    /*
    Un Fragment est une partie d'une IHM qui doit être utiisé dans une Activity
     */
    public void btnFragmentClick(View view) {
        startActivity(new Intent(this, DemoFragmentActivity.class));
    }


    /*
    Notifications: nécessite la permission <uses-permission: VIBRATE/>
    si Android >= 13 (api 33) ajoutez cette permission <uses-permission POST_NOTIFICATION/>
    De plus, s'il s'agit d'une permission dangereuse, on doit demander explicitement au user d'autoriser ou pas l'envoi
    des notifs via une boite de dialogue, car l'envoi est désactivé par défaut.

    Pour Android <= 12: la boite de dilaogue s'affiche automatiquement.

     */

    static final int NOTIFICATION_PERMISSION_CODE = 111;

    public void btnNotifClick(View view) {

        //Récupérer le gestionnaire de notifs
        NotificationManager notifManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        //Demande d'autorisation si API Android >= 33 (afficher une boite de dialogue)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            PermissionTool.checkPermission(this, Manifest.permission.POST_NOTIFICATIONS, NOTIFICATION_PERMISSION_CODE);
        }

        //Si API Android >= 26: un canal est nécessaire pour l'envoi des notifs
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("channel1", "myChannel", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("my Channel");
            notifManager.createNotificationChannel(channel);
        }

        //Création d'une large icone:
        Bitmap largeIcone = BitmapFactory.decodeResource(getResources(),R.drawable.dawan);

        //Création d'un bigText
        String bigText = "Dawan : un centre engagé\n" +
                "Transmettre l’envie d’apprendre, de comprendre, de progresser, de partager : c’est ce qui nous motive chaque " +
                "jour pour améliorer sans cesse l’entreprise, le travail de nos équipes et la satisfaction de nos clients.\n" +
                "C’est pour cela que nous mettons tout en œuvre pour vous accueillir dans le meilleur des cadres, avec les meilleurs contenus, les meilleurs formateurs, " +
                "des supports constamment renouvelés et une démarche commerciale résolument innovante.";

        //Création de l'action annuler;
        Intent cancelIntent = new Intent(this, CancelReceiver.class);

        PendingIntent cancelPending = PendingIntent.getBroadcast(this, 0, cancelIntent, PendingIntent.FLAG_IMMUTABLE);
        NotificationCompat.Action cancelAction = new NotificationCompat.Action.Builder(IconCompat.createWithResource(this,R.drawable.flotting_icon), "Cancel", cancelPending).build();

        //Création de l'action Toast
        Intent toastIntent = new Intent(this, ToastReceiver.class);
        toastIntent.putExtra("data", "Données fournies via la notif......");

        PendingIntent toastPending = PendingIntent.getBroadcast(this, 1, toastIntent, PendingIntent.FLAG_IMMUTABLE);
        NotificationCompat.Action toastAction = new NotificationCompat.Action.Builder(IconCompat.createWithResource(this,R.drawable.ic_launcher_foreground),"Toast", toastPending).build();



        //Création de la notification via le builder
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"channel1");
        builder.setContentTitle("My Notif")
                .setSmallIcon(R.drawable.ic_launcher_foreground) // smallIcon est obligatoire
                .setContentText("Welcome, depuis la notif....")
                .setLargeIcon(largeIcone)
                .addAction(cancelAction)
                .addAction(toastAction)

                //.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(largeIcone)); //scrollable
                .setStyle(new NotificationCompat.BigTextStyle().bigText(bigText)); //écrase contentText et bigPicture

        Notification notif = builder.build();

        notifManager.notify(100, notif);


    }

    /*
    Communications inter applications:
    ContentProvider: est e seul moyen permettant à des applications de communiquer (de partager du contenu)
    Un ContentProvider doit être déclaré dans le manifest.xml
    <provider
            android:authorities="fr.dawan.myapplication.providers.notes"
            android:name=".providers.NoteProvider"
            android:enabled="true"
            android:exported="true"/>

      URI: content://fr.dawan.myapplication.providers.notes
     */


    public void btnProvidersClick(View view) {
        //Afficher la listes des ContentProviders
        List<PackageInfo> pkInfos = getPackageManager().getInstalledPackages(PackageManager.GET_PROVIDERS);


        for(PackageInfo pi : pkInfos){
            ProviderInfo[] providers = pi.providers;
            if(providers != null){
            for(ProviderInfo provInfo : providers) {

                if (provInfo.authority != null) {
                    //authority: uri du provider
                    Log.i(">>> provider", provInfo.authority + " " + provInfo.enabled);

                }
            }

            }
        }

        //Vérification du fonctionnement du ContentProvider
        //Application appelante: COntentResover
        ContentResolver resolver = getContentResolver();
        Uri uri = Uri.parse("content://fr.dawan.myapplication.providers.notes");
        Cursor cursor = resolver.query(uri,null,null,null,null);
        while(cursor.moveToNext()){
            Log.i(">>> Note", cursor.getString(1));
        }
        cursor.close();
    }

}