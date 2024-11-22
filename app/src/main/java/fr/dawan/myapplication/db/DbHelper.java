package fr.dawan.myapplication.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import fr.dawan.myapplication.entities.Note;

/*
Une appli. Android ne peut utiliser qu'un seul type de BD (SQLite) et ne peut embaruée qu'une seule BD.
Pour le gestion de la BD, il suffit de créer une classe qui hérité de la classe SQLiteOpenHelper et d'implémenter
les 2 méthodes onCreate et onUpgrade

La suppression de l'application implique la suppression de la base de données créée

 */

public class DbHelper extends SQLiteOpenHelper {

    //Version de database
    private static final int DATABASE_VERSION = 2;

    //Database name
    private static final String DATABASE_NAME = "Note Manager";

    //Table Name
    private static String NOTE_TABLE = "note";

    //Colonnes
    private static final String COLONNE_NOTE_ID = "note_id";
    private static final String COLONNE_NOTE_TITLE = "note_title";
    private static final String COLONNE_NOTE_CONTENT = "note_content";



    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /*
    Méthode qui s'exécute qu'une seule fois lors de la création de la BD
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

        /*
        Cette gère la structure de la base de données
         */

        Log.i(">>>> Database","Created.....");

        /*
        Commandes SQL LDD: Langage de définition de données (concerne la structure de BD): CREATE, DROP, ALTER
        Commandes SQL LMD: Langage de manipulation de données: Select, Insert, Delete, Update
         */

        //V1
        String sql = "CREATE TABLE "+NOTE_TABLE+"("
                +COLONNE_NOTE_ID+" Integer Primary Key,"+COLONNE_NOTE_TITLE+" Text,"+COLONNE_NOTE_CONTENT+" Text"+")";

        db.execSQL(sql);

        //Restaurer les données sauvegardées dans onUpgrade

        //V2: ajout de la table product
        db.execSQL("CREATE TABLE products(id integer primary key autoincrement, description varchar(50), price real)");

        for (int i = 1; i < 6; i++) {
            db.execSQL("insert into products(description,price) values (?,?)", new Object[]{"prod"+i, i * 10});
        }


    }

    /*
    Méthode qui s'exécute à chaque modification de la version
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /*
        gère la sauvegarde éventuelle de la base de données
         */

        Log.i(">>>Database","updated.....");

        //Possibilité de faire des sauvegardes avant suppression
        //Ex: db.getAllNotes();
        db.execSQL("drop table if exists "+NOTE_TABLE);

        onCreate(db);
    }

    public void addNote(Note note){
        SQLiteDatabase db = this.getWritableDatabase(); //ouverture d'une connexion en écriture
        ContentValues values = new ContentValues();
        values.put(COLONNE_NOTE_TITLE, note.getNoteTitle());
        values.put(COLONNE_NOTE_CONTENT, note.getNoteContent());
        db.insert(NOTE_TABLE,null, values);
        db.close();
    }

    public void deleteNote(Note note){
        SQLiteDatabase db = this.getWritableDatabase(); //ouverture d'une connexion en écriture
        db.delete(NOTE_TABLE, COLONNE_NOTE_ID+" = ?", new String[]{String.valueOf(note.getNoteId())});
        db.close();
    }

    public int updateNote(Note note){
        SQLiteDatabase db = this.getWritableDatabase(); //ouverture d'une connexion en écriture
        ContentValues values = new ContentValues();
        values.put(COLONNE_NOTE_TITLE, note.getNoteTitle());
        values.put(COLONNE_NOTE_CONTENT, note.getNoteContent());
        int result = db.update(NOTE_TABLE,values,COLONNE_NOTE_ID+" = ?", new String[]{String.valueOf(note.getNoteId())});
        db.close();
        return result;
    }

    public List<Note> getAllNotes(){
        List<Note> notes = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "select * from "+NOTE_TABLE; // "select note_title, note_content from "+NOTE_TABLE;
        /*
        Cursor: flux qui pointe vers les lignes de la table
         */
        Cursor cursor = db.rawQuery(sql,null);
        if(cursor.moveToFirst()){
            do{
                Note note = new Note();
                note.setNoteId(Integer.parseInt(cursor.getString(0)));
                note.setNoteTitle(cursor.getString(1));
                note.setNoteContent(cursor.getString(2));
                notes.add(note);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return notes;
    }

    public Note getNoteById(int id){
        Note note = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(NOTE_TABLE, new String[]{COLONNE_NOTE_ID,COLONNE_NOTE_TITLE,COLONNE_NOTE_CONTENT}, COLONNE_NOTE_ID+"= ?",
                new String[]{String.valueOf(id)},null,null,null);

        if(cursor != null){
            cursor.moveToFirst();
            note = new Note();
            note.setNoteId(Integer.parseInt(cursor.getString(0)));
            note.setNoteTitle(cursor.getString(1));
            note.setNoteContent(cursor.getString(2));
        }
        cursor.close();
        db.close();

        return note;
    }

    public int getNoteCount(){
        String sql = "select * from "+NOTE_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql,null);
        int nb = cursor.getCount();
        cursor.close();
        db.close();
        return nb;
    }


    //Méthode pour insérer des données de test
    public void insertTestData(){
        if(this.getNoteCount() == 0){
            Note note1 = new Note("Android ListView","Voir ListVIew Exemple....");
            Note note2 = new Note("Android Permissions","Voir Request Permissions....");
            this.addNote(note1);
            this.addNote(note2);

        }
    }

    //Méthode pour exécuter des commandes SQL personnalisées côté Activity
    public Cursor getAllCursor(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from "+NOTE_TABLE, null);
        return cursor;
    }


}
