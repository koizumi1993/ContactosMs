package com.fundamentos.abisu.contactosmas.Clases;

public class Constantes {
    //public static final String KEY_ID = "idContacto";
    public static final String KEY_NOMBRE = "nombreContacto";
    public static final String KEY_NUMERO = "numeroContacto";
    public static final String KEY_EMAIL = "emailContacto";
    public static final String KEY_FOTO = "fotoContacto";

    public static final String DATABASE_NOMBRE = "agenda";
    public static final int DATABASE_VERSION = 1;

    public static final String DATABASE_TABLE = "contactos";

    public static final String CREATE_TABLE_CONTACTOS="CREATE TABLE " +DATABASE_TABLE+"("
            +KEY_NOMBRE+" TEXT,"+KEY_NUMERO+" TEXT, "+KEY_EMAIL+" TEXT, "+KEY_FOTO+" TEXT)";
}
