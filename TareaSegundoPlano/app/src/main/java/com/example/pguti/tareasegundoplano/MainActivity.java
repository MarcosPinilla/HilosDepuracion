package com.example.pguti.tareasegundoplano;


import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private String resultado;
    private TextView textoResultado;

    private Button btnThread;
    private Button btnAsyncTask;
    private Button limpiar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textoResultado = (TextView) findViewById(R.id.textoResultado);

        btnThread = (Button) findViewById(R.id.btnThread);
        btnAsyncTask = (Button) findViewById(R.id.btnAsyncTask);
        limpiar = (Button) findViewById(R.id.btnLimpiar);

        btnThread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /////////////////////HILO SECUNDARIO///////////////////////////////////////////////////////
                new Thread(new Runnable() { //Instanciamos un nuevo objeto de tipo Thread
                    public void run() { //El constructor de la clase Thread recibe como parametro un nuevo obejeto de tipo Runnable
                        ///////////////////////////////////////////////////////////////////////////////////
                        runOnUiThread(new Runnable() {
                            public void run() {
                                if (rest()) textoResultado.setText("HILO SECUNDARIO\n" + resultado);
                                else textoResultado.setText("no funciona :C");
                            }
                        });
                        ///////////////////////////////////////////////////////////////////////////////////
                        if (rest()) textoResultado.post(new Runnable() {
                            @Override
                            public void run() {
                                textoResultado.setText("HILO SECUNDARIO\n" + resultado);
                            }
                        });
                        else textoResultado.post(new Runnable() {
                            @Override
                            public void run() {
                                textoResultado.setText("no funciona :C");
                            }
                        });//////////////////////////////////////////////////////////////////////////////
                    }
                }).start();  //Método de la clase Thread que comienza la ejecución en 2do plano.
                ///////////////////////////////////////////////////////////////////////////////////////////
            }
        });

        btnAsyncTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tareaAsincrona tarAs = new tareaAsincrona();
                tarAs.execute();
            }
        });

        limpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textoResultado.setText("Borrado");
            }
        });
    }

    private Boolean rest(){
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        HttpClient cliente = new DefaultHttpClient();
        HttpGet get = new HttpGet("http://indicadoresdeldia.cl/webservice/indicadores.json");
        get.setHeader("content-type", "application/json");

        try{
            HttpResponse respuesta = cliente.execute(get);
            this.resultado = EntityUtils.toString(respuesta.getEntity());
            JSONObject jobjeto = new JSONObject(resultado);
            return true;
        }catch (Exception e){
            Log.e("Error: ", e.getMessage());
            return false;
        }
    }

    public class tareaAsincrona extends AsyncTask<String, String, Boolean>{

        @Override
        protected Boolean doInBackground(String... params) {
            //Contendrá el código principal de nuestra tarea.
            return rest();
        }

        @Override
        protected void onProgressUpdate(String... values) {
            //Se ejecutará cada vez que llamemos al método publishProgress() desde el método doInBackground().
        }

        @Override
        protected void onPreExecute() {
            //Se ejecutará antes del código principal de nuestra tarea. Se suele utilizar para preparar la ejecución de la tarea, inicializar la interfaz, etc.
        }

        @Override
        protected void onPostExecute(Boolean result) {
            //Se ejecutará cuando finalice nuestra tarea, o dicho de otra forma, tras la finalización del método doInBackground().
            textoResultado.setText("HILO SECUNDARIO\n" + resultado);
        }

        @Override
        protected void onCancelled() {
            //Se ejecutará cuando se cancele la ejecución de la tarea antes de su finalización normal.
        }
    }
}
