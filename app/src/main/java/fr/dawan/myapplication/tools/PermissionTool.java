package fr.dawan.myapplication.tools;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class PermissionTool {

    public static void checkPermission(Context context, String permission, int permissionCode){
        if(ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_DENIED){
            //Demander la permission
            ActivityCompat.requestPermissions((Activity) context, new String[] {permission}, permissionCode);
        }else{
            Toast.makeText(context, "Permission déjà accordée", Toast.LENGTH_LONG).show();
        }
    }
}
