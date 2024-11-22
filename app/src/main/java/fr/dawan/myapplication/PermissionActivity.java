package fr.dawan.myapplication;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class PermissionActivity extends BaseActivity {

    Button btnStorage, btnCamera;

    //2 codes aléatoires pour identifier les 2 permissions
    private static final int CAMERA_PERMISSION_CODE = 100;
    private static final int STORAGE_PERMISSION_CODE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_permission);
        /*
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });*/

        btnCamera = findViewById(R.id.btnCamera);
        btnStorage =findViewById(R.id.btnStorage);

        btnStorage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
                    verifPermission(Manifest.permission.READ_MEDIA_IMAGES, STORAGE_PERMISSION_CODE);
                }else{
                    verifPermission(Manifest.permission.READ_EXTERNAL_STORAGE, STORAGE_PERMISSION_CODE);
                }
            }
        });

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifPermission(Manifest.permission.CAMERA, CAMERA_PERMISSION_CODE);
            }
        });




    }

    //Méthode qui verifie et demande la permission via une dialogue
    private void verifPermission(String permission, int permissionCode) {
        if(ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_DENIED){
            //Demander la permission
            ActivityCompat.requestPermissions(this, new String[] {permission}, permissionCode);
        }else{
            Toast.makeText(this, "Permission déjà accordée", Toast.LENGTH_LONG).show();
        }
    }

    //Méthode qui s'exécute quand le user accèpte ou pas d'accorder la permission -> après la boite de dialogue
    //A utiliser en cas de besoin, selon la réponse du user


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_CODE){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Permission Camera accordée", Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(this, "Permission Camera refusée", Toast.LENGTH_LONG).show();
            }
        }

        if (requestCode == STORAGE_PERMISSION_CODE){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Permission Stockage accordée", Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(this, "Permission Stockage refusé", Toast.LENGTH_LONG).show();
            }
        }
    }
}