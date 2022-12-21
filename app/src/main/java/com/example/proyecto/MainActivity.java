package com.example.proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button btnIS, btnR;
    private ImageButton imageButton;
    private TextView textAge, textLastName, textName, textTitle;
    private EditText editAge, editLasName, editName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        //Esnlazando atibutos con la pantalla
        btnIS = (Button) findViewById(R.id.btnIS);
        btnR = (Button) findViewById(R.id.btnR);
        imageButton = (ImageButton) findViewById(R.id.imageButton);



        //Utilizando los atributos

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                startActivity(intent);
            }
        });
        btnIS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), IniciarSeccion.class);
                startActivity(intent);
            }
        });

        btnR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), registrarme.class);
                startActivity(intent);
            }
        });



    }
}