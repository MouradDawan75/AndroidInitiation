package fr.dawan.myapplication.providers;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import fr.dawan.myapplication.db.DbHelper;
import fr.dawan.myapplication.entities.Note;

public class NoteProvider extends ContentProvider {

    //Table Name
    private static String NOTE_TABLE = "note";

    //Colonnes
    private static final String COLONNE_NOTE_ID = "note_id";
    private static final String COLONNE_NOTE_TITLE = "note_title";
    private static final String COLONNE_NOTE_CONTENT = "note_content";

    private DbHelper dbHelper;

    @Override
    public boolean onCreate() {
        dbHelper = new DbHelper(getContext());
        return true;
    }

    //uri: content://fr.dawan.myapplication.providers.notes
    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        try{
            return dbHelper.getAllCursor();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return "text/plain";
        //uri: content://fr.dawan.myapplication.providers.notes
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        try{
            if(values.containsKey(COLONNE_NOTE_TITLE) && values.containsKey(COLONNE_NOTE_CONTENT)){
                Note note = new Note(values.getAsString(COLONNE_NOTE_TITLE), values.getAsString(COLONNE_NOTE_CONTENT));
                dbHelper.addNote(note);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return uri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {

        try{
            int id = Integer.parseInt(selection);
            Note note = dbHelper.getNoteById(id);
            if(note != null){
                dbHelper.deleteNote(note);
                return 1;
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {

        try{
            if( values.containsKey( COLONNE_NOTE_ID) && values.containsKey( COLONNE_NOTE_TITLE) && values.containsKey(COLONNE_NOTE_CONTENT)){
                Note note = new Note(values.getAsInteger(COLONNE_NOTE_ID),   values.getAsString(COLONNE_NOTE_TITLE), values.getAsString(COLONNE_NOTE_CONTENT));
                return dbHelper.updateNote(note);

            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return 0;
    }
}
