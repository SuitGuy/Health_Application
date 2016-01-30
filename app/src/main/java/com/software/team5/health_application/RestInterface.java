package com.software.team5.health_application;



import android.os.AsyncTask;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by Paul on 30/01/2016.
 */
public class RestInterface extends AsyncTask<Void, Void, String>{
    private String baseUrl;
    private String GetURL;

    public RestInterface(String baseUrl){
        this.baseUrl = baseUrl;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }

    @Override
    protected String doInBackground(Void... urls){
        HttpResponse response = null;
        try {
            // Create http client object to send request to server
            HttpClient client = new DefaultHttpClient();
            // Create URL string
            String URL = baseUrl;
            // Create Request to server and get response
            HttpGet httpget= new HttpGet();
            httpget.setURI(new URI(URL));
            response = client.execute(httpget);
            Toast.makeText(null, response.toString(), Toast.LENGTH_SHORT).show();

            return response.toString();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
        } catch (IOException e) {
            // TODO Auto-generated catch block
        }
        return "fail";
    }

}
