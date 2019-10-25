package com.example.culturalprobres;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class verDocumentos extends AppCompatActivity {

    ListView lvSeeNotas;
    Context context = this;
    private ArrayList<String> notasList =null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_documentos);
        lvSeeNotas = findViewById(R.id.seeNotas);

        MyOpenHelper MOH = new MyOpenHelper(context);
        String notas[][];
        notas = MOH.showNote();

        int cont=notas.length;
        int fi=0, co=0;
        notasList = new ArrayList<>();
         while(cont!=0){
                notasList.add("TITULO: "+notas[fi][co]+ "\n" + "CONTENIDO: " + notas[fi][co+1] + "\n\n");
                fi++;
                cont--;
         }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,notasList);
        lvSeeNotas.setAdapter(adapter);
    }
}
