package com.example.culturalprobres;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class verDocumentos extends AppCompatActivity {

    ListView lvSeeNotas;
    Context context = this;
    private ArrayList<String> notasList =null;

    int fi=0, co=0;

    String titulo;

    Button btnRegresarInicio1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_documentos);
        lvSeeNotas = findViewById(R.id.seeNotas);

        MyOpenHelper dbHelper = new MyOpenHelper(this);
        dbHelper.deleteNoteTrash();

        btnRegresarInicio1 = findViewById(R.id.btnRegresar1);
        btnRegresarInicio1.setBackgroundColor(Color.TRANSPARENT);
        btnRegresarInicio1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backInicio2 = new Intent(verDocumentos.this, Documentos.class);
                startActivity(backInicio2);
            }
        });

        MyOpenHelper MOH = new MyOpenHelper(context);
        final String notas[][];
        notas = MOH.showNote();

        int cont=notas.length;

        notasList = new ArrayList<>();
         while(cont!=0){
                notasList.add("TITULO: "+notas[fi][co]+ "\n" + "CONTENIDO: " + notas[fi][co+1] + "\n\n");
                fi++;
                cont--;
         }
         //Te muestra los resultados en lista
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,notasList);
        lvSeeNotas.setAdapter(adapter);

        lvSeeNotas.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final int posicion=position;

                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(verDocumentos.this);
                dialogo1.setTitle("Importante");
                dialogo1.setMessage("¿ Seguro quieres eliminar la nota ?");
                dialogo1.setCancelable(false);
                dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        titulo = notasList.get(posicion);
                        String borrar[] = titulo.split("\n");
                        //Toast.makeText(context, borrar[0], Toast.LENGTH_LONG).show();
                        MyOpenHelper MOH = new MyOpenHelper(context);
                        MOH.deleteNote(borrar[0]);
                        notasList.remove(posicion);
                        adapter.notifyDataSetChanged();
                    }
                });
                dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                    }
                });
                dialogo1.show();
                return false;
                //Aqui debe de ir el codigo que borre los datos de la base de datos
            }
        });

    }
}
