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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class IniciarSeccion extends AppCompatActivity {

    private Button btnIngresar, btnVolver;
    private EditText editUsuario, editContrasena;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciarseccion);

        getSupportActionBar().hide();

        mAuth = FirebaseAuth.getInstance();

        btnIngresar = (Button) findViewById(R.id.btnIngresar);
        btnVolver = (Button) findViewById(R.id.btnVolver);


        editUsuario = (EditText) findViewById(R.id.editUsuario);
        editContrasena = (EditText) findViewById(R.id.editContrasena);

        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailUser = editUsuario.getText().toString().trim();
                String passUser = editContrasena.getText().toString().trim();

                if(emailUser.isEmpty() && passUser.isEmpty()){

                    Toast.makeText(IniciarSeccion.this,"Ingresar los datos", Toast.LENGTH_SHORT).show();

                }else{

                    loginUser(emailUser, passUser);
                }



            }
        });

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void loginUser(String emailUser, String passUser) {

        mAuth.signInWithEmailAndPassword(emailUser, passUser).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    finish();

                    Intent intent = new Intent(getApplicationContext(), Home.class);
                    startActivity(intent);
                    Toast.makeText(IniciarSeccion.this, "Bienvenido", Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(IniciarSeccion.this, "Error", Toast.LENGTH_SHORT).show();
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(IniciarSeccion.this, "Error al iniciar sesion", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if(user != null){
            Intent intent = new Intent(getApplicationContext(), Home.class);
            startActivity(intent);
            finish();

        }
    }
}