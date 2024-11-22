package fr.dawan.myapplication.tools;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;

public class FileTool {

    /*

     2 type de fux pour les fichies:
     flux binaires: FileInputStream, FileOutputStream (+ buffer: BufferedOutputStream, BufferedInputStream)
     flux caractères: FileReader, FileWriter (+ buffer: BufferedReader, BufferedWriter)
      */

    public static void writeInternalStorage(String path, String content) throws Exception{

        //Flux binaire: ecriture carctère/caractère
        //doubler la taille du buffer
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(path), 16384);
        bos.write(content.getBytes());
        bos.close();
    }

    public static String readInternaStorage(String path) throws Exception{
        //flux caractères: lecture ligne / ligne
        BufferedReader br = new BufferedReader(new FileReader(path));
        String ligne = null;
        StringBuilder sb = new StringBuilder();
        while((ligne = br.readLine()) != null){
            sb.append(ligne).append("\n");
        }
        br.close();
        return sb.toString();
    }

    //Vérifier si le stockage externe est disponible
    public static boolean isExternalStorageWritable(){
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    public static boolean isExternalStorageReadable(){
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)
                || Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED_READ_ONLY);
    }

    public static boolean writeExternalStorage(Context context, String fileName, String content) throws Exception{
        if(isExternalStorageWritable()){

            File f = null;
            //Fichiers supprimés si appli. supprimée
            f = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);

            //Fichiers conservés si appli. supprimée
            //f = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
            if(!f.exists()){
                f.mkdirs();
            }
            Log.i(">>>> Chemin stockage externe", f.getAbsolutePath());
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(f.getAbsolutePath()+"/"+fileName));
            bos.write(content.getBytes());
            bos.close();
            return true;

        }else{
            throw new Exception("External storage unmounted.");
        }
    }

    public static String readExternalStorage(Context context, String fileName) throws Exception{
        if(isExternalStorageReadable()){

            File f = null;
            //Fichiers supprimés si appli. supprimée
            f = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);

            //Fichiers conservés si appli. supprimée
            //f = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);

            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(f.getAbsolutePath()+"/"+fileName));
            StringBuilder sb = new StringBuilder();
            int n = -1; //-1: caractère null

            byte[] buffer = new byte[2048];
            while((n = bis.read()) != -1){

                //new String(buffer, 0, n): pour convertir le code ASCII n (int) en String
                sb.append(new String(buffer, 0, n));
            }

            bis.close();
            return sb.toString();

        }else{
            throw new Exception("External storage unmounted.");
        }
    }

}
