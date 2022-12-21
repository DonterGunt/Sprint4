package com.example.proyecto.Adaptadores;

import android.content.Context;
import android.content.Intent;
import android.icu.text.IDNA;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.proyecto.BD.DBFirebase;
import com.example.proyecto.Entidades.Producto;
import com.example.proyecto.Home;
import com.example.proyecto.InfoProductos;
import com.example.proyecto.ProductForm;
import com.example.proyecto.R;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class ProductoAdapter extends BaseAdapter {
    private ArrayList<Producto> arrayProductos;
    private Context context;
    //private StorageReference storageReference;


    public ProductoAdapter(Context context, ArrayList<Producto> arrayProductos) {
        this.arrayProductos = arrayProductos;
        this.context = context;

    }

    @Override
    public int getCount() {
        return arrayProductos.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayProductos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater layoutInflater = LayoutInflater.from(this.context);
        view = layoutInflater.inflate(R.layout.servicios_template,null);


        ImageButton imageEdit = (ImageButton) view.findViewById(R.id.imageEdit);
        ImageButton imageEli = (ImageButton) view.findViewById(R.id.imageEli);

        ImageView imgServ = (ImageView) view.findViewById(R.id.imgServ);
        TextView textNameServ =(TextView) view.findViewById(R.id.textNameServ);
        TextView textPrecServ =(TextView) view.findViewById(R.id.textPrecServ);

        Producto producto = arrayProductos.get(i);

        textNameServ.setText(producto.getName());
        textPrecServ.setText(String.valueOf(producto.getPrice()));


        Glide.with(context)
                .load(producto.getImage())
                .fitCenter()
                .centerCrop()
                .override(150,150)
                .into(imgServ);


        imgServ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context.getApplicationContext(), InfoProductos.class);
                intent.putExtra("name", producto.getName());
                intent.putExtra("description", producto.getDescription());
                intent.putExtra("price",String.valueOf (producto.getPrice()));
                intent.putExtra("image", producto.getImage());
                context.startActivity(intent);

            }
        });

        imageEli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBFirebase dbFirebase;
                dbFirebase = new DBFirebase();
                dbFirebase.deleteData(producto.getId());
                Intent intent = new Intent(context.getApplicationContext(), Home.class);
                context.startActivity(intent);
            }
        });

        imageEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context.getApplicationContext(), ProductForm.class);
                intent.putExtra("edit", true);
                intent.putExtra("id", producto.getId());
                intent.putExtra("name", producto.getName());
                intent.putExtra("description", producto.getDescription());
                intent.putExtra("price", String.valueOf(producto.getPrice()));
                intent.putExtra("image", producto.getImage());
                context.startActivity(intent);
            }
        });

        return view;
    }
}
