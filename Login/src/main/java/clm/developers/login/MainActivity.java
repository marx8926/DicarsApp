package clm.developers.login;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.os.AsyncTask;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dialog = new ProgressDialog(this);
        dialog.setMessage("Ingresando...");
        dialog.setTitle("Progreso");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setCancelable(false);
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

        String user = Usuario.getText().toString();
        String pass = Pass.getText().toString();

        Log.d("user", user);
        Log.d("pass", pass);

        connect(user, pass);

    }


    public void connect(String username, String password)
    {
        class HttpGetAsyncTask extends AsyncTask<String, Void, String>{

            @Override
            protected String doInBackground(String... params) {

                HttpClient httpClient = new DefaultHttpClient();

                //construir peticion get
                HttpGet httpGet = new HttpGet("http://192.168.1.34/Dicars/web/app_dev.php/api/login");

                String user = params[0];
                String pass = params[1];
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

            }

        }
        // Initialize the AsyncTask class
        HttpGetAsyncTask httpGetAsyncTask = new HttpGetAsyncTask();
        // Parameter we pass in the execute() method is relate to the first generic type of the AsyncTask
        // We are passing the connectWithHttpGet() method arguments to that
        httpGetAsyncTask.execute(username, password);
    }
}
