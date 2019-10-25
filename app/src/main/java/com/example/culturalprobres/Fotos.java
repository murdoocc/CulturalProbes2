package com.example.culturalprobres;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.MediaScannerConnection;
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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Fotos extends AppCompatActivity {

    Button btnRegresarInicio3;

    Button btnTomarFoto;

    Button btnCarpeta3;
    private static final int REQUEST_CAMERA = 1;
    private static final int RESULT_OK = 1;
    private static final int REQUEST_SELECT_PHOTO = 0;
    private static final int MY_PERMISSIONS_REQUEST_CAMARA = 1;

    protected Uri imageUri;
    private String photoPath="";

    private String filePath;
    private String fileName;
    private Date currentTime = Calendar.getInstance().getTime();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fotos);

        btnRegresarInicio3 = findViewById(R.id.btnRegresar3);
        btnRegresarInicio3.setBackgroundColor(Color.TRANSPARENT);

        btnTomarFoto = findViewById(R.id.btnTomarFoto);

        btnRegresarInicio3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backInicio3 = new Intent(Fotos.this, Inicio.class);
                startActivity(backInicio3);
            }
        });

        btnTomarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (Build.VERSION.SDK_INT>Build.VERSION_CODES.LOLLIPOP_MR1) {// Marshmallow+
                        if (ContextCompat.checkSelfPermission(Fotos.this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED) {
                            // Should we show an explanation?
                            if (ActivityCompat.shouldShowRequestPermissionRationale(Fotos.this, Manifest.permission.CAMERA)) {
                                // Show an expanation to the user *asynchronously* -- don't block
                                // this thread waiting for the user's response! After the user
                                // sees the explanation, try again to request the permission.
                            } else {
                                // No se necesita dar una explicación al usuario, sólo pedimos el permiso.
                                ActivityCompat.requestPermissions(Fotos.this,new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMARA );
                                // MY_PERMISSIONS_REQUEST_CAMARA es una constante definida en la app. El método callback obtiene el resultado de la petición.
                            }
                        }else{ //have permissions
                            abrirCamara ();
                        }
                    }else{ // Pre-Marshmallow
                        abrirCamara ();
                    }
                }catch (Exception e){}

                }

        });

        btnCarpeta3 = findViewById(R.id.btnCarpeta3);
        btnCarpeta3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(android.content.Intent.ACTION_VIEW);
                intent.setType("image/*");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        try {
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
        }catch (Exception e){}

    }


    public void abrirCamara () throws Exception{
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(i, REQUEST_CAMERA);

    }

    @Override
    protected  void onActivityResult(int requestCode, int resultCode, Intent i) {
        if(resultCode == Activity.RESULT_OK) {
            super.onActivityResult(requestCode, resultCode, i);
            Bundle extras = i.getExtras();
            Bitmap bitmap = (Bitmap) extras.get("data");
            Toast.makeText(this, i.getDataString(), Toast.LENGTH_LONG).show();
            String CurrentDateAndTime = getCurrentDateAndTime();
            fileName = "Foto-" + CurrentDateAndTime + ".jpg";
            String ruta = createDirectoryAndSaveFile(bitmap, fileName);
            //Toast.makeText(getApplicationContext(), ruta, Toast.LENGTH_LONG).show();
        }


    }

    private String createDirectoryAndSaveFile(Bitmap imagen, String fileName) {
        ContextWrapper cw = new ContextWrapper(this);
        File dirImages = cw.getDir("CulturalProbes", this.MODE_PRIVATE);
        File myPath = new File(dirImages, fileName);
        FileOutputStream fos = null;
        try{
            fos = new FileOutputStream(myPath);
            imagen.compress(Bitmap.CompressFormat.JPEG, 10, fos);
            fos.flush();
        }catch (FileNotFoundException ex){
            ex.printStackTrace();
        }catch (IOException ex){
            ex.printStackTrace();
        }
        return myPath.getAbsolutePath();
        /*
        //File directory = new File(Environment.getExternalStorageDirectory()+"/CulturalProbes");
        String folderName = "/CulturalProbes";
        String str_path = Environment.getExternalStorageDirectory().getAbsolutePath() + folderName;
        File directory = new File(str_path);
        if (!directory.exists()){
            directory.mkdirs();
        }
        File file = new File(directory, fileName);
        if (file.exists()) {
            file.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(file);
            imgSave.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
            MakeSureFileWasCreatedThenMakeAvabile(file);
        }catch(FileNotFoundException e) {
                UnableToSave();
            }
        catch(IOException e) {
                UnableToSave();
            }*/

    }

    private void MakeSureFileWasCreatedThenMakeAvabile(File file){
        MediaScannerConnection.scanFile(this, new String[] { file.toString() } , null,
                new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String path, Uri uri) {

                    }
                });
    }


    private String getCurrentDateAndTime() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH-mm-­ss");
        String formattedDate = df.format(c.getTime());
        return formattedDate;
    }


    private void UnableToSave() {
        Toast.makeText(this, "¡No se ha podido guardar la imagen!", Toast.LENGTH_SHORT).show();
    }


    private void AbleToSave() {
        Toast.makeText(this, "Imagen guardada en la galería.", Toast.LENGTH_SHORT).show();
    }





}
