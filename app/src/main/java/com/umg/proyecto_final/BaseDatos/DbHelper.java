package com.umg.proyecto_final.BaseDatos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "restaurant.db";
    private static final int DATABASE_VERSION = 2; // Incrementa la versión si ya instalaste la app

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Crear la tabla de hamburguesas
        String createTableHamburguesas = "CREATE TABLE tb_hamburguesas (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nombre TEXT, " +
                "precio REAL)";
        db.execSQL(createTableHamburguesas);

        // Crear la tabla de productos
        String createTableProductos = "CREATE TABLE tb_productos (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nombre TEXT NOT NULL, " +
                "precio REAL NOT NULL)";
        db.execSQL(createTableProductos);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS tb_hamburguesas");
        db.execSQL("DROP TABLE IF EXISTS tb_productos"); // Asegúrate de eliminar también tb_productos
        onCreate(db);
    }
}

