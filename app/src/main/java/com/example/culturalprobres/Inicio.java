package com.example.culturalprobres;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

// Ruta del apk C:\Users\Juancho\AndroidStudioProjects\CulturalProbresOriginal\app\build\outputs\apk\debug

public class Inicio extends AppCompatActivity {

    Button btnBackLogin;
    Button btnVideos;
    Button btnDocumentos;
    Button btnFotos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        btnBackLogin = findViewById(R.id.btnRegresar);
        btnDocumentos = findViewById(R.id.btnDocumentos);
        btnVideos = findViewById(R.id.btnVideos);
        btnFotos = findViewById(R.id.btnFotos);

        btnVideos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent vtnVideos = new Intent(Inicio.this, Videos.class);
                startActivity(vtnVideos);
            }
        });

        btnDocumentos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent vtnDocumentos = new Intent(Inicio.this, Documentos.class);
                startActivity(vtnDocumentos);
            }
        });

        btnFotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent vtnFotos = new Intent(Inicio.this, Fotos.class);
                startActivity(vtnFotos);
            }
        });


        btnBackLogin.setBackgroundColor(Color.TRANSPARENT);
        btnBackLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //MyOpenHelper dbHelper = new MyOpenHelper(v.getContext());
                //dbHelper.newUser("","");
                Intent backLogin = new Intent(Inicio.this, MainActivity.class);
                startActivity(backLogin);
            }
        });
    }
}
