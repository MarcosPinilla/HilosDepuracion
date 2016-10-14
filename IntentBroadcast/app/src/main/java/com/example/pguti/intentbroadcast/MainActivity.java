package com.example.pguti.intentbroadcast;

import android.os.Bundle;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends Activity {

    private Button btnEjecutar;
    private ProgressBar pbarProgreso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnEjecutar = (Button)findViewById(R.id.btnEjecutar);
        pbarProgreso = (ProgressBar)findViewById(R.id.pbarProgreso);

        btnEjecutar.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                //llamamos a la funcion en segundo plano
                Intent msgIntent = new Intent(MainActivity.this, MiIntentService.class);
                //asignamos a las interaciones un numero
                msgIntent.putExtra("iteraciones", 10);
                //ejecutamos la funcion en segundo plano
                startService(msgIntent);
            }
        });
        //registramos las acciones que usaremos
        IntentFilter filter = new IntentFilter();
        filter.addAction(MiIntentService.ACTION_PROGRESO);
        filter.addAction(MiIntentService.ACTION_FIN);
        //le asigamos al ProgressReceiver los tipos de mensajes que recibira
        ProgressReceiver rcv = new ProgressReceiver();
        registerReceiver(rcv, filter);
    }
    //recibe los datos enviados por broadcast de sus respectivas acciones


    public class ProgressReceiver extends BroadcastReceiver {



        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(MiIntentService.ACTION_PROGRESO)) {
                //recibe los datos del extra progreso en el MiIntentService
                int prog = intent.getIntExtra("progreso", 0);
                //se los asigna al layout
                pbarProgreso.setProgress(prog);
            }
            else if(intent.getAction().equals(MiIntentService.ACTION_FIN)) {
                Toast.makeText(MainActivity.this, "Tarea finalizada!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}