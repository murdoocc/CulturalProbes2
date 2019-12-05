package com.example.culturalprobres;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class Videos extends AppCompatActivity {

    Button btnRegresarInicio2;
    Button btnCamara;
    Button btnGaleria2;

    private static final int MY_PERMISSIONS_REQUEST_CAMARA = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videos);

        btnRegresarInicio2= findViewById(R.id.btnRegresar2);
        btnRegresarInicio2.setBackgroundColor(Color.TRANSPARENT);
        btnRegresarInicio2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backInicio1 = new Intent(Videos.this, Inicio.class);
                startActivity(backInicio1);
            }
        });

        btnCamara = findViewById(R.id.btnCamaraVideo);
        btnCamara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //necesitas una version mayor a Lollipop
                if (Build.VERSION.SDK_INT>Build.VERSION_CODES.LOLLIPOP_MR1) {// Marshmallow+
                    //debes tener permisos para la camara
                    if (ContextCompat.checkSelfPermission(Videos.this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED) {
                        // Should we show an explanation?
                        if (ActivityCompat.shouldShowRequestPermissionRationale(Videos.this, Manifest.permission.CAMERA)) {
                            // Show an expanation to the user *asynchronously* -- don't block
                            // this thread waiting for the user's response! After the user
                            // sees the explanation, try again to request the permission.
                        } else {
                            // No se necesita dar una explicación al usuario, sólo pedimos el permiso.
                            ActivityCompat.requestPermissions(Videos.this,new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMARA );
                            // MY_PERMISSIONS_REQUEST_CAMARA es una constante definida en la app. El método callback obtiene el resultado de la petición.
                        }
                    }else{ //have permissions
                        abrirCamara ();
                    }
                }else{ // Pre-Marshmallow
                    abrirCamara ();
                }
            }
        });

        btnGaleria2 = findViewById(R.id.btnGaleria2);
        btnGaleria2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("content://media/internal/images/media"));
                startActivity(intent); //Abre galería.
            }
        });


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CAMARA : {
                // Si la petición es cancelada, el array resultante estará vacío.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // El permiso ha sido concedido.
                    abrirCamara ();
                } else {
                    // Permiso denegado, deshabilita la funcionalidad que depende de este permiso.
                }
                return;
            }
            // otros bloques de 'case' para controlar otros permisos de la aplicación
        }
    }

    Intent i;
    public void abrirCamara (){
        i = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        startActivityForResult(i,1);

    }



}
