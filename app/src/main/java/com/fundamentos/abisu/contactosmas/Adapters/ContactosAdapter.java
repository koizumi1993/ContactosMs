package com.fundamentos.abisu.contactosmas.Adapters;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fundamentos.abisu.contactosmas.Clases.Contacto;
import com.fundamentos.abisu.contactosmas.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ContactosAdapter extends RecyclerView.Adapter<ContactosAdapter.ContactosViewHolder> {
    private List<Contacto> listaContactos;

    public ContactosAdapter(List<Contacto> contactos) {
        this.listaContactos = contactos;
    }

    @Override
    public ContactosViewHolder onCreateViewHolder( ViewGroup viewGroup, int i) {
        View vista = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list,viewGroup,false);
        return new ContactosViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(final ContactosViewHolder holder, int i) {
        Uri uri = Uri.parse(listaContactos.get(i).getFotoUri());
        final String id = String.valueOf(listaContactos.get(i).getId());
        holder.txtId.setText(id);
        holder.txtNombre.setText(listaContactos.get(i).getNombre());
        holder.txtNumero.setText(listaContactos.get(i).getNumero());
        holder.txtEmail.setText(listaContactos.get(i).getEmail());
        holder.imgPerfil.setImageURI(uri);
    }

    @Override
    public int getItemCount() {
        return listaContactos.size();
    }

    public class ContactosViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView imgPerfil;
        private TextView txtId, txtNombre, txtNumero, txtEmail;

        public ContactosViewHolder( View itemView) {
            super(itemView);
            imgPerfil = itemView.findViewById(R.id.imgPerfil);
            txtId = itemView.findViewById(R.id.txtId);
            txtNombre = itemView.findViewById(R.id.txtNombre);
            txtNumero = itemView.findViewById(R.id.txtNumero);
            txtEmail = itemView.findViewById(R.id.txtEmail);
        }
    }

}
