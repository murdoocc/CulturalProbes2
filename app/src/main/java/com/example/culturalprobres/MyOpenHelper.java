package com.example.culturalprobres;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class MyOpenHelper extends SQLiteOpenHelper {
    //SQLite necesita heredar de SQLiteOpenHelper, esta clase crea 2 metodos y su constructor
    //Al constructor debemos de darle el nombre de la base de datos, "nombre".db, el .db ya es por
    //convención
    //Necesita la versión en la que vamos a trabajar y si alguna vez se cambia, el cambio se hace con el metodo
    //onUpgrade
    //El contexto donde nos encontramos y valor que puede se asignado como nulo, no afecta
    /* contexto: Contexto usado para abrir o crear la base de datos.
        nombre: Nombre de la base de datos que se creará. En nuestro caso “puntuaciones”.
        version: Número de versión de la base de datos empezando desde 1. En el caso de que la base de datos actual tenga
        una versión más antigua se llamará a onUpgrade() para que actualice la base de datos.*/
    private static final String DB_NAME = "cultural.db";
    private static final int DB_VERSION = 1;
    public MyOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE 'notas'(\n" +
                " 'titulo' varchar(50), \n" +
                " 'contenido' varchar(100) \n" +
                ");"
        );
        db.execSQL("CREATE TABLE 'usuarios'(\n" +
                " 'usuario' varchar(20), \n" +
                " 'nombre' varchar(20), \n" +
                " 'apellidos' varchar(50), \n" +
                " 'correo' varchar(50), \n" +
                " 'contra' varchar(16) \n" +
                ");"
        );
    }
    /*El metodo onCreate soporta todos las setencias sql, por cuestión de una mejor legibilidad del
    * codigo, algunos solo lo usan para crear las tablas
    * Afuera uno puede crear metodos en especifico para hacer las demas sentencias*/
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


    }

    public void userRegister(String userData[]){
        //ContentValues se utiliza para mandar datos a las tablas
        //el metodo put guarda los valores en nuestra instancia de la clase ContentValues
        //el primer parametro del metodo put es el nombre de la columna y el segundo el dato que
        //queremos guardar
        SQLiteDatabase cultural = this.getReadableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("usuario",userData[0]);
        valores.put("nombre",userData[1]);
        valores.put("apellidos",userData[2]);
        valores.put("correo",userData[3]);
        valores.put("contra",userData[4]);
        //newUser(userData[0],userData[4]);
        cultural.insert("usuarios",null,valores);
        //despues utilizamos el metodo insert de nuestra instancia de la clase SQLiteDatabase
        //y ese metodo nos pide, el nombre de la tabla, el segundo es por si queremos que los datos
        // se agreguen sobre toda al fila de una columna en especifico, por ultimo los valores recolectados
        cultural.close();
    }

    /*La clase SQLiteDatabase es la que nos permite manejar todos los datos dentro del dispositivo, con la clase
    * podemos definir que es lo que vamos a hacer, como por ejemplo cuando usamos getReadableDatabase para obtener
    * una referencia de la base de datos
    *
    * Para Realizar una consulta utilizando el método rawQuery(), con la que obtiene un cursor que utiliza para
    * leer todas las filas devueltas en la consulta*/

    //El metodo noteRegister recibe un arreglo matricial que guarda los valores de una nota que fue registrada
    //ese arreglo solo guarda el titulo como primera posicion y como segunda posicion el contenido
    public void noteRegister(String noteData[][]){
        SQLiteDatabase cultural = this.getReadableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("titulo",noteData[0][0]);
        valores.put("contenido",noteData[0][1]);
        cultural.insert("notas",null,valores);
        cultural.close();
    }

    public String[][] showNote() {
        SQLiteDatabase cultural = this.getReadableDatabase();
        Cursor c = cultural.rawQuery("SELECT * FROM notas",null);
        c.moveToFirst();

        String[][] notas = new String[c.getCount()][2];
        int fi=0;
        int co=0;
        for(int i=0; i<c.getCount(); i++) {
            notas[fi][co] = c.getString(0);
            notas[fi][co+1] = c.getString(1);
            fi++;
            co=0;
            c.moveToNext();
        }
        return notas;
    }

    public void deleteNoteTrash() {
        SQLiteDatabase cultural = this.getReadableDatabase();
        cultural.delete("notas", "titulo=''",null);
    }

    public void deleteNote(String titulo) {
        SQLiteDatabase cultural = this.getReadableDatabase();
        String delete[] = titulo.split(": ");
        cultural.delete("notas", "titulo="+"'"+ delete[1] +"'",null);
    }


    boolean tf;
    public boolean newUser(String user, String contra) {
        SQLiteDatabase cultural = this.getReadableDatabase();
        //Todas los metodos fuera de onCreate piden crear una instancia para obtener todos los herramientas
        //disponibles de sqlite
        //cursor es para poder recorrer los datos de una tabla
        //el metodo rawQuery es el mas usado porque nos permite simplemente escribir las sintaxis de nuestra
        //sentencia

        Cursor c = cultural.rawQuery("SELECT usuario, contra FROM usuarios WHERE usuario='"+user+"' AND contra='"+contra+"'",null);
        c.moveToFirst();
        //Obtiene los valores con getString, el metodo pide indicar la columna que queremos recuperar
        //for(int o=0; o<=i; o++) {
        if (c.getCount()>0) {
            String u = c.getString(0);
            String con = c.getString(1);
            if (user.equals(u) && contra.equals((con))) {
                return tf = true;
            } else {
                return tf = false;
            }
        }else{
            return  tf = false;
        }
        //}
        //return  tf;
    }

    public boolean sesionState(){
        return tf;
    }

    public boolean existenciaRegistro(){
        SQLiteDatabase agendaescolar = this.getReadableDatabase();
        Cursor c = agendaescolar.rawQuery("SELECT * FROM usuarios",null);
        if (c.getCount()>0){
            return true;
        }else{
            return false;
        }
    }
}