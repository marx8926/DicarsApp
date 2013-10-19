package clm.services.library;

/**
 * Created by marks on 10/19/13.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

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

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;


public class JSONAct{

    public JSONArray jsonArray;
    public String resultado;
    private ProgressDialog dialog;
    private String user, pass;
    public  boolean flag;


    public String readJSONFeed(String URL) {
        StringBuilder stringBuilder = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(URL);

        httpGet.addHeader(BasicScheme.authenticate(
                new UsernamePasswordCredentials(user, pass), "UTF-8", false));
        try {
            HttpResponse response = client.execute(httpGet);
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
                Log.d("place", "ok");
            } else {
                Log.e("JSON", "Failed to download file");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    public void build(String URL,ProgressDialog dia,boolean flag, String user, String pass)
    {

        this.user = user;
        this.pass = pass;

        this.flag=flag;

        if(this.flag)
            this.dialog = dia;

        new ReadJSONFeedTask().execute(URL);
    }

    public class ReadJSONFeedTask extends AsyncTask<String, Void, String> {


        protected String doInBackground(String... urls) {
            return resultado = readJSONFeed(urls[0]);
        }
        protected void onPreExecute()
        {
            if(flag)
            {
                dialog.setProgress(0);
                dialog.setMax(100);
                dialog.show();
            }
        }
        protected void onPostExecute(String result) {

            Log.d("postJs", "json");
            Log.d("resultado", result);

            if(result!=null)
            {

                try {
                    jsonArray = new JSONArray(result);
                    Log.i("JSON", "Number of surveys in feed: " +
                            jsonArray.length());


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
            if(flag)
                dialog.dismiss();

        }
    }
}
/** Called when the activity is first created. */
