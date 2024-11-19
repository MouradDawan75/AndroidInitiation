package fr.dawan.myapplication.dialogs;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import fr.dawan.myapplication.MyListActivity;
import fr.dawan.myapplication.R;
import fr.dawan.myapplication.entities.Product;

public class ProductDialog extends DialogFragment {

    EditText edId, edDescription, edPrice;
    Button btnValider, btnSupprimer, btnAnnuler;
    Context context;
    int selectedPosition = -1; // L'index d'une liste commence à 0 : -1 veut dire aucun élément selectionné


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    //CRUD: Create Read Update Delete

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.dialog_product_fragment, container, false);

        edId = v.findViewById(R.id.id_product_dialog);
        edDescription = v.findViewById(R.id.description_product_dialog);
        edPrice = v.findViewById(R.id.price_product_dialog);

        btnValider = v.findViewById(R.id.btn_valider_product_dialog);
        btnSupprimer = v.findViewById(R.id.btn_supprimer_product_dialog);
        btnAnnuler = v.findViewById(R.id.btn_annuler_product_dialog);

        //Gestion des données fournies par MyListActivity - le Bundle
        if(getArguments() != null){
            Product prod = (Product) getArguments().getSerializable("prod");
            //Remplir les différents champs de saisie
            edId.setText(String.valueOf(prod.getId()));
            edDescription.setText(prod.getDescription());
            edPrice.setText(String.valueOf(prod.getPrice()));
            selectedPosition = getArguments().getInt("selectedItemPosition");
            btnValider.setText("Modifier");
        }else{
            btnValider.setText("Ajouter");
        }

        //clic du btnAnnuler
        btnAnnuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        //clic btnValider: gère l'ajout et la modif
        btnValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Créer un product à partir des champs de saisie
                if(edId.getText().toString().isBlank() || edDescription.getText().toString().isBlank()
                        || edPrice.getText().toString().isBlank()){
                    Toast.makeText(context, "Tous les champs sont obligatoires", Toast.LENGTH_SHORT).show();
                }else{
                    Product p = new Product();
                    p.setId(Integer.parseInt(edId.getText().toString()));
                    p.setDescription(edDescription.getText().toString());
                    p.setPrice(Double.parseDouble(edPrice.getText().toString()));

                    //Actualiser ListView - Fermer la dialog
                    ((MyListActivity)context).addProduct(p, selectedPosition);
                    getDialog().dismiss();
                }

            }
        });

        //clic btnSupprimer
        btnSupprimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Fournir la position du produit à l'Activity appelante
                ((MyListActivity)context).deleteProduct(selectedPosition);
                getDialog().dismiss();
            }
        });

        return v;
    }
}
