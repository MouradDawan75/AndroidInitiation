package fr.dawan.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import fr.dawan.myapplication.db.DbHelper;
import fr.dawan.myapplication.entities.Note;

public class AddOrUpdateNoteActivity extends BaseActivity {

    private static final int MODE_CREATE = 1;
    private static final int MODE_UPDATE = 2;
    int mode;

    EditText edTitle, edContent;
    Button btnValider, btnAnnuler;
    Note note;
    boolean actualiser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_or_update_note);
        /*
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });*/

        edTitle = findViewById(R.id.ed_note_title);
        edContent = findViewById(R.id.ed_note_content);
        btnValider = findViewById(R.id.btn_note_add_update_valider);
        btnAnnuler = findViewById(R.id.btn_note_add_update_annuler);

        //Données transmises par Activity appelante
        note = (Note) getIntent().getSerializableExtra("note");
        if(note == null){
            mode = MODE_CREATE;
        }else{
            mode = MODE_UPDATE;
            edTitle.setText(note.getNoteTitle());
            edContent.setText(note.getNoteContent());
        }

        btnAnnuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualiser = false;
                getOnBackPressedDispatcher().onBackPressed(); //déclenche l'appel de la méthode finish() qui ferme cette Activity
            }
        });

        btnValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Gestion de l'ajout et de la modif
                DbHelper db = new DbHelper(AddOrUpdateNoteActivity.this);
                String title = edTitle.getText().toString();
                String content = edContent.getText().toString();
                if (mode == MODE_CREATE){
                    note = new Note(title,content);
                    db.addNote(note);
                }else{
                    note.setNoteTitle(title);
                    note.setNoteContent(content);
                    db.updateNote(note);
                }

                db.close();
                actualiser = true;
                getOnBackPressedDispatcher().onBackPressed();
            }
        });
    }

    //Méthode appelée après le onBackPressed
    @Override
    public void finish() {;

        Log.i(">>>onBackPressed", "appelée.....");
        Intent data = new Intent();
        data.putExtra("actualiser",actualiser);
        setResult(Activity.RESULT_OK, data);
        super.finish();
    }
}