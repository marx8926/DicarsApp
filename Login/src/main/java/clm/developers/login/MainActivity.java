package clm.developers.login;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends Activity {

    private ProgressDialog dialog;

   /* List<String> list=new ArrayList<String>();
    Spinner sp = null;
    ArrayAdapter<String> adp = null;
    */
    LinearLayout linear = null;

    private Intent intent;
    String user, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dialog = new ProgressDialog(this);
        dialog.setMessage("Ingresando...");
        dialog.setTitle("Progreso");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setCancelable(false);



        intent = new Intent(this,LugarActivity.class);

        /*
        list.add("Item 1");
        list.add("Item 2");
        list.add("Item 3");
        list.add("Item 4");
        list.add("Item 5");

        sp=new Spinner(MainActivity.this);
        adp= new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,list);
        adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        */

        linear = (LinearLayout) findViewById(R.id.linear);

        Dialog dialog = new Dialog(MainActivity.this);

        dialog.setTitle("Custom Dialog");
        dialog.setCancelable(true);


        //dialog.show();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void onLogin(View w)
    {
        EditText Usuario = (EditText)this.findViewById(R.id.loginEmail);
        EditText Pass = (EditText)this.findViewById(R.id.loginPassword);

        user = Usuario.getText().toString();
        pass = Pass.getText().toString();

        Log.d("user", user);
        Log.d("pass", pass);

        connect(user, pass);

    }


    public void connect(String username, String password)
    {
        class HttpGetAsyncTask extends AsyncTask<String, Void, String>{
            String user;
            String pass;

            @Override
            protected String doInBackground(String... params) {

                HttpClient httpClient = new DefaultHttpClient();

                //construir peticion get
                HttpGet httpGet = new HttpGet("http://192.168.0.12/Dicars/web/app_dev.php/api/login");

                user = params[0];
                pass = params[1];
                StringBuilder stringBuilder = new StringBuilder();


                httpGet.addHeader(BasicScheme.authenticate(
                        new UsernamePasswordCredentials(user, pass), "UTF-8", false));


                //ejecutar get y recibir respuesta
                HttpResponse response = null;
                Boolean error = false;
                try {
                    response = httpClient.execute(httpGet);

                    StatusLine statusLine = response.getStatusLine();
                    int statusCode = statusLine.getStatusCode();
                    if (statusCode == 200) {
                        HttpEntity entity = response.getEntity();
                        InputStream content = entity.getContent();
                        BufferedReader reader = new BufferedReader(
                                new InputStreamReader(content));
                        String line;
                        while ((line = reader.readLine()) != null) {
                            stringBuilder.append(line);
                        }
                        Log.d("resp","OK");

                    } else {
                        Log.e("JSON", "Failed to download file");
                    }
                } catch (ClientProtocolException e) {
                    error = true;
                    e.printStackTrace();
                } catch (IOException e) {
                    error = true;
                    e.printStackTrace();
                } finally{
                    //cerrar conexion htl y liberar recursos
                    httpClient.getConnectionManager().shutdown();
                }

                return stringBuilder.toString();
            }

            protected void onPreExecute()
            {
                dialog.setProgress(0);
                dialog.setMax(100);
                dialog.show();
            }

            protected void onPostExecute(String result) {

                Log.d("loginfin", "termino");
                Log.d("loginfin",result);

                dialog.dismiss();

                Toast.makeText(getBaseContext(),"Ingreso ",Toast.LENGTH_LONG).show();
                /*
                sp.setAdapteandroid.widget.Toast Toast;r(adp);
                linear.addView(sp);
                */

                intent.putExtra("user", user);
                intent.putExtra("pass", pass);

                startActivity(intent);

            }

        }
        // Initialize the AsyncTask class
        HttpGetAsyncTask httpGetAsyncTask = new HttpGetAsyncTask();
        // Parameter we pass in the execute() method is relate to the first generic type of the AsyncTask
        // We are passing the connectWithHttpGet() method arguments to that
        httpGetAsyncTask.execute(username, password);
    }
}
