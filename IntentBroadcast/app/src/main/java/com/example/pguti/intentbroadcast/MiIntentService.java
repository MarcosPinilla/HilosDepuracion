package com.example.pguti.intentbroadcast;

/**
 * Created by pguti on 05-10-2016.
 */
import android.app.IntentService;
import android.content.Intent;



public class MiIntentService extends IntentService {


    //nombre de la accion broadcast
    //los usamos para comunicarnos con el primer hilo
    public static final String ACTION_PROGRESO = "PROGRESO";
    public static final String ACTION_FIN = "FIN";

    //constructor de la clase
    public MiIntentService() {
        super("MiIntentService");
    }

    //metodo donde se realizara todas las acciones en segundo plano
    @Override
    protected void onHandleIntent(Intent intent)
    {
        //obtenemos los datos del extra que obtuvimos en el MainActivity
        int iter = intent.getIntExtra("iteraciones", 0);

        for(int i=1; i<=iter; i++) {
            //se ejecuta entre cierto intervalo de tiempo
            tareaLarga();
            //instanciamos la clase intent
            Intent bcIntent = new Intent();
            //definimos la accion que ocuparemos
            bcIntent.setAction(ACTION_PROGRESO);
            //creamos un extra
            bcIntent.putExtra("progreso", i*10);
            //enviamos por broadcast
            sendBroadcast(bcIntent);
        }

        Intent bcIntent = new Intent();
        bcIntent.setAction(ACTION_FIN);
        sendBroadcast(bcIntent);
    }

    private void tareaLarga()
    {
        try {
            Thread.sleep(1000);
        } catch(InterruptedException e) {}
    }
}