package com.example.culturalprobres;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    Button btnIngresar;
    Button btnRegistrar;

    TextInputEditText tidtUsuario;
    TextInputEditText tidtContra;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyOpenHelper dbHelper = new MyOpenHelper(this);
        boolean su=dbHelper.sesionState();
        if(su==true){
            Intent inicioDirecto = new Intent(this, Inicio.class);
            startActivity(inicioDirecto);
        }

        tidtUsuario = findViewById(R.id.usuario);
        tidtContra = findViewById(R.id.contra);

        btnIngresar = findViewById(R.id.btnIngresar);
        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyOpenHelper dbHelper = new MyOpenHelper(v.getContext());
                String user = tidtUsuario.getText().toString();
                String contra = tidtContra.getText().toString();
                if(dbHelper.existenciaRegistro()==true) {
                    if (dbHelper.newUser(user, contra) == true) {
                        ventanaIncio();
                    } else {
                        Toast.makeText(v.getContext(), "El usuario o contraseña son incorrectos", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(v.getContext(), "Aun no existe registro de alguién", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btnRegistrar = findViewById(R.id.btnRegistrar);
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ventanaRegistro = new Intent(MainActivity.this, Registro.class);
                startActivity(ventanaRegistro);
            }
        });


    }

    public void ventanaIncio(){
        Intent vtnIncio = new Intent(this, Inicio.class);
        startActivity(vtnIncio);
    }
}
