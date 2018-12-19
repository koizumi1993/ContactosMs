package com.fundamentos.abisu.contactosmas.Fragments;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fundamentos.abisu.contactosmas.Adapters.ContactosAdapter;
import com.fundamentos.abisu.contactosmas.BaseDatos.SqlDatabase;
import com.fundamentos.abisu.contactosmas.Clases.Constantes;
import com.fundamentos.abisu.contactosmas.Clases.Contacto;
import com.fundamentos.abisu.contactosmas.R;

import java.util.ArrayList;

@SuppressLint("ValidFragment")
public class ListContactFragment extends Fragment {


    RecyclerView recyclerContactos;
    View inflatedView;
    ArrayList<Contacto> listaContactos;
    SqlDatabase sqlDatabase;

    public ListContactFragment() {

    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        inflatedView = inflater.inflate(R.layout.fragment_list_contact,container,false);

        listaContactos = new ArrayList<>();

        sqlDatabase = new SqlDatabase(getContext(),Constantes.DATABASE_NOMBRE,null,Constantes.DATABASE_VERSION);

        recyclerContactos = inflatedView.findViewById(R.id.recyclerId);
        recyclerContactos.setLayoutManager(new LinearLayoutManager(getContext()));
        
        consultarListaContactos();

        ContactosAdapter adapter = new ContactosAdapter(listaContactos);
        recyclerContactos.setAdapter(adapter);

        return inflatedView;
    }


    private void consultarListaContactos() {
        SQLiteDatabase db = sqlDatabase.getReadableDatabase();

        Contacto contacto = null;

        Cursor cursor = db.rawQuery("SELECT * FROM "+Constantes.DATABASE_TABLE,null);

        while (cursor.moveToNext()){
            contacto = new Contacto();
            contacto.setId(cursor.getInt(0));
            contacto.setNombre(cursor.getString(1));
            contacto.setNumero(cursor.getString(2));
            contacto.setEmail(cursor.getString(3));
            contacto.setFotoUri(cursor.getString(4));

            listaContactos.add(contacto);
        }
    }
}
