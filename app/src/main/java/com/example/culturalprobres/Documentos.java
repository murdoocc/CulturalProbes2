package com.example.culturalprobres;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileOutputStream;

public class Documentos extends AppCompatActivity {

    Button btnRegresarInicio1;
    Button btnNewDocument;
    Button btnSeeDocument;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_documentos);

        btnRegresarInicio1 = findViewById(R.id.btnRegresar1);
        btnRegresarInicio1.setBackgroundColor(Color.TRANSPARENT);
        btnRegresarInicio1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backInicio2 = new Intent(Documentos.this, Inicio.class);
                startActivity(backInicio2);
            }
        });

        btnNewDocument = findViewById(R.id.btnNewDocument);
        btnSeeDocument = findViewById(R.id.btnSeeDocument);

        btnNewDocument.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent windowNewDocument = new Intent(Documentos.this, New_Document.class);
                startActivity(windowNewDocument);
            }
        });

        btnSeeDocument.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent verD = new Intent(v.getContext(), verDocumentos.class);
                    startActivity(verD);
                }catch (Exception e){
                    Toast.makeText(v.getContext(), e.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
