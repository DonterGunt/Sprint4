package com.example.proyecto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class registrarme extends AppCompatActivity {

    private Button btnRegistro, btnsalirr;
    private EditText editContra,editCelular,editCorreo,editNombre;
    FirebaseFirestore mFirestore;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarme);

        getSupportActionBar().hide();

        mFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        btnRegistro = (Button) findViewById(R.id.btnRegistro);
        btnsalirr = (Button) findViewById(R.id.btnsalirr);

        editContra = (EditText) findViewById(R.id.editContra);
        editCelular = (EditText) findViewById(R.id.editCelular);
        editCorreo = (EditText) findViewById(R.id.editCorreo);
        editNombre = (EditText) findViewById(R.id.editNombre);

        btnsalirr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nameUser = editNombre.getText().toString().trim();
                String emailUser = editCorreo.getText().toString().trim();
                //int celUser = editCelular.getText().toString().trim();
                String passUser = editContra.getText().toString().trim();

                if(nameUser.isEmpty() && emailUser.isEmpty() && passUser.isEmpty()){

                    Toast.makeText(registrarme.this, "Complete los datos", Toast.LENGTH_SHORT).show();

                }else{
                    registerUser(nameUser,emailUser,passUser);
                }


            }
        });
    }

    private void registerUser(String nameUser, String emailUser, String passUser) {
        mAuth.createUserWithEmailAndPassword(emailUser, passUser).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                String id = mAuth.getCurrentUser().getUid();
                Map<String, Object> map = new HashMap<>();
                map.put("id", id);
                map.put("name", nameUser);
                map.put("email", emailUser);
                map.put("password", passUser);

                mFirestore.collection("User").document(id).set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                        finish();
                        Intent intent = new Intent(getApplicationContext(), IniciarSeccion.class);
                        startActivity(intent);
                        Toast.makeText(registrarme.this,"Usuario Registrador", Toast.LENGTH_SHORT).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(registrarme.this,"Error al guardar", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(registrarme.this,"Error al registrar", Toast.LENGTH_SHORT).show();
            }
        });

    }
}