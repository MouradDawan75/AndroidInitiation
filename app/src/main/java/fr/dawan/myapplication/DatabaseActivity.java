package fr.dawan.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

import fr.dawan.myapplication.db.DbHelper;
import fr.dawan.myapplication.entities.Note;

public class DatabaseActivity extends BaseActivity {

    ListView lvNotes;
    ArrayAdapter<Note> adapter;
    DbHelper db;
    List<Note> noteList = new ArrayList<>();
    ActivityResultLauncher<Intent> refreshResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_database);

        /*
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });*/
        lvNotes = findViewById(R.id.listview_note);
        db = new DbHelper(this);
        db.insertTestData();
        noteList = db.getAllNotes();


        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, noteList);
        lvNotes.setAdapter(adapter);

        //Rattacher le menu contextuel
        registerForContextMenu(lvNotes);

        refreshResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if(result.getResultCode() == RESULT_OK){
                            Intent i = result.getData();
                            boolean actualiser = i.getBooleanExtra("actualiser",true);
                            if(actualiser){
                                noteList.clear();
                                noteList.addAll(db.getAllNotes());
                                adapter.notifyDataSetChanged();
                            }
                        }

                    }
                });


    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        getMenuInflater().inflate(R.menu.context_menu_database, menu);

        menu.setHeaderTitle("Choisir une action");

    }

    //Gestion des actions du menu contextuel

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        //Récupérer la note sélectionnée
         AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
         Note selectedNote = (Note) lvNotes.getItemAtPosition(info.position);

        switch (item.getItemId())
        {
            case R.id.action_details:
                Toast.makeText(this,selectedNote.getNoteContent() , Toast.LENGTH_SHORT).show();
                break;

            case R.id.action_add:

                Intent intent1 = new Intent(this, AddOrUpdateNoteActivity.class);
                //ActivityForResult
                refreshResult.launch(intent1);


                break;

            case R.id.action_delete:

                new AlertDialog.Builder(this)
                        .setTitle("Delete note")
                        .setMessage("Are you sure?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                db.deleteNote(selectedNote);
                                noteList.remove(selectedNote);
                                adapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();

                break;

            case R.id.action_update:
                Intent intent = new Intent(this, AddOrUpdateNoteActivity.class);
                //Fournir selectedNote
                //ActivityForResult
                intent.putExtra("note",selectedNote);
                refreshResult.launch(intent);

                break;
        }

        return super.onContextItemSelected(item);
    }
}