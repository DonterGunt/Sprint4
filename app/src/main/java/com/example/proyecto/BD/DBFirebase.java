package com.example.proyecto.BD;


import android.util.Log;

import androidx.annotation.NonNull;

import com.example.proyecto.Adaptadores.ProductoAdapter;
import com.example.proyecto.Entidades.Producto;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DBFirebase {

    private FirebaseFirestore db;

    public DBFirebase(){
        this.db = FirebaseFirestore.getInstance();
    }

    public void insertData(Producto producto){

        // Create a new user with a first and last name
        Map<String, Object> prod = new HashMap<>();
        prod.put("id", producto.getId());
        prod.put("name", producto.getName());
        prod.put("descripcion", producto.getDescription());
        prod.put("price", producto.getPrice());
        prod.put("imagen", producto.getImage());

// Add a new document with a generated ID
        db.collection("products").add(prod);

    }

    public void getData (ProductoAdapter adapter, ArrayList<Producto> list){

        db.collection("products")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Producto producto = new Producto(
                                        document.getData().get("id").toString(),
                                        document.getData().get("name").toString(),
                                        document.getData().get("descripcion").toString(),
                                        Integer.parseInt(document.get("price").toString()),
                                        document.getData().get("imagen").toString()
                                );

                                list.add(producto);
                            }
                            adapter.notifyDataSetChanged();

                        } else {
                            Log.e("Error documento", "Error getting documents.", task.getException());
                        }
                    }
                });

    }

    public void deleteData(String id) {
        db.collection("products").whereEqualTo("id", id)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                document.getReference().delete();
                            }
                        }
                    }
                });
    }

    public void updateData(Producto producto){

        db.collection("products").whereEqualTo("id", producto.getId())
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            DocumentSnapshot document =
                                    task.getResult().getDocuments().get(0);

                            document.getReference().update(
                                    "id", producto.getId(),
                                    "name", producto.getName(),
                                    "description", producto.getDescription(),
                                    "price", producto.getPrice(),
                                    "image", producto.getImage()
                            );
                        }
                    }
                });
    }

}
