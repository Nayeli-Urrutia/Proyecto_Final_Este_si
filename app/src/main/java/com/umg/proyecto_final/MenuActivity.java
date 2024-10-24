package com.umg.proyecto_final;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.umg.proyecto_final.BaseDatos.DbHelper;
import com.umg.proyecto_final.BaseDatos.Entidades.Hamburguesas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MenuActivity extends AppCompatActivity {

    private ExpandableListView expandableListView;
    private HashMap<String, List<String>> listaDatos;
    private List<String> listaCategorias;
    private ExpandableListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        expandableListView = findViewById(R.id.expandableListView);
        listaCategorias = new ArrayList<>();
        listaDatos = new HashMap<>();

        // Agregar categorías
        listaCategorias.add("Hamburguesas");
        listaCategorias.add("Bebidas");
        listaCategorias.add("Postres");

        // Crear listas de productos para cada categoría
        List<String> hamburguesas = new ArrayList<>();
        hamburguesas.add("Hamburguesa Clásica - 25.0");
        hamburguesas.add("Hamburguesa con Queso - 30.0");
        hamburguesas.add("Hamburguesa BBQ - 35.0");

        List<String> bebidas = new ArrayList<>();
        bebidas.add("Coca-Cola - 10.0");
        bebidas.add("Pepsi - 10.0");
        bebidas.add("Limonada - 12.0");

        List<String> postres = new ArrayList<>();
        postres.add("Tarta de Fresa - 20.0");
        postres.add("Helado de Chocolate - 15.0");
        postres.add("Pastel de Queso - 25.0");

        // Asignar productos a sus respectivas categorías
        listaDatos.put(listaCategorias.get(0), hamburguesas);  // Hamburguesas
        listaDatos.put(listaCategorias.get(1), bebidas);      // Bebidas
        listaDatos.put(listaCategorias.get(2), postres);      // Postres

        // Configurar el adaptador
        adapter = new ExpandableListAdapter(this, listaCategorias, listaDatos);
        expandableListView.setAdapter(adapter);

        // Guardar selección en base de datos al hacer click en un elemento
        expandableListView.setOnChildClickListener((parent, v, groupPosition, childPosition, id) -> {
            String itemSeleccionado = listaDatos.get(listaCategorias.get(groupPosition)).get(childPosition);
            guardarEnBaseDeDatos(itemSeleccionado);
            return false;
        });

        // Botón de Listo
        Button btnListo = findViewById(R.id.btn_Listo);
        btnListo.setOnClickListener(v -> Toast.makeText(this, "Pedido completado", Toast.LENGTH_SHORT).show());
    }

    // Método para guardar el producto seleccionado en la base de datos
    private void guardarEnBaseDeDatos(String item) {
        // Aquí se debe realizar la inserción en la base de datos
        SQLiteDatabase db = new DbHelper(this).getWritableDatabase();
        ContentValues valores = new ContentValues();

        String[] partes = item.split(" - ");  // Separar nombre y precio
        String nombre = partes[0];
        double precio = Double.parseDouble(partes[1]);

        valores.put("nombre", nombre);
        valores.put("precio", precio);

        long resultado = db.insert("tb_productos", null, valores);
        if (resultado != -1) {
            Toast.makeText(this, nombre + " guardado con éxito", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error al guardar", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }
}

