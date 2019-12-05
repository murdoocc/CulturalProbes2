package com.example.culturalprobres;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class New_Document extends AppCompatActivity {

    EditText etNota;
    EditText etTitulo;

    Button btnSaveNota;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new__document);

        etNota = findViewById(R.id.etNota);

        etTitulo = findViewById(R.id.etTitulo);

        btnSaveNota = findViewById(R.id.btnSaveNote);
        btnSaveNota.setBackgroundColor(Color.TRANSPARENT);

        btnSaveNota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    /*Este if sirve para validar que no existan campos vacios
                    if (etNota.getText().toString().equals("") || etTitulo.getText().toString().equals("")) {
                        Toast.makeText(v.getContext(), "No dejes campos vacios", Toast.LENGTH_LONG).show();
                    }else{*/
                        //Se crea una variable que pueda almacenar el titulo y el contenido
                        String nota[][] = new String[1][2];
                        //Se instancia la clase de sqlite para usar sus metodos
                        MyOpenHelper MOP = new MyOpenHelper(v.getContext());
                        //Obtenemos los valores que digita el cliente
                        nota[0][0] = etTitulo.getText().toString();
                        nota[0][1] = etNota.getText().toString();

                        //Mandamos los valores recabados al metodo de la clase de la base de datos
                        MOP.noteRegister(nota);

                        etNota.setText("");
                        etTitulo.setText("");
                        nota[0][0] = "";
                        nota[0][1] = "";

                        Intent intent = new Intent(New_Document.this, Documentos.class);
                        startActivity(intent);
                    //}
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
    }
}
