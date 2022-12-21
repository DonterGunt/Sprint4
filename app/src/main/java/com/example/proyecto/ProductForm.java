package com.example.proyecto;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.example.proyecto.BD.DBFirebase;
import com.example.proyecto.BD.DBHepler;
import com.example.proyecto.Entidades.Producto;
import com.example.proyecto.Servicios.ProductService;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class ProductForm extends AppCompatActivity {
    private DBHepler dbHepler;
    private DBFirebase dbFirebase;
    private ProductService productService;
    private Button btnProductForm, btnVolver, btn_subir;
    private ImageView subirImagen;
    private EditText editNameProductForm,editDescriptionProductForm,editPriceProductForm;
    private StorageReference mStorage;
    private static final int GALLERY_INTENT = 1;
    private String urlImage = "";
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_form);

        getSupportActionBar().hide();

        mStorage = FirebaseStorage.getInstance().getReference();

        btnProductForm = (Button) findViewById(R.id.btnProductForm);
        btnVolver = (Button) findViewById(R.id.btnVolver);
        btn_subir = (Button) findViewById(R.id.btn_subir);
        subirImagen = (ImageView) findViewById(R.id.subirImagen);
        editNameProductForm = (EditText) findViewById(R.id.editNameProductForm);
        editDescriptionProductForm = (EditText) findViewById(R.id.editDescriptionProductForm);
        editPriceProductForm = (EditText) findViewById(R.id.editPriceProductForm);
        mProgressDialog = new ProgressDialog(this);

        try{
            dbHepler = new DBHepler(this);
            dbFirebase = new DBFirebase();
        } catch (Exception e){
            Log.e("Error DB", e.toString());
        }


        productService = new ProductService();

        btn_subir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, GALLERY_INTENT);

            }
        });

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Home.class);
                startActivity(intent);
            }
        });
        btnProductForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Producto producto = new Producto(
                        editNameProductForm.getText().toString(),
                        editDescriptionProductForm.getText().toString(),
                        Integer.parseInt(editPriceProductForm.getText().toString()),
                        urlImage
                );
                dbFirebase.insertData(producto);
                Intent intent = new Intent(getApplicationContext(), Home.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);



        if(requestCode == GALLERY_INTENT && resultCode == RESULT_OK){

            mProgressDialog.setTitle("Subiendo foto....");
            mProgressDialog.setMessage("Subiendo foto a la nube");
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();

            Uri uri =data.getData();
            StorageReference filePath = mStorage.child("fotos").child(uri.getLastPathSegment());

            filePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            mProgressDialog.dismiss();
                            Uri descargarFoto = uri;
                            urlImage = descargarFoto.toString();
                            Glide.with(ProductForm.this)
                                    .load(descargarFoto)
                                    .fitCenter()
                                    .centerCrop()
                                    .override(500,500)
                                    .into(subirImagen);
                        }
                    });

                    Toast.makeText(ProductForm.this, "Foto subida", Toast.LENGTH_SHORT).show();
                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            Toast.makeText(ProductForm.this, "Error subiendo foto", Toast.LENGTH_SHORT).show();

                        }
                    });

        }
    }
}