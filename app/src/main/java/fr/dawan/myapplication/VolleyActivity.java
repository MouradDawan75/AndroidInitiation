package fr.dawan.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

import java.util.List;

import fr.dawan.myapplication.entities.Product;

public class VolleyActivity extends BaseActivity {

    Button btnCall;
    ListView lvApi;
    ArrayAdapter<Product> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_volley);

        /*
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });*/

        btnCall = findViewById(R.id.btn_call_api);
        lvApi = findViewById(R.id.list_view_api);

        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                Pour les end point  et sites web en http:
                ajouter dans le manifest.xml
                <application:
                    android:usesCleartextTraffic="true"
                 */

                String url = "https://dawan.org/files/products.json";
                //Volley gère une pile de request
                //Initialiser la pile
                RequestQueue queue = Volley.newRequestQueue(VolleyActivity.this);

                JsonArrayRequest jsonArrayRequest =
                        new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {

                                try {
                                    List<Product> lst = Product.fromJson(response);
                                    adapter = new ArrayAdapter<>(VolleyActivity.this, android.R.layout.simple_list_item_1, lst);
                                    lvApi.setAdapter(adapter);

                                } catch (Exception e) {
                                   e.printStackTrace();
                                    Toast.makeText(VolleyActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                }

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(VolleyActivity.this, ""+error, Toast.LENGTH_LONG).show();
                            }
                        });

                //Ajout de a req dans la file
                queue.add(jsonArrayRequest);
            }
        });


    }
}