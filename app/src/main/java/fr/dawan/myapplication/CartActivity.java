package fr.dawan.myapplication;

import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import fr.dawan.myapplication.entities.CartLine;

public class CartActivity extends BaseActivity {

    TableLayout tbLines;
    TextView tvTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cart);

        /*
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });*/

        tbLines = findViewById(R.id.table_layout_cart);
        tvTotal = findViewById(R.id.tv_total_cart);

        displayLines();
    }

    private void displayLines() {
        /*
        Faire une boucle for sur les lignes du panier
        Pour chaque ligne,: créer un TableRow ensuite l'ajouter dans TableLayout: tbLines.addView(tableRow)
         */

        addLineToTable(null, -1); //pour générer les headers des colonnes

        for (int i = 0; i < OrderActivity.cart.getLines().size(); i++){

            //Récupérer la ligne en cours dans le panier
            CartLine line = OrderActivity.cart.getLines().get(i);
            addLineToTable(line,i);
        }

        tvTotal.setText("Total panier = "+OrderActivity.cart.getCartTotal());
    }

    private void addLineToTable(CartLine line, int i) {

        TableRow row = new TableRow(this);

        // Colonne Prod
        TextView tvProd = new TextView(this);
        if(i == -1){
            tvProd.setText("Prod");
            tvProd.setTypeface(tvProd.getTypeface(), Typeface.BOLD);
            tvProd.setGravity(Gravity.CENTER);
            tvProd.setTextColor(getResources().getColor(R. color. red));
        }else{
            tvProd.setText(line.getProduct().getDescription());
        }


        row.addView(tvProd);

        //Colonne Qte
        TextView tvQte = new TextView(this);
        if (i == -1){
            tvQte.setText("Qté");
            tvQte.setGravity(Gravity.CENTER);
            tvQte.setTypeface(tvProd.getTypeface(), Typeface.BOLD);
            tvQte.setTextColor(getResources().getColor(R. color. red));
            row.addView(tvQte);
        }else{
            LinearLayout linearLayout = new LinearLayout(this);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);

            Button btnMoins = new Button(this);
            btnMoins.setText("-");

            Button btnPlus = new Button(this);
            btnPlus.setText("+");

            //Gestion des clics btnMoins et btnPLus

            tvQte.setText(String.valueOf(line.getQty()));

            linearLayout.addView(btnMoins);
            linearLayout.addView(tvQte);
            linearLayout.addView(btnPlus);

            row.addView(linearLayout);
        }

        //Colonne PrixU
        TextView tvPrixU = new TextView(this);
        if (i == -1){
            tvPrixU.setText("PrixU");
            tvPrixU.setGravity(Gravity.CENTER);
            tvPrixU.setTypeface(tvProd.getTypeface(), Typeface.BOLD);
            tvPrixU.setTextColor(getResources().getColor(R. color. red));
        }else{
            tvPrixU.setText(String.valueOf(line.getProduct().getPrice()));
        }

        row.addView(tvPrixU);

        //Colonne PrixT
        TextView tvPrixT = new TextView(this);
        if(i == -1){
            tvPrixT.setText("PrixT");
            tvPrixT.setGravity(Gravity.CENTER);
            tvPrixT.setTypeface(tvProd.getTypeface(), Typeface.BOLD);
            tvPrixT.setTextColor(getResources().getColor(R. color. red));
        }else{
            tvPrixT.setText(String.valueOf(line.getTotal()));
        }

        row.addView(tvPrixT);

        //Colonne Action
        if(i == -1){
            TextView tvAction = new TextView(this);
            tvAction.setText("Action");
            tvAction.setGravity(Gravity.CENTER);
            tvAction.setTypeface(tvProd.getTypeface(), Typeface.BOLD);
            tvAction.setTextColor(getResources().getColor(R. color. red));
            row.addView(tvAction);
        }else{
            Button btnDelete = new Button(this);
            btnDelete.setText("[X]");

            //Gestion du clic

            row.addView(btnDelete);
        }

        //Affecter row à tableLayout
        tbLines.addView(row);

    }
}