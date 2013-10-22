package clm.developers.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class LugarActivity extends Activity {

    private ProgressDialog dialog;
    private String user;
    private String pass;
    private Intent intent;
    public JSONArray jsonArray;
    private ListView lview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lugar);


        dialog = new ProgressDialog(this);
        dialog.setMessage("Enviando..");
        dialog.setTitle("Solicitud");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setCancelable(false);

        lview = (ListView)findViewById(R.id.listLugares);


        Intent i = this.getIntent();
        user = i.getStringExtra("user");
        pass = i.getStringExtra("pass");

        intent = new Intent(this,MenuVentaCobranzaActivity.class);
        intent.putExtra("user",user);
        intent.putExtra("pass", pass);

        lview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // ListView Clicked item index
                int itemPosition     = position;

                // ListView Clicked item value
                String  itemValue    = (String) lview.getItemAtPosition(position);
                intent.putExtra("tienda",itemValue);

                startActivity(intent);

            }
        });

        build("http://192.168.0.91/Dicars/web/app_dev.php/api/locales", user, pass);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.lugar, menu);
        return true;
    }


    public void build(String url, String username, String password)
    {


        class HttpGetAsyncTask extends AsyncTask<String, Void, String> {
            String user;
            String pass;
            String url;

            @Override
            protected String doInBackground(String... params) {

                HttpClient httpClient = new DefaultHttpClient();

                user = params[0];
                pass = params[1];
                url = params[2];

                //construir peticion get
                HttpGet httpGet = new HttpGet(url);
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
                        Log.d("resp", "OK");

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

                if(result!=null)
                {

                    try {
                        jsonArray = new JSONArray(result);
                        Log.i("JSON", "Number of surveys in feed: " +
                                jsonArray.length());

                        loadlist(jsonArray);

                        //---print out the content of the json feed---
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);

    				/*
    				Toast.makeText(getBaseContext(), jsonObject.getString("appeId") +
    						" - " + jsonObject.getString("inputTime"),
    						Toast.LENGTH_SHORT).show();
    				*/

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else
                {
                    jsonArray = new JSONArray();
                    Log.i("JSON", "Number of elements: " + jsonArray.length());
                }
                dialog.dismiss();

            }

        }
        // Initialize the AsyncTask class
        HttpGetAsyncTask httpGetAsyncTask = new HttpGetAsyncTask();
        // Parameter we pass in the execute() method is relate to the first generic type of the AsyncTask
        // We are passing the connectWithHttpGet() method arguments to that
        httpGetAsyncTask.execute(username, password, url);

    }

    public void loadlist(JSONArray jsonArray)
    {
        List<String> list = new ArrayList<String>();

        try{
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String element = jsonObject.getString("cLocalDesc");
                list.add(element);

            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_single_choice, list);
            lview.setAdapter(adapter);



        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }

}
