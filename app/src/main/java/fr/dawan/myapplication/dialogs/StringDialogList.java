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

public class StringDialogList extends DialogFragment {

    EditText editText;
    Button btnAdd;
    Context context;

    //1ére Méthode du cycle de vie de la dialog exécutée: onAttach qui permet de relier l'Activity à la boite de dialogue

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    //Méthode qui permet de convertir (désérialiser le layout xml)  en Vue
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //Pour désérialiser des layout xml, on utiliser un LayoutInflater
        View v = inflater.inflate(R.layout.dialog_list_fragment,container, false);

        editText = v.findViewById(R.id.ed_addString);
        btnAdd = v.findViewById(R.id.btn_addString);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Récupérer le contenu du champs de saisie
                //l'ajouter dans la listView et fermer cette dialogue

                String contenu = editText.getText().toString();

                ((MyListActivity)context).addString(contenu);
                getDialog().dismiss(); //fermer la dialogue
            }
        });

        return v;
    }

    //Adapter la taile de la dialogue à l'Activity appelante


    @Override
    public void onStart() {
        super.onStart();
        //ViewGroup pointe vers le layout l'Activity appelante
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }
}
