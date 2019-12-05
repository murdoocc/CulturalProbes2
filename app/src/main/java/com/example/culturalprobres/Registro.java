package com.example.culturalprobres;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;

public class Registro extends AppCompatActivity {

    Button btnCancelar;
    Button btnSendRegister;

    EditText eTNombre;
    EditText eTApellidos;
    EditText eTEmail;
    EditText eTUsuario;
    EditText eTContra1;
    EditText eTContra2;

    public static final String TAG = "logcat";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        btnCancelar = findViewById(R.id.btnCancelar);
        btnSendRegister = findViewById(R.id.btnSendRegister);

        btnCancelar.setBackgroundColor(Color.TRANSPARENT);
        btnSendRegister.setBackgroundColor(Color.TRANSPARENT);

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cancelar = new Intent(Registro.this, MainActivity.class);
                startActivity(cancelar);
            }
        });


        eTUsuario = findViewById(R.id.eTUsuario);
        eTNombre = findViewById(R.id.eTNombre);
        eTApellidos = findViewById(R.id.eTApellidos);
        eTEmail = findViewById(R.id.eTEmail);
        eTContra1 = findViewById(R.id.eTContra1);
        eTContra2 = findViewById(R.id.eTContra2);

        btnSendRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pass1, pass2;
                pass1 = eTContra1.getText().toString();
                pass2 = eTContra2.getText().toString();

                if (eTNombre.getText().toString().equals("") || eTApellidos.getText().toString().equals("") || eTEmail.getText().toString().equals("")
                || eTUsuario.getText().toString().equals("") || eTContra1.getText().toString().equals("") || eTContra2.getText().toString().equals("")) {

                    Toast.makeText(v.getContext(), "No dejes algun campo vacio", Toast.LENGTH_LONG).show();

                }else if (pass1.equals(pass2)) {
                        MyOpenHelper dbHelper = new MyOpenHelper(v.getContext());
                        String[] userData = new String[6];
                        userData[0] = eTUsuario.getText().toString();
                        userData[1] = eTNombre.getText().toString();
                        userData[2] = eTApellidos.getText().toString();
                        userData[3] = eTEmail.getText().toString();
                        userData[4] = pass1;
                        dbHelper.userRegister(userData);
                        Toast.makeText(v.getContext(), "Bienvenido " + userData[0], Toast.LENGTH_LONG).show();
                        Intent windowInicio = new Intent(v.getContext(), Inicio.class);
                        startActivity(windowInicio);
                    } else {
                        eTContra1.setText("");
                        eTContra2.setText("");
                        Toast.makeText(v.getContext(), "Las contraseñas no coinciden", Toast.LENGTH_LONG).show();
                }


            }
        });

    }

}
