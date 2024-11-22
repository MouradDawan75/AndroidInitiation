package fr.dawan.myapplication;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import fr.dawan.myapplication.fragments.Fragment1;
import fr.dawan.myapplication.fragments.Fragment2;

public class DemoFragmentActivity extends AppCompatActivity {

Fragment frag1, frag2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_demo_fragment);

        /*
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });*/

        //frag1 s'affiche par défaut dans cette Activity
        frag1 = Fragment1.newInstance(null,null);
        loadFragment(frag1);
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fragManager = getSupportFragmentManager();
        fragManager.beginTransaction().replace(R.id.frame_layout, fragment).commit();
    }

    public void btnFrag1Click(View view) {
        loadFragment(frag1);
    }

    public void btnFrag2Click(View view) {
        if (frag2 == null){
            frag2 = Fragment2.newInstance(null,null);
            loadFragment(frag2);
        }
    }
}