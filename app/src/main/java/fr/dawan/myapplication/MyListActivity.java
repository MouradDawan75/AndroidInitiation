package fr.dawan.myapplication;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.dawan.myapplication.dialogs.StringDialogList;
import fr.dawan.myapplication.entities.Product;

public class MyListActivity extends AppCompatActivity {

    ListView lv;

    //Un ArrayAdapter est nécessaire pour manipuler une LIstView
    ArrayAdapter<String> myStringAdapter;
    ArrayAdapter<Product> myProdAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_my_list);

        /*
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });*/
        lv = findViewById(R.id.listView);

        //Type simple: String
        //fillWithStrings();

        //Type complèxe:
        fillWithProducts();

    }

    private void fillWithProducts() {
        List<Product> products = new ArrayList<>();
        products.add(new Product(1,"PC Dell", 1500));
        products.add(new Product(2,"Ecran HP", 99));
        products.add(new Product(3,"Copieur Xerox", 2450));

        myProdAdapter = new ArrayAdapter<>(MyListActivity.this, android.R.layout.simple_list_item_1, products);
        lv.setAdapter(myProdAdapter);


    }

    /*
    Pour créer des boites de dialogues personnalisées:
    1- Définir son design (un layout dans le dossier layout)
    2- Créer une classe qui hérite de la classe DilogFragment
    Par définition, une boite de dialogue est un fraguement (est un bout d'une interface)
     */

    private void fillWithStrings() {

        List<String> lst = new ArrayList<>(Arrays.asList("Toto","Tata","Titi"));

        //Affecter la liste de chaines à l'adapter en choisissant le context et un template
        /*
        R: pointe vers nos ressources (les res qui contient nos images, layouts, icones....)
        android.R: pointe vers des ressources fournies par Android (c'est des templates)
         */
        myStringAdapter = new ArrayAdapter<>(MyListActivity.this, android.R.layout.simple_list_item_1, lst);

        //Affecter l'adater à la ListView
        lv.setAdapter(myStringAdapter);

        //Gestion du cloc du btnAjouter
        findViewById(R.id.btn_listview_ajouter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Afficher la dialogue personnalisée
                StringDialogList dialog = new StringDialogList(); //appel implicite de la méthode onAttach
                dialog.show(getSupportFragmentManager(),"dialog1");

            }
        });

        //Gestion de la suppression
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //Afficher une boite de dilogue pour la confirmation
                AlertDialog.Builder builder = new AlertDialog.Builder(MyListActivity.this);
                builder.setTitle("Confirmation de la suppression")
                        .setMessage("Etes-vous sûr de vouloir supprimer l'item?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String item = myStringAdapter.getItem(position);
                                myStringAdapter.remove(item);
                                myStringAdapter.notifyDataSetChanged();
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                builder.create().show();



            }
        });

    }

    public void addString(String contenu) {
        myStringAdapter.add(contenu);
        myStringAdapter.notifyDataSetChanged(); //actualiser la ListView
    }
}