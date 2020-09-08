package com.example.diseobanco;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);


        //Mejorar funcion del login y del registro
        findViewById(R.id.editSaldo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new ConsultarSaldo(111, view.getContext()).execute();
            }
        });

        findViewById(R.id.editTrans).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                findViewById(R.id.inicioLayout).setVisibility(View.GONE);
                findViewById(R.id.realizarTransaccion).setVisibility(View.VISIBLE);
                findViewById(R.id.volver).setVisibility(View.VISIBLE);
            }
        });

        findViewById(R.id.editMov).setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View view){
               findViewById(R.id.ultimos).setVisibility(View.VISIBLE);
               findViewById(R.id.inicioLayout).setVisibility(View.GONE);
               findViewById(R.id.volver).setVisibility(View.VISIBLE);

               new consultarMovimientos(view.getContext()).execute();
           }
        });
        findViewById(R.id.editDatos).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                findViewById(R.id.cambiarDatos).setVisibility(View.VISIBLE);
                findViewById(R.id.inicioLayout).setVisibility(View.GONE);
                findViewById(R.id.volver).setVisibility((View.VISIBLE));

                new cambiarDatos().execute();
            }
        });
        findViewById(R.id.btnVolver).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                findViewById(R.id.volver).setVisibility(View.GONE);
                findViewById(R.id.inicioLayout).setVisibility(View.VISIBLE);
                findViewById(R.id.realizarTransaccion).setVisibility(View.GONE);
                findViewById(R.id.ultimos).setVisibility(View.GONE);
                findViewById(R.id.cambiarDatos).setVisibility(View.GONE);
            }
        });

    }

    //Consulta de Saldo
    private class ConsultarSaldo extends AsyncTask<String, String, String> {

        private Integer doc;
        private Context context;

        private ConsultarSaldo(Integer doc, Context context) {
            this.doc = doc;
            this.context = context;
        }

        @Override
        protected String doInBackground(String... strings) {

            String result = null;

            try {
                 //result = RESTService.makeGetRequest("http://localhost:8080/bancogrupo2/rest/cuenta/documento/" + this.doc);
                    result = RESTService.makeGetRequest("http://localhost:8080/bancogrupo2/rest/cuenta/documento/1111");
            } catch (Exception e) {
                Log.d("INFO", e.toString());
            }

            return result;
        }

        @Override
        protected void onPostExecute(String result) {

            if (result != null) {

                try {
                    JSONObject cuenta = new JSONObject(result);


                    String saldo = cuenta.getString("id");

                    Toast toastSaldo = Toast.makeText(context, "Su saldo actual es:  $" + saldo, Toast.LENGTH_LONG);
                    toastSaldo.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                    toastSaldo.show();

                }catch (JSONException e){
                    Log.d("INFO",e.toString());
                }
            }
        }
    }


    //Consulta de Movimientos
    private class consultarMovimientos extends  AsyncTask<String,String,String>{
        private Context context;

        private consultarMovimientos(Context context){
            this.context = context;
        }

        @Override
        protected String doInBackground(String... strings) {

            String result = null;

            try {
                result = RESTService.makeGetRequest("http://localhost:8080/bancogrupo2/rest/movimientos/cuenta/1");
            } catch (Exception e) {
                Log.d("INFO", e.toString());
            }
            Log.d("INFO", result.toString());
            return result;
        }

        @Override
        protected void onPostExecute(String result){

            if (result != null){

                JSONArray movimientos;
                JSONObject movimiento = new JSONObject();

                try{
                    movimientos = new JSONArray(result);
                }catch (JSONException e){
                    Log.d("INFO", e.toString());
                    return;
                }
                Log.d("INFO", movimientos.toString());

                TextView respuesta;

                //Fila 1

                respuesta = findViewById(R.id.idOrigen1);
                try{
                    movimiento = movimientos.getJSONObject(0);
                }catch (JSONException e) {Log.d("INFO", e.toString());}

                try {
                    respuesta.setText(String.valueOf(movimiento.getInt("idCuentaOrigen")));

                } catch(JSONException e) {Log.d("INFO", e.toString());}

                respuesta = findViewById(R.id.idDestino1);
                try {
                    respuesta.setText(String.valueOf(movimiento.getInt("idCuentaDestino")));
                } catch(JSONException e) {Log.d("INFO", e.toString());}

                respuesta = findViewById(R.id.idMonto1);
                try{
                    respuesta.setText(String.valueOf(movimiento.getInt("monto")));
                }catch(JSONException e) {Log.d("INFO", e.toString());}

                //Fila 2

                respuesta = findViewById(R.id.idOrigen1);
                try{
                    movimiento = movimientos.getJSONObject(1);
                }catch (JSONException e) {Log.d("INFO", e.toString());}

                try {
                    respuesta.setText(String.valueOf(movimiento.getInt("idCuentaOrigen")));

                } catch(JSONException e) {Log.d("INFO", e.toString());}

                respuesta = findViewById(R.id.idDestino2);
                try {
                    respuesta.setText(String.valueOf(movimiento.getInt("idCuentaDestino")));
                } catch(JSONException e) {Log.d("INFO", e.toString());}

                respuesta = findViewById(R.id.idMonto2);
                try{
                    respuesta.setText(String.valueOf(movimiento.getInt("monto")));
                }catch(JSONException e) {Log.d("INFO", e.toString());}



                //Fila 3

                respuesta = findViewById(R.id.idOrigen3);
                try{
                    movimiento = movimientos.getJSONObject(2);
                }catch (JSONException e) {Log.d("INFO", e.toString());}

                try {
                    respuesta.setText(String.valueOf(movimiento.getInt("idCuentaOrigen")));

                } catch(JSONException e) {Log.d("INFO", e.toString());}

                respuesta = findViewById(R.id.idDestino3);
                try {
                    respuesta.setText(String.valueOf(movimiento.getInt("idCuentaDestino")));
                } catch(JSONException e) {Log.d("INFO", e.toString());}

                respuesta = findViewById(R.id.idMonto3);
                try{
                    respuesta.setText(String.valueOf(movimiento.getInt("monto")));
                }catch(JSONException e) {Log.d("INFO", e.toString());}



                //Fila 4

                respuesta = findViewById(R.id.idOrigen4);
                try{
                    movimiento = movimientos.getJSONObject(3);
                }catch (JSONException e) {Log.d("INFO", e.toString());}

                try {
                    respuesta.setText(String.valueOf(movimiento.getInt("idCuentaOrigen")));

                } catch(JSONException e) {Log.d("INFO", e.toString());}

                respuesta = findViewById(R.id.idDestino4);
                try {
                    respuesta.setText(String.valueOf(movimiento.getInt("idCuentaDestino")));
                } catch(JSONException e) {Log.d("INFO", e.toString());}

                respuesta = findViewById(R.id.idMonto4);
                try{
                    respuesta.setText(String.valueOf(movimiento.getInt("monto")));
                }catch(JSONException e) {Log.d("INFO", e.toString());}


            }
        }
    }

    private class transaccion extends AsyncTask<String, String,String>{



        EditText origen = findViewById(R.id.cuentaOrigen);
        EditText destino = findViewById(R.id.cuentaDestino);
        EditText monto = findViewById(R.id.importe);

        String cuentaOrigen = origen.getText().toString();
        String cuentaDestino = destino.getText().toString();
        String importe = monto.getText().toString();

        /*
        private Integer doc;
        private Context context;
        private transaccion(Integer doc, Context context){
            this.doc = doc;
            this.context = context;
        }
        */

        @Override
        protected String doInBackground(String... strings) {
            String result = null;

            try{

                JSONObject transferencia = new JSONObject();
                transferencia.put("cuentaOrigen",cuentaOrigen);
                transferencia.put("cuentaDestino",cuentaDestino);
                transferencia.put("importe",importe);
                transferencia.put("fechaInicio","");
                transferencia.put("fechaFin", "");
                transferencia.put("estado", "Realizado");

                result = RESTService.callREST("http://localhost:8080/bancogrupo2/rest/movimientos/realizar/","POST",transferencia);

            }catch (JSONException e){Log.d("INFO", e.toString());}

            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            Toast notificacion = Toast.makeText(getApplicationContext(), "Transferencia completa", Toast.LENGTH_SHORT);
            notificacion.show();
        }
    }


    private class cambiarDatos extends AsyncTask<String, String,String>{



        EditText nombre = findViewById(R.id.datosNombre);
        EditText lastname = findViewById(R.id.idApellido);
        EditText direccion = findViewById(R.id.datosDireccion);
        EditText dni = findViewById(R.id.datosDNI);
        EditText fechaDeNacimiento = findViewById(R.id.fechaDeNacimiento);
        EditText idOrigen = findViewById(R.id.idCuentaOrigen);
        EditText psw = findViewById(R.id.idPassword);


        String name = nombre.getText().toString();
        String apellido = lastname.getText().toString();
        String localidad = direccion.getText().toString();
        String documento = dni.getText().toString();
        String fechaNacimiento = fechaDeNacimiento.getText().toString();
        String idCuenta = idOrigen.getText().toString();
        String password = psw.getText().toString();

        /*
        private Integer doc;
        private Context context;
        private transaccion(Integer doc, Context context){
            this.doc = doc;
            this.context = context;
        }
        */

        @Override
        protected String doInBackground(String... strings) {
            String result = null;

            try{

                JSONObject datosClientes = new JSONObject();
                datosClientes.put("nombre",name);
                datosClientes.put("apellido",apellido);
                datosClientes.put("id_cliente",idCuenta);
                datosClientes.put("password",password);
                datosClientes.put("documento",documento);
                datosClientes.put("direccion",localidad );
                datosClientes.put("fecha_de_nacimiento", fechaNacimiento);

                result = RESTService.callREST("http://localhost:8080/bancogrupo2/rest/clientes/","PUT",datosClientes);

            }catch (JSONException e){Log.d("INFO", e.toString());}

            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            Toast notificacion = Toast.makeText(getApplicationContext(), "Datos Actualizados", Toast.LENGTH_SHORT);
            notificacion.show();
        }
    }




}


