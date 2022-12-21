package com.example.proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.proyecto.Adaptadores.ProductoAdapter;
import com.example.proyecto.BD.DBFirebase;
import com.example.proyecto.BD.DBHepler;
import com.example.proyecto.Entidades.Producto;
import com.example.proyecto.Servicios.ProductService;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class Home extends AppCompatActivity {
    private DBHepler dbHelper;
    private DBFirebase dbFirebase;
    private ProductService productService;
    private Button btnSalir, btnRegistrar;
    private ListView listServicios;
    private ProductoAdapter productoAdapter;
    private ArrayList <Producto> arrayProductos;
    FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getSupportActionBar().hide();
        mAuth = FirebaseAuth.getInstance();

        btnSalir = (Button) findViewById(R.id.btnSalir);
        btnRegistrar = (Button) findViewById(R.id.btnRegistrar);
        listServicios = (ListView) findViewById(R.id.listServicios);

        arrayProductos = new ArrayList<>();
        productService = new ProductService();


        try{
            dbHelper = new DBHepler(this);
            dbFirebase = new DBFirebase();
        } catch (Exception e){
            Log.e("Error DB",e.toString());
        }

        //arrayProductos = productService.cursorToArrayList(dbHelper.getData());
        productoAdapter = new ProductoAdapter(this,arrayProductos);
        listServicios.setAdapter(productoAdapter);

        dbFirebase.getData(productoAdapter, arrayProductos);

        btnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mAuth.signOut();
                finish();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ProductForm.class);
                startActivity(intent);
            }
        });

    }
}