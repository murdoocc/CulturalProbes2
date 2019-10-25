package com.example.culturalprobres;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
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
                    String nota[][] = new String[1][2];
                    MyOpenHelper MOP = new MyOpenHelper(v.getContext());
                    nota[0][0]=etTitulo.getText().toString();
                    nota[0][1]=etNota.getText().toString();


                    MOP.noteRegister(nota);

                    etNota.setText("");
                    etTitulo.setText("");
                    nota[0][0]="";
                    nota[0][1]="";
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
    }
}
