package fr.dawan.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class SharedPreferenceActivity extends AppCompatActivity {

    EditText edEmail, edPassword;
    Button btnLogin;
    CheckBox chRemember;
    Context context = this;
    SharedPreferences sharedPref;
    FloatingActionButton floatButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_shared_preference);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        edEmail = findViewById(R.id.edEmail);
        edPassword = findViewById(R.id.edPassword);
        chRemember = findViewById(R.id.cb_remember);
        btnLogin = findViewById(R.id.btn_login);
        floatButton = findViewById(R.id.floatingActionButton);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chRemember.isChecked()){
                    saveData();
                    Toast.makeText(context, "Données sauvegardées......", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(context, "Aucune sauvegarde......", Toast.LENGTH_LONG).show();
                }
            }
        });

        floatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeActivity();
            }
        });

        restoreData();
    }

    private void closeActivity() {
        this.finish();
    }

    private void restoreData() {

        sharedPref = getSharedPreferences("remember", MODE_PRIVATE);
        edEmail.setText(sharedPref.getString("email",""));
        edPassword.setText(sharedPref.getString("password",""));
        Toast.makeText(context,"Données restaurées", Toast.LENGTH_LONG).show();
    }

    private void saveData() {
        //Initialiser SharedPreferences

        sharedPref = getSharedPreferences("remember", MODE_PRIVATE); //fichier visible uniquement par cette application
        //Editer le fichier
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("email", edEmail.getText().toString());
        editor.putString("password", edPassword.getText().toString());
        editor.commit(); //ce commit vaide les modifs

        //emplacement: data/data/la package/shared_prefs
    }
}