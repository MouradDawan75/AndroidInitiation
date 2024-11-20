package fr.dawan.myapplication;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class BaseActivity extends AppCompatActivity {

    /*
    Classe contenant les traitements (méthodes) communs aux autres Activity
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void attacherBoutonQuitter(){
        findViewById(R.id.btn_quitter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseActivity.this.finishAffinity(); //ferme cette Activity et les Activity filles (mais pas les services)
                //System.exit(0); ferme tous les composants de l'applications, pas uniquement les Activity
            }
        });
    }

    //Gestion du main_menu: partagé par les autres Activity

    //Désérialisation du menu xml
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);

        //Gestion du SearchView
        SearchView sv = (SearchView) menu.findItem(R.id.search_bar_main_menu).getActionView();

        //service système nécessaire pour activer la fonctionnalité de recherche
        SearchManager sm = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        sv.setSearchableInfo(sm.getSearchableInfo(getComponentName()));

        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            //Méthode qui s'exécute lors du clic sur la loupe qui s'affiche dans le clavier
            @Override
            public boolean onQueryTextSubmit(String query) {

                Toast.makeText(BaseActivity.this, query, Toast.LENGTH_LONG).show();
                return false;
            }

            //Méthode qui s'exécute à chaque modification du text du search view
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


        return super.onCreateOptionsMenu(menu);
    }

    //Gestion des actions du menu
    /*
    R: classe générée par l'IDE contenant des ids (entiers en contantes) pour chaque fichier ressource
    les IDS des menus et des menus contextuels ne sont pas des constantes depuis Gradle en version (8).
    Solution: modifier les propriétés de gradle en ajoutant cette ligne dans le fichier build.gradle.properties
    android.nonFinalResIds=false
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.item_exit:
                BaseActivity.this.finishAffinity();
                return true;

            case R.id.item_imc_activity:
                Intent i = new Intent(this, ImcActivity.class);
                startActivity(i);
                return true;

            case R.id.item_listview:
                Intent intent2 = new Intent(this, MyListActivity.class);
                startActivity(intent2);
                return true;

        }

        return super.onOptionsItemSelected(item);
    }
}