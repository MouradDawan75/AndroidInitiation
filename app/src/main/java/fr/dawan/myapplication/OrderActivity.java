package fr.dawan.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

import fr.dawan.myapplication.entities.Cart;
import fr.dawan.myapplication.entities.CartLine;
import fr.dawan.myapplication.entities.Product;

public class OrderActivity extends BaseActivity {

    Spinner prodSpinner;
    ArrayAdapter<Product> adapter;
    EditText edQuantite;
    Button btnAjouterPanier, btnVoirPanier;

    //Variable de classe: instancier une seule fois en mémoire
    public static Cart cart = new Cart();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_order);

        /*
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });*/

        prodSpinner = findViewById(R.id.spinner_products);
        edQuantite = findViewById(R.id.ed_quantite);
        btnAjouterPanier = findViewById(R.id.btn_ajouter_panier);
        btnVoirPanier = findViewById(R.id.btn_voir_panier);

        fillSpinner();

        btnAjouterPanier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Ajouter une ligne dans le panier
                Product prodSelectionne = adapter.getItem(prodSpinner.getSelectedItemPosition());
                int qty = Integer.parseInt(edQuantite.getText().toString());
                CartLine line = new CartLine(prodSelectionne, qty);

                //Ajoute de la line dans le panier
                cart.addLine(line);

                //MAJ du text de btnVoirPanier
                btnVoirPanier.setText("Voir le panier ("+cart.nbItems()+")");
            }
        });

        btnVoirPanier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(OrderActivity.this, CartActivity.class);
                startActivity(i);
            }
        });
    }

    private void fillSpinner() {

        List<Product> products = new ArrayList<>();
        products.add(new Product(1,"PC Dell", 1500));
        products.add(new Product(2,"Ecran HP", 99));
        products.add(new Product(3,"Copieur Xerox", 2450));

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, products);
        prodSpinner.setAdapter(adapter);
    }
}