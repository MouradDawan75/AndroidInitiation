package fr.dawan.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CycleVieActivity extends BaseActivity {

    TextView tvUser, tvPassword;
    String contenu = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cycle_vie);

        /*
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        */

        //Récupérer les données simples transmises
        //Entre 2 Activity il y'à toujours un intent
        String user = getIntent().getStringExtra("user");
        String password = getIntent().getStringExtra("password");

        //Récupérer les données complèxes transmises
        //1- Récupérer le Bundle
        Bundle b = getIntent().getExtras();

        //2- Extraire les données injectées le Bundle
        String user2 = b.getString("user_bundle");
        String password2 = b.getString("password_bundle");

        tvUser = findViewById(R.id.tv_user);
        tvPassword = findViewById(R.id.tv_password);

        tvUser.setText(user2);
        tvPassword.setText(password2);

        Log.i(">>>Message", "onCreate");

    }

    //Méthodes du cycle de vie: permettent de sauvergder l'état actuel d'une Activity
    //Ex: lors de a réception d'un appel, l'application sera mise en pause. Si le contenu d'un champs de saisie
    // n'est pas sauvegardé il sera perdu.

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(">>>Message", "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(">>>Message", "onResume");

        //Restaurer le contenu sauvergardé
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(">>>Message", "onRestart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(">>>Message", "onPause");
        //Faire une sauvergarde d'un champs de saisie
        contenu = tvPassword.getText().toString();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(">>>Message", "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(">>>Message", "onDestroy");
    }


}