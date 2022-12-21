package com.example.proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.StorageReference;

public class InfoProductos extends AppCompatActivity {

    private TextView textMainInfo,textDesInfo,textPresInfo;
    private ImageView imgServ;
    private Button btnInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_productos);

        getSupportActionBar().hide();


        textMainInfo = (TextView) findViewById(R.id.textMainInfo);
        textDesInfo = (TextView) findViewById(R.id.textDesInfo);
        textPresInfo = (TextView) findViewById(R.id.textPresInfo);

        imgServ = (ImageView) findViewById(R.id.imgServ);
        btnInfo = (Button) findViewById(R.id.btnInfo);

        Intent intentIN = getIntent();
        textMainInfo.setText(intentIN.getStringExtra("name"));
        textDesInfo.setText(intentIN.getStringExtra("description"));
        textPresInfo.setText(intentIN.getStringExtra("price"));
        //imgServ.setImageResource(intentIN.getIntExtra("image",R.drawable.mb);

        Glide.with(InfoProductos.this)
                .load((intentIN.getStringExtra("image")))
                .fitCenter()
                .centerCrop()
                .override(600,600)
                .into(imgServ);

        btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Home.class);
                startActivity(intent);


            }
        });

    }
}