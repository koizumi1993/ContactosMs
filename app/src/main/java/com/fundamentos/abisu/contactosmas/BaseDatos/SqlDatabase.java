package com.fundamentos.abisu.contactosmas.BaseDatos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.fundamentos.abisu.contactosmas.Clases.Constantes;

public class SqlDatabase extends SQLiteOpenHelper {
    Constantes constant = null;


    public SqlDatabase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(constant.CREATE_TABLE_CONTACTOS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS"+constant.DATABASE_TABLE);
    }

    //private DbHelper mHelper;

    //private final Context mContext;

    /*private SQLiteDatabase mDatabase;

    public void close() {
    }*/


    /*public SqlDatabase (Context c) {
        mContext = c;
    }

    public SqlDatabase open() throws SQLException {
        //Set up the helper with the context
        mHelper = new DbHelper (mContext);
        //Open the database with our helper
        mDatabase = mHelper.getWritableDatabase();
        return this;
    }

    public long createEntry(String nombre, String numero, String email,
                            String foto) {
        ContentValues cv = new ContentValues();
        cv.put(KEY_NOMBRE, nombre);
        cv.put(KEY_NUMERO, numero);
        cv.put(KEY_EMAIL, email);
        cv.put(KEY_FOTO, foto);
        return mDatabase.insert(DATABASE_NOMBRE, null, cv);
    }*/
}
