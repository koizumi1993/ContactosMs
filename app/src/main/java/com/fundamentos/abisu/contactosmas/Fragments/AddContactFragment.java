package com.fundamentos.abisu.contactosmas.Fragments;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.fundamentos.abisu.contactosmas.BaseDatos.SqlDatabase;
import com.fundamentos.abisu.contactosmas.Clases.Constantes;
import com.fundamentos.abisu.contactosmas.R;

import java.io.File;

public class AddContactFragment extends Fragment {
    private static final String CARPETA_PRINCIPAL = "imagenesDeMiAPP/";//carpeta principal de la apliacion
    private static final String CARPETA_IMAGEN = "imagenes";//carpeta donde se guardaran las fotos
    private static final String DIRECTORIO_IMAGEN = CARPETA_PRINCIPAL + CARPETA_IMAGEN;//ruta completa
    private String path;//variable donde se alamacena la ruta de la imagen
    File fileImagen;
    Bitmap bitmap;

    private static final int FOTO_SELECCIONADA = 10;
    private static final int FOTO = 20;

    View inflatedView;
    TextView nombreCnt;
    TextView numeroCnt;
    TextView emailCnt;
    Button btnAdd;
    Button btnFoto;
    ImageView imgFoto;


    public AddContactFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        inflatedView = inflater.inflate(R.layout.fragment_add_contact, container,false);

        nombreCnt = (TextView) inflatedView.findViewById(R.id.nombreContacto);
        numeroCnt = (TextView) inflatedView.findViewById(R.id.numeroContacto);
        emailCnt = (TextView) inflatedView.findViewById(R.id.emailContacto);
        btnAdd = (Button) inflatedView.findViewById(R.id.botonAgregar);
        btnFoto = (Button) inflatedView.findViewById(R.id.botonFoto);
        imgFoto = (ImageView) inflatedView.findViewById(R.id.imgFoto);

        btnFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarDialogoOpciones();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                agregarContacto();
            }
        });

        return inflatedView;
    }

    private void agregarContacto() {
        //SqlDatabase sqlDatabase = new SqlDatabase(getContext());
        SqlDatabase conn = new SqlDatabase(getContext(),Constantes.DATABASE_NOMBRE,null,Constantes.DATABASE_VERSION);

        SQLiteDatabase db = conn.getWritableDatabase();

        String insert="INSERT INTO "+Constantes.DATABASE_TABLE
                +" ("+Constantes.KEY_NOMBRE+","+Constantes.KEY_NUMERO
                +","+Constantes.KEY_EMAIL+","+Constantes.KEY_FOTO+")"
                + " VALUES ('"+nombreCnt.getText().toString()+"', '"+numeroCnt.getText().toString()
                +"', '"+emailCnt.getText()+"', '"+path+"')";
        db.execSQL(insert);

        Toast.makeText(getActivity(),"Exitantemente guardado",Toast.LENGTH_SHORT).show();

        db.close();
        /*ContentValues nuevoRegistro = new ContentValues();
        nuevoRegistro.put("KEY_NOMBRE",nombreCnt.getText().toString());
        nuevoRegistro.put("KEY_NUMERO",numeroCnt.getText().toString());
        nuevoRegistro.put("KEY_EMAIL",emailCnt.getText().toString());
        nuevoRegistro.put("KEY_FOTO",path);

        db.insert(Constantes.DATABASE_TABLE,null,nuevoRegistro);*/
    }


    private void mostrarDialogoOpciones() {
        final CharSequence[] opciones = {"Camara","Galeria","Cancelar"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Seleccione una opción");
        builder.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (opciones[i].equals("Camara")){
                    //llamada al metodo de camara
                    abrirCamara();
                }else{
                    if (opciones[i].equals("Galeria")){
                        Intent intent = new Intent(Intent.ACTION_PICK,
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.setType("image/");
                        startActivityForResult(intent.createChooser(intent,"Selecciona"),FOTO_SELECCIONADA);
                    }else{
                        dialogInterface.dismiss();
                    }

                }
            }
        });
        builder.show();
    }

    private void abrirCamara() {
        File miFile = new File(Environment.getExternalStorageDirectory(),DIRECTORIO_IMAGEN);
        boolean imagenCreada=miFile.exists();

        if (imagenCreada==false){
            imagenCreada=miFile.mkdirs();
        }

        if (imagenCreada==true){
            Long consecutivo = System.currentTimeMillis()/1000;
            String nombre = consecutivo.toString()+".jpg";

            path = Environment.getExternalStorageDirectory()+File.separator+DIRECTORIO_IMAGEN
            +File.separator+nombre;

            fileImagen=new File(path);

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(fileImagen));

            startActivityForResult(intent,FOTO);

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case FOTO_SELECCIONADA:
                Uri miPath = data.getData();
                path=miPath.toString();
                imgFoto.setImageURI(miPath);
                Toast.makeText(getActivity(),path,Toast.LENGTH_SHORT).show();
                break;
            case FOTO:
                MediaScannerConnection.scanFile(getContext(), new String[]{path}, null,
                        new MediaScannerConnection.OnScanCompletedListener() {
                            @Override
                            public void onScanCompleted(String s, Uri uri) {
                                Log.i("Path",""+path);
                            }
                        });
                bitmap = BitmapFactory.decodeFile(path);
                imgFoto.setImageBitmap(bitmap);
                Toast.makeText(getActivity(),path,Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public void refresh() {
    }
}
