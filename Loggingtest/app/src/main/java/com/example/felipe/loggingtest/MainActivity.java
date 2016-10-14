package com.example.felipe.loggingtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private final String LOGTAG = "Logging test";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    /**
     * Fecha/Hora del mensaje.
     * Criticidad: Nivel de gravedad del mensaje
     * PID: Código interno del proceso que ha introducido el mensaje
     * Tag: Etiqueta identificativa del mensaje
     * Mensaje: El texto completo del mensaje
     */

    public void probarLog(View view){
        Log.e(LOGTAG, "Mensaje de error"); //ERROR
        Log.w(LOGTAG, "Mensaje de advertencia"); //WARNING
        Log.i(LOGTAG, "Mensaje de información"); //INFO
        Log.d(LOGTAG, "Mensaje de depuración"); //DEBUG
        Log.v(LOGTAG, "Mensaje de verbose"); //VERBOSE


        try {
            int a = 1/0;
        }
        catch(Exception ex) {
            Log.e(LOGTAG, "División por cero!", ex);
        }

    }
}
