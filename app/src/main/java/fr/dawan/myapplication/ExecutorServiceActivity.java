package fr.dawan.myapplication;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorServiceActivity extends BaseActivity     {

    TextView tvExecutor;
    Button btnExecutor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_executor_service);
        /*
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });*/

        tvExecutor = findViewById(R.id.tv_executor_service);
        btnExecutor = findViewById(R.id.btn_executor_service);

        //Réexecuter le btn storage pour générer le fichier à traiter
        File file = new File(getFilesDir().getAbsolutePath()+"/test.txt");

        /*
        Lecture du fichier ligne/ligne
        Actualiser le TextView à chaque fois qu'on trouve une occurrence de toto
         */
        ExecutorService executorService = Executors.newSingleThreadExecutor(); //résevoir de threads ne contenant qu'1 seul Thread
        Handler handler = new Handler(Looper.myLooper()); //nécessaire pour manipuler les composants graphiques

        btnExecutor.setOnClickListener(new View.OnClickListener() {

            int count = 0;

            @Override
            public void onClick(View v) {

                Toast.makeText(ExecutorServiceActivity.this, "Début de la tâche de fonds.....", Toast.LENGTH_LONG).show();


                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {

                            Log.i(">>> Executor Service","Path: "+file);
                            BufferedReader br = new BufferedReader(new FileReader(file));
                            String ligne = null;

                            int i = 0;
                            while ((ligne = br.readLine()) != null){
                                i++;
                                Log.i(">>> Ligne:", "Ligne"+i);

                                if(ligne.contains("toto")){
                                    count++;
                                }

                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        tvExecutor.setText("En cours: "+count);
                                    }
                                });

                                //simuler tâche lente
                                SystemClock.sleep(1000);

                            }

                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    count = 0;
                                    Toast.makeText(ExecutorServiceActivity.this,"Fin de la tâche de fonds", Toast.LENGTH_LONG).show();
                                }
                            });


                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                });

            }
        });
    }
}